package net.mcreator.ssc.gametest;

import com.mojang.authlib.GameProfile;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.mcreator.ssc.medical.MedicalPlayerEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Live player state through the registered production event adapters. */
@EventBusSubscriber(modid = "ssc_14")
public final class MedicalGameTests {
    private static final String[] NAMES = {"medical_build_events", "medical_modes_reset", "medical_blindness_merges",
            "medical_player_lifecycle", "medical_full_player_tick", "medical_blindness_veto_retry", "medical_blindness_tick_phase"};
    private static final String OWNERSHIP = "ssc_14:medical_effects";
    private static final String VETO = "ssc_14:test_blindness_veto";
    private static final String PROBE = "ssc_14:test_blindness_probe";
    private static final String ATTEMPTS = "ssc_14:test_blindness_attempts";
    private static final String LATE_ADDITION = "ssc_14:test_blindness_late_pre";

    private MedicalGameTests() { }

    private static Identifier id(String name) { return Identifier.fromNamespaceAndPath("ssc_14", name); }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> {
            registry.register(id(NAMES[0]), MedicalGameTests::buildEvents);
            registry.register(id(NAMES[1]), MedicalGameTests::modesReset);
            registry.register(id(NAMES[2]), MedicalGameTests::blindnessMerges);
            registry.register(id(NAMES[3]), MedicalGameTests::lifecycle);
            registry.register(id(NAMES[4]), MedicalGameTests::fullTick);
            registry.register(id(NAMES[5]), MedicalGameTests::vetoRetry);
            registry.register(id(NAMES[6]), MedicalGameTests::tickPhase);
        });
    }

    @SubscribeEvent
    public static void vetoFixture(MobEffectEvent.Applicable event) {
        var data = event.getEntity().getPersistentData();
        if (data.getBooleanOr(PROBE, false) && event.getEffectInstance().getEffect().equals(MobEffects.BLINDNESS)) {
            data.putInt(ATTEMPTS, data.getIntOr(ATTEMPTS, 0) + 1);
            if (data.getBooleanOr(VETO, false)) event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void latePreFixture(PlayerTickEvent.Pre event) {
        var data = event.getEntity().getPersistentData();
        if (data.getBooleanOr(LATE_ADDITION, false)) {
            data.remove(LATE_ADDITION);
            event.getEntity().addEffect(blindness(40, 1));
        }
    }

    private static void vetoRetry(GameTestHelper helper) {
        var player = criticalPlayer(helper);
        player.addEffect(blindness(40, 1));
        var mixed = player.getEffect(MobEffects.BLINDNESS);
        var data = player.getPersistentData();
        data.putBoolean(PROBE, true);
        data.putBoolean(VETO, true);
        data.putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 99);
        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Pre(player));
        helper.assertTrue(data.getCompound(OWNERSHIP).map(state -> state.getBooleanOr("blindness", false)).orElse(false),
                "A denied external-only restoration must retain SSC cleanup ownership");
        helper.assertTrue(player.getEffect(MobEffects.BLINDNESS) == mixed, "The mod veto must be honored");
        helper.assertTrue(player.getForcedPose() == null, "A blindness veto must not retain unrelated owned pose");
        helper.getLevel().getServer().getCommands().performPrefixedCommand(
                player.createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS), "ssc14 hels @s reset");
        helper.assertTrue(data.getIntOr(ATTEMPTS, 0) == 1, "Repeated recovery/reset must not retry in the same server tick");
        helper.runAfterDelay(1, () -> {
            data.remove(VETO);
            NeoForge.EVENT_BUS.post(new PlayerTickEvent.Pre(player));
            helper.assertTrue(data.getIntOr(ATTEMPTS, 0) == 2, "The next server tick must retry normal application once");
            helper.assertTrue(!data.contains(OWNERSHIP), "Successful replacement must release pending ownership");
            helper.assertTrue(player.getEffect(MobEffects.BLINDNESS).getAmplifier() == 1,
                    "Allowed recovery must preserve the external effect");
            for (int tick = 0; tick < 40; tick++) player.doTick();
            helper.assertTrue(!player.hasEffect(MobEffects.BLINDNESS), "SSC must not resurface after an initially vetoed recovery");
            helper.succeed();
        });
    }

    private static void tickPhase(GameTestHelper helper) {
        var player = criticalPlayer(helper);
        player.getPersistentData().putBoolean(LATE_ADDITION, true);
        player.doTick();
        helper.assertTrue(player.getEffect(MobEffects.BLINDNESS).getDuration() == 39, "The real effect tick must consume the later-Pre addition once");
        player.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 99);
        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Pre(player));
        helper.assertTrue(player.getEffect(MobEffects.BLINDNESS).getDuration() == 39,
                "External-only restoration must have exactly the already-ticked remaining duration");
        helper.succeed();
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("medical_environment"));
        var data = new TestData<>(environment, id("atmosphere_test_room"), 160, 1, true);
        for (String name : NAMES) {
            event.registerTest(id(name), new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, id(name)), data));
        }
    }

    private static void buildEvents(GameTestHelper helper) {
        var player = player(helper);
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(3, 3, 3));
        level.setBlock(pos, Blocks.STONE.defaultBlockState(), 2);
        var snapshot = BlockSnapshot.create(level.dimension(), level, pos);
        for (double damage : new double[] {99, 100, 200, 201}) {
            player.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, damage);
            boolean expected = damage == 100 || damage == 200;
            var placing = new BlockEvent.EntityPlaceEvent(snapshot, Blocks.STONE.defaultBlockState(), player);
            NeoForge.EVENT_BUS.post(placing);
            helper.assertTrue(placing.isCanceled() == expected, "Registered place adapter must honor the critical window");
            var multi = new BlockEvent.EntityMultiPlaceEvent(List.of(snapshot, snapshot), Blocks.STONE.defaultBlockState(), player);
            NeoForge.EVENT_BUS.post(multi);
            helper.assertTrue(multi.isCanceled() == expected, "Registered multi-place must use the same restriction");
            var breaking = new BreakBlockEvent(level, pos, Blocks.STONE.defaultBlockState(), player);
            NeoForge.EVENT_BUS.post(breaking);
            helper.assertTrue(breaking.isCanceled() == expected, "Registered break adapter must honor the critical window");
            helper.assertTrue(breaking.shouldNotifyClient() == expected, "Server-only break denial must request client correction");
        }
        helper.succeed();
    }

    private static void modesReset(GameTestHelper helper) {
        for (var mode : GameType.values()) {
            var player = player(helper);
            player.setGameMode(mode);
            player.getAbilities().mayBuild = false; // Also exercise another ability owner's deliberate restriction.
            var baseline = abilities(player);
            int packets = player.abilityUpdates;
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400));
            player.setInvisible(true);
            player.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 100);
            NeoForge.EVENT_BUS.post(new PlayerTickEvent.Pre(player));
            helper.assertTrue(Arrays.equals(baseline, abilities(player)), "Critical entry must preserve game-mode abilities");
            helper.getLevel().getServer().getCommands().performPrefixedCommand(
                    player.createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS), "ssc14 hels @s reset");
            helper.assertTrue(!player.getPersistentData().contains(MedicalPlayerEffects.TOTAL_DAMAGE), "The registered reset command must execute");
            helper.assertTrue(player.gameMode.getGameModeForPlayer() == mode, "Reset must preserve the current game mode");
            helper.assertTrue(Arrays.equals(baseline, abilities(player)), "Reset must preserve all external ability bits");
            helper.assertTrue(player.abilityUpdates == packets, "Medical entry/reset must not send abilities updates");
            helper.assertTrue(!player.hasEffect(MobEffects.BLINDNESS), "Reset must remove owned blindness");
            helper.assertTrue(player.hasEffect(MobEffects.NIGHT_VISION) && player.isInvisible(), "Reset must preserve external effects and invisibility");
            helper.assertTrue(player.getForcedPose() == null && !player.getPersistentData().contains(OWNERSHIP), "Reset must release pose and ownership");
        }
        helper.succeed();
    }

    private static void blindnessMerges(GameTestHelper helper) {
        var shorter = criticalPlayer(helper);
        helper.assertTrue(!shorter.addEffect(blindness(20, 0)), "Shorter same-amplifier merge must be a real no-op");
        shorter.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 99);
        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Pre(shorter));
        helper.assertTrue(!shorter.hasEffect(MobEffects.BLINDNESS), "A no-op external addition must not strand SSC blindness");

        var stronger = criticalPlayer(helper);
        stronger.addEffect(blindness(40, 1));
        stronger.addEffect(blindness(4, 2));
        stronger.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 0);
        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Pre(stronger));
        helper.assertTrue(stronger.getEffect(MobEffects.BLINDNESS).getAmplifier() == 2, "Recovery must preserve the external active effect");
        for (int tick = 0; tick < 4; tick++) stronger.doTick();
        helper.assertTrue(stronger.getEffect(MobEffects.BLINDNESS).getAmplifier() == 1, "External hidden effect must survive recovery");
        for (int tick = 0; tick < 36; tick++) stronger.doTick();
        helper.assertTrue(!stronger.hasEffect(MobEffects.BLINDNESS), "SSC must never resurface after the external chain expires");

        var external = player(helper);
        external.addEffect(blindness(600, 0));
        external.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 100);
        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Pre(external));
        external.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 201);
        NeoForge.EVENT_BUS.post(new PlayerTickEvent.Pre(external));
        helper.assertTrue(external.getEffect(MobEffects.BLINDNESS).getDuration() == 600, "An external baseline must remain untouched");
        helper.succeed();
    }

    private static void lifecycle(GameTestHelper helper) {
        var original = criticalPlayer(helper);
        var replacement = player(helper);
        replacement.addEffect(blindness(600, 0));
        replacement.setGameMode(GameType.ADVENTURE);
        NeoForge.EVENT_BUS.post(new PlayerEvent.Clone(replacement, original, true));
        helper.assertTrue(!original.hasEffect(MobEffects.BLINDNESS) && original.getForcedPose() == null,
                "Clone must release the original's owned effects");
        helper.assertTrue(replacement.getEffect(MobEffects.BLINDNESS).getDuration() == 600,
                "Clone must not remove unowned replacement blindness");
        helper.assertTrue(!replacement.getAbilities().mayBuild, "Clone must preserve Adventure rights");

        var reconnecting = criticalPlayer(helper);
        var savedOwnership = reconnecting.getPersistentData().copy();
        NeoForge.EVENT_BUS.post(new EntityLeaveLevelEvent(reconnecting, helper.getLevel()));
        helper.assertTrue(!reconnecting.hasEffect(MobEffects.BLINDNESS) && !reconnecting.getPersistentData().contains(OWNERSHIP),
                "Leave adapter must release owned state");
        reconnecting.addEffect(blindness(Integer.MAX_VALUE, 0));
        reconnecting.getPersistentData().merge(savedOwnership);
        NeoForge.EVENT_BUS.post(new EntityJoinLevelEvent(reconnecting, helper.getLevel()));
        helper.assertTrue(!reconnecting.hasEffect(MobEffects.BLINDNESS) && reconnecting.getForcedPose() == null,
                "Join must reconcile ownership saved before leave");
        helper.assertTrue(!reconnecting.getPersistentData().contains(OWNERSHIP), "Join must clear serialized ownership");
        helper.succeed();
    }

    private static void fullTick(GameTestHelper helper) {
        var player = player(helper);
        // Outside the structure ceiling: natural crouching/swimming must not mask recovery.
        player.setPos(helper.absolutePos(new BlockPos(3, 50, 3)).getCenter());
        helper.assertTrue(helper.getLevel().noCollision(player, player.getDimensions(Pose.STANDING).makeBoundingBox(player.position())),
                "The pose recovery fixture must have standing clearance");
        player.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 100);
        player.doTick(); // FakePlayer.tick is empty; inherited doTick runs Player/LivingEntity's full tick.
        helper.assertTrue(player.getForcedPose() == Pose.SWIMMING && player.getPose() == Pose.SWIMMING,
                "The forced medical pose must survive vanilla pose selection");
        helper.assertTrue(Math.abs(player.getBoundingBox().getYsize() - 0.45) < 0.00001,
                "Critical dimensions must survive a full movement/effect/pose tick");
        player.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 99);
        player.doTick();
        helper.assertTrue(player.getForcedPose() == null && player.getPose() == Pose.STANDING,
                "Healing must release the forced medical pose through the real tick: forced="
                        + player.getForcedPose() + ", pose=" + player.getPose());
        helper.assertTrue(player.getBoundingBox().getYsize() > 1.7, "Healing must restore normal player dimensions");
        helper.assertTrue(!player.hasEffect(MobEffects.BLINDNESS), "Healing tick must release owned blindness");
        helper.succeed();
    }

    private static CountingFakePlayer criticalPlayer(GameTestHelper helper) {
        var player = player(helper);
        player.getPersistentData().putDouble(MedicalPlayerEffects.TOTAL_DAMAGE, 100);
        MedicalPlayerEffects.reconcile(player, Integer.MAX_VALUE);
        return player;
    }

    private static CountingFakePlayer player(GameTestHelper helper) {
        var player = new CountingFakePlayer(helper.getLevel());
        player.setGameMode(GameType.SURVIVAL);
        player.setPos(helper.absolutePos(new BlockPos(3, 4, 3)).getCenter());
        player.setNoGravity(true);
        return player;
    }

    private static boolean[] abilities(FakePlayer player) {
        var abilities = player.getAbilities();
        return new boolean[] {abilities.mayBuild, abilities.mayfly, abilities.flying, abilities.invulnerable, abilities.instabuild};
    }

    private static MobEffectInstance blindness(int duration, int amplifier) {
        return new MobEffectInstance(MobEffects.BLINDNESS, duration, amplifier, false, false);
    }

    private static final class CountingFakePlayer extends FakePlayer {
        private int abilityUpdates;
        private CountingFakePlayer(ServerLevel level) {
            super(level, new GameProfile(UUID.randomUUID(), "medical-test"));
        }
        @Override
        public void onUpdateAbilities() {
            abilityUpdates++;
            super.onUpdateAbilities();
        }
    }
}
