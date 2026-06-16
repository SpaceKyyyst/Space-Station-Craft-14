
package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModParticleTypes;

public class RCDinterfParticGenerateProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        
        if (itemstack == null || itemstack.isEmpty()) {
            itemstack = livingEntity.getMainHandItem();
        }
        
        if (!itemstack.is(Ssc14ModItems.RSU.get())) return;

        int mode = (int) itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Mode", 0);
        if (mode == 0) return;

        HitResult hitResult = livingEntity.level().clip(new ClipContext(
                livingEntity.getEyePosition(1f),
                livingEntity.getEyePosition(1f).add(livingEntity.getViewVector(1f).scale(5)),
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                livingEntity
        ));

        if (!(hitResult instanceof BlockHitResult blockHit)) return;
        BlockPos clickedPos = blockHit.getBlockPos();
        Direction clickedFace = blockHit.getDirection();
        BlockPos placePos = clickedPos.relative(clickedFace);

        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            if (mode == 1) {
                BlockState targetState = serverLevel.getBlockState(clickedPos);
                if (targetState.is(BlockTags.create(ResourceLocation.parse("ssc14:rcd_deconstruct")))) {
                    spawnAimingParticles(serverLevel, clickedPos);
                }
            } else if (mode >= 2 && mode <= 10) {
                double charges = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Charges", 0);
                double cost = getBuildCost(mode);
                
                if (charges >= cost && serverLevel.getBlockState(placePos).isAir()) {
                    // Для направленных окон (8, 9) рисуем пластинку
                    if (mode == 8 || mode == 9) {
                        Direction facing = getFacingFromLook(livingEntity);
                        spawnDirectedWindowParticles(serverLevel, placePos, facing);
                    } else {
                        spawnAimingParticles(serverLevel, placePos);
                    }
                }
            }
        }
    }

    // =========================================================================
    // ПОЛУЧЕНИЕ НАПРАВЛЕНИЯ ВЗГЛЯДА (дубликат для клиентской процедуры)
    // =========================================================================
    private static Direction getFacingFromLook(LivingEntity entity) {
        float xRot = entity.getXRot();
        if (xRot < -45) return Direction.UP;
        if (xRot > 45) return Direction.DOWN;
        return entity.getDirection();
    }

    // =========================================================================
    // СПАВН ПАРТИКЛОВ НАВОДКИ (Полный куб)
    // =========================================================================
    private static void spawnAimingParticles(ServerLevel serverLevel, BlockPos pos) {
        SimpleParticleType interferenceParticle = Ssc14ModParticleTypes.RCD_INTERFERENCE_P.get();
        double offset = 0.01; 
        int steps = 4;
        double stepSize = 1.0 / steps;

        for (int i = 0; i <= steps; i++) {
            double t = i * stepSize;
            serverLevel.sendParticles(interferenceParticle, pos.getX() + t, pos.getY() + offset, pos.getZ() + offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + t, pos.getY() + offset, pos.getZ() + 1 - offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + t, pos.getY() + 1 - offset, pos.getZ() + offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + t, pos.getY() + 1 - offset, pos.getZ() + 1 - offset, 1, 0, 0, 0, 0);
        }

        for (int i = 0; i <= steps; i++) {
            double t = i * stepSize;
            serverLevel.sendParticles(interferenceParticle, pos.getX() + offset, pos.getY() + t, pos.getZ() + offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + offset, pos.getY() + t, pos.getZ() + 1 - offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + 1 - offset, pos.getY() + t, pos.getZ() + offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + 1 - offset, pos.getY() + t, pos.getZ() + 1 - offset, 1, 0, 0, 0, 0);
        }

        for (int i = 0; i <= steps; i++) {
            double t = i * stepSize;
            serverLevel.sendParticles(interferenceParticle, pos.getX() + offset, pos.getY() + offset, pos.getZ() + t, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + offset, pos.getY() + 1 - offset, pos.getZ() + t, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + 1 - offset, pos.getY() + offset, pos.getZ() + t, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + 1 - offset, pos.getY() + 1 - offset, pos.getZ() + t, 1, 0, 0, 0, 0);
        }
    }

    // =========================================================================
    // СПАВН ПАРТИКЛОВ ДЛЯ НАПРАВЛЕННЫХ ОКОН (Пластинка 3 пикселя)
    // =========================================================================
    private static void spawnDirectedWindowParticles(ServerLevel serverLevel, BlockPos pos, Direction facing) {
        SimpleParticleType particle = Ssc14ModParticleTypes.RCD_INTERFERENCE_P.get();
        double thickness = 3.0 / 16.0;
        double halfThickness = thickness / 2.0;
        
        double cx = pos.getX() + 0.5 + facing.getStepX() * (0.5 - halfThickness);
        double cy = pos.getY() + 0.5 + facing.getStepY() * (0.5 - halfThickness);
        double cz = pos.getZ() + 0.5 + facing.getStepZ() * (0.5 - halfThickness);
        
        int steps = 4;
        double stepSize = 1.0 / steps;
        double edgeOffset = 0.01;

        if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            for (int i = 0; i <= steps; i++) {
                double t = i * stepSize;
                serverLevel.sendParticles(particle, pos.getX() + t, pos.getY() + edgeOffset, cz, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(particle, pos.getX() + t, pos.getY() + 1 - edgeOffset, cz, 1, 0, 0, 0, 0);
            }
            for (int i = 0; i <= steps; i++) {
                double t = i * stepSize;
                serverLevel.sendParticles(particle, pos.getX() + edgeOffset, pos.getY() + t, cz, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(particle, pos.getX() + 1 - edgeOffset, pos.getY() + t, cz, 1, 0, 0, 0, 0);
            }
        } else if (facing == Direction.EAST || facing == Direction.WEST) {
            for (int i = 0; i <= steps; i++) {
                double t = i * stepSize;
                serverLevel.sendParticles(particle, cx, pos.getY() + edgeOffset, pos.getZ() + t, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(particle, cx, pos.getY() + 1 - edgeOffset, pos.getZ() + t, 1, 0, 0, 0, 0);
            }
            for (int i = 0; i <= steps; i++) {
                double t = i * stepSize;
                serverLevel.sendParticles(particle, cx, pos.getY() + t, pos.getZ() + edgeOffset, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(particle, cx, pos.getY() + t, pos.getZ() + 1 - edgeOffset, 1, 0, 0, 0, 0);
            }
        } else if (facing == Direction.UP || facing == Direction.DOWN) {
            for (int i = 0; i <= steps; i++) {
                double t = i * stepSize;
                serverLevel.sendParticles(particle, pos.getX() + t, cy, pos.getZ() + edgeOffset, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(particle, pos.getX() + t, cy, pos.getZ() + 1 - edgeOffset, 1, 0, 0, 0, 0);
            }
            for (int i = 0; i <= steps; i++) {
                double t = i * stepSize;
                serverLevel.sendParticles(particle, pos.getX() + edgeOffset, cy, pos.getZ() + t, 1, 0, 0, 0, 0);
                serverLevel.sendParticles(particle, pos.getX() + 1 - edgeOffset, cy, pos.getZ() + t, 1, 0, 0, 0, 0);
            }
        }
    }

    private static double getBuildCost(int mode) {
        return switch (mode) {
            case 2 -> 5.0;           // Стена
            case 3, 4, 5, 6 -> 1.0;  // Плитка, Обшивка, Мостик, Решётка
            case 8 -> 2.0;           // Направленное окно
            case 7, 9 -> 3.0;        // Окно, Направленное укреплённое окно
            case 10 -> 4.0;          // Укреплённое окно
            default -> 999.0;
        };
    }
}
