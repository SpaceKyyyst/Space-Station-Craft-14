
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block; // ← ДОБАВЛЕН ОТСУТСТВУЮЩИЙ ИМПОРТ
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.ProblemReporter;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class FireAxe_UseProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
        // === БАЗОВЫЕ ПРОВЕРКИ ===
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;
        
        ItemStack mainHand = livingEntity.getMainHandItem();
        if (!mainHand.is(Ssc14ModItems.FIRE_AXE.get())) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        double posHash = entity.getX() + entity.getY() + entity.getZ();
        Block targetBlock = blockstate.getBlock();
        int originalBlockstateValue = getPropertyInt(blockstate, "blockstate");

        // Вычисляем направление взгляда ОДИН раз
        Direction hitDir = entity.level().clip(new ClipContext(
            entity.getEyePosition(1f), 
            entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(6)),
            ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity
        )).getDirection();

        // === КОНФИГУРАЦИЯ (Java 21 record) ===
        // drop теперь типа Block, а не Item (совместимо с Ssc14ModBlocks.TITLE_STEEL.get())
        record AxeConfig(int[] delays, Block target, Block result, Block drop, double dropY, Direction requiredDir) {}
        final AxeConfig config;

        if (targetBlock == Ssc14ModBlocks.SHEATHING.get()) {
            config = new AxeConfig(new int[]{10,10,10,10,10,10}, Ssc14ModBlocks.SHEATHING.get(), Ssc14ModBlocks.ROD_FLOOR.get(), Ssc14ModBlocks.TITLE_STEEL.get(), y + 1.0, Direction.UP);
        } else if (targetBlock == Ssc14ModBlocks.UPER_SHEATHING.get()) {
            config = new AxeConfig(new int[]{10,10,10,10,10,10}, Ssc14ModBlocks.UPER_SHEATHING.get(), Ssc14ModBlocks.ROD_UP_FLOOR.get(), Ssc14ModBlocks.TITLE_STEEL.get(), y + 0.5, Direction.DOWN);
        } else {
            return;
        }

        if (hitDir != config.requiredDir()) return;

        // === ЛОКАЛЬНЫЙ КЛАСС ПРОЦЕССА (внутри execute, БЕЗ static!) ===
        class AxeProcess {
            void run(int step) {
                // 1. Отмена при движении
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                // 2. Отмена при смене предмета (используем livingEntity вместо entity!)
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.FIRE_AXE.get())) { reset(); return; }
                // 3. Отмена при изменении блока
                if (world.getBlockState(pos).getBlock() != config.target()) { reset(); return; }

                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < 6) {
                    Ssc14Mod.queueServerWork(config.delays()[step - 1], () -> run(step + 1));
                } else {
                    Ssc14Mod.queueServerWork(config.delays()[5], this::executeFinal);
                }
            }

            private void executeFinal() {
                // Финальные проверки
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.FIRE_AXE.get())) { reset(); return; }
                if (world.getBlockState(pos).getBlock() != config.target()) { reset(); return; }

                BlockState resultState = config.result().defaultBlockState();
                
                // Устанавливаем свойство "blockstate" из исходного блока
                if (resultState.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty ip) {
                    resultState = resultState.setValue(ip, originalBlockstateValue);
                }
                
                // Копируем остальные свойства старого блока
                resultState = copyProperties(world.getBlockState(pos), resultState);

                // Сохраняем BlockEntity (NBT)
                BlockEntity be = world.getBlockEntity(pos);
                CompoundTag nbt = null;
                if (be != null) {
                    nbt = be.saveWithFullMetadata(world.registryAccess());
                    be.setRemoved();
                }

                world.setBlock(pos, resultState, 3);

                // Восстанавливаем NBT
                if (nbt != null) {
                    be = world.getBlockEntity(pos);
                    if (be != null) {
                        try {
                            be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), nbt));
                        } catch (Exception ignored) {}
                    }
                }

                // Дроп и звук
                if (config.drop() != null && world instanceof ServerLevel sLevel) {
                    ItemEntity drop = new ItemEntity(sLevel, pos.getX() + 0.1, config.dropY(), pos.getZ() + 0.1, new ItemStack(config.drop()));
                    drop.setPickUpDelay(1);
                    drop.setUnlimitedLifetime();
                    sLevel.addFreshEntity(drop);
                }
                playSound(world, pos, "ssc_14:title_off", 1.0F, 1.0F);
                reset();
            }

            void reset() {
                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
            }
        }

        new AxeProcess().run(1);
    }

    // ============================================================================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    // ============================================================================

    private static int getPropertyInt(BlockState state, String name) {
        for (Property<?> prop : state.getProperties()) {
            if (prop.getName().equals(name) && prop instanceof IntegerProperty ip) return state.getValue(ip);
        }
        return -1;
    }

    /** Исправлена проблема с дженериками Property<?> */
    private static BlockState copyProperties(BlockState from, BlockState toBase) {
        BlockState result = toBase;
        for (Property<?> oldProp : from.getProperties()) {
            Property<?> newProp = result.getBlock().getStateDefinition().getProperty(oldProp.getName());
            if (newProp != null) {
                try {
                    // Приведение к сырому типу Property решает конфликт дженериков в Minecraft
                    result = result.setValue((Property) newProp, from.getValue((Property) oldProp));
                } catch (Exception ignored) {}
            }
        }
        return result;
    }

    private static void playSound(LevelAccessor world, BlockPos pos, String soundId, float volume, float pitch) {
        if (world instanceof Level lvl) {
            var sound = BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse(soundId));
            if (sound != null) {
                if (!lvl.isClientSide()) lvl.playSound(null, pos, sound, SoundSource.NEUTRAL, volume, pitch);
                else lvl.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.NEUTRAL, volume, pitch, false);
            }
        }
    }
}
