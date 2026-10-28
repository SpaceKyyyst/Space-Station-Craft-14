package net.ssc14.client.block;

import java.util.List;
import net.minecraft.client.color.block.BlockTintSources;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

/** Client-only tint binding for planetary vegetation. */
@EventBusSubscriber(modid = Ssc14Identity.MOD_ID, value = Dist.CLIENT)
public final class PlanetaryBlockColorHandlers {
    private PlanetaryBlockColorHandlers() {
    }

    @SubscribeEvent
    public static void registerBlockTints(RegisterColorHandlersEvent.BlockTintSources event) {
        event.getBlockColors().register(
                List.of(BlockTintSources.grass()),
                Ssc14Blocks.GRASS_LIGHT_BLOCK.get(),
                Ssc14Blocks.LOW_PLANET_GRASS.get(),
                Ssc14Blocks.MED_PLANET_GRASS.get());
        event.getBlockColors().register(
                List.of(BlockTintSources.foliage()), Ssc14Blocks.PLANETARY_TREE_FOLIAGE.get());
    }
}
