package net.mcreator.ssc.gametest;

import net.ssc14.content.block.gravity.GravityGeneratorBlockEntity;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.ssc14.content.gravity.EntityGravityController;
import net.ssc14.common.gravity.GravityPolicy;
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
    private static final Identifier OWNERSHIP_TEST = Identifier.fromNamespaceAndPath("ssc_14", "gravity_override_ownership");
    private static final Identifier LIFECYCLE_TEST = Identifier.fromNamespaceAndPath("ssc_14", "gravity_override_save_load");
    private static final Identifier EXTERNAL_INERTIA_TEST = Identifier.fromNamespaceAndPath("ssc_14", "gravity_external_inertia");

    private GravityWindowGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST, GravityWindowGameTests::access));
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(OWNERSHIP_TEST, GravityWindowGameTests::ownership));
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(LIFECYCLE_TEST, GravityWindowGameTests::saveLoad));
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(EXTERNAL_INERTIA_TEST, GravityWindowGameTests::externalInertia));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(Identifier.fromNamespaceAndPath("ssc_14", "gravity_window_environment"));
        var data = new TestData<>(environment, Identifier.fromNamespaceAndPath("ssc_14", "atmosphere_test_room"), 100, 1, true);
        event.registerTest(TEST, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, TEST), data));
        event.registerTest(OWNERSHIP_TEST, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, OWNERSHIP_TEST), data));
        event.registerTest(LIFECYCLE_TEST, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, LIFECYCLE_TEST), data));
        event.registerTest(EXTERNAL_INERTIA_TEST, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, EXTERNAL_INERTIA_TEST), data));
    }

    private static void ownership(GameTestHelper helper) {
        var entity = helper.spawn(net.minecraft.world.entity.EntityType.COW, new BlockPos(3, 3, 3));
        entity.setNoAi(true);
        entity.setNoGravity(true);
        net.ssc14.content.gravity.EntityGravityTickHandler.apply(
                helper.getLevel(), entity.getX(), entity.getY(), entity.getZ(), entity);
        helper.assertTrue(entity.isNoGravity(), "SSC must preserve an external true baseline outside spaced");
        helper.runAfterDelay(2, () -> {
            helper.assertTrue(entity.isNoGravity(), "Normal server ticks must preserve the external flag");
            entity.discard();
            helper.assertTrue(entity.isNoGravity(), "Removal must not restore an unowned flag");
            helper.succeed();
        });
    }

    private static void saveLoad(GameTestHelper helper) {
        var level = helper.getLevel();
        var entity = helper.spawn(net.minecraft.world.entity.EntityType.COW, new BlockPos(3, 3, 3));
        entity.setNoAi(true);
        entity.getPersistentData().putString("gravity_test_other", "keep");
        EntityGravityController.apply(entity, GravityPolicy.Decision.NO_GRAVITY);
        helper.assertTrue(entity.isNoGravity(), "SSC must apply the space override to a false baseline");

        var output = net.minecraft.world.level.storage.TagValueOutput.createWithContext(
            net.minecraft.util.ProblemReporter.DISCARDING, level.registryAccess());
        helper.assertTrue(entity.save(output), "Live entity must serialize with its ownership marker");
        var saved = output.buildResult();
        entity.remove(net.minecraft.world.entity.Entity.RemovalReason.UNLOADED_TO_CHUNK);
        helper.assertTrue(!entity.isNoGravity(), "The real level leave callback must restore SSC's baseline");
        helper.assertTrue(entity.getPersistentData().size() == 1, "Leave must clear ownership and retain unrelated data");

        var loaded = net.minecraft.world.entity.EntityType.loadEntityRecursive(saved, level,
            net.minecraft.world.entity.EntitySpawnReason.LOAD, value -> value);
        helper.assertTrue(loaded != null && loaded.isNoGravity(), "Saved NoGravity must be loaded before join reconciliation");
        helper.assertTrue(level.addFreshEntity(loaded), "Reloaded entity must join the real server level");
        helper.assertTrue(!loaded.isNoGravity(), "Join must reconcile a snapshot saved before the leave callback");
        helper.assertTrue(loaded.getPersistentData().size() == 1, "Reload must clear ownership and retain unrelated data");
        helper.assertTrue(loaded.getPersistentData().getStringOr("gravity_test_other", "").equals("keep"), "Unrelated persistent data must survive");

        EntityGravityController.apply(loaded, GravityPolicy.Decision.NO_GRAVITY);
        helper.assertTrue(loaded.isNoGravity(), "Reloaded entity must be eligible for a new space episode");
        EntityGravityController.apply(loaded, GravityPolicy.Decision.GRAVITY);
        loaded.setNoGravity(true);
        loaded.discard();
        helper.assertTrue(loaded.isNoGravity(), "Leave must preserve an external takeover of SSC's last false write");
        helper.assertTrue(loaded.getPersistentData().size() == 1, "External takeover must leave no ownership marker");
        helper.succeed();
    }

    private static void externalInertia(GameTestHelper helper) {
        // GameTest's makeMockPlayer is not a ServerPlayer and cannot exercise server inertia.
        var player = new net.neoforged.neoforge.common.util.FakePlayer(helper.getLevel(),
            new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), "gravity-test"));
        player.setGameMode(GameType.SURVIVAL);
        // Above the fixture: no nearby blocks may hide an unintended inertia write.
        player.setPos(helper.absolutePos(new BlockPos(3, 50, 3)).getCenter());
        player.setNoGravity(true);
        var velocity = new net.minecraft.world.phys.Vec3(0.5, 0.1, 0.5);
        player.setDeltaMovement(velocity);
        net.ssc14.content.gravity.EntityGravityTickHandler.apply(
            helper.getLevel(), player.getX(), player.getY(), player.getZ(), player);
        helper.assertTrue(player.isNoGravity(), "External player gravity must be preserved outside spaced");
        helper.assertTrue(player.getDeltaMovement().equals(velocity), "SSC must preserve external player velocity outside spaced");
        helper.succeed();
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
