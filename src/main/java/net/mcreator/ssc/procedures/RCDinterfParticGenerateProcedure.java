
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
        // 1. Базовые проверки
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        
        // На случай, если MCreator передаст null, берем предмет из руки
        if (itemstack == null || itemstack.isEmpty()) {
            itemstack = livingEntity.getMainHandItem();
        }
        
        if (!itemstack.is(Ssc14ModItems.RSU.get())) return;

        int mode = (int) itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Mode", 0);
        if (mode == 0) return;

        // 2. Рейкаст (наведение)
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

        // 3. Логика отображения рамки ТОЛЬКО если это сервер (чтобы использовать sendParticles)
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            if (mode == 1) {
                // РЕЖИМ РАЗБОРКИ: Проверяем тег блока
                BlockState targetState = serverLevel.getBlockState(clickedPos);
                if (targetState.is(BlockTags.create(ResourceLocation.parse("ssc14:rcd_deconstruct")))) {
                    spawnAimingParticles(serverLevel, clickedPos);
                }
            } else if (mode >= 2 && mode <= 5) {
                // РЕЖИМ СТРОИТЕЛЬСТВА: Проверяем заряды и пустоту места
                double charges = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Charges", 0);
                double cost = getBuildCost(mode);
                
                if (charges >= cost && serverLevel.getBlockState(placePos).isAir()) {
                    spawnAimingParticles(serverLevel, placePos);
                }
            }
        }
    }

    // =========================================================================
    // СПАВН ПАРТИКЛОВ НАВОДКИ (Серверный метод через sendParticles)
    // =========================================================================
    private static void spawnAimingParticles(ServerLevel serverLevel, BlockPos pos) {
        SimpleParticleType interferenceParticle = Ssc14ModParticleTypes.RCD_INTERFERENCE_P.get();
        double offset = 0.01; 
        int steps = 4; // 5 точек на ребро (0.0, 0.25, 0.5, 0.75, 1.0). Всего 60 частиц. Идеально для сервера.
        double stepSize = 1.0 / steps;

        // Рёбра параллельные оси X
        for (int i = 0; i <= steps; i++) {
            double t = i * stepSize;
            serverLevel.sendParticles(interferenceParticle, pos.getX() + t, pos.getY() + offset, pos.getZ() + offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + t, pos.getY() + offset, pos.getZ() + 1 - offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + t, pos.getY() + 1 - offset, pos.getZ() + offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + t, pos.getY() + 1 - offset, pos.getZ() + 1 - offset, 1, 0, 0, 0, 0);
        }

        // Рёбра параллельные оси Y
        for (int i = 0; i <= steps; i++) {
            double t = i * stepSize;
            serverLevel.sendParticles(interferenceParticle, pos.getX() + offset, pos.getY() + t, pos.getZ() + offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + offset, pos.getY() + t, pos.getZ() + 1 - offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + 1 - offset, pos.getY() + t, pos.getZ() + offset, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + 1 - offset, pos.getY() + t, pos.getZ() + 1 - offset, 1, 0, 0, 0, 0);
        }

        // Рёбра параллельные оси Z
        for (int i = 0; i <= steps; i++) {
            double t = i * stepSize;
            serverLevel.sendParticles(interferenceParticle, pos.getX() + offset, pos.getY() + offset, pos.getZ() + t, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + offset, pos.getY() + 1 - offset, pos.getZ() + t, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + 1 - offset, pos.getY() + offset, pos.getZ() + t, 1, 0, 0, 0, 0);
            serverLevel.sendParticles(interferenceParticle, pos.getX() + 1 - offset, pos.getY() + 1 - offset, pos.getZ() + t, 1, 0, 0, 0, 0);
        }
    }

    private static double getBuildCost(int mode) {
        return switch (mode) {
            case 2 -> 5.0; // Стена
            case 3, 4, 5 -> 1.0; // Плитка, Обшивка, Мостик
            default -> 999.0;
        };
    }
}
