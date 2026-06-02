
package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import net.mcreator.ssc.world.inventory.RCDGUIMenu;
import io.netty.buffer.Unpooled;

public class RSU_useOnBlock_Procedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;
        if (!itemstack.is(Ssc14ModItems.RSU.get())) return;

        // === 1. ОТКРЫТИЕ GUI ПРИ ЗАЖАТОМ SHIFT ===
        if (entity.isShiftKeyDown()) {
            if (entity instanceof ServerPlayer serverPlayer) {
                BlockPos _bpos = BlockPos.containing(x, y, z);
                serverPlayer.openMenu(new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.literal("RCDGUI");
                    }
                    @Override
                    public boolean shouldTriggerClientSideContainerClosingOnOpen() {
                        return false;
                    }
                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        return new RCDGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
                    }
                }, _bpos);
            }
            return; 
        }

        int mode = (int) itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Mode", 0);
        if (mode == 0) return;

        HitResult hitResult = entity.level().clip(new ClipContext(
                entity.getEyePosition(1f),
                entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(5)),
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                entity
        ));

        if (!(hitResult instanceof BlockHitResult blockHit)) return;
        BlockPos clickedPos = blockHit.getBlockPos();
        Direction clickedFace = blockHit.getDirection();
        BlockPos placePos = clickedPos.relative(clickedFace);
        BlockState clickedBlockState = world.getBlockState(clickedPos);

        if (mode == 1) {
            handleDeconstruction(world, livingEntity, itemstack, clickedPos, clickedBlockState);
            return;
        }

        if (mode >= 2 && mode <= 5) {
            handleConstruction(world, livingEntity, itemstack, placePos, mode, clickedFace);
            return;
        }
    }

    // =========================================================================
    // РАЗБОРКА
    // =========================================================================
    private static void handleDeconstruction(LevelAccessor world, LivingEntity livingEntity, ItemStack itemstack, 
                                              BlockPos pos, BlockState blockState) {
        if (!blockState.is(BlockTags.create(ResourceLocation.parse("ssc14:rcd_deconstruct")))) return;
        if (blockState.isAir()) return;

        double posHash = livingEntity.getX() + livingEntity.getY() + livingEntity.getZ();
        final int STEP_DELAY = 20;
        final int TOTAL_STEPS = 6;

        // СПАВН 6 ПАРТИКЛОВ РАЗБОРКИ (Только в момент начала действия!)
        if (world instanceof ServerLevel serverLevel) {
            spawnBuildParticles(serverLevel, pos);
        }

        class DeconstructProcess {
            void run(int step) {
                if (livingEntity.getX() + livingEntity.getY() + livingEntity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.RSU.get())) { reset(); return; }
                if (!world.getBlockState(pos).is(blockState.getBlock())) { reset(); return; }

                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < TOTAL_STEPS) {
                    Ssc14Mod.queueServerWork(STEP_DELAY, () -> run(step + 1));
                } else {
                    world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    
                    double currentCharges = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                            .copyTag().getDoubleOr("Charges", 0);
                    if (currentCharges < 30) {
                        final double newCharges = currentCharges + 1;
                        CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("Charges", newCharges));
                    }
                    
                    playSound(world, pos, "ssc_14:items_rcd_deconstruct", 1.0F, 1.0F);
                    reset();
                }
            }

            void reset() {
                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
            }
        }

        new DeconstructProcess().run(1);
    }

    // =========================================================================
    // СТРОИТЕЛЬСТВО
    // =========================================================================
    private static void handleConstruction(LevelAccessor world, LivingEntity livingEntity, ItemStack itemstack,
                                           BlockPos placePos, int mode, Direction face) {
        if (!world.getBlockState(placePos).isAir()) return;

        BuildConfig config = getBuildConfig(mode);
        if (config == null) return;

        // === НОВАЯ ПРОВЕРКА: УСЛОВИЯ РАЗМЕЩЕНИЯ ДЛЯ ПЛИТКИ И МОСТИКА ===
        if (mode == 3) { // Плитка (Tile)
            if (!Title_Placement_ConditionsProcedure.execute(world, placePos.getX(), placePos.getY(), placePos.getZ())) {
                return; // Блок под местом установки не подходит для плитки
            }
        } else if (mode == 5) { // Мостик (Catwalk)
            if (!CatwalkPlacementConditionsProcedure.execute(world, placePos.getX(), placePos.getY(), placePos.getZ())) {
                return; // Блок под местом установки не подходит для мостика
            }
        }

        double charges = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("Charges", 0);
        
        if (charges < config.cost()) return;

        double posHash = livingEntity.getX() + livingEntity.getY() + livingEntity.getZ();

        if (config.delay() == 0) {
            // Мгновенная постройка
            world.setBlock(placePos, config.blockState(), 3);
            
            final double newCharges = charges - config.cost();
            CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("Charges", newCharges));
            
            playSound(world, placePos, "ssc_14:items_rcd_deconstruct", 1.0F, 1.0F);
            return;
        }

        // Постройка с прогресс-баром (стена)
        if (world instanceof ServerLevel serverLevel) {
            spawnBuildParticles(serverLevel, placePos);
        }

        class BuildProcess {
            void run(int step) {
                if (livingEntity.getX() + livingEntity.getY() + livingEntity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.RSU.get())) { reset(); return; }
                if (!world.getBlockState(placePos).isAir()) { reset(); return; }
                
                // Повторная проверка зарядов во время процесса
                double currentCharges = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                        .copyTag().getDoubleOr("Charges", 0);
                if (currentCharges < config.cost()) { reset(); return; }

                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < 6) {
                    Ssc14Mod.queueServerWork(config.delay(), () -> run(step + 1));
                } else {
                    world.setBlock(placePos, config.blockState(), 3);
                    
                    final double finalCharges = currentCharges - config.cost();
                    CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble("Charges", finalCharges));
                    
                    playSound(world, placePos, "ssc_14:items_rcd_deconstruct", 1.0F, 1.0F);
                    reset();
                }
            }

            void reset() {
                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
            }
        }

        new BuildProcess().run(1);
    }

    // =========================================================================
    // СПАВН 6 ПАРТИКЛОВ СБОРКИ/РАЗБОРКИ (Большие партиклы на гранях)
    // =========================================================================
    private static void spawnBuildParticles(ServerLevel serverLevel, BlockPos pos) {
        double offset = 0.5001;
        for (Direction dir : Direction.values()) {
            double px = pos.getX() + 0.5 + dir.getStepX() * offset;
            double py = pos.getY() + 0.5 + dir.getStepY() * offset;
            double pz = pos.getZ() + 0.5 + dir.getStepZ() * offset;
            
            SimpleParticleType particleType;
            if (dir == Direction.UP || dir == Direction.DOWN) {
                particleType = Ssc14ModParticleTypes.DECONSTRUCT_P.get();
            } else if (dir == Direction.EAST || dir == Direction.WEST) {
                particleType = Ssc14ModParticleTypes.DECONSTRUCT_PX.get();
            } else {
                particleType = Ssc14ModParticleTypes.DECONSTRUCT_PZ.get();
            }
            
            serverLevel.sendParticles(particleType, px, py, pz, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    // =========================================================================
    // КОНФИГУРАЦИЯ
    // =========================================================================
    private record BuildConfig(int delay, double cost, BlockState blockState) {}
    
    private static BuildConfig getBuildConfig(int mode) {
        return switch (mode) {
            case 2 -> new BuildConfig(20, 5, Ssc14ModBlocks.STEEL_WALL.get().defaultBlockState());
            case 3 -> new BuildConfig(0, 1, Ssc14ModBlocks.TITLE_STEEL.get().defaultBlockState());
            case 4 -> new BuildConfig(0, 1, Ssc14ModBlocks.SHEATHING.get().defaultBlockState());
            case 5 -> new BuildConfig(0, 1, Ssc14ModBlocks.CATWALK_FLOOR.get().defaultBlockState());
            default -> null;
        };
    }

    private static void playSound(LevelAccessor world, BlockPos pos, String soundId, float volume, float pitch) {
        if (world instanceof Level lvl) {
            var sound = net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT.getValue(
                    net.minecraft.resources.ResourceLocation.parse(soundId)
            );
            if (sound != null) {
                if (!lvl.isClientSide()) {
                    lvl.playSound(null, pos, sound, SoundSource.BLOCKS, volume, pitch);
                } else {
                    lvl.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.BLOCKS, volume, pitch, false);
                }
            }
        }
    }
}
