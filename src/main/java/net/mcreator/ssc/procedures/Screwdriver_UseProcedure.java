
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block; // ← ВАЖНО: добавлен импорт
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.ProblemReporter;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Screwdriver_UseProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
        // === БАЗОВЫЕ ПРОВЕРКИ ===
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;
        
        ItemStack mainHand = livingEntity.getMainHandItem();
        if (!mainHand.is(Ssc14ModItems.SCREWDRIVER.get())) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        double posHash = entity.getX() + entity.getY() + entity.getZ();
        Block targetBlock = blockstate.getBlock();

        // Поиск свойств ОДИН раз для оптимизации
        IntegerProperty ip_blockstate = findIntProp(blockstate, "blockstate");
        IntegerProperty ip_window = findIntProp(blockstate, "window_disassembly");
        BooleanProperty bp_panel = findBoolProp(blockstate, "panel_open");
        BooleanProperty bp_ready = findBoolProp(blockstate, "ready");
        int bsVal = ip_blockstate != null ? blockstate.getValue(ip_blockstate) : -1;

        // === КОНФИГУРАЦИЯ (Java 21 record) ===
        record ScrewConfig(int[] delays, Block target, IntegerProperty intProp, int newIntVal, 
                           BooleanProperty boolProp, boolean newBoolVal, boolean updateNeighbors, Runnable finalAction) {}
        final ScrewConfig config;

        // === ОПРЕДЕЛЕНИЕ НАСТРОЕК ПО БЛОКУ ===
        if (targetBlock == Ssc14ModBlocks.BASE_WINDOW.get() && ip_window != null && blockstate.getValue(ip_window) == 0) {
            config = new ScrewConfig(new int[]{4,4,4,4,4,4}, targetBlock, null, 0, null, false, false, () -> {
                BlockState bs = world.getBlockState(pos);
                if (bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty p1) bs = bs.setValue(p1, 1);
                if (bs.getBlock().getStateDefinition().getProperty("window_disassembly") instanceof IntegerProperty p2) bs = bs.setValue(p2, 1);
                world.setBlock(pos, bs, 3);
            });
        }
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get() && bsVal == 1) {
            config = new ScrewConfig(new int[]{3,3,3,3,3,3}, targetBlock, ip_blockstate, 2, null, false, false, null);
        }
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get() && bsVal == 7) {
            config = new ScrewConfig(new int[]{2,1,2,1,2,1}, targetBlock, ip_blockstate, 8, null, false, false, null);
        }
        else if (blockstate.is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks")))) {
            boolean isOpen = bp_panel != null && blockstate.getValue(bp_panel);
            boolean newVal = !isOpen;
            config = new ScrewConfig(new int[]{3,3,3,3,3,3}, targetBlock, null, 0, bp_panel, newVal, true, () -> {
                BlockState bs = world.getBlockState(pos);
                if (bs.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty p) {
                    world.setBlock(pos, bs.setValue(p, newVal), 3);
                }
                // Вызов внешней процедуры только при открытии панели
                if (newVal) {
                    try { BaseAirlockD1PutProcedure.execute(world, x, y, z, bs); } catch(Exception ignored) {}
                }
            });
        }
        else if (targetBlock == Ssc14ModBlocks.ARMORED_WINDOW.get() && bsVal == 1) {
            config = new ScrewConfig(new int[]{2,1,2,1,2,1}, targetBlock, ip_blockstate, 2, null, false, false, null);
        }
        else if (targetBlock == Ssc14ModBlocks.ARMORED_WINDOW.get() && bsVal == 4) {
            config = new ScrewConfig(new int[]{3,3,3,3,3,3}, targetBlock, ip_blockstate, 5, null, false, false, null);
        }
        else if (targetBlock == Ssc14ModBlocks.MACHINE_FRAME_2.get() && bp_ready != null && blockstate.getValue(bp_ready)) {
            config = new ScrewConfig(new int[]{4,4,4,4,4,4}, targetBlock, null, 0, null, false, false, () -> handleMachineFrameFinal(world, pos));
        }
        else if (targetBlock == Ssc14ModBlocks.PODSTATION.get() || targetBlock == Ssc14ModBlocks.SMES.get()) {
            config = new ScrewConfig(new int[]{6,6,6,6,6,6}, targetBlock, null, 0, null, false, false, () -> handleStationFinal(world, pos, targetBlock));
        }
        else { return; } // Блок не подходит

        // === ЛОКАЛЬНЫЙ КЛАСС ПРОЦЕССА (внутри execute, БЕЗ static!) ===
        class ScrewProcess {
            void run(int step) {
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.SCREWDRIVER.get())) { reset(); return; }
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
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.SCREWDRIVER.get())) { reset(); return; }
                if (world.getBlockState(pos).getBlock() != config.target()) { reset(); return; }

                // Выполняем действие
                if (config.finalAction() != null) {
                    config.finalAction().run();
                } else if (config.intProp() != null) {
                    BlockState bs = world.getBlockState(pos);
                    if (bs.getBlock().getStateDefinition().getProperty(config.intProp().getName()) instanceof IntegerProperty p) {
                        world.setBlock(pos, bs.setValue(p, config.newIntVal()), 3);
                    }
                } else if (config.boolProp() != null) {
                    BlockState bs = world.getBlockState(pos);
                    if (bs.getBlock().getStateDefinition().getProperty(config.boolProp().getName()) instanceof BooleanProperty p) {
                        world.setBlock(pos, bs.setValue(p, config.newBoolVal()), 3);
                    }
                }

                // Обновление соседей (для шлюзов)
                if (config.updateNeighbors() && world instanceof Level lvl) {
                    lvl.updateNeighborsAt(pos, lvl.getBlockState(pos).getBlock());
                }

                playSound(world, pos, "ssc_14:screwdriver", 1.0F, 1.0F);
                reset();
            }

            void reset() { livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0); }
        }

        new ScrewProcess().run(1);
    }

    // ============================================================================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ (вне execute)
    // ============================================================================

    private static IntegerProperty findIntProp(BlockState state, String name) {
        for (Property<?> p : state.getProperties()) {
            if (p.getName().equals(name) && p instanceof IntegerProperty ip) return ip;
        } return null;
    }

    private static BooleanProperty findBoolProp(BlockState state, String name) {
        for (Property<?> p : state.getProperties()) {
            if (p.getName().equals(name) && p instanceof BooleanProperty bp) return bp;
        } return null;
    }

    /** Исправлена проблема с дженериками Property<?> через безопасный raw-cast */
    private static BlockState copyProperties(BlockState from, BlockState toBase) {
        BlockState result = toBase;
        for (Property<?> oldProp : from.getProperties()) {
            Property<?> newProp = result.getBlock().getStateDefinition().getProperty(oldProp.getName());
            if (newProp != null) {
                try { result = result.setValue((Property) newProp, from.getValue((Property) oldProp)); } catch (Exception ignored) {}
            }
        } return result;
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

    /** Логика финального шага для MACHINE_FRAME_2 */
    private static void handleMachineFrameFinal(LevelAccessor world, BlockPos pos) {
        IItemHandler handler = null;
        if (world instanceof ILevelExtension ext) handler = ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
        if (handler == null) return;

        ItemStack slot0 = handler.getStackInSlot(0);
        if (slot0.isEmpty()) return;

        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) return;
        // ✅ ИСПРАВЛЕНО: getDouble() → getDoubleOr() для совместимости с 1.21
        int plugCount = (int) be.getPersistentData().getDoubleOr("plug", 0.0);

        // Очищаем слоты и обновляем NBT (оптимизировано без повторных запросов capability)
        if (world instanceof ILevelExtension ext && ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) instanceof IItemHandlerModifiable mod) {
            for (int i = 0; i < 9; i++) {
                mod.setStackInSlot(i, ItemStack.EMPTY);
                plugCount++;
            }
        }
        be.getPersistentData().putDouble("plug", plugCount);
        if (world instanceof Level lvl) lvl.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

        // Определяем блок для замены
        Block replace = null;
        if (slot0.getItem() == Ssc14ModItems.SUBSTATION_BOARD.get()) replace = Ssc14ModBlocks.PODSTATION.get();
        else if (slot0.getItem() == Ssc14ModItems.SMES_BOARD.get()) replace = Ssc14ModBlocks.SMES.get();

        if (replace != null) {
            BlockState bs = replace.defaultBlockState();
            bs = copyProperties(world.getBlockState(pos), bs);
            world.setBlock(pos, bs, 3);
        }
    }

    /** Логика финального шага для PODSTATION / SMES */
    private static void handleStationFinal(LevelAccessor world, BlockPos pos, Block originalBlock) {
        // Замена на MACHINE_FRAME_2 с переносом NBT
        BlockState targetBs = Ssc14ModBlocks.MACHINE_FRAME_2.get().defaultBlockState();
        BlockEntity be = world.getBlockEntity(pos);
        CompoundTag nbt = null;
        if (be != null) {
            nbt = be.saveWithFullMetadata(world.registryAccess());
            be.setRemoved();
        }
        targetBs = copyProperties(world.getBlockState(pos), targetBs);
        world.setBlock(pos, targetBs, 3);
        if (nbt != null) {
            be = world.getBlockEntity(pos);
            if (be != null) {
                try { be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), nbt)); } catch(Exception ignored) {}
            }
        }

        // Заполнение инвентаря
        if (world instanceof ILevelExtension ext && ext.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) instanceof IItemHandlerModifiable mod) {
            if (originalBlock == Ssc14ModBlocks.PODSTATION.get()) {
                mod.setStackInSlot(0, new ItemStack(Ssc14ModItems.SUBSTATION_BOARD.get(), 1));
                mod.setStackInSlot(1, new ItemStack(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get(), 5));
                mod.setStackInSlot(2, new ItemStack(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(), 5));
                mod.setStackInSlot(3, new ItemStack(Ssc14ModItems.LOW_BATTERIE.get(), 1));
                mod.setStackInSlot(4, new ItemStack(Ssc14ModItems.CAPACITOR.get(), 1));
            } else if (originalBlock == Ssc14ModBlocks.SMES.get()) {
                mod.setStackInSlot(0, new ItemStack(Ssc14ModItems.SMES_BOARD.get(), 1));
                mod.setStackInSlot(1, new ItemStack(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(), 10));
                mod.setStackInSlot(2, new ItemStack(Ssc14ModItems.CAPACITOR.get(), 1));
                mod.setStackInSlot(3, new ItemStack(Ssc14ModItems.LOW_BATTERIE.get(), 1));
                mod.setStackInSlot(4, new ItemStack(Ssc14ModItems.LOW_BATTERIE.get(), 1));
                mod.setStackInSlot(5, new ItemStack(Ssc14ModItems.LOW_BATTERIE.get(), 1));
                mod.setStackInSlot(6, new ItemStack(Ssc14ModItems.LOW_BATTERIE.get(), 1));
            }
        }
    }
}
