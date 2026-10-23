package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.furniture.ConnectedTableBlock;
import net.ssc14.content.block.furniture.GlassTableBlock;
import net.ssc14.content.block.furniture.SteelTableBlock;
import net.ssc14.content.block.furniture.TableConnectionService;
import net.ssc14.content.block.furniture.WoodTableBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksTableGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_table_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            new ExpectedBlock(Ssc14Blocks.STEEL_TABLE, "steel_table", SteelTableBlock.class, SoundType.LANTERN),
            new ExpectedBlock(Ssc14Blocks.WOOD_TABLE, "wood_table", WoodTableBlock.class, SoundType.WOOD),
            new ExpectedBlock(Ssc14Blocks.GLASS_TABLE, "glass_table", GlassTableBlock.class, SoundType.LANTERN));

    private Ssc14BlocksTableGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksTableGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_table_compatibility_environment"));
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
            helper.assertTrue(Float.compare(5.0f, block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(Float.compare(5.0f, block.getExplosionResistance()) == 0,
                    expected.path() + " explosion resistance changed");
            helper.assertTrue(state.getSoundType() == expected.sound(), expected.path() + " sound changed");
            helper.assertTrue(state.getValue(ConnectedTableBlock.BLOCKSTATE) == 0,
                    expected.path() + " default connection state changed");
            helper.assertTrue(ConnectedTableBlock.BLOCKSTATE.getPossibleValues().size() == 16,
                    expected.path() + " connection range changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 1,
                    expected.path() + " item stack limit changed");
            helper.setBlock(relative, state);
            helper.assertTrue(!Shapes.joinIsNotEmpty(
                            state.getShape(helper.getLevel(), absolute),
                            Block.box(0, 14, 0, 16, 16, 16), BooleanOp.NOT_SAME),
                    expected.path() + " shape changed");
            helper.assertTrue(state.getVisualShape(helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                    expected.path() + " visual shape changed");
            helper.assertTrue(!state.isRedstoneConductor(helper.getLevel(), absolute),
                    expected.path() + " became a redstone conductor");
        }

        BlockPos center = new BlockPos(5, 2, 5);
        helper.setBlock(center, Ssc14Blocks.STEEL_TABLE.get());
        helper.setBlock(center.north(), Ssc14Blocks.WOOD_TABLE.get());
        helper.setBlock(center.east(), Ssc14Blocks.GLASS_TABLE.get());
        helper.setBlock(center.south(), Ssc14Blocks.WOOD_TABLE.get());
        helper.setBlock(center.west(), Ssc14Blocks.GLASS_TABLE.get());
        assertConnection(helper, center, 15);
        assertConnection(helper, center.north(), 3);
        assertConnection(helper, center.east(), 4);
        assertConnection(helper, center.south(), 1);
        assertConnection(helper, center.west(), 2);

        helper.setBlock(center.east(), Blocks.AIR);
        TableConnectionService.refresh(helper.getLevel(), helper.absolutePos(center.east()));
        assertConnection(helper, center, 11);
        helper.succeed();
    }

    private static void assertConnection(GameTestHelper helper, BlockPos relative, int expected) {
        int actual = helper.getBlockState(relative).getValue(ConnectedTableBlock.BLOCKSTATE);
        helper.assertTrue(actual == expected,
                "connection state at " + relative + " changed: expected " + expected + ", got " + actual);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(
            DeferredBlock<Block> holder, String path, Class<? extends Block> implementation, SoundType sound) {
    }
}
