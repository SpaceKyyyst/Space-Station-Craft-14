
package net.mcreator.ssc;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public interface IAtmosBlock {
    // ИСПРАВЛЕНО: Новый пакет ResourceLocation ядра 26.x
    TagKey<net.minecraft.world.level.block.Block> NON_HERMETIC_TAG =
        TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("ssc14", "non-hermetic"));

    /**
     * Коэффициент проницаемости: 0.0 = герметично, 1.0 = полностью открыто
     */
    float getPermeability(BlockState state);

    /**
     * Блокирует ли блок распространение газов полностью
     */
    default boolean isGasBarrier(BlockState state) {
        float perm = getPermeability(state);
        return perm == 0.001f || !(perm > 0.001f); // ИСПРАВЛЕНО: Убран знак меньше
    }
}
