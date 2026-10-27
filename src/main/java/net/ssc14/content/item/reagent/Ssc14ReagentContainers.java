package net.ssc14.content.item.reagent;

import java.util.Map;
import net.minecraft.resources.Identifier;
import net.ssc14.common.Ssc14Identity;

/** Canonical capacities and sealed-container definitions. */
public final class Ssc14ReagentContainers {
    public static final int DEFAULT_CAPACITY = 100;
    public static final int FLOUR_CAPACITY = 50;
    public static final int SUGAR_CAPACITY = 50;
    public static final int MILK_CAPACITY = 100;
    public static final int ALL_PURPOSE_SAUCE_CAPACITY = 30;

    public static final ReagentContainerDefinition FLOUR = definition(
            "flour", FLOUR_CAPACITY, "package_opening");
    public static final ReagentContainerDefinition SUGAR = definition(
            "sugar", SUGAR_CAPACITY, "package_opening");
    public static final ReagentContainerDefinition MILK = definition(
            "milk", MILK_CAPACITY, "items_bottle_open");
    public static final ReagentContainerDefinition ALL_PURPOSE_SAUCE = definition(
            "enzyme", ALL_PURPOSE_SAUCE_CAPACITY, "items_bottle_open");

    public static final Map<Identifier, Integer> CAPACITIES = Map.of(
            id("reagent_container"), DEFAULT_CAPACITY,
            id("flour_open"), FLOUR_CAPACITY,
            id("sugar_open"), SUGAR_CAPACITY,
            id("milk_open"), MILK_CAPACITY,
            id("all_purpose_sauce_open"), ALL_PURPOSE_SAUCE_CAPACITY);

    private Ssc14ReagentContainers() { }

    private static ReagentContainerDefinition definition(
            String reagentId, int fillAmount, String soundId) {
        return new ReagentContainerDefinition(reagentId, fillAmount, id(soundId));
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
