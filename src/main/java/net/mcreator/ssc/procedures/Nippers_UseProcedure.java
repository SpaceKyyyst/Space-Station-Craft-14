
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
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

public class Nippers_UseProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
        // === БАЗОВЫЕ ПРОВЕРКИ ===
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;
        
        ItemStack mainHand = livingEntity.getMainHandItem();
        if (!mainHand.is(Ssc14ModItems.NIPPERS.get())) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        double posHash = entity.getX() + entity.getY() + entity.getZ();
        Block targetBlock = blockstate.getBlock();
        IntegerProperty stateProp = findIntegerProperty(blockstate, "blockstate");
        int currentState = stateProp != null ? blockstate.getValue(stateProp) : -1;

        // === КОНФИГУРАЦИЯ ===
        record NipperConfig(int[] delays, int action, IntegerProperty prop, int requiredState, int targetState, 
                            Block newBlock, net.minecraft.world.item.Item dropItem, double dropY, boolean copyProps) {}
        
        NipperConfig config = null;

        // ROD_FLOOR / ROD_UP_FLOOR / SHEATHING
        if (targetBlock == Ssc14ModBlocks.ROD_FLOOR.get() || targetBlock == Ssc14ModBlocks.ROD_UP_FLOOR.get() || targetBlock == Ssc14ModBlocks.SHEATHING.get()) {
            int[] delays = new int[]{1, 1, 1, 1, 1, 1};
            double dropY = (targetBlock == Ssc14ModBlocks.ROD_UP_FLOOR.get()) ? y : y + 1.0;
            
            if (currentState == 0) {
                config = new NipperConfig(delays, 1, null, 0, -1, null, Ssc14ModItems.ROOD.get(), dropY, false);
            } else if (currentState > 0 && currentState <= 7) {
                int targetS = -1; net.minecraft.world.item.Item drop = null;
                switch (currentState) {
                    case 1 -> { targetS = 0; drop = Ssc14ModItems.LOW_VOLTAGE_CABLE.get(); }
                    case 2 -> { targetS = 0; drop = Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get(); }
                    case 3 -> { targetS = 0; drop = Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(); }
                    case 4 -> { targetS = 2; drop = Ssc14ModItems.LOW_VOLTAGE_CABLE.get(); }
                    case 5 -> { targetS = 3; drop = Ssc14ModItems.LOW_VOLTAGE_CABLE.get(); }
                    case 6 -> { targetS = 3; drop = Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get(); }
                    case 7 -> { targetS = 6; drop = Ssc14ModItems.LOW_VOLTAGE_CABLE.get(); }
                }
                if (targetS != -1) config = new NipperConfig(delays, 2, stateProp, currentState, targetS, null, drop, y + 1.0, false);
            }
            // Проверка направления для SHEATHING
            if (config != null && targetBlock == Ssc14ModBlocks.SHEATHING.get()) {
                Direction hit = entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(6)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getDirection();
                if (hit != Direction.UP) config = null;
            }
        }
        // PLASTEEL_WALL
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
            if (currentState == 0) config = new NipperConfig(new int[]{3,3,3,3,3,3}, 2, stateProp, 0, 1, null, null, y + 1.0, false);
            else if (currentState == 8) config = new NipperConfig(new int[]{3,1,2,1,2,1}, 3, null, 8, -1, Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get(), Ssc14ModItems.PLASTEEL.get(), entity.getY(), true);
        }
        // PLASTEEL_WALL_CARCASE
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get()) {
            config = new NipperConfig(new int[]{3,3,3,1,3,3}, 3, null, -1, -1, Ssc14ModBlocks.WALL_CARCASE.get(), Ssc14ModItems.PLASTEEL.get(), entity.getY(), true);
        }
        // GRILLE
        else if (targetBlock == Ssc14ModBlocks.GRILLE.get()) {
            config = new NipperConfig(new int[]{1,2,1,2,1,2}, 1, null, -1, -1, null, Ssc14ModItems.ROOD.get(), y + 0.1, false);
        }
        // BROKEN_GRILLE
        else if (targetBlock == Ssc14ModBlocks.BROKEN_GRILLE.get()) {
            config = new NipperConfig(new int[]{1,2,1,2,1,2}, 5, null, -1, -1, null, null, y + 0.5, false);
        }

        // Если конфигурация не создана — выходим
        if (config == null) return;

        // === ЛОКАЛЬНЫЙ КЛАСС ПРОЦЕССА ===
        class NipperProcess {
            private final LivingEntity entity;
            private final LevelAccessor world;
            private final BlockPos pos;
            private final double posHash;
            private final Block targetBlock;
            private final NipperConfig config;
            private final int requiredState; // ← Запоминаем требуемое состояние для защиты от повторов

            NipperProcess(LivingEntity entity, LevelAccessor world, BlockPos pos, double posHash, Block targetBlock, NipperConfig config, int requiredState) {
                this.entity = entity; this.world = world; this.pos = pos; this.posHash = posHash;
                this.targetBlock = targetBlock; this.config = config; this.requiredState = requiredState;
            }

            void run(int step) {
                // === ЖЁСТКИЕ ПРОВЕРКИ НА КАЖДОМ ШАГЕ ===
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.NIPPERS.get())) { reset(); return; }
                
                BlockState currentBs = world.getBlockState(pos);
                // 1. Блок должен быть того же типа
                if (currentBs.getBlock() != targetBlock) { reset(); return; }
                // 2. Если конфиг требует конкретное состояние — проверяем его (защита от повторов!)
                if (config.requiredState() != -1 && config.prop() != null) {
                    int currentVal = currentBs.getValue(config.prop());
                    if (currentVal != config.requiredState()) { reset(); return; }
                }

                entity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < 6) {
                    Ssc14Mod.queueServerWork(config.delays()[step - 1], () -> run(step + 1));
                } else {
                    Ssc14Mod.queueServerWork(config.delays()[5], this::executeFinal);
                }
            }

            private void executeFinal() {
                // Финальные проверки (те же, что в run)
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.NIPPERS.get())) { reset(); return; }
                
                BlockState currentBs = world.getBlockState(pos);
                if (currentBs.getBlock() != targetBlock) { reset(); return; }
                // Ключевая проверка: если состояние уже изменилось — значит, действие уже выполнено, не делаем ничего!
                if (config.requiredState() != -1 && config.prop() != null) {
                    int currentVal = currentBs.getValue(config.prop());
                    if (currentVal != config.requiredState()) { reset(); return; } // ← Уже не то состояние → отменяем
                }

                // Выполняем действие
                switch (config.action()) {
                    case 1 -> { world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); spawnDrop(config.dropItem(), config.dropY()); }
                    case 2 -> {
                        if (config.prop() != null) {
                            IntegerProperty p = findIntegerProperty(currentBs, "blockstate");
                            if (p != null) world.setBlock(pos, currentBs.setValue(p, config.targetState()), 3);
                        }
                        spawnDrop(config.dropItem(), config.dropY());
                    }
                    case 3, 4 -> {
                        BlockState newBs = config.newBlock().defaultBlockState();
                        if (config.copyProps()) copyProperties(currentBs, newBs, pos);
                        else world.setBlock(pos, newBs, 3);
                        if (config.action() == 3) spawnDrop(config.dropItem(), config.dropY());
                    }
                    case 5 -> world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
                playSound(world, pos, "ssc_14:nippers_use", 1.0F, 1.0F);
                reset();
            }

            private void spawnDrop(net.minecraft.world.item.Item item, double dropY) {
                if (item != null && world instanceof ServerLevel sLevel) {
                    ItemEntity drop = new ItemEntity(sLevel, pos.getX() + 0.5, dropY, pos.getZ() + 0.5, new ItemStack(item));
                    drop.setPickUpDelay(1); drop.setUnlimitedLifetime(); sLevel.addFreshEntity(drop);
                }
            }

            private void copyProperties(BlockState from, BlockState toBase, BlockPos targetPos) {
                BlockState finalState = toBase;
                for (Property<?> oldProp : from.getProperties()) {
                    Property<?> newProp = finalState.getBlock().getStateDefinition().getProperty(oldProp.getName());
                    if (newProp != null) {
                        try { finalState = finalState.setValue((Property) newProp, from.getValue((Property) oldProp)); } catch (Exception ignored) {}
                    }
                }
                world.setBlock(targetPos, finalState, 3);
            }

            void reset() {
                entity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
            }
        }

        // Запуск процесса (передаём requiredState для проверки)
        new NipperProcess(livingEntity, world, pos, posHash, targetBlock, config, currentState).run(1);
    }

    // ============================================================================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    // ============================================================================

    private static IntegerProperty findIntegerProperty(BlockState state, String name) {
        for (Property<?> prop : state.getProperties()) {
            if (prop.getName().equals(name) && prop instanceof IntegerProperty ip) return ip;
        } return null;
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
