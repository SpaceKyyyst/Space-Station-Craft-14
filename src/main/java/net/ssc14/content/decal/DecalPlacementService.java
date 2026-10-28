package net.ssc14.content.decal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.ssc14.integration.neoforge.decal.DecalData;

/** Places a decal using the canonical attachment-backed storage. */
public final class DecalPlacementService {
    private DecalPlacementService() { }

    public static void execute(LevelAccessor world, Entity entity, BlockPos pos,
            Direction face, ItemStack stack) {
        if (world == null || entity == null || pos == null || face == null || stack == null
                || world.isClientSide() || !(world.getChunk(pos) instanceof LevelChunk chunk)) return;

        String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        String decalId = path.equals("decal_2spawner") || path.contains("decal2") ? "decal_2"
                : path.equals("decal_3spawner") || path.contains("decal3") ? "decal_3"
                : path.equals("decal_4spawner") || path.contains("decal4") ? "decal_4"
                : path.equals("decal_5spawner") || path.contains("decal5") ? "decal_5"
                : path.equals("decal_6spawner") || path.contains("decal6") ? "decal_6" : "decal_1";
        int color = color(stack.get(DataComponents.CUSTOM_DATA));
        DecalService.addDecal(chunk, new DecalData(pos, face, decalId, 0, color));
    }

    private static int color(CustomData customData) {
        if (customData == null) return -1;
        CompoundTag tag = customData.copyTag();
        if (!tag.contains("R") || !tag.contains("G") || !tag.contains("B")) return -1;
        int r = (int) Mth.clamp(tag.getDouble("R").orElse(0.0), 0.0, 255.0);
        int g = (int) Mth.clamp(tag.getDouble("G").orElse(0.0), 0.0, 255.0);
        int b = (int) Mth.clamp(tag.getDouble("B").orElse(0.0), 0.0, 255.0);
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }
}
