package net.ssc14.content.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.clothing.ScarfItem;
import net.ssc14.content.item.clothing.PassiveEquipmentItem;
import net.ssc14.content.item.clothing.UniformAccessoryItem;
import net.ssc14.content.item.clothing.UniformJumpsuitItem;
import net.ssc14.content.item.communication.RadioEncryptionKeyItem;
import net.ssc14.content.item.food.FoodPackagingWasteItem;
import net.ssc14.content.item.lighting.LightingComponentItem;
import net.ssc14.content.item.machinery.MachineComponentItem;
import net.ssc14.content.item.material.MaterialItem;
import net.ssc14.content.item.medical.MedicalSupplyItem;
import net.ssc14.content.item.poster.PosterItem;

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
    public static final DeferredItem<Item> TIE_BLACK = uniformAccessory("tie_black");
    public static final DeferredItem<Item> TIE_RED = uniformAccessory("tie_red");
    public static final DeferredItem<Item> TIE_SCIENCE = uniformAccessory("tie_science");
    public static final DeferredItem<Item> STOCKINGS_BLUE = uniformAccessory("stockings_blue");
    public static final DeferredItem<Item> STOCKINGS_CYAN = uniformAccessory("stockings_cyan");
    public static final DeferredItem<Item> STOCKINGS_D_PINK = uniformAccessory("stockings_d_pink");
    public static final DeferredItem<Item> STOCKINGS_GREEN = uniformAccessory("stockings_green");
    public static final DeferredItem<Item> STOCKINGS_L_PINK = uniformAccessory("stockings_l_pink");
    public static final DeferredItem<Item> STOCKINGS_ORANGE = uniformAccessory("stockings_orange");
    public static final DeferredItem<Item> STOCKINGS_PURPLE = uniformAccessory("stockings_purple");
    public static final DeferredItem<Item> STOCKINGS_YELLOW = uniformAccessory("stockings_yellow");
    public static final DeferredItem<Item> SECURITY_JUMPSUIT = uniformJumpsuit("security_jumpsuit");
    public static final DeferredItem<Item> PASSANGER_JUMPSUIT = uniformJumpsuit("passanger_jumpsuit");
    public static final DeferredItem<Item> ATMOS_JUMPSUIT = uniformJumpsuit("atmos_jumpsuit");
    public static final DeferredItem<Item> BOTANIST_JUMPSUIT = uniformJumpsuit("botanist_jumpsuit");
    public static final DeferredItem<Item> CAPTAIN_JUMPSUIT = uniformJumpsuit("captain_jumpsuit");
    public static final DeferredItem<Item> CARGO_JUMPSUIT = uniformJumpsuit("cargo_jumpsuit");
    public static final DeferredItem<Item> CE_JUMPSUIT = uniformJumpsuit("ce_jumpsuit");
    public static final DeferredItem<Item> CHEMIST_JUMPSUIT = uniformJumpsuit("chemist_jumpsuit");
    public static final DeferredItem<Item> CMO_JUMPSUIT = uniformJumpsuit("cmo_jumpsuit");
    public static final DeferredItem<Item> ENGENEER_JUMPSUIT = uniformJumpsuit("engeneer_jumpsuit");
    public static final DeferredItem<Item> HO_P_JUMPSUIT = uniformJumpsuit("ho_p_jumpsuit");
    public static final DeferredItem<Item> HO_S_JUMPSUIT = uniformJumpsuit("ho_s_jumpsuit");
    public static final DeferredItem<Item> MEDICAL_JUMPSUIT = uniformJumpsuit("medical_jumpsuit");
    public static final DeferredItem<Item> QM_JUMPSUIT = uniformJumpsuit("qm_jumpsuit");
    public static final DeferredItem<Item> RD_JUMPSUIT = uniformJumpsuit("rd_jumpsuit");
    public static final DeferredItem<Item> RND_JUMPSUIT = uniformJumpsuit("rnd_jumpsuit");
    public static final DeferredItem<Item> WARDEN_JUMPSUIT = uniformJumpsuit("warden_jumpsuit");
    public static final DeferredItem<Item> BARTENDER_JUMPSUIT = uniformJumpsuit("bartender_jumpsuit");
    public static final DeferredItem<Item> BRIGMEDIC_JUMPSUIT = uniformJumpsuit("brigmedic_jumpsuit");
    public static final DeferredItem<Item> CAPFORMAL_JUMPSUIT = uniformJumpsuit("capformal_jumpsuit");
    public static final DeferredItem<Item> CASUAL_JUMPSUIT = uniformJumpsuit("casual_jumpsuit");
    public static final DeferredItem<Item> C_ETURTLE_JUMPSUIT = uniformJumpsuit("c_eturtle_jumpsuit");
    public static final DeferredItem<Item> CENTCOM_AGENT_JUMPSUIT = uniformJumpsuit("centcom_agent_jumpsuit");
    public static final DeferredItem<Item> CENTCOM_OFFICER_JUMPSUIT = uniformJumpsuit("centcom_officer_jumpsuit");
    public static final DeferredItem<Item> CENTCOM_OFFICIAL_JUMPSUIT = uniformJumpsuit("centcom_official_jumpsuit");
    public static final DeferredItem<Item> CHAPLAIN_JUMPSUIT = uniformJumpsuit("chaplain_jumpsuit");
    public static final DeferredItem<Item> CHEF_JUMPSUIT = uniformJumpsuit("chef_jumpsuit");
    public static final DeferredItem<Item> CM_OTURTLE_JUMPSUIT = uniformJumpsuit("cm_oturtle_jumpsuit");
    public static final DeferredItem<Item> HO_SPARADE_JUMPSUIT = uniformJumpsuit("ho_sparade_jumpsuit");
    public static final DeferredItem<Item> MAID_JUMPSUIT = uniformJumpsuit("maid_jumpsuit");
    public static final DeferredItem<Item> ISOLATED_GLOVES = passiveEquipment("isolated_gloves");
    public static final DeferredItem<Item> COMBAT_BOOTS_ITEM = passiveEquipment("combat_boots_item");
    public static final DeferredItem<Item> HELMET_SECURITY = passiveEquipment("helmet_security");
    public static final DeferredItem<Item> BELT_SECURITY = passiveEquipment("belt_security");
    public static final DeferredItem<Item> HELMETS_EVA = passiveEquipment("helmets_eva");
    public static final DeferredItem<Item> GAS_MASK = passiveEquipment("gas_mask");
    public static final DeferredItem<Item> WORK_BOOTS = passiveEquipment("work_boots");
    public static final DeferredItem<Item> COAT_ATMOS = passiveEquipment("coat_atmos");
    public static final DeferredItem<Item> COAT_SECURITY = passiveEquipment("coat_security");
    public static final DeferredItem<Item> GLASS_GLASSES = passiveEquipment("glass_glasses");
    public static final DeferredItem<Item> BLACK_GLOVES = passiveEquipment("black_gloves");
    public static final DeferredItem<Item> HELMET_COSMONAUT = passiveEquipment("helmet_cosmonaut");
    public static final DeferredItem<Item> BELT_SECURITY_WEBBING = passiveEquipment("belt_security_webbing");
    public static final DeferredItem<Item> BELT_INSTRUMENTAL = passiveEquipment("belt_instrumental");
    public static final DeferredItem<Item> MASK_GAS_SECURITY = passiveEquipment("mask_gas_security");
    public static final DeferredItem<Item> HARDSUIT_SALVAGE = passiveEquipment("hardsuit_salvage");
    public static final DeferredItem<Item> HARDSUIT_SALVAGE_HELMET = passiveEquipment("hardsuit_salvage_helmet");
    public static final DeferredItem<Item> BRUTEPACK = medicalSupply("brutepack");
    public static final DeferredItem<Item> BLOODPACK = medicalSupply("bloodpack");
    public static final DeferredItem<Item> GAUZE = medicalSupply("gauze");
    public static final DeferredItem<Item> OINTMENT = medicalSupply("ointment");
    public static final DeferredItem<Item> POSTER_RANDOM_ANYTHING = poster("poster_random_anything");
    public static final DeferredItem<Item> POSTER_ANOMALY = poster("poster_anomaly");
    public static final DeferredItem<Item> POSTER_ARMORY = poster("poster_armory");
    public static final DeferredItem<Item> POSTER_ATMOS = poster("poster_atmos");
    public static final DeferredItem<Item> POSTER_BAR = poster("poster_bar");
    public static final DeferredItem<Item> POSTER_BRIDGE = poster("poster_bridge");
    public static final DeferredItem<Item> POSTER_CARGO = poster("poster_cargo");
    public static final DeferredItem<Item> POSTER_CHAPEL = poster("poster_chapel");
    public static final DeferredItem<Item> POSTER_CHEM = poster("poster_chem");
    public static final DeferredItem<Item> POSTER_COMMANDER = poster("poster_commander");
    public static final DeferredItem<Item> POSTER_DEATHSPOSAL = poster("poster_deathsposal");
    public static final DeferredItem<Item> POSTER_DETECTIVE = poster("poster_detective");
    public static final DeferredItem<Item> POSTER_DRAMA = poster("poster_drama");
    public static final DeferredItem<Item> POSTER_ENG = poster("poster_eng");
    public static final DeferredItem<Item> POSTER_EVA = poster("poster_eva");
    public static final DeferredItem<Item> POSTER_GRAVI = poster("poster_gravi");
    public static final DeferredItem<Item> POSTER_HYDRO = poster("poster_hydro");
    public static final DeferredItem<Item> POSTER_JANITOR = poster("poster_janitor");
    public static final DeferredItem<Item> POSTER_KITCHEN = poster("poster_kitchen");
    public static final DeferredItem<Item> POSTER_LAW = poster("poster_law");
    public static final DeferredItem<Item> POSTER_MAIL = poster("poster_mail");
    public static final DeferredItem<Item> POSTER_MEDBAY = poster("poster_medbay");
    public static final DeferredItem<Item> POSTER_MEDIUM_SECURE = poster("poster_medium_secure");
    public static final DeferredItem<Item> POSTER_PODS = poster("poster_pods");
    public static final DeferredItem<Item> POSTER_PRISON = poster("poster_prison");
    public static final DeferredItem<Item> POSTER_RND = poster("poster_rnd");
    public static final DeferredItem<Item> POSTER_SALVAGE = poster("poster_salvage");
    public static final DeferredItem<Item> POSTER_SCI = poster("poster_sci");
    public static final DeferredItem<Item> POSTER_SECURITY = poster("poster_security");
    public static final DeferredItem<Item> POSTER_SPACE = poster("poster_space");
    public static final DeferredItem<Item> POSTER_SURGERY = poster("poster_surgery");
    public static final DeferredItem<Item> POSTER_TELECOMS = poster("poster_telecoms");
    public static final DeferredItem<Item> POSTER_VAULT = poster("poster_vault");
    public static final DeferredItem<Item> POSTER_VIROLOGY = poster("poster_virology");
    public static final DeferredItem<Item> POSTER_XENOARCH = poster("poster_xenoarch");

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

    private static DeferredItem<Item> uniformAccessory(String name) {
        return REGISTRY.<Item>registerItem(name, UniformAccessoryItem::new, Item.Properties::new);
    }

    private static DeferredItem<Item> uniformJumpsuit(String name) {
        return REGISTRY.<Item>registerItem(name, UniformJumpsuitItem::new, Item.Properties::new);
    }

    private static DeferredItem<Item> passiveEquipment(String name) {
        return REGISTRY.<Item>registerItem(name, PassiveEquipmentItem::new, Item.Properties::new);
    }

    private static DeferredItem<Item> medicalSupply(String name) {
        return REGISTRY.<Item>registerItem(name, MedicalSupplyItem::new, Item.Properties::new);
    }

    private static DeferredItem<Item> poster(String name) {
        return REGISTRY.<Item>registerItem(name, properties -> new PosterItem(properties, name));
    }
}
