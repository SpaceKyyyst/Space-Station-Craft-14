package net.ssc14.content.atmosphere;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/** Canonical gas-permeability contract for atmosphere-aware blocks. */
public interface AtmosphericBlock {
    TagKey<Block> LEGACY_NON_HERMETIC_TAG = TagKey.create(Registries.BLOCK,
            Identifier.fromNamespaceAndPath("ssc14", "non-hermetic"));

    float getPermeability(BlockState state);

    default boolean isGasBarrier(BlockState state) {
        float permeability = getPermeability(state);
        return !Float.isFinite(permeability) || permeability <= 0.0F;
    }
}
