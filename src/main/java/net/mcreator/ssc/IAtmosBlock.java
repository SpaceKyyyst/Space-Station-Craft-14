
package net.mcreator.ssc;

/** @deprecated Use {@link net.ssc14.content.atmosphere.AtmosphericBlock}. */
@Deprecated(forRemoval = false)
public interface IAtmosBlock extends net.ssc14.content.atmosphere.AtmosphericBlock {
    net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> NON_HERMETIC_TAG =
            LEGACY_NON_HERMETIC_TAG;
}
