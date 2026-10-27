package net.mcreator.ssc.init;

import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.content.tab.Ssc14CreativeTabs;

/**
 * Compatibility facade for generated integrations that still reference the legacy tab registry.
 */
@Deprecated(forRemoval = false)
public final class Ssc14ModTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY =
            Ssc14CreativeTabs.REGISTRY;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14_COATING =
            Ssc14CreativeTabs.SSC_14_COATING;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14_WALLS =
            Ssc14CreativeTabs.SSC_14_WALLS;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14_OBJECTS =
            Ssc14CreativeTabs.SSC_14_OBJECTS;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14_ITEMS =
            Ssc14CreativeTabs.SSC_14_ITEMS;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SSC_14MOBS =
            Ssc14CreativeTabs.SSC_14MOBS;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DECALS =
            Ssc14CreativeTabs.DECALS;

    private Ssc14ModTabs() { }
}
