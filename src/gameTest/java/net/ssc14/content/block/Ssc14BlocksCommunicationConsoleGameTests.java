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
import net.ssc14.content.block.communication.CommunicationConsoleBlock;
import net.ssc14.content.block.communication.CommunicationConsoleBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksCommunicationConsoleGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_communication_console_contract");

    private Ssc14BlocksCommunicationConsoleGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksCommunicationConsoleGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_communication_console_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        CommunicationConsoleBlock block =
                (CommunicationConsoleBlock) Ssc14Blocks.COMMUNICATION_CONSOLE.get();
        helper.assertTrue(id("communication_console").equals(
                        BuiltInRegistries.BLOCK.getKey(block)),
                "communication console registry ID changed");
        var state = block.defaultBlockState();
        helper.assertTrue(state.getValue(CommunicationConsoleBlock.FACING) == Direction.NORTH,
                "communication console default facing changed");
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, state);
        var blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof CommunicationConsoleBlockEntity,
                "communication console block entity changed");
        helper.assertTrue(id("communication_console").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "communication console block entity registry ID changed");
        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == CommunicationConsoleBlockEntity.SLOT_COUNT,
                "communication console item capability slot contract changed");
        helper.assertTrue(state.getShape(helper.getLevel(), absolutePos)
                        .max(Direction.Axis.Y) == 24.77111 / 16.0,
                "communication console height changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
