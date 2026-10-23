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
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.furniture.AutoRotatingChairBlock;
import net.ssc14.content.block.furniture.BarChairBlock;
import net.ssc14.content.block.furniture.DirectionalChairBlock;
import net.ssc14.content.block.furniture.OfficeChairBlock;
import net.ssc14.content.block.furniture.SteelChairBlock;
import net.ssc14.content.block.furniture.WoodChairBlock;
import net.ssc14.content.entity.ChairEntityEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksChairGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_chair_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            expected(Ssc14Blocks.STEEL_CHAIR, "steel_chair", SteelChairBlock.class, SoundType.IRON, false),
            expected(Ssc14Blocks.WOOD_CHAIR, "wood_chair", WoodChairBlock.class, SoundType.WOOD, false),
            expected(Ssc14Blocks.OFICE_BLACK_CHAIR, "ofice_black_chair", OfficeChairBlock.class,
                    SoundType.CALCITE, true),
            expected(Ssc14Blocks.OFICE_WHITE_CHAIR, "ofice_white_chair", OfficeChairBlock.class,
                    SoundType.CALCITE, true),
            expected(Ssc14Blocks.BAR_CHAIR, "bar_chair", BarChairBlock.class, SoundType.IRON, true));

    private Ssc14BlocksChairGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksChairGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_chair_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relative = new BlockPos(1, 2, 1);
        BlockPos absolute = helper.absolutePos(relative);
        for (ExpectedBlock expected : EXPECTED) {
            Block block = expected.holder().get();
            BlockState state = block.defaultBlockState();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(expected.implementation().isInstance(block),
                    expected.path() + " canonical implementation changed");
            helper.assertTrue(expected.autoRotating() == (block instanceof AutoRotatingChairBlock),
                    expected.path() + " autorotation behavior changed");
            helper.assertTrue(Float.compare(5.0f, block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(Float.compare(5.0f, block.getExplosionResistance()) == 0,
                    expected.path() + " explosion resistance changed");
            helper.assertTrue(state.getSoundType() == expected.sound(), expected.path() + " sound changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 1,
                    expected.path() + " item stack limit changed");
            helper.assertTrue(state.getValue(DirectionalChairBlock.FACING) == Direction.NORTH,
                    expected.path() + " default facing changed");
            helper.assertTrue(state.rotate(Rotation.CLOCKWISE_90)
                    .getValue(DirectionalChairBlock.FACING) == Direction.EAST,
                    expected.path() + " rotation changed");
            helper.assertTrue(state.setValue(DirectionalChairBlock.FACING, Direction.EAST)
                    .mirror(Mirror.FRONT_BACK).getValue(DirectionalChairBlock.FACING) == Direction.WEST,
                    expected.path() + " mirror changed");
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState directed = state.setValue(DirectionalChairBlock.FACING, direction);
                helper.setBlock(relative, directed);
                helper.assertTrue(!directed.getShape(helper.getLevel(), absolute).isEmpty(),
                        expected.path() + " outline shape became empty for " + direction);
                helper.assertTrue(directed.getCollisionShape(
                        helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                        expected.path() + " collision shape changed");
                helper.assertTrue(directed.getVisualShape(
                        helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                        expected.path() + " visual shape changed");
                helper.assertTrue(!directed.isRedstoneConductor(helper.getLevel(), absolute),
                        expected.path() + " became a redstone conductor");
            }
        }

        helper.setBlock(relative, Ssc14Blocks.STEEL_CHAIR.get().defaultBlockState());
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.useBlock(relative, player);
        helper.assertTrue(player.isPassenger(), "chair interaction no longer mounts the player");
        helper.assertTrue(player.getRootVehicle() instanceof ChairEntityEntity,
                "chair interaction mounted a different entity type");
        helper.succeed();
    }

    private static ExpectedBlock expected(
            DeferredBlock<Block> holder, String path, Class<? extends Block> implementation,
            SoundType sound, boolean autoRotating) {
        return new ExpectedBlock(holder, path, implementation, sound, autoRotating);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(
            DeferredBlock<Block> holder, String path, Class<? extends Block> implementation,
            SoundType sound, boolean autoRotating) {
    }
}
