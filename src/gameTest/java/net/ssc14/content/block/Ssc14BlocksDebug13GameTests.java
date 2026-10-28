package net.ssc14.content.block;

import net.mcreator.ssc.network.Ssc14ModVariables;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.debug.StationCodeDebugBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksDebug13GameTests {
    private static final Identifier TEST_ID = id("compatibility_block_debug_13_contract");

    private Ssc14BlocksDebug13GameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksDebug13GameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_debug_13_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        helper.assertTrue(id("debug_13").equals(
                        BuiltInRegistries.BLOCK.getKey(Ssc14Blocks.DEBUG_13.get())),
                "debug_13 registry ID changed");
        var block = (StationCodeDebugBlock) Ssc14Blocks.DEBUG_13.get();
        BlockPos relativePos = new BlockPos(1, 2, 1);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        var tallState = block.defaultBlockState().setValue(StationCodeDebugBlock.BLOCKSTATE, 1);
        helper.setBlock(relativePos, tallState);
        helper.assertTrue(block.getShape(tallState, helper.getLevel(), absolutePos,
                        net.minecraft.world.phys.shapes.CollisionContext.empty()).bounds().maxY == 2.0,
                "debug_13 tall collision shape changed");

        var variables = Ssc14ModVariables.MapVariables.get(helper.getLevel());
        variables.station_code = 5;
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var hit = new BlockHitResult(Vec3.atCenterOf(absolutePos), Direction.UP,
                absolutePos, false);
        block.useWithoutItem(tallState, helper.getLevel(), absolutePos, player, hit);
        helper.assertTrue(variables.station_code == 6,
                "debug_13 did not increment station_code");
        player.setShiftKeyDown(true);
        block.useWithoutItem(tallState, helper.getLevel(), absolutePos, player, hit);
        helper.assertTrue(variables.station_code == 0,
                "debug_13 shift interaction did not reset station_code");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
