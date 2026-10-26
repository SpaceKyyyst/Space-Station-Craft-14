package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.common.network.NetworkTrigger;
import net.ssc14.content.block.control.NetworkButtonBlock;
import net.ssc14.content.block.control.NetworkButtonBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksNetworkButtonGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_network_button_contract");

    private Ssc14BlocksNetworkButtonGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksNetworkButtonGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_network_button_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 60, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        NetworkButtonBlock block = (NetworkButtonBlock) Ssc14Blocks.BUTTON.get();
        helper.assertTrue(id("button").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "network button registry ID changed");
        helper.assertTrue(block instanceof NetworkTrigger,
                "network button trigger contract changed");
        helper.assertTrue(((NetworkTrigger) block).getAvailableTriggers().contains("activate"),
                "network button activate trigger changed");

        helper.setBlock(relativePos, block.defaultBlockState());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof NetworkButtonBlockEntity,
                "network button block entity changed");
        NetworkButtonBlockEntity buttonEntity = (NetworkButtonBlockEntity) entity;
        helper.assertTrue(id("button").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(buttonEntity.getType())),
                "network button block entity ID changed");
        helper.assertTrue(buttonEntity.getContainerSize() == 0
                        && buttonEntity.getMaxStackSize() == 1
                        && "button".equals(buttonEntity.getDefaultName().getString())
                        && "Button".equals(buttonEntity.getDisplayName().getString()),
                "network button container contract changed");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(buttonEntity.createMenu(1, player.getInventory()) instanceof ChestMenu,
                "network button menu contract changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 0,
                "network button item capability changed");

        var hit = new BlockHitResult(Vec3.atCenterOf(absolutePos),
                net.minecraft.core.Direction.NORTH, absolutePos, false);
        block.useWithoutItem(block.defaultBlockState(), helper.getLevel(), absolutePos,
                player, hit);
        BlockState activeState = helper.getLevel().getBlockState(absolutePos);
        helper.assertTrue(activeState.is(block)
                        && activeState.getValue(NetworkButtonBlock.ACTIVE),
                "network button no longer activates on click");
        helper.runAfterDelay(20, () -> helper.assertTrue(
                helper.getLevel().getBlockState(absolutePos)
                        .getValue(NetworkButtonBlock.ACTIVE),
                "network button resets before its 20-tick scheduler callback"));
        helper.runAfterDelay(21, () -> {
            BlockState resetState = helper.getLevel().getBlockState(absolutePos);
            helper.assertTrue(resetState.is(block)
                            && !resetState.getValue(NetworkButtonBlock.ACTIVE),
                    "network button no longer resets after its 20-tick scheduler callback");
            helper.succeed();
        });
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
