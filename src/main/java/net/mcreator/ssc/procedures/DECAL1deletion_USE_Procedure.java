
package net.mcreator.ssc.procedures;

import net.mcreator.ssc.DecalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class DECAL1deletion_USE_Procedure {
    
    // Заглушка, если MCreator попытается вызвать метод без аргументов
    public static void execute() {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.level != null && mc.player != null) {
            execute(mc.level, mc.player);
        }
    }

    public static void execute(LevelAccessor world, Entity entity) {
        if (world == null || entity == null) return;
        if (world.isClientSide() || !(entity instanceof Player player)) return;

        HitResult hit = player.pick(5.0D, 0.0F, false);
        if (hit.getType() == HitResult.Type.BLOCK && hit instanceof BlockHitResult blockHit) {
            BlockPos pos = blockHit.getBlockPos();

            if (world.getChunk(pos) instanceof LevelChunk chunk) {
                // Стираем декали в радиусе 1.5 блоков от точки клика
                DecalRegistry.removeDecalsInRadius(chunk, pos, 1.5D);
            }
        }
    }
}
