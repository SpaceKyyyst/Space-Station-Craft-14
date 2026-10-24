package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.storage.ClosetBlockEntity;
import net.ssc14.content.block.storage.SecureClosetBlock;
import net.ssc14.content.block.storage.SecureClosetInteractionService;
import net.ssc14.content.block.storage.StationClosetBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksSecureClosetGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_secure_closet_contract");

    private Ssc14BlocksSecureClosetGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksSecureClosetGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_secure_closet_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        SecureClosetBlock closet = (SecureClosetBlock) Ssc14Blocks.CLOSET_SECURE.get();
        helper.assertTrue(id("closet_secure").equals(BuiltInRegistries.BLOCK.getKey(closet)),
                "secure closet registry ID changed");

        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, closet.defaultBlockState());
        BlockEntity blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof ClosetBlockEntity,
                "secure closet canonical block entity changed");
        helper.assertTrue(id("closet_secure").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "secure closet block entity registry ID changed");
        helper.assertTrue(blockEntity instanceof Container container
                        && container.getContainerSize() == 30,
                "secure closet inventory size changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 30,
                "secure closet item capability changed");
        helper.assertTrue(helper.getLevel().getBlockState(absolutePos.above())
                        .is(Ssc14Blocks.UP_CLOSET_PLUG.get()),
                "secure closet did not place its canonical upper plug");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var state = helper.getLevel().getBlockState(absolutePos);
        SecureClosetInteractionService.interact(helper.getLevel(), absolutePos, state, player);
        state = helper.getLevel().getBlockState(absolutePos);
        helper.assertTrue(!state.getValue(SecureClosetBlock.BLOCKED)
                        && !state.getValue(StationClosetBlock.OPEN),
                "first authorized interaction did not unlock the secure closet");
        SecureClosetInteractionService.interact(helper.getLevel(), absolutePos, state, player);
        helper.assertTrue(helper.getLevel().getBlockState(absolutePos)
                        .getValue(StationClosetBlock.OPEN),
                "second authorized interaction did not open the secure closet");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
