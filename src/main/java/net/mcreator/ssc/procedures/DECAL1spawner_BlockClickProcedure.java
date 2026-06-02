
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.ssc.entity.Decal1Entity;
import net.mcreator.ssc.init.Ssc14ModEntities;
import net.mcreator.ssc.init.Ssc14ModBlocks;

public class DECAL1spawner_BlockClickProcedure {
    public static void execute(LevelAccessor worldAccessor, double x, double y, double z,
                               net.minecraft.world.level.block.state.BlockState blockstate,
                               Entity entity) {
        if (entity == null || !(entity instanceof Player player)) return;
        if (worldAccessor.isClientSide()) return;
        if (!(worldAccessor instanceof Level world)) return;

        HitResult hitResult = player.pick(6.0, 0.0f, false);
        if (!(hitResult instanceof BlockHitResult blockHit)) return;
        
        // Только верхняя грань (пол)
        if (blockHit.getDirection() != net.minecraft.core.Direction.UP) return;
        
        BlockPos clickedPos = blockHit.getBlockPos();

        // Проверка блока
        var clickedBlock = world.getBlockState(clickedPos);
        boolean isValidTile = clickedBlock.is(BlockTags.create(ResourceLocation.parse("ssc14:tiles"))) 
                           || clickedBlock.getBlock() == Ssc14ModBlocks.SHEATHING.get();
        
        if (!isValidTile) return;

        var entityType = Ssc14ModEntities.DECAL_1.get();
        if (entityType == null) return;

        Decal1Entity decal = new Decal1Entity(entityType, world);
        decal.setAttachedPos(clickedPos);
        
        // 🔑 КРИТИЧНО: спавним сущность ВНУТРИ блока (Y + 0.5), 
        // чтобы хитбокс был внутри плитки и не блокировал атаки
        // Модель в Modeldecal_1 должна быть поднята вверх, чтобы лежать НА поверхности
        decal.setPos(clickedPos.getX() + 0.5, clickedPos.getY() + 0.5, clickedPos.getZ() + 0.5);
        
        boolean spawned = world.addFreshEntity(decal);
        System.out.println("[SSC14] Spawned: " + spawned + " at Y=" + decal.getY());
    }
}
