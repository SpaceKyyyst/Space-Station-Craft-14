package net.ssc14.content.block;

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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.catwalk.CatwalkFloorBlock;
import net.ssc14.content.block.catwalk.CatwalkSupportPolicy;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksCatwalkGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_catwalk_contract");

    private Ssc14BlocksCatwalkGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksCatwalkGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_catwalk_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos support = helper.absolutePos(new BlockPos(1, 1, 1));
        BlockPos pos = support.above();
        CatwalkFloorBlock block = (CatwalkFloorBlock) Ssc14Blocks.CATWALK_FLOOR.get();
        var state = block.defaultBlockState();

        helper.assertTrue(id("catwalk_floor").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "catwalk registry ID changed");
        helper.assertTrue(Float.compare(50.0f, block.defaultDestroyTime()) == 0
                        && Float.compare(2.5f, block.getExplosionResistance()) == 0,
                "catwalk strength changed");
        helper.assertTrue(state.getSoundType() == SoundType.CHAIN,
                "catwalk sound changed");
        helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 1,
                "catwalk item contract changed");
        helper.assertTrue(!state.isRedstoneConductor(level, pos)
                        && state.getVisualShape(level, pos, CollisionContext.empty()).isEmpty(),
                "catwalk transparency contract changed");
        var expected = Shapes.or(Block.box(0, 12, 0, 16, 16, 16),
                Block.box(0, 0, 0, 1, 16, 1), Block.box(0, 0, 15, 1, 16, 16),
                Block.box(15, 0, 0, 16, 16, 1), Block.box(15, 0, 15, 16, 16, 16));
        helper.assertTrue(!Shapes.joinIsNotEmpty(expected, state.getShape(level, pos),
                        BooleanOp.NOT_SAME), "catwalk shape changed");

        level.setBlock(support, Ssc14Blocks.ROD_FLOOR.get().defaultBlockState(), Block.UPDATE_ALL);
        helper.assertTrue(CatwalkSupportPolicy.canSupport(level, pos) && state.canSurvive(level, pos),
                "catwalk rejected suitable support");
        level.setBlock(pos, state, Block.UPDATE_ALL);
        level.setBlock(support, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        helper.assertTrue(!CatwalkSupportPolicy.canSupport(level, pos) && !state.canSurvive(level, pos),
                "catwalk accepted unsuitable support");
        helper.assertTrue(level.getBlockState(pos).isAir(),
                "catwalk remained after its support was replaced");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
