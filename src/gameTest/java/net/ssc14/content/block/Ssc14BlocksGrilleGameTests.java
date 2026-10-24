package net.ssc14.content.block;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.grille.BrokenGrilleBlock;
import net.ssc14.content.block.grille.GrilleBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksGrilleGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_grille_contract");
    private static final Map<Direction, VoxelShape> EXPECTED_SHAPES = expectedShapes();

    private Ssc14BlocksGrilleGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksGrilleGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_grille_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos pos = helper.absolutePos(new BlockPos(1, 2, 1));
        GrilleBlock grille = (GrilleBlock) Ssc14Blocks.GRILLE.get();
        BrokenGrilleBlock broken = (BrokenGrilleBlock) Ssc14Blocks.BROKEN_GRILLE.get();
        var grilleState = grille.defaultBlockState();
        var brokenState = broken.defaultBlockState();

        helper.assertTrue(id("grille").equals(BuiltInRegistries.BLOCK.getKey(grille)),
                "grille registry ID changed");
        helper.assertTrue(id("broken_grille").equals(BuiltInRegistries.BLOCK.getKey(broken)),
                "broken grille registry ID changed");
        helper.assertTrue(Float.compare(5.0f, grille.defaultDestroyTime()) == 0
                        && Float.compare(5.0f, grille.getExplosionResistance()) == 0,
                "grille strength changed");
        helper.assertTrue(Float.compare(2.0f, broken.defaultDestroyTime()) == 0
                        && Float.compare(2.0f, broken.getExplosionResistance()) == 0,
                "broken grille strength changed");
        helper.assertTrue(grilleState.getSoundType() == SoundType.CHAIN
                        && brokenState.getSoundType() == SoundType.CHAIN,
                "grille sound changed");
        helper.assertTrue(grille.asItem().getDefaultInstance().getMaxStackSize() == 1
                        && broken.asItem().getDefaultInstance().getMaxStackSize() == 1,
                "grille stack size changed");
        helper.assertTrue(grilleState.getVisualShape(level, pos, CollisionContext.empty()).isEmpty()
                        && brokenState.getVisualShape(level, pos, CollisionContext.empty()).isEmpty(),
                "grille visual shape changed");
        helper.assertTrue(!grilleState.isRedstoneConductor(level, pos)
                        && !brokenState.isRedstoneConductor(level, pos),
                "grille became a redstone conductor");
        helper.assertTrue(!grilleState.getCollisionShape(level, pos).isEmpty()
                        && brokenState.getCollisionShape(level, pos).isEmpty(),
                "grille collision contract changed");

        helper.assertTrue(BrokenGrilleBlock.FACING.getPossibleValues().size() == 6
                        && brokenState.getValue(BrokenGrilleBlock.FACING) == Direction.NORTH,
                "broken grille facing range/default changed");
        helper.assertTrue(brokenState.rotate(Rotation.CLOCKWISE_90)
                        .getValue(BrokenGrilleBlock.FACING) == Direction.EAST,
                "broken grille rotation changed");
        helper.assertTrue(brokenState.mirror(Mirror.LEFT_RIGHT)
                        .getValue(BrokenGrilleBlock.FACING) == Direction.SOUTH,
                "broken grille mirror changed");
        var placementPlayer = helper.makeMockPlayer(GameType.SURVIVAL);
        placementPlayer.setXRot(-90.0f);
        var placement = new BlockPlaceContext(level, placementPlayer, InteractionHand.MAIN_HAND,
                ItemStack.EMPTY, new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));
        helper.assertTrue(broken.getStateForPlacement(placement).getValue(BrokenGrilleBlock.FACING)
                        == Direction.DOWN,
                "broken grille placement direction changed");
        for (var entry : EXPECTED_SHAPES.entrySet()) {
            VoxelShape actual = brokenState.setValue(BrokenGrilleBlock.FACING, entry.getKey())
                    .getShape(level, pos);
            helper.assertTrue(!Shapes.joinIsNotEmpty(entry.getValue(), actual, BooleanOp.NOT_SAME),
                    "broken grille shape changed for " + entry.getKey());
        }

        level.setBlock(pos, grilleState, 3);
        var survival = helper.makeMockPlayer(GameType.SURVIVAL);
        grille.onDestroyedByPlayer(grilleState, level, pos, survival, ItemStack.EMPTY, false,
                Fluids.EMPTY.defaultFluidState());
        helper.assertTrue(level.getBlockState(pos).is(broken),
                "survival destruction did not create a broken grille");
        helper.assertTrue(BrokenGrilleBlock.FACING.getPossibleValues().contains(
                        level.getBlockState(pos).getValue(BrokenGrilleBlock.FACING)),
                "survival destruction produced an invalid facing");

        level.setBlock(pos, grilleState, 3);
        var creative = helper.makeMockPlayer(GameType.CREATIVE);
        grille.onDestroyedByPlayer(grilleState, level, pos, creative, ItemStack.EMPTY, false,
                Fluids.EMPTY.defaultFluidState());
        helper.assertTrue(level.getBlockState(pos).isAir(),
                "creative destruction incorrectly created a broken grille");
        helper.succeed();
    }

    private static Map<Direction, VoxelShape> expectedShapes() {
        Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
        shapes.put(Direction.NORTH, hollow(Block.box(0, 0, 11, 16, 16, 16),
                Block.box(2, 2, 0, 14, 14, 16)));
        shapes.put(Direction.SOUTH, hollow(Block.box(0, 0, 0, 16, 16, 5),
                Block.box(2, 2, 0, 14, 14, 16)));
        shapes.put(Direction.EAST, hollow(Block.box(0, 0, 0, 5, 16, 16),
                Block.box(0, 2, 2, 16, 14, 14)));
        shapes.put(Direction.WEST, hollow(Block.box(11, 0, 0, 16, 16, 16),
                Block.box(0, 2, 2, 16, 14, 14)));
        shapes.put(Direction.UP, hollow(Block.box(0, 0, 0, 16, 5, 16),
                Block.box(2, 0, 2, 14, 16, 14)));
        shapes.put(Direction.DOWN, hollow(Block.box(0, 11, 0, 16, 16, 16),
                Block.box(2, 0, 2, 14, 16, 14)));
        return shapes;
    }

    private static VoxelShape hollow(VoxelShape outside, VoxelShape inside) {
        return Shapes.join(outside, inside, BooleanOp.ONLY_FIRST);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
