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
public final class Ssc14TileBlockItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_tile_blocks");
    private static final List<Expected> EXPECTED = List.of(
            expected("title_steel", Ssc14Blocks.TITLE_STEEL, Ssc14Items.TITLE_STEEL),
            expected("title_dark", Ssc14Blocks.TITLE_DARK, Ssc14Items.TITLE_DARK),
            expected("title_steel_diagonal_mini", Ssc14Blocks.TITLE_STEEL_DIAGONAL_MINI, Ssc14Items.TITLE_STEEL_DIAGONAL_MINI),
            expected("title_steel_diagonal", Ssc14Blocks.TITLE_STEEL_DIAGONAL, Ssc14Items.TITLE_STEEL_DIAGONAL),
            expected("title_steel_mini", Ssc14Blocks.TITLE_STEEL_MINI, Ssc14Items.TITLE_STEEL_MINI),
            expected("title_steel_mono", Ssc14Blocks.TITLE_STEEL_MONO, Ssc14Items.TITLE_STEEL_MONO),
            expected("title_steel_offset", Ssc14Blocks.TITLE_STEEL_OFFSET, Ssc14Items.TITLE_STEEL_OFFSET),
            expected("title_steel_pavement", Ssc14Blocks.TITLE_STEEL_PAVEMENT, Ssc14Items.TITLE_STEEL_PAVEMENT),
            expected("title_steel_herringbone", Ssc14Blocks.TITLE_STEEL_HERRINGBONE, Ssc14Items.TITLE_STEEL_HERRINGBONE),
            expected("title_plastic", Ssc14Blocks.TITLE_PLASTIC, Ssc14Items.TITLE_PLASTIC),
            expected("title_dark_diagonal_mini", Ssc14Blocks.TITLE_DARK_DIAGONAL_MINI, Ssc14Items.TITLE_DARK_DIAGONAL_MINI),
            expected("title_dark_diagonal", Ssc14Blocks.TITLE_DARK_DIAGONAL, Ssc14Items.TITLE_DARK_DIAGONAL),
            expected("title_dark_mini", Ssc14Blocks.TITLE_DARK_MINI, Ssc14Items.TITLE_DARK_MINI),
            expected("title_dark_mono", Ssc14Blocks.TITLE_DARK_MONO, Ssc14Items.TITLE_DARK_MONO),
            expected("title_dark_offset", Ssc14Blocks.TITLE_DARK_OFFSET, Ssc14Items.TITLE_DARK_OFFSET),
            expected("title_dark_pavement", Ssc14Blocks.TITLE_DARK_PAVEMENT, Ssc14Items.TITLE_DARK_PAVEMENT),
            expected("title_dark_pavement_vertical", Ssc14Blocks.TITLE_DARK_PAVEMENT_VERTICAL, Ssc14Items.TITLE_DARK_PAVEMENT_VERTICAL),
            expected("title_dark_herringbone", Ssc14Blocks.TITLE_DARK_HERRINGBONE, Ssc14Items.TITLE_DARK_HERRINGBONE),
            expected("title_dark_plastic", Ssc14Blocks.TITLE_DARK_PLASTIC, Ssc14Items.TITLE_DARK_PLASTIC),
            expected("title_white", Ssc14Blocks.TITLE_WHITE, Ssc14Items.TITLE_WHITE),
            expected("title_white_diagonal_mini", Ssc14Blocks.TITLE_WHITE_DIAGONAL_MINI, Ssc14Items.TITLE_WHITE_DIAGONAL_MINI),
            expected("title_white_diagonal", Ssc14Blocks.TITLE_WHITE_DIAGONAL, Ssc14Items.TITLE_WHITE_DIAGONAL),
            expected("title_white_herringbone", Ssc14Blocks.TITLE_WHITE_HERRINGBONE, Ssc14Items.TITLE_WHITE_HERRINGBONE),
            expected("title_white_mini", Ssc14Blocks.TITLE_WHITE_MINI, Ssc14Items.TITLE_WHITE_MINI),
            expected("title_white_mono", Ssc14Blocks.TITLE_WHITE_MONO, Ssc14Items.TITLE_WHITE_MONO),
            expected("title_white_offset", Ssc14Blocks.TITLE_WHITE_OFFSET, Ssc14Items.TITLE_WHITE_OFFSET),
            expected("title_white_pavement", Ssc14Blocks.TITLE_WHITE_PAVEMENT, Ssc14Items.TITLE_WHITE_PAVEMENT),
            expected("title_white_pavement_vertical", Ssc14Blocks.TITLE_WHITE_PAVEMENT_VERTICAL, Ssc14Items.TITLE_WHITE_PAVEMENT_VERTICAL),
            expected("title_white_plastic", Ssc14Blocks.TITLE_WHITE_PLASTIC, Ssc14Items.TITLE_WHITE_PLASTIC),
            expected("title_steel_pavement_vertical", Ssc14Blocks.TITLE_STEEL_PAVEMENT_VERTICAL, Ssc14Items.TITLE_STEEL_PAVEMENT_VERTICAL),
            expected("title_arcadeblue", Ssc14Blocks.TITLE_ARCADEBLUE, Ssc14Items.TITLE_ARCADEBLUE),
            expected("title_arcadered", Ssc14Blocks.TITLE_ARCADERED, Ssc14Items.TITLE_ARCADERED),
            expected("title_bar", Ssc14Blocks.TITLE_BAR, Ssc14Items.TITLE_BAR),
            expected("title_blue_circuit", Ssc14Blocks.TITLE_BLUE_CIRCUIT, Ssc14Items.TITLE_BLUE_CIRCUIT),
            expected("title_blue", Ssc14Blocks.TITLE_BLUE, Ssc14Items.TITLE_BLUE),
            expected("title_boxing", Ssc14Blocks.TITLE_BOXING, Ssc14Items.TITLE_BOXING),
            expected("title_cafeteria", Ssc14Blocks.TITLE_CAFETERIA, Ssc14Items.TITLE_CAFETERIA),
            expected("title_carpetclown", Ssc14Blocks.TITLE_CARPETCLOWN, Ssc14Items.TITLE_CARPETCLOWN),
            expected("title_carpetoffice", Ssc14Blocks.TITLE_CARPETOFFICE, Ssc14Items.TITLE_CARPETOFFICE),
            expected("title_checkerdark", Ssc14Blocks.TITLE_CHECKERDARK, Ssc14Items.TITLE_CHECKERDARK),
            expected("title_clown", Ssc14Blocks.TITLE_CLOWN, Ssc14Items.TITLE_CLOWN),
            expected("title_darktest", Ssc14Blocks.TITLE_DARKTEST, Ssc14Items.TITLE_DARKTEST),
            expected("title_elevatorshaft", Ssc14Blocks.TITLE_ELEVATORSHAFT, Ssc14Items.TITLE_ELEVATORSHAFT),
            expected("title_exoborg", Ssc14Blocks.TITLE_EXOBORG, Ssc14Items.TITLE_EXOBORG),
            expected("title_freezer", Ssc14Blocks.TITLE_FREEZER, Ssc14Items.TITLE_FREEZER),
            expected("title_gold", Ssc14Blocks.TITLE_GOLD, Ssc14Items.TITLE_GOLD),
            expected("title_gratingmaint", Ssc14Blocks.TITLE_GRATINGMAINT, Ssc14Items.TITLE_GRATINGMAINT),
            expected("title_greencircuit", Ssc14Blocks.TITLE_GREENCIRCUIT, Ssc14Items.TITLE_GREENCIRCUIT),
            expected("title_gym", Ssc14Blocks.TITLE_GYM, Ssc14Items.TITLE_GYM),
            expected("title_hullreinforced", Ssc14Blocks.TITLE_HULLREINFORCED, Ssc14Items.TITLE_HULLREINFORCED),
            expected("title_hull", Ssc14Blocks.TITLE_HULL, Ssc14Items.TITLE_HULL),
            expected("title_hydro", Ssc14Blocks.TITLE_HYDRO, Ssc14Items.TITLE_HYDRO),
            expected("title_kitchen", Ssc14Blocks.TITLE_KITCHEN, Ssc14Items.TITLE_KITCHEN),
            expected("title_laundry", Ssc14Blocks.TITLE_LAUNDRY, Ssc14Items.TITLE_LAUNDRY),
            expected("title_lime", Ssc14Blocks.TITLE_LIME, Ssc14Items.TITLE_LIME),
            expected("title_lino", Ssc14Blocks.TITLE_LINO, Ssc14Items.TITLE_LINO),
            expected("title_meat", Ssc14Blocks.TITLE_MEAT, Ssc14Items.TITLE_MEAT),
            expected("title_metaldiamond", Ssc14Blocks.TITLE_METALDIAMOND, Ssc14Items.TITLE_METALDIAMOND),
            expected("title_mime", Ssc14Blocks.TITLE_MIME, Ssc14Items.TITLE_MIME),
            expected("title_miningfloordark", Ssc14Blocks.TITLE_MININGFLOORDARK, Ssc14Items.TITLE_MININGFLOORDARK),
            expected("title_miningfloorlight", Ssc14Blocks.TITLE_MININGFLOORLIGHT, Ssc14Items.TITLE_MININGFLOORLIGHT),
            expected("title_miningfloor", Ssc14Blocks.TITLE_MININGFLOOR, Ssc14Items.TITLE_MININGFLOOR),
            expected("title_mono", Ssc14Blocks.TITLE_MONO, Ssc14Items.TITLE_MONO),
            expected("title_redcircuit", Ssc14Blocks.TITLE_REDCIRCUIT, Ssc14Items.TITLE_REDCIRCUIT),
            expected("title_reinforced", Ssc14Blocks.TITLE_REINFORCED, Ssc14Items.TITLE_REINFORCED),
            expected("title_rockvault", Ssc14Blocks.TITLE_ROCKVAULT, Ssc14Items.TITLE_ROCKVAULT),
            expected("title_showroom", Ssc14Blocks.TITLE_SHOWROOM, Ssc14Items.TITLE_SHOWROOM),
            expected("title_shuttleblack", Ssc14Blocks.TITLE_SHUTTLEBLACK, Ssc14Items.TITLE_SHUTTLEBLACK),
            expected("title_shuttleblue", Ssc14Blocks.TITLE_SHUTTLEBLUE, Ssc14Items.TITLE_SHUTTLEBLUE),
            expected("title_shuttlegrey", Ssc14Blocks.TITLE_SHUTTLEGREY, Ssc14Items.TITLE_SHUTTLEGREY),
            expected("title_shuttleorange", Ssc14Blocks.TITLE_SHUTTLEORANGE, Ssc14Items.TITLE_SHUTTLEORANGE),
            expected("title_shuttlepurple", Ssc14Blocks.TITLE_SHUTTLEPURPLE, Ssc14Items.TITLE_SHUTTLEPURPLE),
            expected("title_shuttlered", Ssc14Blocks.TITLE_SHUTTLERED, Ssc14Items.TITLE_SHUTTLERED),
            expected("title_shuttlewhite", Ssc14Blocks.TITLE_SHUTTLEWHITE, Ssc14Items.TITLE_SHUTTLEWHITE),
            expected("title_silver", Ssc14Blocks.TITLE_SILVER, Ssc14Items.TITLE_SILVER),
            expected("title_steeldamaged", Ssc14Blocks.TITLE_STEELDAMAGED, Ssc14Items.TITLE_STEELDAMAGED),
            expected("title_steeldirty", Ssc14Blocks.TITLE_STEELDIRTY, Ssc14Items.TITLE_STEELDIRTY),
            expected("title_steelmaint", Ssc14Blocks.TITLE_STEELMAINT, Ssc14Items.TITLE_STEELMAINT),
            expected("title_superreinforced", Ssc14Blocks.TITLE_SUPERREINFORCED, Ssc14Items.TITLE_SUPERREINFORCED),
            expected("title_techmaintdark", Ssc14Blocks.TITLE_TECHMAINTDARK, Ssc14Items.TITLE_TECHMAINTDARK),
            expected("title_techmaint", Ssc14Blocks.TITLE_TECHMAINT, Ssc14Items.TITLE_TECHMAINT),
            expected("title_woodbroken", Ssc14Blocks.TITLE_WOODBROKEN, Ssc14Items.TITLE_WOODBROKEN),
            expected("title_woodlarge", Ssc14Blocks.TITLE_WOODLARGE, Ssc14Items.TITLE_WOODLARGE),
            expected("title_wood", Ssc14Blocks.TITLE_WOOD, Ssc14Items.TITLE_WOOD),
            expected("title_woodtile", Ssc14Blocks.TITLE_WOODTILE, Ssc14Items.TITLE_WOODTILE));

    private Ssc14TileBlockItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14TileBlockItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_tile_blocks_environment"));
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
            helper.assertTrue(stack.getMaxStackSize() == 30,
                    expected.path() + " stack limit changed");
            helper.assertTrue(stack.has(DataComponents.DAMAGE_RESISTANT),
                    expected.path() + " fire resistance changed");
        }
        helper.succeed();
    }

    private static Expected expected(String path, DeferredBlock<?> block,
            DeferredItem<?> item) {
        return new Expected(path, block, item);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record Expected(String path, DeferredBlock<?> block, DeferredItem<?> item) { }
}
