package net.mcreator.ssc.gametest;

import net.mcreator.ssc.block.entity.GravityGeneratorBlockEntity;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Exercises the Handy provider against real world and player lifecycle state. */
@EventBusSubscriber(modid = "ssc_14")
public final class GravityWindowGameTests {
    private static final Identifier TEST = Identifier.fromNamespaceAndPath("ssc_14", "gravity_window_access");

    private GravityWindowGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST, GravityWindowGameTests::access));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(Identifier.fromNamespaceAndPath("ssc_14", "gravity_window_environment"));
        var data = new TestData<>(environment, Identifier.fromNamespaceAndPath("ssc_14", "atmosphere_test_room"), 100, 1, true);
        event.registerTest(TEST, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, TEST), data));
    }

    private static void access(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(3, 3, 3));
        // No neighbour updates: this fixture tests access, not multiblock placement rules.
        level.setBlock(pos, Ssc14ModBlocks.GRAVITY_GENERATOR.get().defaultBlockState(), 2);
        var generator = (GravityGeneratorBlockEntity) level.getBlockEntity(pos);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(!com.lowdragmc.lowdraglib2.gui.factory.PlayerUIMenuType.openUI(player,
                com.ssc14.handy.window.RootMUI.START_UI), "Handy login must not open an empty container screen");
        player.setPos(pos.getCenter());
        var source = new GlobalPos(level.dimension(), pos);
        helper.assertTrue(generator.hasAccess(player, source), "Nearby player must be allowed without a container menu");
        helper.assertTrue(!generator.hasAccess(player, null), "Null source must be rejected");
        helper.assertTrue(!generator.hasAccess(player, new GlobalPos(Level.NETHER, pos)), "Other dimension must be rejected");
        helper.assertTrue(!generator.hasAccess(player, new GlobalPos(level.dimension(), pos.above())), "Other block must be rejected");
        player.setPos(pos.getCenter().add(9, 0, 0));
        helper.assertTrue(!generator.hasAccess(player, source), "Distant player must be rejected");
        player.getAbilities().instabuild = true;
        helper.assertTrue(!generator.hasAccess(player, source), "Creative mode must not bypass distance");
        player.setPos(pos.getCenter());
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        helper.assertTrue(!generator.hasAccess(player, source), "Removed generator must be rejected");
        helper.succeed();
    }
}
