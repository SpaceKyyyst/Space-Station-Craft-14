package net.ssc14.content.item;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14FinalBlockItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_final_blocks");
    private static final List<Expected> EXPECTED = List.of(
            expected("sheathing", Ssc14Blocks.SHEATHING, Ssc14Items.SHEATHING, 1, true, Rarity.COMMON),
            expected("base_airlock_d_1", Ssc14Blocks.BASE_AIRLOCK_D_1, Ssc14Items.BASE_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("console_of_id", Ssc14Blocks.CONSOLE_OF_ID, Ssc14Items.CONSOLE_OF_ID, 1, true, Rarity.UNCOMMON),
            expected("portable_sheater", Ssc14Blocks.PORTABLE_SHEATER, Ssc14Items.PORTABLE_SHEATER, 1, false, Rarity.COMMON),
            expected("apc", Ssc14Blocks.APC, Ssc14Items.APC, 1, true, Rarity.COMMON),
            expected("podstation", Ssc14Blocks.PODSTATION, Ssc14Items.PODSTATION, 1, true, Rarity.COMMON),
            expected("debu_ggenerator", Ssc14Blocks.DEBU_GGENERATOR, Ssc14Items.DEBU_GGENERATOR, 1, true, Rarity.EPIC),
            expected("lamp", Ssc14Blocks.LAMP, Ssc14Items.LAMP, 1, true, Rarity.COMMON),
            expected("smes", Ssc14Blocks.SMES, Ssc14Items.SMES, 1, true, Rarity.COMMON),
            expected("tech_lamp", Ssc14Blocks.TECH_LAMP, Ssc14Items.TECH_LAMP, 1, true, Rarity.COMMON),
            expected("debug_lamp", Ssc14Blocks.DEBUG_LAMP, Ssc14Items.DEBUG_LAMP, 1, true, Rarity.COMMON),
            expected("debug_tech_lamp", Ssc14Blocks.DEBUG_TECH_LAMP, Ssc14Items.DEBUG_TECH_LAMP, 1, true, Rarity.COMMON),
            expected("gravity_generator", Ssc14Blocks.GRAVITY_GENERATOR, Ssc14Items.GRAVITY_GENERATOR, 1, true, Rarity.COMMON),
            expected("c_4", Ssc14Blocks.C_4, Ssc14Items.C_4, 1, false, Rarity.COMMON),
            expected("machine_frame", Ssc14Blocks.MACHINE_FRAME, Ssc14Items.MACHINE_FRAME, 1, false, Rarity.COMMON),
            expected("machine_frame_2", Ssc14Blocks.MACHINE_FRAME_2, Ssc14Items.MACHINE_FRAME_2, 1, false, Rarity.COMMON),
            expected("steel_table", Ssc14Blocks.STEEL_TABLE, Ssc14Items.STEEL_TABLE, 1, false, Rarity.COMMON),
            expected("steel_chair", Ssc14Blocks.STEEL_CHAIR, Ssc14Items.STEEL_CHAIR, 1, false, Rarity.COMMON),
            expected("wood_chair", Ssc14Blocks.WOOD_CHAIR, Ssc14Items.WOOD_CHAIR, 1, false, Rarity.COMMON),
            expected("ofice_black_chair", Ssc14Blocks.OFICE_BLACK_CHAIR, Ssc14Items.OFICE_BLACK_CHAIR, 1, false, Rarity.COMMON),
            expected("ofice_white_chair", Ssc14Blocks.OFICE_WHITE_CHAIR, Ssc14Items.OFICE_WHITE_CHAIR, 1, false, Rarity.COMMON),
            expected("bar_chair", Ssc14Blocks.BAR_CHAIR, Ssc14Items.BAR_CHAIR, 1, false, Rarity.COMMON),
            expected("kostilniblok", Ssc14Blocks.KOSTILNIBLOK, Ssc14Items.KOSTILNIBLOK, 1, true, Rarity.EPIC),
            expected("debug_grav_gen", Ssc14Blocks.DEBUG_GRAV_GEN, Ssc14Items.DEBUG_GRAV_GEN, 1, true, Rarity.EPIC),
            expected("nuclear_warhead", Ssc14Blocks.NUCLEAR_WARHEAD, Ssc14Items.NUCLEAR_WARHEAD, 1, false, Rarity.COMMON),
            expected("computer", Ssc14Blocks.COMPUTER, Ssc14Items.COMPUTER, 1, true, Rarity.UNCOMMON),
            expected("closet_secure", Ssc14Blocks.CLOSET_SECURE, Ssc14Items.CLOSET_SECURE, 1, true, Rarity.COMMON),
            expected("closet", Ssc14Blocks.CLOSET, Ssc14Items.CLOSET, 1, true, Rarity.COMMON),
            expected("debug_13", Ssc14Blocks.DEBUG_13, Ssc14Items.DEBUG_13, 64, false, Rarity.COMMON),
            expected("extinguisher_cabinet", Ssc14Blocks.EXTINGUISHER_CABINET, Ssc14Items.EXTINGUISHER_CABINET, 1, false, Rarity.COMMON),
            expected("communication_servers", Ssc14Blocks.COMMUNICATION_SERVERS, Ssc14Items.COMMUNICATION_SERVERS, 1, false, Rarity.COMMON),
            expected("wood_table", Ssc14Blocks.WOOD_TABLE, Ssc14Items.WOOD_TABLE, 1, false, Rarity.COMMON),
            expected("communication_console", Ssc14Blocks.COMMUNICATION_CONSOLE, Ssc14Items.COMMUNICATION_CONSOLE, 1, true, Rarity.UNCOMMON),
            expected("tech_airlock_d_1", Ssc14Blocks.TECH_AIRLOCK_D_1, Ssc14Items.TECH_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("gas_producer_o_2", Ssc14Blocks.GAS_PRODUCER_O_2, Ssc14Items.GAS_PRODUCER_O_2, 1, false, Rarity.COMMON),
            expected("vending_aut_1", Ssc14Blocks.VENDING_AUT_1, Ssc14Items.VENDING_AUT_1, 64, true, Rarity.COMMON),
            expected("shutters", Ssc14Blocks.SHUTTERS, Ssc14Items.SHUTTERS, 1, false, Rarity.COMMON),
            expected("button", Ssc14Blocks.BUTTON, Ssc14Items.BUTTON, 1, false, Rarity.COMMON),
            expected("microwawe", Ssc14Blocks.MICROWAWE, Ssc14Items.MICROWAWE, 1, false, Rarity.COMMON),
            expected("atmospherics_airlock_d_1", Ssc14Blocks.ATMOSPHERICS_AIRLOCK_D_1, Ssc14Items.ATMOSPHERICS_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("cargo_airlock_d_1", Ssc14Blocks.CARGO_AIRLOCK_D_1, Ssc14Items.CARGO_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("cent_com_airlock_d_1", Ssc14Blocks.CENT_COM_AIRLOCK_D_1, Ssc14Items.CENT_COM_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("chemistry_airlock_d_1", Ssc14Blocks.CHEMISTRY_AIRLOCK_D_1, Ssc14Items.CHEMISTRY_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("command_airlock_d_1", Ssc14Blocks.COMMAND_AIRLOCK_D_1, Ssc14Items.COMMAND_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("engineering_airlock_d_1", Ssc14Blocks.ENGINEERING_AIRLOCK_D_1, Ssc14Items.ENGINEERING_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("hydroponics_airlock_d_1", Ssc14Blocks.HYDROPONICS_AIRLOCK_D_1, Ssc14Items.HYDROPONICS_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("medical_airlock_d_1", Ssc14Blocks.MEDICAL_AIRLOCK_D_1, Ssc14Items.MEDICAL_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("salvage_airlock_d_1", Ssc14Blocks.SALVAGE_AIRLOCK_D_1, Ssc14Items.SALVAGE_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("science_airlock_d_1", Ssc14Blocks.SCIENCE_AIRLOCK_D_1, Ssc14Items.SCIENCE_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("security_airlock_d_1", Ssc14Blocks.SECURITY_AIRLOCK_D_1, Ssc14Items.SECURITY_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("syndicate_airlock_d_1", Ssc14Blocks.SYNDICATE_AIRLOCK_D_1, Ssc14Items.SYNDICATE_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("virology_airlock_d_1", Ssc14Blocks.VIROLOGY_AIRLOCK_D_1, Ssc14Items.VIROLOGY_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("closet_emerg", Ssc14Blocks.CLOSET_EMERG, Ssc14Items.CLOSET_EMERG, 1, true, Rarity.COMMON),
            expected("closet_fire", Ssc14Blocks.CLOSET_FIRE, Ssc14Items.CLOSET_FIRE, 1, true, Rarity.COMMON),
            expected("closet_black", Ssc14Blocks.CLOSET_BLACK, Ssc14Items.CLOSET_BLACK, 1, true, Rarity.COMMON),
            expected("closet_blue", Ssc14Blocks.CLOSET_BLUE, Ssc14Items.CLOSET_BLUE, 1, true, Rarity.COMMON),
            expected("closet_gray", Ssc14Blocks.CLOSET_GRAY, Ssc14Items.CLOSET_GRAY, 1, true, Rarity.COMMON),
            expected("closet_green", Ssc14Blocks.CLOSET_GREEN, Ssc14Items.CLOSET_GREEN, 1, true, Rarity.COMMON),
            expected("closet_mixed", Ssc14Blocks.CLOSET_MIXED, Ssc14Items.CLOSET_MIXED, 1, true, Rarity.COMMON),
            expected("closet_n_2", Ssc14Blocks.CLOSET_N_2, Ssc14Items.CLOSET_N_2, 1, true, Rarity.COMMON),
            expected("closet_orange", Ssc14Blocks.CLOSET_ORANGE, Ssc14Items.CLOSET_ORANGE, 1, true, Rarity.COMMON),
            expected("closet_pink", Ssc14Blocks.CLOSET_PINK, Ssc14Items.CLOSET_PINK, 1, true, Rarity.COMMON),
            expected("glass_table", Ssc14Blocks.GLASS_TABLE, Ssc14Items.GLASS_TABLE, 1, false, Rarity.COMMON),
            expected("glass_airlock_d_1", Ssc14Blocks.GLASS_AIRLOCK_D_1, Ssc14Items.GLASS_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("base_glass_airlock_d_1", Ssc14Blocks.BASE_GLASS_AIRLOCK_D_1, Ssc14Items.BASE_GLASS_AIRLOCK_D_1, 1, true, Rarity.COMMON),
            expected("fuel_tank", Ssc14Blocks.FUEL_TANK, Ssc14Items.FUEL_TANK, 1, false, Rarity.COMMON),
            expected("order_console", Ssc14Blocks.ORDER_CONSOLE, Ssc14Items.ORDER_CONSOLE, 1, true, Rarity.UNCOMMON),
            expected("order_pallet", Ssc14Blocks.ORDER_PALLET, Ssc14Items.ORDER_PALLET, 1, false, Rarity.COMMON),
            expected("sale_pallet", Ssc14Blocks.SALE_PALLET, Ssc14Items.SALE_PALLET, 1, false, Rarity.COMMON),
            expected("sale_console", Ssc14Blocks.SALE_CONSOLE, Ssc14Items.SALE_CONSOLE, 1, true, Rarity.UNCOMMON),
            expected("arm_chair_base", Ssc14Blocks.ARM_CHAIR_BASE, Ssc14Items.ARM_CHAIR_BASE, 1, false, Rarity.COMMON),
            expected("arm_chair_brown", Ssc14Blocks.ARM_CHAIR_BROWN, Ssc14Items.ARM_CHAIR_BROWN, 1, false, Rarity.COMMON),
            expected("arm_chair_carp", Ssc14Blocks.ARM_CHAIR_CARP, Ssc14Items.ARM_CHAIR_CARP, 1, false, Rarity.COMMON),
            expected("emergency_light", Ssc14Blocks.EMERGENCY_LIGHT, Ssc14Items.EMERGENCY_LIGHT, 1, true, Rarity.COMMON));

    private Ssc14FinalBlockItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14FinalBlockItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_final_blocks_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (Expected expected : EXPECTED) {
            var item = expected.item().get();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.path() + " item ID changed");
            helper.assertTrue(item instanceof BlockItem blockItem
                            && blockItem.getBlock() == expected.block().get(),
                    expected.path() + " no longer places its canonical block");
            var stack = item.getDefaultInstance();
            helper.assertTrue(stack.getMaxStackSize() == expected.stackSize(),
                    expected.path() + " stack limit changed");
            helper.assertTrue(stack.has(DataComponents.DAMAGE_RESISTANT) == expected.fireResistant(),
                    expected.path() + " fire resistance changed");
            helper.assertTrue(stack.getRarity() == expected.rarity(),
                    expected.path() + " rarity changed");
        }
        helper.succeed();
    }

    private static Expected expected(String path, DeferredBlock<?> block,
            DeferredItem<?> item, int stackSize, boolean fireResistant, Rarity rarity) {
        return new Expected(path, block, item, stackSize, fireResistant, rarity);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record Expected(String path, DeferredBlock<?> block, DeferredItem<?> item,
            int stackSize, boolean fireResistant, Rarity rarity) { }
}
