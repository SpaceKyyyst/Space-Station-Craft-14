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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksAirlockThresholdGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_airlock_threshold_contract");

    private Ssc14BlocksAirlockThresholdGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksAirlockThresholdGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_airlock_threshold_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        helper.assertTrue(id("airlock_threshold").equals(
                        BuiltInRegistries.BLOCK.getKey(Ssc14Blocks.AIRLOCK_THRESHOLD.get())),
                "airlock threshold registry ID changed");
        BlockPos root = helper.absolutePos(new BlockPos(2, 3, 2));
        BlockPos floor = root.below();
        helper.getLevel().setBlock(root,
                Ssc14Blocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), Block.UPDATE_ALL);
        var floorState = helper.getLevel().getBlockState(floor);
        helper.assertTrue(floorState.is(Ssc14Blocks.AIRLOCK_THRESHOLD.get()),
                "airlock did not install its canonical lower-part seal");
        helper.assertTrue(floorState.getRenderShape() == RenderShape.INVISIBLE,
                "airlock threshold became visible");
        var crawl = Block.box(2, 0, 2, 14, 10, 14);
        helper.assertTrue(Shapes.joinIsNotEmpty(
                        floorState.getCollisionShape(helper.getLevel(), floor),
                        crawl, BooleanOp.AND),
                "closed airlock lost its lower-part collision seal");
        helper.getLevel().setBlock(root, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        helper.assertTrue(helper.getLevel().getBlockState(floor).isAir(),
                "orphaned airlock threshold was not removed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
