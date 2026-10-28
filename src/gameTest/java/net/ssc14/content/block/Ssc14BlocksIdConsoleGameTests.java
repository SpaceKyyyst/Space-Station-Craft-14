package net.ssc14.content.block;

import net.ssc14.content.menu.access.IDcodeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.access.IdConsoleBlock;
import net.ssc14.content.block.access.IdConsoleBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksIdConsoleGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_id_console_contract");

    private Ssc14BlocksIdConsoleGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksIdConsoleGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_id_console_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        IdConsoleBlock block = (IdConsoleBlock) Ssc14Blocks.CONSOLE_OF_ID.get();
        helper.assertTrue(id("console_of_id").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "ID console registry ID changed");
        helper.setBlock(relativePos, block.defaultBlockState());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof IdConsoleBlockEntity,
                "ID console block entity changed");
        IdConsoleBlockEntity console = (IdConsoleBlockEntity) entity;
        helper.assertTrue(id("console_of_id").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(console.getType())),
                "ID console block entity ID changed");
        helper.assertTrue(console.getContainerSize() == 2 && console.getMaxStackSize() == 1,
                "ID console inventory limits changed");
        helper.assertTrue("console_of_id".equals(console.getDefaultName().getString())
                        && "Console Of ID-card".equals(console.getDisplayName().getString()),
                "ID console names changed");

        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 2,
                "ID console item capability changed");
        ItemStack remainder = handler == null ? new ItemStack(Items.BARRIER)
                : handler.insertItem(0, new ItemStack(Items.IRON_INGOT, 2), false);
        helper.assertTrue(console.getItem(0).is(Items.IRON_INGOT)
                        && console.getItem(0).getCount() == 1
                        && remainder.is(Items.IRON_INGOT) && remainder.getCount() == 1,
                "ID console max-stack capability behavior changed");
        ItemStack namedCard = new ItemStack(Items.PAPER);
        namedCard.set(DataComponents.CUSTOM_NAME, Component.literal("Access card"));
        ItemStack cardRemainder = handler == null ? namedCard
                : handler.insertItem(1, namedCard, false);
        helper.assertTrue(cardRemainder.isEmpty()
                        && console.getItem(1).is(Items.PAPER)
                        && "Access card".equals(console.getItem(1).getHoverName().getString()),
                "ID console second slot or item components changed");

        var tag = console.saveWithFullMetadata(helper.getLevel().registryAccess());
        var restored = new IdConsoleBlockEntity(absolutePos, block.defaultBlockState());
        restored.loadWithComponents(TagValueInput.create(
                ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), tag));
        helper.assertTrue(restored.getItem(0).is(Items.IRON_INGOT)
                        && restored.getItem(0).getCount() == 1,
                "ID console inventory persistence changed");
        helper.assertTrue(restored.getItem(1).is(Items.PAPER)
                        && "Access card".equals(restored.getItem(1).getHoverName().getString()),
                "ID console component persistence changed");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(console.createMenu(1, player.getInventory()) instanceof IDcodeMenu,
                "ID console menu routing changed");
        var hit = new BlockHitResult(Vec3.atCenterOf(absolutePos), Direction.UP,
                absolutePos, false);
        InteractionResult useResult = block.useWithoutItem(block.defaultBlockState(),
                helper.getLevel(), absolutePos, player, hit);
        helper.assertTrue(useResult == InteractionResult.SUCCESS,
                "ID console interaction result changed");
        helper.assertTrue(block.defaultBlockState().getValue(IdConsoleBlock.FACING)
                        == Direction.NORTH,
                "ID console default facing changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
