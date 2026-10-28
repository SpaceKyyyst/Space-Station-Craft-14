package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.window.ArmoredWindowBlock;
import net.ssc14.content.block.window.AbstractWindowBlock;
import net.ssc14.content.block.window.BaseWindowBlock;
import net.ssc14.content.block.window.DirectedArmoredWindowBlock;
import net.ssc14.content.block.window.DirectedBaseWindowBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksWindowGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_window_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            new ExpectedBlock(Ssc14Blocks.BASE_WINDOW.getId(), BaseWindowBlock.class, 10.0f, 5.0f, 30),
            new ExpectedBlock(Ssc14Blocks.ARMORED_WINDOW.getId(), ArmoredWindowBlock.class, 100.0f, 12.0f, 30),
            new ExpectedBlock(Ssc14Blocks.DIRECTED_BASE_WINDOW.getId(), DirectedBaseWindowBlock.class, 5.0f, 4.0f, 1),
            new ExpectedBlock(Ssc14Blocks.DIRECTED_ARMORED_WINDOW.getId(), DirectedArmoredWindowBlock.class, 15.0f, 10.0f, 1));

    private Ssc14BlocksWindowGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksWindowGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_window_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relative = new BlockPos(1, 2, 1);
        BlockPos absolute = helper.absolutePos(relative);
        for (ExpectedBlock expected : EXPECTED) {
            Block block = BuiltInRegistries.BLOCK.getValue(expected.id());
            BlockState state = block.defaultBlockState();
            helper.assertTrue(expected.type().isInstance(block), expected.id() + " implementation changed");
            helper.assertTrue(Float.compare(expected.strength(), block.defaultDestroyTime()) == 0,
                    expected.id() + " destroy time changed");
            helper.assertTrue(Float.compare(expected.resistance(), block.getExplosionResistance()) == 0,
                    expected.id() + " explosion resistance changed");
            helper.assertTrue(state.getSoundType() == SoundType.GLASS, expected.id() + " sound changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == expected.stackSize(),
                    expected.id() + " stack size changed");
            helper.assertTrue(state.propagatesSkylightDown(), expected.id() + " stopped propagating skylight");
            helper.assertTrue(((AbstractWindowBlock) block).getLightDampening(state) == 0,
                    expected.id() + " light dampening changed");
            helper.assertTrue(state.getVisualShape(helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                    expected.id() + " visual shape changed");
            helper.assertTrue(!state.isRedstoneConductor(helper.getLevel(), absolute),
                    expected.id() + " became a redstone conductor");
            helper.assertTrue(((AbstractWindowBlock) block).skipRendering(state, state, Direction.NORTH),
                    expected.id() + " stopped culling same-block faces");
        }

        BlockState base = Ssc14Blocks.BASE_WINDOW.get().defaultBlockState();
        helper.assertTrue(base.getValue(BaseWindowBlock.WINDOW_DISASSEMBLY) == 0,
                "base window default disassembly changed");
        helper.assertTrue(BaseWindowBlock.WINDOW_DISASSEMBLY.getPossibleValues().size() == 2,
                "base window disassembly range changed");
        BlockState armored = Ssc14Blocks.ARMORED_WINDOW.get().defaultBlockState();
        helper.assertTrue(armored.getValue(ArmoredWindowBlock.BLOCKSTATE) == 0,
                "armored window default state changed");
        helper.assertTrue(ArmoredWindowBlock.BLOCKSTATE.getPossibleValues().size() == 6,
                "armored window state range changed");

        verifyDirected(helper, absolute, Ssc14Blocks.DIRECTED_BASE_WINDOW.get().defaultBlockState(),
                DirectedBaseWindowBlock.FACING);
        BlockState directedBase = Ssc14Blocks.DIRECTED_BASE_WINDOW.get().defaultBlockState();
        helper.assertTrue(directedBase.getValue(DirectedBaseWindowBlock.WINDOW_DISASSEMBLY) == 0,
                "directed base window default disassembly changed");
        helper.assertTrue(directedBase.getValue(DirectedBaseWindowBlock.BLOCKSTATE) == 0,
                "directed base window default blockstate changed");
        helper.assertTrue("window_disassembly".equals(DirectedBaseWindowBlock.WINDOW_DISASSEMBLY.getName())
                        && DirectedBaseWindowBlock.WINDOW_DISASSEMBLY.getPossibleValues().size() == 2,
                "directed base window disassembly contract changed");
        helper.assertTrue("blockstate".equals(DirectedBaseWindowBlock.BLOCKSTATE.getName())
                        && DirectedBaseWindowBlock.BLOCKSTATE.getPossibleValues().size() == 2,
                "directed base window blockstate contract changed");
        verifyDirected(helper, absolute, Ssc14Blocks.DIRECTED_ARMORED_WINDOW.get().defaultBlockState(),
                DirectedArmoredWindowBlock.FACING);
        helper.assertTrue("window_dissasambly".equals(DirectedArmoredWindowBlock.WINDOW_DISSASAMBLY.getName()),
                "legacy directed armored property spelling changed");
        helper.assertTrue(DirectedArmoredWindowBlock.WINDOW_DISSASAMBLY.getPossibleValues().size() == 6,
                "directed armored window range changed");
        helper.assertTrue(InteractionResult.PASS == net.ssc14.content.block.window.WindowKnockService.knock(
                helper.getLevel(), absolute.getX(), absolute.getY(), absolute.getZ(),
                helper.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL)),
                "server-side knock result changed");
        helper.succeed();
    }

    private static void verifyDirected(GameTestHelper helper, BlockPos pos, BlockState state,
            net.minecraft.world.level.block.state.properties.EnumProperty<Direction> facingProperty) {
        helper.assertTrue(state.getValue(facingProperty) == Direction.NORTH, "default facing changed");
        helper.assertTrue(state.rotate(Rotation.CLOCKWISE_90).getValue(facingProperty) == Direction.EAST,
                "rotation changed");
        helper.assertTrue(state.setValue(facingProperty, Direction.EAST).mirror(Mirror.FRONT_BACK)
                .getValue(facingProperty) == Direction.WEST, "mirror changed");
        for (Direction direction : Direction.values()) {
            BlockState directed = state.setValue(facingProperty, direction);
            helper.assertTrue(!Shapes.joinIsNotEmpty(directed.getShape(helper.getLevel(), pos), shape(direction),
                    BooleanOp.NOT_SAME), "shape changed for " + direction);
        }
    }

    private static VoxelShape shape(Direction direction) {
        return switch (direction) {
            case NORTH -> Block.box(0, 0, 13, 16, 16, 16);
            case EAST -> Block.box(0, 0, 0, 3, 16, 16);
            case WEST -> Block.box(13, 0, 0, 16, 16, 16);
            case UP -> Block.box(0, 0, 0, 16, 3, 16);
            case DOWN -> Block.box(0, 13, 0, 16, 16, 16);
            default -> Block.box(0, 0, 0, 16, 16, 3);
        };
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(Identifier id, Class<? extends Block> type, float strength,
            float resistance, int stackSize) { }
}
