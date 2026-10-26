package net.ssc14.content.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.lighting.LightingComponentItem;
import net.ssc14.content.item.machinery.MachineComponentItem;
import net.ssc14.content.item.material.MaterialItem;

/** Canonical owner of SSC14 item registrations. */
public final class Ssc14Items {
    public static final DeferredRegister.Items REGISTRY =
            DeferredRegister.createItems(Ssc14Identity.MOD_ID);

    public static final DeferredItem<Item> SILVER = material("silver");
    public static final DeferredItem<Item> SSC_14_GOLD = material("ssc_14_gold");
    public static final DeferredItem<Item> URAN = material("uran");
    public static final DeferredItem<Item> PLASMA = material("plasma");
    public static final DeferredItem<Item> PLASTIC = material("plastic");
    public static final DeferredItem<Item> GLASS = material("glass");
    public static final DeferredItem<Item> ARM_GLASS = material("arm_glass");
    public static final DeferredItem<Item> RAW_IRON = material("raw_iron");
    public static final DeferredItem<Item> RAW_QUARTZ = material("raw_quartz");
    public static final DeferredItem<Item> RAW_COAL = material("raw_coal");
    public static final DeferredItem<Item> RAW_GOLD = material("raw_gold");
    public static final DeferredItem<Item> RAW_PLASMA = material("raw_plasma");
    public static final DeferredItem<Item> RAW_SILVER = material("raw_silver");
    public static final DeferredItem<Item> RAW_URAN = material("raw_uran");
    public static final DeferredItem<Item> RAW_DIAMOND = material("raw_diamond");
    public static final DeferredItem<Item> CAPACITOR =
            machineComponent("capacitor", MachineComponentItem.BULK_STACK_SIZE);
    public static final DeferredItem<Item> MATTER_BIN =
            machineComponent("matter_bin", MachineComponentItem.BULK_STACK_SIZE);
    public static final DeferredItem<Item> MICRO_MANIPULATOR =
            machineComponent("micro_manipulator", MachineComponentItem.BULK_STACK_SIZE);
    public static final DeferredItem<Item> SUBSTATION_BOARD =
            machineComponent("substation_board", MachineComponentItem.SINGLE_STACK_SIZE);
    public static final DeferredItem<Item> SMES_BOARD =
            machineComponent("smes_board", MachineComponentItem.SINGLE_STACK_SIZE);
    public static final DeferredItem<Item> APC_BOARD =
            machineComponent("apc_board", MachineComponentItem.SINGLE_STACK_SIZE);
    public static final DeferredItem<Item> LOW_BATTERIE =
            machineComponent("low_batterie", MachineComponentItem.SINGLE_STACK_SIZE);
    public static final DeferredItem<Item> LED_LIGHT_TUBE = lightingComponent("led_light_tube");
    public static final DeferredItem<Item> LIGHT_BULB = lightingComponent("light_bulb");
    public static final DeferredItem<Item> LED_LIGHT_TUBE_BROKEN =
            lightingComponent("led_light_tube_broken");
    public static final DeferredItem<Item> LIGHT_BULB_BROKEN =
            lightingComponent("light_bulb_broken");

    private Ssc14Items() { }

    private static DeferredItem<Item> material(String name) {
        return REGISTRY.<Item>registerItem(name, MaterialItem::new, Item.Properties::new);
    }

    private static DeferredItem<Item> machineComponent(String name, int maxStackSize) {
        return REGISTRY.<Item>registerItem(name,
                properties -> new MachineComponentItem(properties, maxStackSize), Item.Properties::new);
    }

    private static DeferredItem<Item> lightingComponent(String name) {
        return REGISTRY.<Item>registerItem(name, LightingComponentItem::new, Item.Properties::new);
    }
}
