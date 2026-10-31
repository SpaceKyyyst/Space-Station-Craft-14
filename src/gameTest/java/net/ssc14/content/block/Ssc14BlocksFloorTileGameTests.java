package net.ssc14.content.block;

import java.util.List;
import net.mcreator.ssc.block.BaseAirlockD1Block;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.floor.AbstractFloorTileBlock;
import net.ssc14.content.block.floor.FloorTileBlock;
import net.ssc14.content.block.floor.FloorTileSupportPolicy;
import net.ssc14.content.block.floor.InsetFloorTileBlock;
import net.ssc14.content.block.floor.RandomizedFloorTileBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksFloorTileGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_floor_tile_contract");
    private static final List<Expected> EXPECTED = List.of(
            expected("TITLE_STEEL", "title_steel", true),
            expected("TITLE_DARK", "title_dark", true),
            expected("TITLE_WHITE", "title_white", true),
            expected("TITLE_ARCADEBLUE", "title_arcadeblue", false),
            expected("TITLEARCADEBLUE_2", "titlearcadeblue_2", false),
            expected("TITLE_ARCADERED", "title_arcadered", false),
            expected("TITLE_BAR", "title_bar", false),
            expected("TITLE_BLUE_CIRCUIT", "title_blue_circuit", false),
            expected("TITLE_BLUE", "title_blue", false),
            expected("TITLE_BOXING", "title_boxing", false),
            expected("TITLE_CAFETERIA", "title_cafeteria", false),
            expected("TITLE_CARPETCLOWN", "title_carpetclown", false),
            expected("TITLE_CARPETOFFICE", "title_carpetoffice", false),
            expected("TITLE_CHECKERDARK", "title_checkerdark", false),
            expected("TITLE_CLOWN", "title_clown", false),
            expected("TITLE_DARKTEST", "title_darktest", false),
            expected("TITLEEIGHTIES", "titleeighties", false),
            expected("TITLE_ELEVATORSHAFT", "title_elevatorshaft", false),
            expected("TITLE_EXOBORG", "title_exoborg", false),
            expected("TITLE_FREEZER", "title_freezer", false),
            expected("TITLE_GOLD", "title_gold", false),
            expected("TITLE_GRATINGMAINT", "title_gratingmaint", false),
            expected("TITLE_GREENCIRCUIT", "title_greencircuit", false),
            expected("TITLE_GYM", "title_gym", false),
            expected("TITLE_HULLREINFORCED", "title_hullreinforced", false),
            expected("TITLE_HULL", "title_hull", false),
            expected("TITLE_HYDRO", "title_hydro", false),
            expected("TITLE_KITCHEN", "title_kitchen", false),
            expected("TITLE_LAUNDRY", "title_laundry", false),
            expected("TITLE_LIME", "title_lime", false),
            expected("TITLE_LINO", "title_lino", false),
            expected("TITLE_MEAT", "title_meat", false),
            expected("TITLE_METALDIAMOND", "title_metaldiamond", false),
            expected("TITLE_MIME", "title_mime", false),
            expected("TITLE_MININGFLOORDARK", "title_miningfloordark", false),
            expected("TITLE_MININGFLOORLIGHT", "title_miningfloorlight", false),
            expected("TITLE_MININGFLOOR", "title_miningfloor", false),
            expected("TITLE_MONO", "title_mono", false),
            expected("TITLE_REDCIRCUIT", "title_redcircuit", false),
            expected("TITLE_REINFORCED", "title_reinforced", false),
            expected("TITLE_ROCKVAULT", "title_rockvault", false),
            expected("TITLE_SHOWROOM", "title_showroom", false),
            expected("TITLE_SHUTTLEBLACK", "title_shuttleblack", false),
            expected("TITLE_SHUTTLEBLUE", "title_shuttleblue", false),
            expected("TITLE_SHUTTLEGREY", "title_shuttlegrey", false),
            expected("TITLE_SHUTTLEORANGE", "title_shuttleorange", false),
            expected("TITLE_SHUTTLEPURPLE", "title_shuttlepurple", false),
            expected("TITLE_SHUTTLERED", "title_shuttlered", false),
            expected("TITLE_SHUTTLEWHITE", "title_shuttlewhite", false),
            expected("TITLE_SILVER", "title_silver", false),
            expected("TITLE_STEELDAMAGED", "title_steeldamaged", false),
            expected("TITLE_STEELDIRTY", "title_steeldirty", false),
            expected("TITLE_STEELMAINT", "title_steelmaint", false),
            expected("TITLE_SUPERREINFORCED", "title_superreinforced", false),
            expected("TITLE_TECHMAINTDARK", "title_techmaintdark", false),
            expected("TITLE_TECHMAINT", "title_techmaint", false),
            expected("TITLE_WOODLARGE", "title_woodlarge", false),
            expected("TITLE_WOOD", "title_wood", false),
            expected("TITLE_WOODBROKEN", "title_woodbroken", false),
            expected("TITLE_WOODTILE", "title_woodtile", false),
            expected("TITLE_DARK_DIAGONAL_MINI", "title_dark_diagonal_mini", false),
            expected("TITLE_DARK_DIAGONAL", "title_dark_diagonal", false),
            expected("TITLE_DARK_MINI", "title_dark_mini", false),
            expected("TITLE_DARK_MONO", "title_dark_mono", false),
            expected("TITLE_DARK_OFFSET", "title_dark_offset", false),
            expected("TITLE_DARK_PAVEMENT", "title_dark_pavement", false),
            expected("TITLE_DARK_PAVEMENT_VERTICAL", "title_dark_pavement_vertical", false),
            expected("TITLE_DARK_HERRINGBONE", "title_dark_herringbone", false),
            expected("TITLE_DARK_PLASTIC", "title_dark_plastic", false),
            expected("TITLE_PLASTIC", "title_plastic", false),
            expected("TITLE_STEEL_DIAGONAL_MINI", "title_steel_diagonal_mini", false),
            expected("TITLE_STEEL_DIAGONAL", "title_steel_diagonal", false),
            expected("TITLE_STEEL_MINI", "title_steel_mini", false),
            expected("TITLE_STEEL_MONO", "title_steel_mono", false),
            expected("TITLE_STEEL_OFFSET", "title_steel_offset", false),
            expected("TITLE_STEEL_PAVEMENT", "title_steel_pavement", false),
            expected("TITLE_STEEL_HERRINGBONE", "title_steel_herringbone", false),
            expected("TITLE_STEEL_PAVEMENT_VERTICAL", "title_steel_pavement_vertical", false),
            expected("TITLE_WHITE_DIAGONAL_MINI", "title_white_diagonal_mini", false),
            expected("TITLE_WHITE_DIAGONAL", "title_white_diagonal", false),
            expected("TITLE_WHITE_HERRINGBONE", "title_white_herringbone", false),
            expected("TITLE_WHITE_MINI", "title_white_mini", false),
            expected("TITLE_WHITE_MONO", "title_white_mono", false),
            expected("TITLE_WHITE_OFFSET", "title_white_offset", false),
            expected("TITLE_WHITE_PAVEMENT", "title_white_pavement", false),
            expected("TITLE_WHITE_PAVEMENT_VERTICAL", "title_white_pavement_vertical", false),
            expected("TITLE_WHITE_PLASTIC", "title_white_plastic", false));

    private Ssc14BlocksFloorTileGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksFloorTileGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_floor_tile_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos support = helper.absolutePos(new BlockPos(1, 1, 1));
        BlockPos pos = support.above();
        VoxelShape full = Shapes.or(Block.box(0, 12, 0, 16, 16, 16),
                Block.box(0, 0, 0, 1, 16, 1), Block.box(0, 0, 15, 1, 16, 16),
                Block.box(15, 0, 0, 16, 16, 1), Block.box(15, 0, 15, 16, 16, 16));
        VoxelShape inset = Shapes.or(Block.box(0, 12, 0, 16, 16, 16),
                Block.box(0, 0, 0, 1, 14, 1), Block.box(0, 0, 15, 1, 14, 16),
                Block.box(15, 0, 0, 16, 14, 1), Block.box(15, 0, 15, 16, 14, 16));

        for (Expected expected : EXPECTED) {
            AbstractFloorTileBlock block = get(expected.field());
            var state = block.defaultBlockState();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(expected.inset()
                            ? block instanceof InsetFloorTileBlock
                            : block instanceof FloorTileBlock,
                    expected.path() + " implementation family changed");
            helper.assertTrue(Float.compare(100.0f, block.defaultDestroyTime()) == 0
                            && Float.compare(5.0f, block.getExplosionResistance()) == 0,
                    expected.path() + " strength changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 30,
                    expected.path() + " stack size changed");
            helper.assertTrue(block.asItem().getDefaultInstance()
                            .has(DataComponents.DAMAGE_RESISTANT),
                    expected.path() + " fire resistance changed");
            helper.assertTrue(block.getLightDampening(state) == 15
                            && state.getVisualShape(level, pos, CollisionContext.empty()).isEmpty()
                            && !state.isRedstoneConductor(level, pos),
                    expected.path() + " transparency contract changed");
            var sound = state.getSoundType();
            helper.assertTrue(Float.compare(1.0f, sound.getVolume()) == 0
                            && Float.compare(1.0f, sound.getPitch()) == 0,
                    expected.path() + " sound volume or pitch changed");
            assertSound(helper, sound.getBreakSound(), id("title_off"),
                    expected.path() + " break sound changed");
            assertSound(helper, sound.getStepSound(), vanilla("block.copper.step"),
                    expected.path() + " step sound changed");
            assertSound(helper, sound.getPlaceSound(), id("title_on"),
                    expected.path() + " place sound changed");
            assertSound(helper, sound.getHitSound(), vanilla("block.copper.hit"),
                    expected.path() + " hit sound changed");
            assertSound(helper, sound.getFallSound(), vanilla("block.copper.fall"),
                    expected.path() + " fall sound changed");
            VoxelShape expectedShape = expected.inset() ? inset : full;
            helper.assertTrue(!Shapes.joinIsNotEmpty(expectedShape, state.getShape(level, pos),
                            BooleanOp.NOT_SAME),
                    expected.path() + " shape changed");
        }

        assertStateRange(helper, get("TITLE_MEAT"), 4);
        assertStateRange(helper, get("TITLE_STEELDAMAGED"), 5);
        assertStateRange(helper, get("TITLE_WOODBROKEN"), 5);

        AbstractFloorTileBlock tile = get("TITLE_STEEL");
        var tileState = tile.defaultBlockState();
        level.setBlock(support, Ssc14Blocks.CAVE_FLOOR_1.get().defaultBlockState(), Block.UPDATE_ALL);
        helper.assertTrue(FloorTileSupportPolicy.canSupport(level, pos)
                        && tileState.canSurvive(level, pos),
                "tile rejected suitable support");
        level.setBlock(pos, tileState, Block.UPDATE_ALL);
        level.setBlock(support, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        helper.assertTrue(level.getBlockState(pos).isAir(),
                "tile remained after losing suitable support");

        level.setBlock(support, Ssc14Blocks.CAVE_FLOOR_1.get().defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(pos, tileState, Block.UPDATE_ALL);
        assertSameShape(helper, inset, tileState.getCollisionShape(level, pos),
                "collision changed without an airlock");
        assertDoorwayCollisions(helper, pos, tileState, inset, Direction.NORTH);
        assertDoorwayCollisions(helper, pos, tileState, inset, Direction.EAST);
        helper.succeed();
    }

    private static void assertStateRange(GameTestHelper helper, AbstractFloorTileBlock block,
            int maximumState) {
        helper.assertTrue(block instanceof RandomizedFloorTileBlock,
                BuiltInRegistries.BLOCK.getKey(block) + " randomized family changed");
        RandomizedFloorTileBlock randomized = (RandomizedFloorTileBlock) block;
        helper.assertTrue(randomized.stateProperty().getPossibleValues().stream()
                        .mapToInt(Integer::intValue).max().orElseThrow() == maximumState,
                BuiltInRegistries.BLOCK.getKey(block) + " state range changed");
    }

    private static void assertDoorwayCollisions(GameTestHelper helper, BlockPos pos,
            net.minecraft.world.level.block.state.BlockState tileState, VoxelShape tile,
            Direction direction) {
        var level = helper.getLevel();
        var door = Ssc14Blocks.BASE_AIRLOCK_D_1.get().defaultBlockState()
                .setValue(BaseAirlockD1Block.FACING, direction);
        boolean alongX = direction.getAxis() == Direction.Axis.Z;
        VoxelShape closedSeal = alongX
                ? Block.box(0, 0, 5, 16, 16, 11)
                : Block.box(5, 0, 0, 11, 16, 16);
        VoxelShape openSeal = alongX
                ? Shapes.or(Block.box(0, 0, 5, 1, 16, 11),
                        Block.box(15, 0, 5, 16, 16, 11))
                : Shapes.or(Block.box(5, 0, 0, 11, 16, 1),
                        Block.box(5, 0, 15, 11, 16, 16));

        level.setBlock(pos.above(), door.setValue(BaseAirlockD1Block.ARL_VARIAT, 0),
                Block.UPDATE_ALL);
        assertSameShape(helper, Shapes.or(tile, closedSeal),
                tileState.getCollisionShape(level, pos),
                "closed airlock seal collision changed for " + direction);
        for (int openFrame : List.of(11, 13, 14)) {
            level.setBlock(pos.above(), door.setValue(BaseAirlockD1Block.ARL_VARIAT, openFrame),
                    Block.UPDATE_ALL);
            assertSameShape(helper, Shapes.or(tile, openSeal),
                    tileState.getCollisionShape(level, pos),
                    "open airlock seal collision changed for " + direction + "/" + openFrame);
        }
    }

    private static void assertSound(GameTestHelper helper, SoundEvent actual,
            Identifier expected, String message) {
        helper.assertTrue(expected.equals(BuiltInRegistries.SOUND_EVENT.getKey(actual)), message);
    }

    private static void assertSameShape(GameTestHelper helper, VoxelShape expected,
            VoxelShape actual, String message) {
        helper.assertTrue(!Shapes.joinIsNotEmpty(expected, actual, BooleanOp.NOT_SAME), message);
    }

    private static AbstractFloorTileBlock get(String field) {
        try {
            Object value = Ssc14Blocks.class.getField(field).get(null);
            return (AbstractFloorTileBlock) ((DeferredBlock<?>) value).get();
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException(error);
        }
    }

    private static Expected expected(String field, String path, boolean inset) {
        return new Expected(field, path, inset);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private static Identifier vanilla(String path) {
        return Identifier.fromNamespaceAndPath("minecraft", path);
    }

    private record Expected(String field, String path, boolean inset) { }
}
