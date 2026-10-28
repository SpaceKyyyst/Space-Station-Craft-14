package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.gravity.GravityGeneratorPlugBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksGravityGeneratorPlugGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_gravity_generator_plug_contract");

    private Ssc14BlocksGravityGeneratorPlugGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksGravityGeneratorPlugGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_gravity_generator_plug_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        GravityGeneratorPlugBlock block =
                (GravityGeneratorPlugBlock) Ssc14Blocks.GRAV_GEN_PLUG_BLOCK.get();
        helper.assertTrue(id("grav_gen_plug_block").equals(
                        BuiltInRegistries.BLOCK.getKey(block)),
                "gravity generator plug registry ID changed");
        BlockPos pos = helper.absolutePos(new BlockPos(2, 2, 2));
        var state = block.defaultBlockState();
        helper.assertTrue(!Shapes.joinIsNotEmpty(Block.box(0, 0, 0, 16, 16, 16),
                        state.getShape(helper.getLevel(), pos), BooleanOp.NOT_SAME),
                "gravity generator plug collision shape changed");
        helper.assertTrue(state.getVisualShape(
                        helper.getLevel(), pos, CollisionContext.empty()).isEmpty(),
                "gravity generator plug visual shape must stay empty");
        helper.assertTrue(block.getCloneItemStack(helper.getLevel(), pos, state, false,
                        helper.makeMockPlayer(GameType.CREATIVE)).isEmpty(),
                "gravity generator plug must not be cloneable");
        helper.assertTrue(block.propagatesSkylightDown(state)
                        && block.getLightDampening(state) == 0,
                "gravity generator plug skylight contract changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
