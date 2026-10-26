package net.ssc14.content.block;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.world.inventory.VendingAut1GUIMenu;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.TagValueInput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.commerce.VendingMachineBlock;
import net.ssc14.content.block.commerce.VendingMachineBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksVendingMachineGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_vending_machine_contract");

    private Ssc14BlocksVendingMachineGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksVendingMachineGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_vending_machine_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        VendingMachineBlock block = (VendingMachineBlock) Ssc14Blocks.VENDING_AUT_1.get();
        helper.assertTrue(id("vending_aut_1").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "vending machine registry ID changed");
        helper.setBlock(relativePos, block.defaultBlockState());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof VendingMachineBlockEntity,
                "vending machine block entity changed");
        VendingMachineBlockEntity vending = (VendingMachineBlockEntity) entity;
        helper.assertTrue(id("vending_aut_1").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(vending.getType())),
                "vending machine block entity ID changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 26,
                "vending machine item capability changed");
        helper.assertTrue(vending.getContainerSize() == 26 && vending.getMaxStackSize() == 50,
                "vending machine inventory limits changed");
        helper.assertTrue("vending_aut_1".equals(vending.getDefaultName().getString())
                        && "Vending Automate (Snaks blue)".equals(
                                vending.getDisplayName().getString()),
                "vending machine names changed");
        helper.assertTrue(vending.getItem(1).is(Ssc14ModItems.CHEESE_HONKERS.get())
                        && vending.getItem(1).getCount() == 1
                        && vending.getItem(2).is(Ssc14ModItems.CHIPS.get())
                        && vending.getItem(2).getCount() == 1
                        && vending.getItem(3).is(Ssc14ModItems.BORITOS.get())
                        && vending.getItem(3).getCount() == 1
                        && vending.getItem(5).is(Ssc14ModItems.POPCORN.get())
                        && vending.getItem(5).getCount() == 1
                        && vending.getItem(6).is(Ssc14ModItems.ENERGY_BAR.get())
                        && vending.getItem(6).getCount() == 1,
                "vending machine initial products changed");
        helper.assertTrue(vending.getPersistentData().getDoubleOr("slot_1", 0) == 4
                        && vending.getPersistentData().getDoubleOr("slot_2", 0) == 4
                        && vending.getPersistentData().getDoubleOr("slot_3", 0) == 4
                        && vending.getPersistentData().getDoubleOr("slot_5", 0) == 4
                        && vending.getPersistentData().getDoubleOr("slot_6", 0) == 4,
                "vending machine initial stock counters changed");
        helper.assertTrue(helper.getLevel().getBlockState(absolutePos.above())
                        .is(Ssc14Blocks.VENDING_KOSTIL.get()),
                "vending machine upper plug generation changed");
        ItemStack remainder = handler == null ? new ItemStack(Items.BARRIER)
                : handler.insertItem(10, new ItemStack(Items.IRON_INGOT, 50), false);
        helper.assertTrue(remainder.isEmpty() && vending.getItem(10).getCount() == 50,
                "vending machine sided insertion changed");
        var tag = vending.saveWithFullMetadata(helper.getLevel().registryAccess());
        var restored = new VendingMachineBlockEntity(absolutePos, block.defaultBlockState());
        restored.loadWithComponents(TagValueInput.create(
                ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), tag));
        helper.assertTrue(restored.getItem(10).is(Items.IRON_INGOT)
                        && restored.getItem(10).getCount() == 50,
                "vending machine inventory persistence changed");
        helper.assertTrue(restored.getPersistentData().getDoubleOr("slot_1", 0) == 4
                        && restored.getPersistentData().getDoubleOr("slot_2", 0) == 4
                        && restored.getPersistentData().getDoubleOr("slot_3", 0) == 4
                        && restored.getPersistentData().getDoubleOr("slot_5", 0) == 4
                        && restored.getPersistentData().getDoubleOr("slot_6", 0) == 4,
                "vending machine stock counter persistence changed");
        ItemStack extracted = handler == null ? ItemStack.EMPTY
                : handler.extractItem(10, 50, false);
        helper.assertTrue(extracted.is(Items.IRON_INGOT) && extracted.getCount() == 50
                        && vending.getItem(10).isEmpty(),
                "vending machine sided extraction changed");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(vending.createMenu(1, player.getInventory())
                        instanceof VendingAut1GUIMenu,
                "vending machine menu routing changed");
        helper.assertTrue(block.defaultBlockState().getValue(VendingMachineBlock.FACING)
                        == Direction.NORTH,
                "vending machine default facing changed");

        BlockPos creativeRelative = new BlockPos(5, 2, 2);
        BlockPos creativeAbsolute = helper.absolutePos(creativeRelative);
        helper.setBlock(creativeRelative, block.defaultBlockState());
        block.attack(block.defaultBlockState(), helper.getLevel(), creativeAbsolute,
                helper.makeMockPlayer(GameType.CREATIVE));
        helper.assertTrue(helper.getLevel().getBlockState(creativeAbsolute).isAir(),
                "creative attack no longer removes the vending machine");

        helper.getLevel().setBlock(absolutePos.above(), Blocks.AIR.defaultBlockState(),
                Block.UPDATE_ALL);
        helper.assertTrue(helper.getLevel().getBlockState(absolutePos).isAir(),
                "vending machine no longer removes itself without the upper plug");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
