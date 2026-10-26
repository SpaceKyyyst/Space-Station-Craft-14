package net.ssc14.content.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.clothing.ScarfItem;
import net.ssc14.content.item.communication.RadioEncryptionKeyItem;
import net.ssc14.content.item.food.FoodPackagingWasteItem;
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
    public static final DeferredItem<Item> ENCRYPTION_KEY_PASSANGER =
            encryptionKey("encryption_key_passanger");
    public static final DeferredItem<Item> ENCRYPTION_KEY_COMMAND =
            encryptionKey("encryption_key_command");
    public static final DeferredItem<Item> ENCRYPTION_KEY_ENGENEER =
            encryptionKey("encryption_key_engeneer");
    public static final DeferredItem<Item> ENCRYPTION_KEY_MEDICAL =
            encryptionKey("encryption_key_medical");
    public static final DeferredItem<Item> ENCRYPTION_KEY_RND =
            encryptionKey("encryption_key_rnd");
    public static final DeferredItem<Item> ENCRYPTION_KEY_SECURITY =
            encryptionKey("encryption_key_security");
    public static final DeferredItem<Item> ENCRYPTION_KEY_SERVICE =
            encryptionKey("encryption_key_service");
    public static final DeferredItem<Item> ENCRYPTION_KEY_CARGO =
            encryptionKey("encryption_key_cargo");
    public static final DeferredItem<Item> RED_SCARF = scarf("red_scarf");
    public static final DeferredItem<Item> BLACK_SCARF = scarf("black_scarf");
    public static final DeferredItem<Item> BLACK_GREEN_SCARF = scarf("black_green_scarf");
    public static final DeferredItem<Item> BLUE_SCARF = scarf("blue_scarf");
    public static final DeferredItem<Item> BROWN_SCARF = scarf("brown_scarf");
    public static final DeferredItem<Item> GREEN_SCARF = scarf("green_scarf");
    public static final DeferredItem<Item> LIGHT_BLUE_SCARF = scarf("light_blue_scarf");
    public static final DeferredItem<Item> LIGHT_BLUE_WHITE_SCARF = scarf("light_blue_white_scarf");
    public static final DeferredItem<Item> ORANGE_SCARF = scarf("orange_scarf");
    public static final DeferredItem<Item> PURPLE_SCARF = scarf("purple_scarf");
    public static final DeferredItem<Item> SINDY_SCARF = scarf("sindy_scarf");
    public static final DeferredItem<Item> TRNS_SCARF = scarf("trns_scarf");
    public static final DeferredItem<Item> YELLOW_SCARF = scarf("yellow_scarf");
    public static final DeferredItem<Item> ENERGY_BAR_TRASH = foodPackagingWaste("energy_bar_trash");
    public static final DeferredItem<Item> CHEESE_HONKERS_TRASH =
            foodPackagingWaste("cheese_honkers_trash");
    public static final DeferredItem<Item> CHIPS_TRASH = foodPackagingWaste("chips_trash");
    public static final DeferredItem<Item> BORITOS_TRASH = foodPackagingWaste("boritos_trash");
    public static final DeferredItem<Item> POPCORN_TRASH = foodPackagingWaste("popcorn_trash");

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

    private static DeferredItem<Item> encryptionKey(String name) {
        return REGISTRY.<Item>registerItem(name, RadioEncryptionKeyItem::new, Item.Properties::new);
    }

    private static DeferredItem<Item> scarf(String name) {
        return REGISTRY.<Item>registerItem(name, ScarfItem::new, Item.Properties::new);
    }

    private static DeferredItem<Item> foodPackagingWaste(String name) {
        return REGISTRY.<Item>registerItem(name, FoodPackagingWasteItem::new, Item.Properties::new);
    }
}
