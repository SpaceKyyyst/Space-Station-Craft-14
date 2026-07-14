
package net.mcreator.ssc.procedures;

import net.mcreator.ssc.DecalRegistry;
import net.mcreator.ssc.DecalData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

public class DECAL1spawner_USE_Procedure {
    public static void execute(LevelAccessor world, Entity entity, BlockPos pos, Direction face, ItemStack itemStack) {
        if (world == null || entity == null || pos == null || face == null || itemStack == null) return;
        
        // Логика должна выполняться строго на сервере
        if (world.isClientSide()) return;

        if (world.getChunk(pos) instanceof LevelChunk chunk) {
            // Определяем ID декали по предмету в руке
            String decalId = "decal_1"; // По умолчанию
            
            // Получаем текстовое имя предмета для точной сверки
            ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
            String itemName = itemKey.getPath();

            if (itemName.equals("decal_2spawner") || itemName.contains("decal2")) {
                decalId = "decal_2";
            }

            // Спавним нужную декаль на кликнутом блоке без вращения (0)
            DecalData newDecal = new DecalData(pos, face, decalId, 0);
            DecalRegistry.addDecal(chunk, newDecal);
        }
    }
}
