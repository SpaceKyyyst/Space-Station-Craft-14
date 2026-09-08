
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
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class DECAL1spawner_USE_Procedure {
    public static void execute(LevelAccessor world, Entity entity, BlockPos pos, Direction face, ItemStack itemStack) {
        if (world == null || entity == null || pos == null || face == null || itemStack == null) return;
        if (world.isClientSide()) return;

        if (world.getChunk(pos) instanceof LevelChunk chunk) {
            String decalId = "decal_1";
            ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
            String itemName = itemKey.getPath();

            if (itemName.equals("decal_2spawner") || itemName.contains("decal2")) { decalId = "decal_2"; }
            if (itemName.equals("decal_3spawner") || itemName.contains("decal3")) { decalId = "decal_3"; }
            if (itemName.equals("decal_4spawner") || itemName.contains("decal4")) { decalId = "decal_4"; }
            if (itemName.equals("decal_5spawner") || itemName.contains("decal5")) { decalId = "decal_5"; }
            if (itemName.equals("decal_6spawner") || itemName.contains("decal6")) { decalId = "decal_6"; }

            int finalColor = -1; 
            CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
            
            if (customData != null) {
                CompoundTag tag = customData.copyTag();
                if (tag.contains("R") && tag.contains("G") && tag.contains("B")) {
                    // ИСПРАВЛЕНО: Прямое получение double параметров без orElse и безопасный зажим диапазона 0-255
                    int r = (int) Mth.clamp(tag.getDouble("R"), 0.0, 255.0);
                    int g = (int) Mth.clamp(tag.getDouble("G"), 0.0, 255.0);
                    int b = (int) Mth.clamp(tag.getDouble("B"), 0.0, 255.0);
                    
                    finalColor = (255 << 24) | (r << 16) | (g << 8) | b;
                }
            }

            DecalData newDecal = new DecalData(pos, face, decalId, 0, finalColor);
            DecalRegistry.addDecal(chunk, newDecal);
        }
    }
}
