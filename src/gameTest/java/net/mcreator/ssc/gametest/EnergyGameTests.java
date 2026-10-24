package net.mcreator.ssc.gametest;

import net.mcreator.ssc.CableType;
import net.mcreator.ssc.EnergyNetwork;
import net.mcreator.ssc.EnergyNetworkManager;
import net.mcreator.ssc.block.LampBlock;
import net.ssc14.content.block.energy.SheathingBlock;
import net.ssc14.content.block.energy.ApcBlockEntity;
import net.ssc14.content.block.energy.PodstationBlockEntity;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GameTestListener;
import net.minecraft.gametest.framework.GameTestRunner;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mcreator.ssc.energy.EnergyTopologyService;
import java.util.function.BooleanSupplier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Real block-entity lifecycle and server ticks drive every network assertion. */
@EventBusSubscriber(modid = "ssc_14")
public final class EnergyGameTests {
    private static final String[] TEST_NAMES = {
        "energy_bridge_removal", "energy_supply_chain", "energy_idle_storage",
        "energy_isolated_chunk_reload", "energy_bridge_chunk_reload"
    };
    private static final long TICKS_PER_SECOND = 20;
    private static final long GENERATOR_WATTS = 100_000;
    private static final long SUBSTATION_WATTS = 150_000;
    private static final long APC_WATTS = 15_000;
    private static final long DEVICE_INPUT_JOULES = 2_500;
    private static final long INITIAL_CHARGE = 50_000;
    private static final int OBSERVATION_TICKS = 20;
    private static final int LIFECYCLE_TIMEOUT_TICKS = 1_200;
    private static final int TRANSITION_TIMEOUT_TICKS = 300;
    private static final int CHUNK_WIDTH = 16;
    private static final int RELOAD_CYCLES = 3;

    private EnergyGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> {
            registry.register(id(TEST_NAMES[0]), EnergyGameTests::bridgeRemoval);
            registry.register(id(TEST_NAMES[1]), EnergyGameTests::supplyChain);
            registry.register(id(TEST_NAMES[2]), EnergyGameTests::idleStorage);
            registry.register(id(TEST_NAMES[3]), EnergyGameTests::isolatedChunkReload);
            registry.register(id(TEST_NAMES[4]), EnergyGameTests::bridgeChunkReload);
        });
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("energy_environment"));
        for (String name : TEST_NAMES) {
            var data = new TestData<>(environment, id("atmosphere_test_room"), LIFECYCLE_TIMEOUT_TICKS, 1, true);
            event.registerTest(id(name), new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, id(name)), data));
        }
    }

    private static void bridgeRemoval(GameTestHelper helper) {
        BlockState cable = cableState(false, false, true);
        for (int x = 2; x <= 6; x++) helper.setBlock(x, 1, 2, cable);
        helper.setBlock(2, 2, 2, Ssc14ModBlocks.APC.get());
        helper.setBlock(8, 3, 2, Ssc14ModBlocks.LAMP.get());
        ApcBlockEntity apc = apc(helper, new BlockPos(2, 2, 2));
        apc.setStoredEnergy(INITIAL_CHARGE);

        awaitEnergy(helper, () -> {
            EnergyNetwork joined = network(helper, CableType.LV, new BlockPos(2, 1, 2));
            helper.assertTrue(joined.getCables().contains(helper.absolutePos(new BlockPos(6, 1, 2))),
                    "Both sides must belong to the same live cable network before removal");
            helper.assertTrue(lampActive(helper, new BlockPos(8, 3, 2)), "The APC must power the lamp before removal");
            long chargeAtDisconnect = apc.getStoredEnergy();
            // Removing one cable layer keeps the sheathing and its block entity in place.
            helper.setBlock(4, 1, 2, cableState(false, false, false));
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(!lampActive(helper, new BlockPos(8, 3, 2)), "Disconnected lamp must turn off on the first tick");
                helper.assertTrue(apc.getStoredEnergy() == chargeAtDisconnect, "Invalid topology must not debit the APC");
            });
            awaitEnergy(helper, () -> {
                EnergyNetwork left = network(helper, CableType.LV, new BlockPos(2, 1, 2));
                EnergyNetwork right = network(helper, CableType.LV, new BlockPos(6, 1, 2));
                helper.assertTrue(left != right, "Removing a bridge must preserve both disconnected networks");
                helper.assertTrue(left.getCableCount() == 2 && right.getCableCount() == 2,
                        "The removed bridge must not remain in either component");
                helper.assertTrue(right.getSources().isEmpty(), "The disconnected lamp must lose its APC source");
                helper.assertTrue(!lampActive(helper, new BlockPos(8, 3, 2)), "A disconnected lamp must turn off");
                helper.assertTrue(apc.getStoredEnergy() == chargeAtDisconnect,
                        "An isolated APC must stop spending energy immediately after disconnect");
                helper.succeed();
            });
        });
    }

    private static void supplyChain(GameTestHelper helper) {
        for (int x = 2; x <= 8; x++) helper.setBlock(x, 1, 2, cableState(true, true, true));
        helper.setBlock(2, 2, 2, Ssc14ModBlocks.DEBU_GGENERATOR.get());
        helper.setBlock(5, 2, 2, Ssc14ModBlocks.PODSTATION.get());
        helper.setBlock(8, 2, 2, Ssc14ModBlocks.APC.get());
        helper.setBlock(9, 3, 2, Ssc14ModBlocks.LAMP.get());
        Circuit circuit = new Circuit(podstation(helper, new BlockPos(5, 2, 2)),
                apc(helper, new BlockPos(8, 2, 2)));
        awaitEnergy(helper, () -> {
            helper.assertTrue(circuit.substation.getStoredEnergy() > 0 && circuit.apc.getStoredEnergy() > 0,
                    "The generator must charge both initially empty storage devices through HV and MV");
            helper.assertTrue(lampActive(helper, new BlockPos(9, 3, 2)), "The full supply chain must light the LV lamp");
            observeCircuit(helper, circuit, OBSERVATION_TICKS, false);
        });
    }

    private static void observeCircuit(GameTestHelper helper, Circuit circuit, int remaining, boolean nearCapacity) {
        long previousSubstation = circuit.substation.getStoredEnergy();
        long previousApc = circuit.apc.getStoredEnergy();
        helper.runAfterDelay(1, () -> {
            EnergyNetwork hv = network(helper, CableType.HV, new BlockPos(2, 1, 2));
            EnergyNetwork mv = network(helper, CableType.MV, new BlockPos(2, 1, 2));
            EnergyNetwork lv = network(helper, CableType.LV, new BlockPos(2, 1, 2));
            assertPower(helper, hv.currentPower, GENERATOR_WATTS, "generator");
            assertPower(helper, mv.currentPower, SUBSTATION_WATTS, "substation");
            assertPower(helper, lv.currentPower, APC_WATTS, "APC");
            helper.assertTrue(hv.currentPower / TICKS_PER_SECOND <= DEVICE_INPUT_JOULES,
                    "Substation input must respect its shared per-tick limit");
            helper.assertTrue(circuit.apc.lastTickReceivingJoules <= DEVICE_INPUT_JOULES,
                    "APC input must respect its shared per-tick limit");
            helper.assertTrue(circuit.apc.lastTickDischargingJoules == 1,
                    "One powered lamp must consume exactly one joule per server tick");
            long substation = circuit.substation.getStoredEnergy();
            long apc = circuit.apc.getStoredEnergy();
            helper.assertTrue(substation >= 0 && substation <= circuit.substation.getMaxEnergy(),
                    "Substation charge must remain within capacity");
            helper.assertTrue(apc >= 0 && apc <= circuit.apc.getMaxEnergy(), "APC charge must remain within capacity");
            helper.assertTrue(substation - previousSubstation == (hv.currentPower - mv.currentPower) / TICKS_PER_SECOND,
                    "Substation charge delta must equal received minus delivered energy");
            helper.assertTrue(apc - previousApc == (mv.currentPower - lv.currentPower) / TICKS_PER_SECOND,
                    "APC charge delta must equal received minus lamp energy");
            helper.assertTrue(lampActive(helper, new BlockPos(9, 3, 2)), "The lamp must remain powered throughout the test");
            if (remaining > 1) {
                observeCircuit(helper, circuit, remaining - 1, nearCapacity);
            } else if (!nearCapacity) {
                circuit.substation.setStoredEnergy(circuit.substation.getMaxEnergy() - 10);
                circuit.apc.setStoredEnergy(circuit.apc.getMaxEnergy() - 1);
                observeCircuit(helper, circuit, OBSERVATION_TICKS, true);
            } else {
                helper.succeed();
            }
        });
    }

    private static void idleStorage(GameTestHelper helper) {
        helper.setBlock(2, 1, 2, cableState(false, true, false));
        helper.setBlock(2, 2, 2, Ssc14ModBlocks.PODSTATION.get());
        helper.setBlock(12, 1, 2, cableState(false, false, true));
        helper.setBlock(12, 2, 2, Ssc14ModBlocks.APC.get());
        PodstationBlockEntity substation = podstation(helper, new BlockPos(2, 2, 2));
        ApcBlockEntity apc = apc(helper, new BlockPos(12, 2, 2));
        substation.setStoredEnergy(INITIAL_CHARGE);
        apc.setStoredEnergy(INITIAL_CHARGE);
        awaitEnergy(helper, () -> helper.runAfterDelay(OBSERVATION_TICKS, () -> {
            EnergyNetwork mv = network(helper, CableType.MV, new BlockPos(2, 1, 2));
            EnergyNetwork lv = network(helper, CableType.LV, new BlockPos(12, 1, 2));
            helper.assertTrue(mv.theoreticalSupply == SUBSTATION_WATTS && lv.theoreticalSupply == APC_WATTS,
                    "Both idle devices must participate in the normal server simulation");
            helper.assertTrue(mv.getConsumers().isEmpty() && lv.getConsumers().isEmpty(), "Idle networks must have no load");
            helper.assertTrue(substation.getStoredEnergy() == INITIAL_CHARGE && apc.getStoredEnergy() == INITIAL_CHARGE,
                    "Networks with no load must not drain storage");
            helper.assertTrue(mv.currentPower == 0 && lv.currentPower == 0 && apc.lastTickDischargingJoules == 0,
                    "No-load networks must report zero delivered energy");
            helper.succeed();
        }));
    }

    private static void awaitEnergy(GameTestHelper helper, Runnable action) {
        helper.runAfterDelay(1, () -> {
            if (net.mcreator.ssc.energy.EnergyTopologyService.rebuilding(helper.getLevel())) awaitEnergy(helper, action);
            else helper.runAfterDelay(2, action);
        });
    }

    /** A remote, radius-zero ticket leaves the GameTest's own forced chunks untouched. */
    private static void isolatedChunkReload(GameTestHelper helper) {
        ChunkPos chunk = new ChunkPos(1_024, 1_024);
        releaseTicketsOnCompletion(helper, chunk);
        loadChunk(helper, chunk);
        BlockPos cable = chunk.getBlockAt(2, 80, 2);
        for (int x = 0; x < 7; x++) helper.getLevel().setBlockAndUpdate(cable.offset(x, 0, 0), cableState(true, true, true));
        awaitTopology(helper, () -> {
            assertLayerOwners(helper, cable, 7);
            reloadIsolated(helper, chunk, cable, RELOAD_CYCLES);
        });
    }

    private static void reloadIsolated(GameTestHelper helper, ChunkPos chunk, BlockPos cable, int remaining) {
        BlockEntity previous = helper.getLevel().getBlockEntity(cable);
        releaseChunk(helper, chunk);
        awaitCondition(helper, () -> previous.isRemoved() && !helper.getLevel().hasChunkAt(cable),
                "Isolated chunk must physically unload and remove its old cable entity", () -> awaitTopology(helper, () -> {
                    assertNoOwners(helper, cable);
                    loadChunk(helper, chunk);
                    helper.assertTrue(helper.getLevel().getBlockEntity(cable) != previous,
                            "Reload must deserialize a fresh cable block entity");
                    awaitTopology(helper, () -> {
                        assertLayerOwners(helper, cable, 7);
                        if (remaining > 1) reloadIsolated(helper, chunk, cable, remaining - 1);
                        else {
                            releaseChunk(helper, chunk);
                            helper.succeed();
                        }
                    });
                }));
    }

    private static void bridgeChunkReload(GameTestHelper helper) {
        ChunkPos left = new ChunkPos(1_056, 1_024);
        ChunkPos right = new ChunkPos(left.x() + 1, left.z());
        releaseTicketsOnCompletion(helper, left, right);
        loadChunk(helper, left);
        loadChunk(helper, right);
        BlockPos origin = left.getBlockAt(0, 80, 0);
        for (int x = 0; x < CHUNK_WIDTH; x++) for (int z = 0; z < CHUNK_WIDTH; z++)
            helper.getLevel().setBlockAndUpdate(origin.offset(x, 0, z), cableState(true, true, true));
        for (int x = CHUNK_WIDTH; x < CHUNK_WIDTH * 2; x++)
            helper.getLevel().setBlockAndUpdate(origin.offset(x, 0, 2), cableState(true, true, true));
        awaitTopology(helper, () -> {
            assertBridgeOwners(helper, origin, true);
            unloadBridge(helper, left, right, origin, () -> {
                // Only the left half is available during this rebuild.
                loadChunk(helper, left);
                awaitTopology(helper, () -> {
                    assertBridgeOwners(helper, origin, false);
                    loadChunk(helper, right);
                    helper.assertTrue(EnergyTopologyService.rebuilding(helper.getLevel()),
                            "Loading the bridge must invalidate its neighbor before deferred rebuilding");
                    // Also cancel queued work through a second physical unload.
                    unloadBridge(helper, left, right, origin, () -> {
                        loadChunk(helper, left);
                        // 768 cable visits cannot finish in two shared budgets of at most 128 steps.
                        helper.assertTrue(CHUNK_WIDTH * CHUNK_WIDTH * CableType.values().length
                                        > 2 * EnergyTopologyService.MAX_WORK_UNITS_PER_TICK,
                                "The reload fixture must exceed two ordinary topology tick budgets");
                        helper.runAfterDelay(2, () -> {
                            helper.assertTrue(EnergyTopologyService.workUnitsThisTick(helper.getLevel()) > 0,
                                    "An ordinary server topology tick must advance the reloaded left half");
                            helper.assertTrue(EnergyTopologyService.rebuilding(helper.getLevel()),
                                    "The advanced left-half generation must still be incomplete before loading the bridge");
                            helper.assertTrue(!helper.getLevel().hasChunkAt(origin.offset(CHUNK_WIDTH, 0, 2)),
                                    "The other half must remain physically unavailable during rebuild progress");
                            loadChunk(helper, right);
                            awaitTopology(helper, () -> {
                                assertBridgeOwners(helper, origin, true);
                                helper.runAfterDelay(OBSERVATION_TICKS, () -> {
                                    assertBridgeOwners(helper, origin, true);
                                    helper.assertTrue(!EnergyTopologyService.rebuilding(helper.getLevel()),
                                            "Repeated availability changes must leave no unfinished topology work");
                                    releaseChunk(helper, left);
                                    releaseChunk(helper, right);
                                    helper.succeed();
                                });
                            });
                        });
                    });
                });
            });
        });
    }

    private static void unloadBridge(GameTestHelper helper, ChunkPos left, ChunkPos right, BlockPos origin, Runnable action) {
        BlockEntity leftEntity = helper.getLevel().getBlockEntity(origin);
        BlockEntity rightEntity = helper.getLevel().getBlockEntity(origin.offset(CHUNK_WIDTH, 0, 2));
        releaseChunk(helper, left);
        releaseChunk(helper, right);
        awaitCondition(helper, () -> leftEntity.isRemoved() && rightEntity.isRemoved()
                        && !helper.getLevel().hasChunkAt(origin)
                        && !helper.getLevel().hasChunkAt(origin.offset(CHUNK_WIDTH, 0, 2)),
                "Both bridge chunks must physically unload before reloading", () -> awaitTopology(helper, () -> {
                    assertNoOwners(helper, origin);
                    assertNoOwners(helper, origin.offset(CHUNK_WIDTH, 0, 2));
                    action.run();
                }));
    }

    private static void assertBridgeOwners(GameTestHelper helper, BlockPos origin, boolean joined) {
        int expected = CHUNK_WIDTH * CHUNK_WIDTH + (joined ? CHUNK_WIDTH : 0);
        assertLayerOwners(helper, origin, expected);
        for (CableType type : CableType.values()) {
            var leftNetwork = EnergyTopologyService.networkAt(helper.getLevel(), type, origin);
            var rightNetwork = EnergyTopologyService.networkAt(helper.getLevel(), type, origin.offset(CHUNK_WIDTH, 0, 2));
            helper.assertTrue(joined ? leftNetwork == rightNetwork : rightNetwork == null,
                    type + " bridge ownership must match actual chunk availability");
        }
    }

    private static void assertLayerOwners(GameTestHelper helper, BlockPos cable, int cableCount) {
        for (CableType type : CableType.values()) {
            var owner = EnergyTopologyService.networkAt(helper.getLevel(), type, cable);
            helper.assertTrue(owner != null && owner.getCableCount() == cableCount,
                    type + " must recover exactly " + cableCount + " cables after real chunk loading");
            for (BlockPos member : owner.getCables()) {
                long owners = EnergyNetworkManager.getNetworks(helper.getLevel(), type).stream()
                        .filter(network -> network.getCables().contains(member)).count();
                helper.assertTrue(owners == 1 && EnergyTopologyService.networkAt(helper.getLevel(), type, member) == owner,
                        type + " cable must have exactly one published owner after repeated reloads");
            }
        }
    }

    private static void assertNoOwners(GameTestHelper helper, BlockPos cable) {
        for (CableType type : CableType.values()) {
            helper.assertTrue(EnergyTopologyService.networkAt(helper.getLevel(), type, cable) == null,
                    type + " unloaded cable must lose its live owner");
            helper.assertTrue(EnergyNetworkManager.getNetworks(helper.getLevel(), type).stream()
                            .noneMatch(network -> network.getCables().contains(cable)),
                    type + " unloaded cable must not remain in a retired published network");
        }
    }

    private static void loadChunk(GameTestHelper helper, ChunkPos chunk) {
        helper.getLevel().getChunkSource().addTicketWithRadius(TicketType.FORCED, chunk, 0);
        helper.getLevel().getChunk(chunk.x(), chunk.z());
    }

    private static void releaseChunk(GameTestHelper helper, ChunkPos chunk) {
        helper.getLevel().getChunkSource().removeTicketWithRadius(TicketType.FORCED, chunk, 0);
    }

    private static void releaseTicketsOnCompletion(GameTestHelper helper, ChunkPos... chunks) {
        helper.testInfo.addListener(new GameTestListener() {
            @Override public void testStructureLoaded(GameTestInfo testInfo) { }
            @Override public void testPassed(GameTestInfo testInfo, GameTestRunner runner) { release(); }
            @Override public void testFailed(GameTestInfo testInfo, GameTestRunner runner) { release(); }
            @Override public void testAddedForRerun(GameTestInfo original, GameTestInfo rerun, GameTestRunner runner) { release(); }
            private void release() {
                for (ChunkPos chunk : chunks) releaseChunk(helper, chunk);
            }
        });
    }

    private static void awaitTopology(GameTestHelper helper, Runnable action) {
        // onLoad is dispatched by the next normal level tick, then its queued rebuild may span ticks.
        helper.runAfterDelay(2, () -> awaitCondition(helper,
                () -> !EnergyTopologyService.rebuilding(helper.getLevel()),
                "Energy topology must converge within the bounded transition timeout", action));
    }

    private static void awaitCondition(GameTestHelper helper, BooleanSupplier condition, String message, Runnable action) {
        awaitCondition(helper, condition, message, action, TRANSITION_TIMEOUT_TICKS);
    }

    private static void awaitCondition(GameTestHelper helper, BooleanSupplier condition, String message, Runnable action, int remaining) {
        if (condition.getAsBoolean()) action.run();
        else {
            helper.assertTrue(remaining > 0, message);
            helper.runAfterDelay(1, () -> awaitCondition(helper, condition, message, action, remaining - 1));
        }
    }

    private static void assertPower(GameTestHelper helper, long power, long limit, String device) {
        helper.assertTrue(power >= 0 && power <= limit && power % TICKS_PER_SECOND == 0,
                device + " must respect its rated output and integer-joule accounting");
    }

    private static EnergyNetwork network(GameTestHelper helper, CableType type, BlockPos relativeCable) {
        BlockPos absoluteCable = helper.absolutePos(relativeCable);
        EnergyNetwork found = EnergyNetworkManager.getNetworks(helper.getLevel(), type).stream()
                .filter(network -> network.isTopologyReady() && network.getCables().contains(absoluteCable)).findFirst().orElse(null);
        helper.assertTrue(found != null, "Cable lifecycle must register the " + type + " network at " + relativeCable);
        return found;
    }

    private static ApcBlockEntity apc(GameTestHelper helper, BlockPos relativePos) {
        var entity = helper.getLevel().getBlockEntity(helper.absolutePos(relativePos));
        helper.assertTrue(entity instanceof ApcBlockEntity, "Placing the APC must create its block entity");
        return (ApcBlockEntity) entity;
    }

    private static PodstationBlockEntity podstation(GameTestHelper helper, BlockPos relativePos) {
        var entity = helper.getLevel().getBlockEntity(helper.absolutePos(relativePos));
        helper.assertTrue(entity instanceof PodstationBlockEntity, "Placing the substation must create its block entity");
        return (PodstationBlockEntity) entity;
    }

    private static boolean lampActive(GameTestHelper helper, BlockPos relativePos) {
        return helper.getLevel().getBlockState(helper.absolutePos(relativePos)).getValue(LampBlock.ACTIVE);
    }

    private static BlockState cableState(boolean hv, boolean mv, boolean lv) {
        return Ssc14ModBlocks.SHEATHING.get().defaultBlockState()
                .setValue(SheathingBlock.HV, hv).setValue(SheathingBlock.MV, mv).setValue(SheathingBlock.LV, lv);
    }

    private static Identifier id(String path) { return Identifier.fromNamespaceAndPath("ssc_14", path); }

    private record Circuit(PodstationBlockEntity substation, ApcBlockEntity apc) { }
}
