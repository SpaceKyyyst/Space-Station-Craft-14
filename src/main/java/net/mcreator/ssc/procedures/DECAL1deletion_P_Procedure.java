
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.particles.ParticleTypes;
import net.mcreator.ssc.entity.Decal1Entity; // ← Прямой импорт класса

import java.util.List;

public class DECAL1deletion_P_Procedure {
    public static void execute(LevelAccessor worldAccessor, double x, double y, double z,
                               net.minecraft.world.level.block.state.BlockState blockstate,
                               Entity entity) {
        if (entity == null || !(entity instanceof Player player)) return;
        if (worldAccessor.isClientSide()) return;
        if (!(worldAccessor instanceof ServerLevel world)) return;
        
        BlockPos clickedPos = BlockPos.containing(x, y, z);
        
        // 🔑 Расширенная область поиска: захватывает весь блок + немного вокруг
        AABB searchBox = new AABB(
            clickedPos.getX() - 0.1, clickedPos.getY() - 0.1, clickedPos.getZ() - 0.1,
            clickedPos.getX() + 1.1, clickedPos.getY() + 1.1, clickedPos.getZ() + 1.1
        );
        
        // === Удаление по ПРЯМОМУ классу (надёжнее тега) ===
        List<Decal1Entity> decals = world.getEntitiesOfClass(Decal1Entity.class, searchBox);
        int removed = 0;
        
        for (Decal1Entity decal : decals) {
            // Дополнительная проверка: удаляем только если привязана к этому блоку
            if (decal.getAttachedPos() != null && decal.getAttachedPos().equals(clickedPos)) {
                decal.discard();
                removed++;
                System.out.println("[SSC14] Deleted decal at " + clickedPos);
            }
        }
        
        // Визуальный фидбек
        if (removed > 0) {
            world.sendParticles(ParticleTypes.CLOUD,
                clickedPos.getX() + 0.5, clickedPos.getY() + 1.0, clickedPos.getZ() + 0.5,
                removed * 5, 0.4, 0.4, 0.4, 0.05);
        } else {
            System.out.println("[SSC14] No decals found at " + clickedPos);
        }
    }
}
