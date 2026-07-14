
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ClipContext;
import net.minecraft.util.RandomSource;

import net.mcreator.ssc.init.*;
import net.mcreator.ssc.Ssc14Mod;

public class UniversalDeconstructionProcedureProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;

        ItemStack mainHand = livingEntity.getMainHandItem();
        Item tool = mainHand.getItem();
        BlockPos pos = BlockPos.containing(x, y, z);
        double posHash = entity.getX() + entity.getY() + entity.getZ();

        SS14ConstructionRegistryProcedure.ConstructionStage stage = SS14ConstructionRegistryProcedure.findStage(blockstate, tool);
        if (stage == null) return;

        Direction hitSide = entity.level().clip(new ClipContext(
            entity.getEyePosition(1f),
            entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)),
            ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity
        )).getDirection();

        if (stage.sideCheck() != null && !stage.sideCheck().test(hitSide)) return;

        class GlobalProcess {
            void run(int step) {
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (livingEntity.getMainHandItem().getItem() != tool) { reset(); return; }
                if (world.getBlockState(pos).getBlock() != blockstate.getBlock()) { reset(); return; }

                if (tool == Ssc14ModItems.ACTIVE_WELDER.get() && world instanceof ServerLevel sl) {
                    sl.sendParticles(Ssc14ModParticleTypes.SPARK.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.2, 0.2, 0.2, 0.2);
                    float pitch = 0.8F + (world instanceof Level l ? l.random : RandomSource.create()).nextFloat() * 0.4F;
                    playLevelSound(world, pos, "ssc_14:welding_work", 0.2F, pitch);
                }

                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < 6) {
                    Ssc14Mod.queueServerWork(stage.delays()[step - 1], () -> run(step + 1));
                } else {
                    if (world instanceof ServerLevel sLevel) {
                        executeFinal(sLevel);
                    }
                }
            }

            private void executeFinal(ServerLevel sLevel) {
                if (entity.getX() + entity.getY() + entity.getZ() != posHash || livingEntity.getMainHandItem().getItem() != tool) { reset(); return; }
                BlockState currentBs = sLevel.getBlockState(pos);
                if (currentBs.getBlock() != blockstate.getBlock()) { reset(); return; }

                // Сохраняем старое состояние для синхронизации
                BlockState oldState = currentBs;

                if (stage.dropBlockAsItem()) {
                    spawnItem(sLevel, new ItemStack(currentBs.getBlock()), stage.dropYOffset());
                    // Флаг 3 (1 | 2) — обновляет блок на сервере и шлет пакет визуального изменения клиенту
                    sLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); 
                } else if (stage.dropItem() != null) {
                    spawnItem(sLevel, new ItemStack(stage.dropItem(), stage.dropAmount()), stage.dropYOffset());
                }

                BlockState resultState = sLevel.getBlockState(pos);
                if (stage.changeToBlock() != null) {
                    resultState = stage.changeToBlock().defaultBlockState();
                    sLevel.setBlock(pos, resultState, 3);
                } else if (stage.targetProperty() != null) {
                    for (Property<?> prop : currentBs.getProperties()) {
                        if (prop.getName().equals(stage.targetProperty()) && prop instanceof IntegerProperty ip) {
                            resultState = currentBs.setValue(ip, stage.nextPropertyValue());
                            sLevel.setBlock(pos, resultState, 3);
                            break;
                        }
                    }
                }

                if (stage.finalAction() != null) {
                    stage.finalAction().accept(new SS14ConstructionRegistryProcedure.RunContext(sLevel, pos, resultState, entity));
                }

                // Принудительно заставляем мир отправить клиенту обновленный блок, если он изменился
                BlockState finalState = sLevel.getBlockState(pos);
                sLevel.sendBlockUpdated(pos, oldState, finalState, 3);

                String soundId = (tool == Ssc14ModItems.SCREWDRIVER.get()) ? "ssc_14:screwdriver" : 
                                 (tool == Ssc14ModItems.SPANNER.get()) ? "ssc_14:spanner_use" : "ssc_14:title_off";
                playLevelSound(sLevel, pos, soundId, 1.0F, 1.0F);

                reset();
            }

            private void spawnItem(ServerLevel sLevel, ItemStack stack, double yOffset) {
                ItemEntity drop = new ItemEntity(sLevel, pos.getX() + 0.5, pos.getY() + yOffset, pos.getZ() + 0.5, stack);
                drop.setPickUpDelay(1);
                drop.setUnlimitedLifetime();
                sLevel.addFreshEntity(drop);
            }

            private void reset() {
                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
            }
        }

        new GlobalProcess().run(1);
    }

    private static void playLevelSound(LevelAccessor world, BlockPos pos, String soundId, float volume, float pitch) {
        if (world instanceof Level lvl) {
            var sound = BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse(soundId));
            if (sound != null) {
                if (!lvl.isClientSide()) lvl.playSound(null, pos, sound, SoundSource.NEUTRAL, volume, pitch);
                else lvl.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.NEUTRAL, volume, pitch, false);
            }
        }
    }
}
