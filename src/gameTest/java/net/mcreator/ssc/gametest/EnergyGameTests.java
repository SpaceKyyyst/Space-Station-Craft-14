package net.mcreator.ssc.gametest;

import net.mcreator.ssc.CableType;
import net.mcreator.ssc.EnergyNetwork;
import net.mcreator.ssc.EnergyNetworkManager;
import net.mcreator.ssc.block.LampBlock;
import net.mcreator.ssc.block.SheathingBlock;
import net.mcreator.ssc.block.entity.APCBlockEntity;
import net.mcreator.ssc.block.entity.PodstationBlockEntity;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Real block-entity lifecycle and server ticks drive every network assertion. */
@EventBusSubscriber(modid = "ssc_14")
public final class EnergyGameTests {
    private static final String[] TEST_NAMES = {
        "energy_bridge_removal", "energy_supply_chain", "energy_idle_storage"
    };
    private static final long TICKS_PER_SECOND = 20;
    private static final long GENERATOR_WATTS = 100_000;
    private static final long SUBSTATION_WATTS = 150_000;
    private static final long APC_WATTS = 15_000;
    private static final long DEVICE_INPUT_JOULES = 2_500;
    private static final long INITIAL_CHARGE = 50_000;
    private static final int OBSERVATION_TICKS = 20;

    private EnergyGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> {
            registry.register(id(TEST_NAMES[0]), EnergyGameTests::bridgeRemoval);
            registry.register(id(TEST_NAMES[1]), EnergyGameTests::supplyChain);
            registry.register(id(TEST_NAMES[2]), EnergyGameTests::idleStorage);
        });
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("energy_environment"));
        for (String name : TEST_NAMES) {
            var data = new TestData<>(environment, id("atmosphere_test_room"), 100, 1, true);
            event.registerTest(id(name), new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, id(name)), data));
        }
    }

    private static void bridgeRemoval(GameTestHelper helper) {
        BlockState cable = cableState(false, false, true);
        for (int x = 2; x <= 6; x++) helper.setBlock(x, 1, 2, cable);
        helper.setBlock(2, 2, 2, Ssc14ModBlocks.APC.get());
        helper.setBlock(8, 3, 2, Ssc14ModBlocks.LAMP.get());
        APCBlockEntity apc = apc(helper, new BlockPos(2, 2, 2));
        apc.setStoredEnergy(INITIAL_CHARGE);

        helper.runAfterDelay(5, () -> {
            EnergyNetwork joined = network(helper, CableType.LV, new BlockPos(2, 1, 2));
            helper.assertTrue(joined.getCables().contains(helper.absolutePos(new BlockPos(6, 1, 2))),
                    "Both sides must belong to the same live cable network before removal");
            helper.assertTrue(lampActive(helper, new BlockPos(8, 3, 2)), "The APC must power the lamp before removal");
            long chargeAtDisconnect = apc.getStoredEnergy();
            // Removing one cable layer keeps the sheathing and its block entity in place.
            helper.setBlock(4, 1, 2, cableState(false, false, false));
            helper.runAfterDelay(3, () -> {
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
        helper.runAfterDelay(5, () -> {
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
        APCBlockEntity apc = apc(helper, new BlockPos(12, 2, 2));
        substation.setStoredEnergy(INITIAL_CHARGE);
        apc.setStoredEnergy(INITIAL_CHARGE);
        helper.runAfterDelay(OBSERVATION_TICKS, () -> {
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
        });
    }

    private static void assertPower(GameTestHelper helper, long power, long limit, String device) {
        helper.assertTrue(power >= 0 && power <= limit && power % TICKS_PER_SECOND == 0,
                device + " must respect its rated output and integer-joule accounting");
    }

    private static EnergyNetwork network(GameTestHelper helper, CableType type, BlockPos relativeCable) {
        BlockPos absoluteCable = helper.absolutePos(relativeCable);
        EnergyNetwork found = EnergyNetworkManager.getNetworks(helper.getLevel(), type).stream()
                .filter(network -> network.getCables().contains(absoluteCable)).findFirst().orElse(null);
        helper.assertTrue(found != null, "Cable lifecycle must register the " + type + " network at " + relativeCable);
        return found;
    }

    private static APCBlockEntity apc(GameTestHelper helper, BlockPos relativePos) {
        var entity = helper.getLevel().getBlockEntity(helper.absolutePos(relativePos));
        helper.assertTrue(entity instanceof APCBlockEntity, "Placing the APC must create its block entity");
        return (APCBlockEntity) entity;
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

    private record Circuit(PodstationBlockEntity substation, APCBlockEntity apc) { }
}
