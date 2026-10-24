package net.ssc14.content.block;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.machinery.AssembledMachineFrameBlock;
import net.ssc14.content.block.machinery.MachineFrameAssemblyService;
import net.ssc14.content.block.machinery.MachineFrameBlock;
import net.ssc14.content.block.machinery.MachineFrameBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksMachineFrameGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_machine_frame_contract");

    private Ssc14BlocksMachineFrameGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID,
                        Ssc14BlocksMachineFrameGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_machine_frame_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        helper.assertTrue(id("machine_frame").equals(
                        BuiltInRegistries.BLOCK.getKey(Ssc14Blocks.MACHINE_FRAME.get())),
                "machine_frame registry ID changed");
        helper.assertTrue(id("machine_frame_2").equals(
                        BuiltInRegistries.BLOCK.getKey(Ssc14Blocks.MACHINE_FRAME_2.get())),
                "machine_frame_2 registry ID changed");
        helper.assertTrue(Ssc14Blocks.MACHINE_FRAME.get() instanceof MachineFrameBlock,
                "machine_frame canonical implementation changed");
        helper.assertTrue(Ssc14Blocks.MACHINE_FRAME_2.get() instanceof AssembledMachineFrameBlock,
                "machine_frame_2 canonical implementation changed");

        BlockPos relative = new BlockPos(1, 2, 1);
        BlockPos absolute = helper.absolutePos(relative);
        helper.setBlock(relative, Ssc14Blocks.MACHINE_FRAME.get().defaultBlockState()
                .setValue(MachineFrameBlock.FACING, Direction.EAST));

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(!MachineFrameAssemblyService.assemble(
                        helper.getLevel(), absolute, player),
                "empty hand unexpectedly assembled the machine frame");
        helper.assertTrue(helper.getLevel().getBlockState(absolute)
                        .is(Ssc14Blocks.MACHINE_FRAME.get()),
                "failed assembly changed the frame stage");

        ItemStack cables = new ItemStack(Ssc14ModItems.LOW_VOLTAGE_CABLE.get(), 2);
        player.setItemInHand(InteractionHand.MAIN_HAND, cables);
        helper.assertTrue(MachineFrameAssemblyService.assemble(
                        helper.getLevel(), absolute, player),
                "low-voltage cable did not assemble the machine frame");

        var assembled = helper.getLevel().getBlockState(absolute);
        helper.assertTrue(assembled.is(Ssc14Blocks.MACHINE_FRAME_2.get()),
                "assembly did not select machine_frame_2");
        helper.assertTrue(assembled.getValue(AssembledMachineFrameBlock.FACING) == Direction.EAST,
                "assembly did not preserve facing");
        helper.assertTrue(!assembled.getValue(AssembledMachineFrameBlock.READY),
                "assembled frame ready default changed");
        helper.assertTrue(cables.getCount() == 1,
                "assembly did not consume exactly one cable");

        BlockEntity blockEntity = helper.getLevel().getBlockEntity(absolute);
        helper.assertTrue(blockEntity instanceof MachineFrameBlockEntity,
                "assembled frame block entity implementation changed");
        helper.assertTrue(id("machine_frame_2").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "machine_frame_2 block entity ID changed");
        helper.assertTrue(blockEntity instanceof Container container
                        && container.getContainerSize() == MachineFrameBlockEntity.SLOT_COUNT
                        && container.getMaxStackSize() == MachineFrameBlockEntity.MAX_STACK_SIZE,
                "machine-frame container contract changed");
        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK, absolute, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == MachineFrameBlockEntity.SLOT_COUNT,
                "machine-frame item capability changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
