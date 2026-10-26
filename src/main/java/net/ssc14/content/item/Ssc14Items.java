package net.ssc14.content.item;

import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.entity.Ssc14EntityTypes;
import net.ssc14.content.item.access.IdCardItem;
import net.ssc14.content.item.clothing.ScarfItem;
import net.ssc14.content.item.clothing.PassiveEquipmentItem;
import net.ssc14.content.item.clothing.UniformAccessoryItem;
import net.ssc14.content.item.clothing.UniformJumpsuitItem;
import net.ssc14.content.item.communication.RadioEncryptionKeyItem;
import net.ssc14.content.item.food.CookingIngredientItem;
import net.ssc14.content.item.food.EdibleFoodItem;
import net.ssc14.content.item.food.FoodPackagingWasteItem;
import net.ssc14.content.item.food.FoodDefinition;
import net.ssc14.content.item.food.FoodPropItem;
import net.ssc14.content.item.food.Ssc14FoodDefinitions;
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
    public static final DeferredItem<Item> ID_CARD_PASSANGER = idCard("id_card_passanger");
    public static final DeferredItem<Item> ID_CARD_CAPITAN = idCard("id_card_capitan");
    public static final DeferredItem<Item> ID_CARD_PNT = idCard("id_card_pnt");
    public static final DeferredItem<Item> ID_CARD_ADUTANT = idCard("id_card_adutant");
    public static final DeferredItem<Item> ID_CARD_AVD = idCard("id_card_avd");
    public static final DeferredItem<Item> ID_CARD_OSH = idCard("id_card_osh");
    public static final DeferredItem<Item> ID_CARD_MIME = idCard("id_card_mime");
    public static final DeferredItem<Item> ID_CARD_CLOWN = idCard("id_card_clown");
    public static final DeferredItem<Item> ID_CARD_HO_P = idCard("id_card_ho_p");
    public static final DeferredItem<Item> ID_CARD_SERVICE_W = idCard("id_card_service_w");
    public static final DeferredItem<Item> ID_CARD_CLEANER = idCard("id_card_cleaner");
    public static final DeferredItem<Item> ID_CARD_CHEF = idCard("id_card_chef");
    public static final DeferredItem<Item> ID_CARD_BOTANIK = idCard("id_card_botanik");
    public static final DeferredItem<Item> ID_CARD_BARMEN = idCard("id_card_barmen");
    public static final DeferredItem<Item> ID_CARD_PRIEST = idCard("id_card_priest");
    public static final DeferredItem<Item> ID_CARD_QM = idCard("id_card_qm");
    public static final DeferredItem<Item> ID_CARD_UTILIZATOR = idCard("id_card_utilizator");
    public static final DeferredItem<Item> ID_CARD_LOADER = idCard("id_card_loader");
    public static final DeferredItem<Item> ID_CARD_REPORTER = idCard("id_card_reporter");
    public static final DeferredItem<Item> ID_CARD_BOXER = idCard("id_card_boxer");
    public static final DeferredItem<Item> ID_CARD_BIBLIOTEKAR = idCard("id_card_bibliotekar");
    public static final DeferredItem<Item> ID_CARD_ZOOTECHNIK = idCard("id_card_zootechnik");
    public static final DeferredItem<Item> ID_CARD_CE = idCard("id_card_ce");
    public static final DeferredItem<Item> ID_CARD_VE = idCard("id_card_ve");
    public static final DeferredItem<Item> ID_CARD_ATMOS = idCard("id_card_atmos");
    public static final DeferredItem<Item> ID_CARD_ENGENEER = idCard("id_card_engeneer");
    public static final DeferredItem<Item> ID_CARD_TECH_ASSISTENT = idCard("id_card_tech_assistent");
    public static final DeferredItem<Item> ID_CARD_MUSICANT = idCard("id_card_musicant");
    public static final DeferredItem<Item> ID_CARD_HO_S = idCard("id_card_ho_s");
    public static final DeferredItem<Item> ID_CARD_INSTRUCTOR_S = idCard("id_card_instructor_s");
    public static final DeferredItem<Item> ID_CARD_WARD = idCard("id_card_ward");
    public static final DeferredItem<Item> ID_CARD_DETECTIVE = idCard("id_card_detective");
    public static final DeferredItem<Item> ID_CARD_BRIG_MED = idCard("id_card_brig_med");
    public static final DeferredItem<Item> ID_CARD_OFICER_S = idCard("id_card_oficer_s");
    public static final DeferredItem<Item> ID_CARD_KADET_S = idCard("id_card_kadet_s");
    public static final DeferredItem<Item> ID_CARD_CMO = idCard("id_card_cmo");
    public static final DeferredItem<Item> ID_CARD_VM = idCard("id_card_vm");
    public static final DeferredItem<Item> ID_CARD_CHIMIC = idCard("id_card_chimic");
    public static final DeferredItem<Item> ID_CARD_PARAMED = idCard("id_card_paramed");
    public static final DeferredItem<Item> ID_CARD_MEDIC = idCard("id_card_medic");
    public static final DeferredItem<Item> ID_CARD_PSIHOLOG = idCard("id_card_psiholog");
    public static final DeferredItem<Item> ID_CARD_INTERN = idCard("id_card_intern");
    public static final DeferredItem<Item> ID_CARD_RD = idCard("id_card_rd");
    public static final DeferredItem<Item> ID_CARD_VS = idCard("id_card_vs");
    public static final DeferredItem<Item> ID_CARD_SCIENTIST = idCard("id_card_scientist");
    public static final DeferredItem<Item> ID_CARD_SCI_ASSISTENT = idCard("id_card_sci_assistent");
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
    public static final DeferredItem<Item> WALL_CARCASE_ENTIT_SPAWN_EGG =
            spawnEgg("wall_carcase_entit_spawn_egg", Ssc14EntityTypes.WALL_CARCASE_ENTIT);
    public static final DeferredItem<Item> ID_CONSOLE_ENTITY_SPAWN_EGG =
            spawnEgg("id_console_entity_spawn_egg", Ssc14EntityTypes.ID_CONSOLE_ENTITY);
    public static final DeferredItem<Item> PLASSTEEL_WALL_CARCASE_ENTIT_SPAWN_EGG =
            spawnEgg("plassteel_wall_carcase_entit_spawn_egg", Ssc14EntityTypes.PLASSTEEL_WALL_CARCASE_ENTIT);
    public static final DeferredItem<Item> MOUSE_BROWN_SPAWN_EGG =
            spawnEgg("mouse_brown_spawn_egg", Ssc14EntityTypes.MOUSE_BROWN);
    public static final DeferredItem<Item> MOUSE_GRAY_SPAWN_EGG =
            spawnEgg("mouse_gray_spawn_egg", Ssc14EntityTypes.MOUSE_GRAY);
    public static final DeferredItem<Item> MOUSE_WHITE_SPAWN_EGG =
            spawnEgg("mouse_white_spawn_egg", Ssc14EntityTypes.MOUSE_WHITE);
    public static final DeferredItem<Item> CARP_SPAWN_EGG =
            spawnEgg("carp_spawn_egg", Ssc14EntityTypes.CARP);
    public static final DeferredItem<Item> DOUGH = cookingIngredient("dough");
    public static final DeferredItem<Item> DOUGH_PIECE = cookingIngredient("dough_piece");
    public static final DeferredItem<Item> EGG = cookingIngredient("egg");
    public static final DeferredItem<Item> EGGSHELL = cookingIngredient("eggshell");
    public static final DeferredItem<Item> BREAD = edibleFood("bread", Ssc14FoodDefinitions.BREAD);
    public static final DeferredItem<Item> BREAD_SLICE =
            edibleFood("bread_slice", Ssc14FoodDefinitions.BREAD_SLICE);
    public static final DeferredItem<Item> BUN = edibleFood("bun", Ssc14FoodDefinitions.BUN);
    public static final DeferredItem<Item> TOFU = edibleFood("tofu", Ssc14FoodDefinitions.TOFU);
    public static final DeferredItem<Item> TOFU_BURGER =
            edibleFood("tofu_burger", Ssc14FoodDefinitions.TOFU_BURGER);
    public static final DeferredItem<Item> TOFU_SLICE =
            edibleFood("tofu_slice", Ssc14FoodDefinitions.TOFU_SLICE);
    public static final DeferredItem<Item> CHEESE =
            edibleFood("cheese", Ssc14FoodDefinitions.CHEESE);
    public static final DeferredItem<Item> CUTLET =
            edibleFood("cutlet", Ssc14FoodDefinitions.CUTLET);
    public static final DeferredItem<Item> FRIED_CUTLET =
            edibleFood("fried_cutlet", Ssc14FoodDefinitions.FRIED_CUTLET);
    public static final DeferredItem<Item> MEAT = edibleFood("meat", Ssc14FoodDefinitions.MEAT);
    public static final DeferredItem<Item> FRIED_MEAT =
            edibleFood("fried_meat", Ssc14FoodDefinitions.FRIED_MEAT);
    public static final DeferredItem<Item> ROTTEN_MEAT =
            edibleFood("rotten_meat", Ssc14FoodDefinitions.ROTTEN_MEAT);
    public static final DeferredItem<Item> ENERGY_BAR_2 =
            edibleFood("energy_bar_2", Ssc14FoodDefinitions.ENERGY_BAR_2);
    public static final DeferredItem<Item> CATBURGER =
            edibleFood("catburger", Ssc14FoodDefinitions.CATBURGER);
    public static final DeferredItem<Item> CHEESEBURGER =
            edibleFood("cheeseburger", Ssc14FoodDefinitions.CHEESEBURGER);
    public static final DeferredItem<Item> CHEESE_HONKERS =
            edibleFood("cheese_honkers",
                    Ssc14FoodDefinitions.cheeseHonkers(CHEESE_HONKERS_TRASH));
    public static final DeferredItem<Item> CHIPS =
            edibleFood("chips", Ssc14FoodDefinitions.chips(CHIPS_TRASH));
    public static final DeferredItem<Item> BORITOS =
            edibleFood("boritos", Ssc14FoodDefinitions.boritos(BORITOS_TRASH));
    public static final DeferredItem<Item> POPCORN =
            edibleFood("popcorn", Ssc14FoodDefinitions.popcorn(POPCORN_TRASH));
    public static final DeferredItem<Item> BAGEL = foodProp("bagel");
    public static final DeferredItem<Item> BAGUETTE = foodProp("baguette");
    public static final DeferredItem<Item> BANANA_NUT_BREAD = foodProp("banana_nut_bread");
    public static final DeferredItem<Item> CHEESE_WHEEL = foodProp("cheese_wheel");
    public static final DeferredItem<Item> CREAM_CHEESE_BREAD =
            foodProp("cream_cheese_bread");
    public static final DeferredItem<Item> FLY_AMANITA = foodProp("fly_amanita");
    public static final DeferredItem<Item> MEATLOAF = foodProp("meatloaf");
    public static final DeferredItem<Item> MOLDY_BREAD_PIECE =
            foodProp("moldy_bread_piece");
    public static final DeferredItem<Item> POPPY_SEED_BAGEL =
            foodProp("poppy_seed_bagel");
    public static final DeferredItem<Item> POTATO = foodProp("potato");
    public static final DeferredItem<Item> SAUSAGE_BREAD = foodProp("sausage_bread");
    public static final DeferredItem<Item> SPACE_FRIES = foodProp("space_fries");
    public static final DeferredItem<Item> TACO_FLATBREAD = foodProp("taco_flatbread");
    public static final DeferredItem<Item> TOAST_WITH_BUTTER =
            foodProp("toast_with_butter");
    public static final DeferredItem<Item> TOFU_BREAD = foodProp("tofu_bread");

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

    private static DeferredItem<Item> idCard(String name) {
        return REGISTRY.<Item>registerItem(name, IdCardItem::new, Item.Properties::new);
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

    private static DeferredItem<Item> cookingIngredient(String name) {
        return REGISTRY.<Item>registerItem(name, CookingIngredientItem::new, Item.Properties::new);
    }

    private static DeferredItem<Item> edibleFood(String name, FoodDefinition definition) {
        return REGISTRY.<Item>registerItem(name,
                properties -> new EdibleFoodItem(properties, definition), Item.Properties::new);
    }

    private static DeferredItem<Item> foodProp(String name) {
        return REGISTRY.<Item>registerItem(name, FoodPropItem::new, Item.Properties::new);
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

    private static DeferredItem<Item> spawnEgg(
            String name, Supplier<? extends EntityType<?>> entityType) {
        return REGISTRY.<Item>registerItem(name,
                properties -> new SpawnEggItem(properties.spawnEgg(entityType.get())));
    }
}
