package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.ssc14.content.block.communication.CommunicationServersBlock;
import net.ssc14.content.block.communication.CommunicationServersBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksCommunicationServersGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_communication_servers_contract");

    private Ssc14BlocksCommunicationServersGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksCommunicationServersGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_communication_servers_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        CommunicationServersBlock block =
                (CommunicationServersBlock) Ssc14Blocks.COMMUNICATION_SERVERS.get();
        helper.assertTrue(id("communication_servers").equals(
                        BuiltInRegistries.BLOCK.getKey(block)),
                "communication servers registry ID changed");
        var state = block.defaultBlockState();
        helper.assertTrue(state.getValue(CommunicationServersBlock.FACING) == Direction.NORTH
                        && state.getValue(CommunicationServersBlock.TYPE) == 0,
                "communication servers default state changed");
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, state);
        var blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof CommunicationServersBlockEntity,
                "communication servers block entity changed");
        helper.assertTrue(id("communication_servers").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "communication servers block entity registry ID changed");
        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == CommunicationServersBlockEntity.SLOT_COUNT,
                "communication servers item capability slot contract changed");
        helper.assertTrue(state.getShape(helper.getLevel(), absolutePos)
                        .max(Direction.Axis.Y) == 31.0 / 16.0,
                "communication servers height changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
