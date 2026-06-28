
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
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
import net.mcreator.ssc.block.SheathingBlock;
import net.mcreator.ssc.EnergyNetworkManager;
import net.mcreator.ssc.Ssc14Mod;

public class Nippers_UseProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;
        
        ItemStack mainHand = livingEntity.getMainHandItem();
        if (!mainHand.is(Ssc14ModItems.NIPPERS.get())) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        double posHash = entity.getX() + entity.getY() + entity.getZ();
        Block targetBlock = blockstate.getBlock();

        record NipperConfig(int[] delays, int action, Property<?> targetProp, Block newBlock, 
                            net.minecraft.world.item.Item dropItem, double dropY, boolean copyProps) {}
        
        NipperConfig config = null;
        int[] fastDelays = new int[]{1, 1, 1, 1, 1, 1};

        // === ОБРАБОТКА БЛОКА ОБШИВКИ (SHEATHING) ===
        if (targetBlock == Ssc14ModBlocks.SHEATHING.get()) {
            Direction hit = entity.level().clip(new ClipContext(entity.getEyePosition(1f), 
                    entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(6)), 
                    ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getDirection();
            
            if (hit == Direction.UP) {
                // ИСПРАВЛЕНО: Теперь приоритет демонтажа идет от меньшего вольтажа к большему: LV -> MV -> HV
                if (blockstate.getValue(SheathingBlock.LV)) {
                    config = new NipperConfig(fastDelays, 10, SheathingBlock.LV, null, Ssc14ModItems.LOW_VOLTAGE_CABLE.get(), y + 1.0, false);
                } else if (blockstate.getValue(SheathingBlock.MV)) {
                    config = new NipperConfig(fastDelays, 10, SheathingBlock.MV, null, Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get(), y + 1.0, false);
                } else if (blockstate.getValue(SheathingBlock.HV)) {
                    config = new NipperConfig(fastDelays, 10, SheathingBlock.HV, null, Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(), y + 1.0, false);
                }
            }
        }
        // === БАЗОВАЯ СОВМЕСТИМОСТЬ ДЛЯ ОСТАЛЬНЫХ СТРУКТУР СТАНЦИИ ===
        else if (targetBlock == Ssc14ModBlocks.ROD_FLOOR.get() || targetBlock == Ssc14ModBlocks.ROD_UP_FLOOR.get()) {
            IntegerProperty stateProp = findIntegerProperty(blockstate, "blockstate");
            int currentState = stateProp != null ? blockstate.getValue(stateProp) : -1;
            double dropY = (targetBlock == Ssc14ModBlocks.ROD_UP_FLOOR.get()) ? y : y + 1.0;
            
            if (currentState == 0) {
                config = new NipperConfig(fastDelays, 1, null, null, Ssc14ModItems.ROOD.get(), dropY, false);
            }
        }
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
            IntegerProperty stateProp = findIntegerProperty(blockstate, "blockstate");
            int currentState = stateProp != null ? blockstate.getValue(stateProp) : -1;
            if (currentState == 0) config = new NipperConfig(new int[]{3,3,3,3,3,3}, 2, stateProp, null, null, y + 1.0, false);
            else if (currentState == 8) config = new NipperConfig(new int[]{3,1,2,1,2,1}, 3, null, Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get(), Ssc14ModItems.PLASTEEL.get(), entity.getY(), true);
        }
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get()) {
            config = new NipperConfig(new int[]{3,3,3,1,3,3}, 3, null, Ssc14ModBlocks.WALL_CARCASE.get(), Ssc14ModItems.PLASTEEL.get(), entity.getY(), true);
        }
        else if (targetBlock == Ssc14ModBlocks.GRILLE.get()) {
            config = new NipperConfig(new int[]{1,2,1,2,1,2}, 1, null, null, Ssc14ModItems.ROOD.get(), y + 0.1, false);
        }
        else if (targetBlock == Ssc14ModBlocks.BROKEN_GRILLE.get()) {
            config = new NipperConfig(fastDelays, 5, null, null, null, y + 0.5, false);
        }

        if (config == null) return;
        class NipperProcess {
            private final LivingEntity entity;
            private final LevelAccessor world;
            private final BlockPos pos;
            private final double posHash;
            private final Block targetBlock;
            private final NipperConfig config;
            private final BlockState initialBs;

            NipperProcess(LivingEntity entity, LevelAccessor world, BlockPos pos, double posHash, Block targetBlock, NipperConfig config, BlockState initialBs) {
                this.entity = entity; this.world = world; this.pos = pos; this.posHash = posHash;
                this.targetBlock = targetBlock; this.config = config; this.initialBs = initialBs;
            }

            void run(int step) {
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.NIPPERS.get())) { reset(); return; }
                
                BlockState currentBs = world.getBlockState(pos);
                if (currentBs.getBlock() != targetBlock) { reset(); return; }
                
                if (targetBlock == Ssc14ModBlocks.SHEATHING.get() && config.targetProp() instanceof BooleanProperty bp) {
                    if (!currentBs.getValue(bp)) { reset(); return; }
                }

                entity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < 6) {
                    Ssc14Mod.queueServerWork(config.delays()[step - 1], () -> run(step + 1));
                } else {
                    Ssc14Mod.queueServerWork(config.delays()[5], this::executeFinal);
                }
            }

            private void executeFinal() {
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.NIPPERS.get())) { reset(); return; }
                
                BlockState currentBs = world.getBlockState(pos);
                if (currentBs.getBlock() != targetBlock) { reset(); return; }

                if (config.action() == 10 && targetBlock == Ssc14ModBlocks.SHEATHING.get() && config.targetProp() instanceof BooleanProperty bp) {
                    if (currentBs.getValue(bp)) {
                        BlockState updatedBs = currentBs.setValue(bp, false);
                        world.setBlock(pos, updatedBs, 3);
                        
                        spawnDrop(config.dropItem(), config.dropY());
                        EnergyNetworkManager.updatePosition(world, pos);
                    }
                } else {
                    switch (config.action()) {
                        case 1 -> { world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); spawnDrop(config.dropItem(), config.dropY()); }
                        case 2 -> {
                            if (config.targetProp() instanceof IntegerProperty ip) {
                                world.setBlock(pos, currentBs.setValue(ip, 1), 3);
                            }
                            spawnDrop(config.dropItem(), config.dropY());
                        }
                        case 3 -> {
                            BlockState newBs = config.newBlock().defaultBlockState();
                            if (config.copyProps()) copyProperties(currentBs, newBs, pos);
                            else world.setBlock(pos, newBs, 3);
                            spawnDrop(config.dropItem(), config.dropY());
                        }
                        case 5 -> world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
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

        new NipperProcess(livingEntity, world, pos, posHash, targetBlock, config, blockstate).run(1);
    }

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
