package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.energy.AbstractEnergyStorageBlockEntity;
import net.ssc14.content.block.energy.ApcBlockEntity;
import net.ssc14.content.block.energy.PodstationBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksEnergyMachineGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_energy_machine_contract");

    private Ssc14BlocksEnergyMachineGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksEnergyMachineGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_energy_machine_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyId(helper, "apc", Ssc14Blocks.APC.get());
        verifyId(helper, "podstation", Ssc14Blocks.PODSTATION.get());
        BlockPos apcPos = helper.absolutePos(new BlockPos(1, 2, 1));
        BlockPos podstationPos = helper.absolutePos(new BlockPos(3, 2, 1));
        helper.setBlock(new BlockPos(1, 2, 1), Ssc14Blocks.APC.get());
        helper.setBlock(new BlockPos(3, 2, 1), Ssc14Blocks.PODSTATION.get());

        var apc = (ApcBlockEntity) helper.getLevel().getBlockEntity(apcPos);
        var podstation = (PodstationBlockEntity) helper.getLevel().getBlockEntity(podstationPos);
        verifyStorage(helper, apcPos, apc, "apc", ApcBlockEntity.MAX_ENERGY);
        verifyStorage(helper, podstationPos, podstation, "podstation",
                PodstationBlockEntity.MAX_ENERGY);

        apc.setStoredEnergySimulation(123L);
        apc.setNetworkCurrentPower(456L);
        apc.setMainBreakerEnabled(false);
        var tag = apc.saveWithFullMetadata(helper.getLevel().registryAccess());
        helper.assertTrue(tag.getLongOr("storedEnergy", -1L) == 123L
                        && tag.getLongOr("netCurrent", -1L) == 456L
                        && !tag.getBooleanOr("mainBreaker", true),
                "APC energy or breaker serialization changed");
        helper.succeed();
    }

    private static void verifyStorage(GameTestHelper helper, BlockPos pos,
            AbstractEnergyStorageBlockEntity storage, String id, long capacity) {
        helper.assertTrue(storage != null && storage.getMaxEnergy() == capacity,
                id + " capacity changed");
        helper.assertTrue(storage instanceof Container container
                        && container.getContainerSize()
                        == AbstractEnergyStorageBlockEntity.SLOT_COUNT,
                id + " inventory size changed");
        helper.assertTrue(id(id).equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(storage.getType())),
                id + " block entity ID changed");
        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK, pos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == AbstractEnergyStorageBlockEntity.SLOT_COUNT,
                id + " item capability changed");
    }

    private static void verifyId(GameTestHelper helper, String path,
            net.minecraft.world.level.block.Block block) {
        helper.assertTrue(id(path).equals(BuiltInRegistries.BLOCK.getKey(block)),
                path + " registry ID changed");
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
