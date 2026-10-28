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
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.storage.TagValueInput;
import net.ssc14.content.menu.commerce.OrderGUIMenu;
import net.ssc14.content.menu.commerce.SaleGUIMenu;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.commerce.CommerceConsoleBlock;
import net.ssc14.content.block.commerce.OrderConsoleBlockEntity;
import net.ssc14.content.block.commerce.SaleConsoleBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksCommerceConsoleGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_commerce_console_contract");

    private Ssc14BlocksCommerceConsoleGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksCommerceConsoleGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_commerce_console_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyOrderConsole(helper, new BlockPos(1, 2, 1));
        verifySaleConsole(helper, new BlockPos(3, 2, 1));
        helper.succeed();
    }

    private static void verifyOrderConsole(GameTestHelper helper, BlockPos relativePos) {
        var block = (CommerceConsoleBlock) Ssc14Blocks.ORDER_CONSOLE.get();
        helper.assertTrue(id("order_console").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "order console registry ID changed");
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block.defaultBlockState());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof OrderConsoleBlockEntity,
                "order console block entity changed");
        OrderConsoleBlockEntity order = (OrderConsoleBlockEntity) entity;
        helper.assertTrue(id("order_console").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(entity.getType())),
                "order console block entity ID changed");
        verifyCapability(helper, absolutePos, OrderConsoleBlockEntity.SLOT_COUNT,
                "order console item capability changed");
        helper.assertTrue(order.getContainerSize() == 21 && order.getMaxStackSize() == 64,
                "order console inventory limits changed");
        helper.assertTrue("order_console".equals(order.getDefaultName().getString())
                        && "Order console".equals(order.getDisplayName().getString()),
                "order console names changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        ItemStack remainder = handler == null ? new ItemStack(Items.BARRIER)
                : handler.insertItem(0, new ItemStack(Items.IRON_INGOT, 64), false);
        helper.assertTrue(remainder.isEmpty() && order.getItem(0).getCount() == 64,
                "order console sided insertion changed");
        var tag = order.saveWithFullMetadata(helper.getLevel().registryAccess());
        var restored = new OrderConsoleBlockEntity(absolutePos, block.defaultBlockState());
        restored.loadWithComponents(TagValueInput.create(
                ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), tag));
        helper.assertTrue(restored.getItem(0).is(Items.IRON_INGOT)
                        && restored.getItem(0).getCount() == 64,
                "order console inventory persistence changed");
        ItemStack extracted = handler == null ? ItemStack.EMPTY
                : handler.extractItem(0, 64, false);
        helper.assertTrue(extracted.is(Items.IRON_INGOT) && extracted.getCount() == 64
                        && order.getItem(0).isEmpty(),
                "order console sided extraction changed");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(order.createMenu(1, player.getInventory()) instanceof OrderGUIMenu,
                "order console menu routing changed");
        helper.assertTrue(block.defaultBlockState().getValue(CommerceConsoleBlock.FACING)
                        == Direction.NORTH,
                "order console default facing changed");
        helper.assertTrue(block.rotate(block.defaultBlockState(), Rotation.CLOCKWISE_90)
                        .getValue(CommerceConsoleBlock.FACING) == Direction.EAST
                        && block.mirror(block.defaultBlockState(), Mirror.LEFT_RIGHT)
                        .getValue(CommerceConsoleBlock.FACING) == Direction.SOUTH,
                "order console transformation rules changed");
    }

    private static void verifySaleConsole(GameTestHelper helper, BlockPos relativePos) {
        var block = Ssc14Blocks.SALE_CONSOLE.get();
        helper.assertTrue(id("sale_console").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "sale console registry ID changed");
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block.defaultBlockState());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof SaleConsoleBlockEntity,
                "sale console block entity changed");
        SaleConsoleBlockEntity sale = (SaleConsoleBlockEntity) entity;
        helper.assertTrue(id("sale_console").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(entity.getType())),
                "sale console block entity ID changed");
        verifyCapability(helper, absolutePos, SaleConsoleBlockEntity.SLOT_COUNT,
                "sale console item capability changed");
        helper.assertTrue(sale.getContainerSize() == 0 && sale.getMaxStackSize() == 1,
                "sale console inventory limits changed");
        helper.assertTrue("sale_console".equals(sale.getDefaultName().getString())
                        && "Sale console".equals(sale.getDisplayName().getString()),
                "sale console names changed");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(sale.createMenu(2, player.getInventory()) instanceof SaleGUIMenu,
                "sale console menu routing changed");
        helper.assertTrue(block.defaultBlockState().getValue(CommerceConsoleBlock.FACING)
                        == Direction.NORTH,
                "sale console default facing changed");
    }

    private static void verifyCapability(GameTestHelper helper, BlockPos absolutePos,
            int expectedSlots, String message) {
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == expectedSlots, message);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
