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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.common.network.NetworkAction;
import net.ssc14.common.network.NetworkTrigger;
import net.ssc14.content.block.airlock.ShuttersBlock;
import net.ssc14.content.block.airlock.ShuttersBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksShuttersGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_shutters_contract");

    private Ssc14BlocksShuttersGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksShuttersGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_shutters_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        ShuttersBlock block = (ShuttersBlock) Ssc14Blocks.SHUTTERS.get();
        helper.assertTrue(id("shutters").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "shutters registry ID changed");
        helper.assertTrue(block instanceof NetworkAction && block instanceof NetworkTrigger,
                "shutters network contracts changed");
        helper.setBlock(relativePos, block.defaultBlockState());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof ShuttersBlockEntity,
                "shutters block entity changed");
        ShuttersBlockEntity shutters = (ShuttersBlockEntity) entity;
        helper.assertTrue(id("shutters").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(shutters.getType())),
                "shutters block entity ID changed");
        helper.assertTrue(shutters.getContainerSize() == 0
                        && shutters.getMaxStackSize() == 1
                        && "shutters".equals(shutters.getDefaultName().getString())
                        && "Shutters".equals(shutters.getDisplayName().getString()),
                "shutters container contract changed");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(shutters.createMenu(1, player.getInventory()) instanceof ChestMenu,
                "shutters menu contract changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 0,
                "shutters item capability changed");

        block.executeNetworkAction("open", helper.getLevel(), absolutePos);
        helper.runAfterDelay(6, () -> {
            helper.assertTrue(helper.getBlockState(relativePos).getValue(ShuttersBlock.OPEN),
                    "shutters no longer open through the canonical action contract");
            block.executeNetworkAction("close", helper.getLevel(), absolutePos);
        });
        helper.runAfterDelay(12, () -> {
            helper.assertTrue(!helper.getBlockState(relativePos).getValue(ShuttersBlock.OPEN),
                    "shutters no longer close through the canonical action contract");
            block.executeNetworkAction("toggle", helper.getLevel(), absolutePos);
        });
        helper.runAfterDelay(18, () -> helper.assertTrue(
                helper.getBlockState(relativePos).getValue(ShuttersBlock.OPEN),
                "shutters no longer toggle through the canonical action contract"));
        helper.runAfterDelay(19, helper::succeed);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
