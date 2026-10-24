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
import net.ssc14.content.block.commerce.AbstractZeroSlotPalletBlockEntity;
import net.ssc14.content.block.commerce.SalePalletBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksSalePalletGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_sale_pallet_contract");

    private Ssc14BlocksSalePalletGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksSalePalletGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_sale_pallet_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        helper.assertTrue(id("sale_pallet").equals(
                        BuiltInRegistries.BLOCK.getKey(Ssc14Blocks.SALE_PALLET.get())),
                "sale pallet registry ID changed");
        BlockPos relativePos = new BlockPos(1, 2, 1);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, Ssc14Blocks.SALE_PALLET.get());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof SalePalletBlockEntity,
                "sale pallet block entity type changed");
        var pallet = (SalePalletBlockEntity) entity;
        helper.assertTrue(pallet.getContainerSize() == AbstractZeroSlotPalletBlockEntity.SLOT_COUNT,
                "sale pallet inventory size changed");
        helper.assertTrue("sale_pallet".equals(pallet.getDefaultName().getString())
                        && "Sale Pallet".equals(pallet.getDisplayName().getString()),
                "sale pallet menu names changed");
        helper.assertTrue(id("sale_pallet").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(pallet.getType())),
                "sale pallet block entity ID changed");
        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK,
                absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 0,
                "sale pallet zero-slot capability changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
