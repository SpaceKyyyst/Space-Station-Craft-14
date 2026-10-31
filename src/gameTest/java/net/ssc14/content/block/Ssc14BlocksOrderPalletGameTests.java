package net.ssc14.content.block;

import net.ssc14.integration.neoforge.state.Ssc14WorldVariables;
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
import net.ssc14.content.block.commerce.AbstractZeroSlotPalletBlockEntity;
import net.ssc14.content.block.commerce.OrderPalletBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksOrderPalletGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_order_pallet_contract");

    private Ssc14BlocksOrderPalletGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksOrderPalletGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_order_pallet_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        helper.assertTrue(id("order_pallet").equals(
                        BuiltInRegistries.BLOCK.getKey(Ssc14Blocks.ORDER_PALLET.get())),
                "order pallet registry ID changed");
        BlockPos relativePos = new BlockPos(1, 2, 1);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, Ssc14Blocks.ORDER_PALLET.get());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof OrderPalletBlockEntity,
                "order pallet block entity type changed");
        var pallet = (OrderPalletBlockEntity) entity;
        helper.assertTrue(pallet.getContainerSize() == AbstractZeroSlotPalletBlockEntity.SLOT_COUNT,
                "order pallet inventory size changed");
        helper.assertTrue("order_pallet".equals(pallet.getDefaultName().getString())
                        && "Order Pallet".equals(pallet.getDisplayName().getString()),
                "order pallet menu names changed");
        helper.assertTrue(id("order_pallet").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(pallet.getType())),
                "order pallet block entity ID changed");
        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK,
                absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 0,
                "order pallet zero-slot capability changed");
        String key = absolutePos.getX() + "," + absolutePos.getY() + ","
                + absolutePos.getZ() + ";";
        helper.assertTrue(Ssc14WorldVariables.WorldVariables.get(helper.getLevel())
                        .PalletRegistry.contains(key),
                "order pallet was not added to the persistent world registry");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
