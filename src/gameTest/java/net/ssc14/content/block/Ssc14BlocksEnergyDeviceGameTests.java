package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.energy.AbstractEnergyMachineContainerBlockEntity;
import net.ssc14.content.block.energy.DebugGeneratorBlockEntity;
import net.ssc14.content.block.energy.SmesBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksEnergyDeviceGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_energy_device_contract");

    private Ssc14BlocksEnergyDeviceGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksEnergyDeviceGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_energy_device_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyId(helper, "debu_ggenerator", Ssc14Blocks.DEBU_GGENERATOR.get());
        verifyId(helper, "smes", Ssc14Blocks.SMES.get());
        verifyDevice(helper, new BlockPos(1, 2, 1), Ssc14Blocks.DEBU_GGENERATOR.get(),
                DebugGeneratorBlockEntity.class, "debu_ggenerator",
                "DEBUG generator [1000 W]");
        verifyDevice(helper, new BlockPos(3, 2, 1), Ssc14Blocks.SMES.get(),
                SmesBlockEntity.class, "smes", "SMES");
        helper.succeed();
    }

    private static void verifyDevice(GameTestHelper helper, BlockPos relativePos,
            net.minecraft.world.level.block.Block block,
            Class<? extends AbstractEnergyMachineContainerBlockEntity> expectedType,
            String id, String displayName) {
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block);
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(expectedType.isInstance(entity), id + " block entity type changed");
        var container = (AbstractEnergyMachineContainerBlockEntity) entity;
        helper.assertTrue(container.getContainerSize()
                        == AbstractEnergyMachineContainerBlockEntity.SLOT_COUNT,
                id + " inventory size changed");
        helper.assertTrue(id.equals(container.getDefaultName().getString())
                        && displayName.equals(container.getDisplayName().getString()),
                id + " menu names changed");
        helper.assertTrue(id(id).equals(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(
                        container.getType())), id + " block entity ID changed");
        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK,
                absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == AbstractEnergyMachineContainerBlockEntity.SLOT_COUNT,
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
