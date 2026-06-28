
package net.mcreator.ssc.procedures;

import net.mcreator.ssc.DecalData;
import net.mcreator.ssc.DecalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.fml.common.EventBusSubscriber;

public class DECAL1spawner_USE_Procedure {
    // MCreator всегда может передать управление через общую сущность и мир
    public static void execute(LevelAccessor world, Entity entity) {
        if (world == null || entity == null) return;
        if (world.isClientSide() || !(entity instanceof Player player)) return;

        System.out.println("[SS14-Decals] Spawner Procedure EXECUTED on Server!");

        HitResult hit = player.pick(5.0D, 0.0F, false);
        if (hit.getType() == HitResult.Type.BLOCK && hit instanceof BlockHitResult blockHit) {
            BlockPos pos = blockHit.getBlockPos();
            Direction face = blockHit.getDirection();

            System.out.println("[SS14-Decals] Server: Player clicked on block " + pos + " face: " + face);

            if (world.getChunk(pos) instanceof LevelChunk chunk) {
                int randomRotation = player.getRandom().nextInt(4);
                DecalData newDecal = new DecalData(pos, face, "debug_decal", randomRotation);
                DecalRegistry.addDecal(chunk, newDecal);
            }
        }
    }
}
