package net.ssc14.content.attribute;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;

public final class Ssc14Attributes {
    public static final DeferredRegister<Attribute> REGISTRY =
            DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Ssc14Identity.MOD_ID);
    public static final DeferredHolder<Attribute, Attribute> PROGRESS_BAR_ATRB =
            register("progress_bar_atrb", 0.0, 0.0, 7.0);
    public static final DeferredHolder<Attribute, Attribute> XY_ZPLAYER =
            register("xy_zplayer", 0.0, -30_000_000.0, 30_000_000.0);
    public static final DeferredHolder<Attribute, Attribute> ENT_PULL =
            register("ent_pull", 0.0, 0.0, 1.0);
    public static final DeferredHolder<Attribute, Attribute> GAS_AN_GU_IATRIB =
            register("gas_an_gu_iatrib", 0.0, 0.0, 1.0);
    public static final DeferredHolder<Attribute, Attribute> OPEN_WORLD_CHECK_MENU =
            register("open_world_check_menu", 0.0, 0.0, 1.0);
    public static final DeferredHolder<Attribute, Attribute> NUTRIENTS =
            register("nutrients", 125.0, 0.0, 200.0);
    public static final DeferredHolder<Attribute, Attribute> DIGESTIVE_PROCESSES =
            register("digestive_processes", 0.0, 0.0, 10.0);
    public static final DeferredHolder<Attribute, Attribute> HEALTH_U_IATTRIBUTE =
            register("health_u_iattribute", 0.0, 0.0, 6.0);
    public static final DeferredHolder<Attribute, Attribute> LIQUID =
            register("liquid", 125.0, 0.0, 200.0);
    public static final DeferredHolder<Attribute, Attribute> FLUID_ABSORPTION =
            register("fluid_absorption", 0.0, 0.0, 10.0);
    public static final DeferredHolder<Attribute, Attribute> RACE =
            register("race", 0.0, 0.0, 2.0);

    private Ssc14Attributes() {
    }

    private static DeferredHolder<Attribute, Attribute> register(
            String path, double defaultValue, double minimumValue, double maximumValue) {
        return REGISTRY.register(path, () -> new RangedAttribute(
                "attribute." + Ssc14Identity.MOD_ID + "." + path,
                defaultValue,
                minimumValue,
                maximumValue)
                .setSyncable(true)
                .setSentiment(Attribute.Sentiment.NEUTRAL));
    }
}
