
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block; // ← ДОБАВЛЕН ПРОПУЩЕННЫЙ ИМПОРТ
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Scrap_ProcedureProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
        // === БАЗОВЫЕ ПРОВЕРКИ ===
        if (entity == null) return;
        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        double posHash = entity.getX() + entity.getY() + entity.getZ();
        ItemStack mainHand = livingEntity.getMainHandItem();
        
        // Проверка предмета в руке
        if (!mainHand.is(Ssc14ModItems.CROWBAR.get())) return;

        // === КОНФИГУРАЦИЯ ДЕМОНТАЖА (Java 21 record) ===
        record BreakConfig(int delay, boolean dropItem, boolean changeState, int newState, IntegerProperty stateProp, boolean checkDirection) {}
        final BreakConfig config;
        Block targetBlock = blockstate.getBlock();

        // Определение типа блока и настроек
        if (blockstate.is(BlockTags.create(ResourceLocation.parse("ssc14:tiles")))) {
            Direction hitSide = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getDirection();
            config = (hitSide == Direction.UP) ? new BreakConfig(1, true, false, -1, null, true) : null;
            if (config == null) return;
            
        } else if (blockstate.is(BlockTags.create(ResourceLocation.parse("ssc14:tiles_up")))) {
            config = new BreakConfig(33, true, false, -1, null, false);
            
        } else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
            IntegerProperty prop = findIntegerProperty(blockstate, "blockstate");
            if (prop == null) return;
            int state = blockstate.getValue(prop);
            if (state == 3) config = new BreakConfig(3, false, true, 4, prop, false);
            else if (state == 6) config = new BreakConfig(3, false, true, 7, prop, false);
            else return;
            
        } else if (targetBlock == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
            IntegerProperty prop = findIntegerProperty(blockstate, "blockstate");
            if (prop == null) return;
            int state = blockstate.getValue(prop);
            if (state == 2) config = new BreakConfig(3, false, true, 3, prop, false);
            else return;
            
        } else return; // Блок не подходит для лома

        // === ЛОКАЛЬНЫЙ КЛАСС ПРОЦЕССА (не static, чтобы видеть config и переменные execute) ===
        class BreakProcess {
            void run(int step) {
                // 1. Отмена при движении
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                // 2. Отмена при смене предмета
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.CROWBAR.get())) { reset(); return; }
                // 3. Отмена при изменении/удалении блока
                if (!world.getBlockState(pos).is(targetBlock)) { reset(); return; }

                // Проверка направления (только на 1 шаге)
                if (config.checkDirection() && step == 1) {
                    Direction hitSide = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getDirection();
                    if (hitSide != Direction.UP) { reset(); return; }
                }

                // Обновление прогресс-бара
                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < 6) {
                    // Планируем следующий шаг
                    Ssc14Mod.queueServerWork(config.delay(), () -> run(step + 1));
                } else {
                    // === ФИНАЛЬНЫЙ ШАГ ===
                    if (config.dropItem()) {
                        if (world instanceof ServerLevel sLevel) {
                            ItemEntity drop = new ItemEntity(sLevel, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(blockstate.getBlock()));
                            drop.setPickUpDelay(1);
                            drop.setUnlimitedLifetime();
                            sLevel.addFreshEntity(drop);
                        }
                        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    } else if (config.changeState() && config.stateProp() != null) {
                        world.setBlock(pos, blockstate.setValue(config.stateProp(), config.newState()), 3);
                    }
                    playSound(world, pos, "ssc_14:title_off", 1.0F, 1.0F);
                    reset();
                }
            }
            void reset() {
                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
            }
        }
        
        // Запуск процесса
        new BreakProcess().run(1);
    }

    // ============================================================================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ (внутри класса, но вне execute)
    // ============================================================================

    private static IntegerProperty findIntegerProperty(BlockState state, String name) {
        for (Property<?> prop : state.getProperties()) {
            if (prop.getName().equals(name) && prop instanceof IntegerProperty ip) return ip;
        }
        return null;
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
