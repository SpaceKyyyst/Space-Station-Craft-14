package net.mcreator.ssc.client;

import net.mcreator.ssc.integration.LegacyReagentCatalog;
import net.mcreator.ssc.Ssc14Mod;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSources;

/** Client rendering for the shared reagent registry, rebuilt on resource reload. */
@EventBusSubscriber(modid = Ssc14Mod.MODID, value = Dist.CLIENT)
public final class ReagentFluidModels {
    private ReagentFluidModels() { }

    @SubscribeEvent
    public static void register(RegisterFluidModelsEvent event) {
        var still = new Material(Identifier.withDefaultNamespace("block/water_still"));
        var flowing = new Material(Identifier.withDefaultNamespace("block/water_flow"));
        for (var reagent : LegacyReagentCatalog.all()) {
            if (reagent.getId().equals("water")) continue;
            var model = new FluidModel.Unbaked(still, flowing, null,
                    FluidTintSources.constant(0xFF000000 | reagent.getColorRgb()));
            event.register(model,
                    BuiltInRegistries.FLUID.getValue(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, reagent.getId())),
                    BuiltInRegistries.FLUID.getValue(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, reagent.getId() + "_flowing")));
        }
    }
}
