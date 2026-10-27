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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14CanonicalBlockItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_canonical_blocks");
    private static final List<Expected> EXPECTED = List.of(
            expected("rod_floor", Ssc14Blocks.ROD_FLOOR, Ssc14Items.ROD_FLOOR, 1, true),
            expected("steel_wall", Ssc14Blocks.STEEL_WALL, Ssc14Items.STEEL_WALL, 30, true),
            expected("wall_carcase", Ssc14Blocks.WALL_CARCASE, Ssc14Items.WALL_CARCASE, 30, true),
            expected("uper_sheathing", Ssc14Blocks.UPER_SHEATHING, Ssc14Items.UPER_SHEATHING, 1, true),
            expected("rod_up_floor", Ssc14Blocks.ROD_UP_FLOOR, Ssc14Items.ROD_UP_FLOOR, 1, true),
            expected("up_title", Ssc14Blocks.UP_TITLE, Ssc14Items.UP_TITLE, 30, true),
            expected("base_window", Ssc14Blocks.BASE_WINDOW, Ssc14Items.BASE_WINDOW, 30, true),
            expected("plasteel_wall", Ssc14Blocks.PLASTEEL_WALL, Ssc14Items.PLASTEEL_WALL, 30, true),
            expected("plasteel_wall_carcase", Ssc14Blocks.PLASTEEL_WALL_CARCASE, Ssc14Items.PLASTEEL_WALL_CARCASE, 30, true),
            expected("up_title_steel", Ssc14Blocks.UP_TITLE_STEEL, Ssc14Items.UP_TITLE_STEEL, 30, true),
            expected("up_title_dark", Ssc14Blocks.UP_TITLE_DARK, Ssc14Items.UP_TITLE_DARK, 30, true),
            expected("armored_window", Ssc14Blocks.ARMORED_WINDOW, Ssc14Items.ARMORED_WINDOW, 30, true),
            expected("grille", Ssc14Blocks.GRILLE, Ssc14Items.GRILLE, 1, true),
            expected("broken_grille", Ssc14Blocks.BROKEN_GRILLE, Ssc14Items.BROKEN_GRILLE, 1, true),
            expected("cave_floor_1", Ssc14Blocks.CAVE_FLOOR_1, Ssc14Items.CAVE_FLOOR_1, 1, true),
            expected("catwalk_floor", Ssc14Blocks.CATWALK_FLOOR, Ssc14Items.CATWALK_FLOOR, 1, true),
            expected("steel_staircase", Ssc14Blocks.STEEL_STAIRCASE, Ssc14Items.STEEL_STAIRCASE, 1, false),
            expected("white_staircase", Ssc14Blocks.WHITE_STAIRCASE, Ssc14Items.WHITE_STAIRCASE, 1, false),
            expected("dark_staircase", Ssc14Blocks.DARK_STAIRCASE, Ssc14Items.DARK_STAIRCASE, 1, false),
            expected("dirt", Ssc14Blocks.DIRT, Ssc14Items.DIRT, 1, false),
            expected("grass_light_block", Ssc14Blocks.GRASS_LIGHT_BLOCK, Ssc14Items.GRASS_LIGHT_BLOCK, 1, false),
            expected("low_planet_grass", Ssc14Blocks.LOW_PLANET_GRASS, Ssc14Items.LOW_PLANET_GRASS, 1, false),
            expected("med_planet_grass", Ssc14Blocks.MED_PLANET_GRASS, Ssc14Items.MED_PLANET_GRASS, 1, false),
            expected("planetary_tree_log", Ssc14Blocks.PLANETARY_TREE_LOG, Ssc14Items.PLANETARY_TREE_LOG, 1, false),
            expected("planetary_tree_foliage", Ssc14Blocks.PLANETARY_TREE_FOLIAGE, Ssc14Items.PLANETARY_TREE_FOLIAGE, 1, false),
            expected("up_steel_staircase", Ssc14Blocks.UP_STEEL_STAIRCASE, Ssc14Items.UP_STEEL_STAIRCASE, 1, false),
            expected("up_white_staircase", Ssc14Blocks.UP_WHITE_STAIRCASE, Ssc14Items.UP_WHITE_STAIRCASE, 1, false),
            expected("up_dark_staircase", Ssc14Blocks.UP_DARK_STAIRCASE, Ssc14Items.UP_DARK_STAIRCASE, 1, false),
            expected("shuttle_wall", Ssc14Blocks.SHUTTLE_WALL, Ssc14Items.SHUTTLE_WALL, 1, true),
            expected("armored_shuttle_wall", Ssc14Blocks.ARMORED_SHUTTLE_WALL, Ssc14Items.ARMORED_SHUTTLE_WALL, 1, true),
            expected("directed_base_window", Ssc14Blocks.DIRECTED_BASE_WINDOW, Ssc14Items.DIRECTED_BASE_WINDOW, 1, true),
            expected("directed_armored_window", Ssc14Blocks.DIRECTED_ARMORED_WINDOW, Ssc14Items.DIRECTED_ARMORED_WINDOW, 1, true),
            expected("titlearcadeblue_2", Ssc14Blocks.TITLEARCADEBLUE_2, Ssc14Items.TITLEARCADEBLUE_2, 30, true),
            expected("titleeighties", Ssc14Blocks.TITLEEIGHTIES, Ssc14Items.TITLEEIGHTIES, 30, true));

    private Ssc14CanonicalBlockItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14CanonicalBlockItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_canonical_blocks_environment"));
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
        }
        helper.succeed();
    }

    private static Expected expected(String path, DeferredBlock<?> block,
            DeferredItem<?> item, int stackSize, boolean fireResistant) {
        return new Expected(path, block, item, stackSize, fireResistant);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record Expected(String path, DeferredBlock<?> block, DeferredItem<?> item,
            int stackSize, boolean fireResistant) { }
}
