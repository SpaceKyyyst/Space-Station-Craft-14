package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.storage.ClosetBlockEntity;
import net.ssc14.content.block.storage.ClosetInteractionService;
import net.ssc14.content.block.storage.StationClosetBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksClosetGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_closet_contract");
    private static final List<ExpectedBlockEntity> EXPECTED = List.of(
            expected(Ssc14Blocks.CLOSET, "closet"),
            expected(Ssc14Blocks.CLOSET_EMERG, "closet_emerg"),
            expected(Ssc14Blocks.CLOSET_FIRE, "closet_fire"),
            expected(Ssc14Blocks.CLOSET_BLACK, "closet_black"),
            expected(Ssc14Blocks.CLOSET_BLUE, "closet_blue"),
            expected(Ssc14Blocks.CLOSET_GRAY, "closet_gray"),
            expected(Ssc14Blocks.CLOSET_GREEN, "closet_green"),
            expected(Ssc14Blocks.CLOSET_MIXED, "closet_mixed"),
            expected(Ssc14Blocks.CLOSET_N_2, "closet_n_2"),
            expected(Ssc14Blocks.CLOSET_ORANGE, "closet_orange"),
            expected(Ssc14Blocks.CLOSET_PINK, "closet_pink"));

    private Ssc14BlocksClosetGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksClosetGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_closet_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relative = new BlockPos(1, 2, 1);
        for (ExpectedBlockEntity expected : EXPECTED) {
            Block block = expected.holder().get();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(block instanceof StationClosetBlock,
                    expected.path() + " canonical implementation changed");
            helper.assertTrue(Float.compare(20.0F, block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(Float.compare(10.0F, block.getExplosionResistance()) == 0,
                    expected.path() + " explosion resistance changed");

            helper.setBlock(relative.above(), Blocks.AIR);
            helper.setBlock(relative, block.defaultBlockState());
            BlockEntity blockEntity = helper.getLevel().getBlockEntity(helper.absolutePos(relative));
            helper.assertTrue(blockEntity instanceof ClosetBlockEntity,
                    expected.path() + " block entity type changed");
            helper.assertTrue(id(expected.path()).equals(
                            BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                    expected.path() + " block entity registry ID changed");
            helper.assertTrue(blockEntity instanceof Container container
                            && container.getContainerSize() == 30,
                    expected.path() + " inventory size changed");
            var itemCapability = helper.getLevel().getCapability(
                    Capabilities.Item.BLOCK, helper.absolutePos(relative), null);
            IItemHandler itemHandler = itemCapability == null
                    ? null : IItemHandler.of(itemCapability);
            helper.assertTrue(itemHandler != null && itemHandler.getSlots() == 30,
                    expected.path() + " item capability changed");
            helper.assertTrue(Identifier.fromNamespaceAndPath("ssc_14", "up_closet_plug")
                            .equals(BuiltInRegistries.BLOCK.getKey(
                                    helper.getBlockState(relative.above()).getBlock())),
                    expected.path() + " upper plug changed");

            ClosetInteractionService.toggle(helper.getLevel(), helper.absolutePos(relative),
                    helper.getBlockState(relative));
            helper.assertTrue(helper.getBlockState(relative).getValue(StationClosetBlock.OPEN),
                    expected.path() + " did not open");
            ClosetInteractionService.toggle(helper.getLevel(), helper.absolutePos(relative),
                    helper.getBlockState(relative));
            helper.assertTrue(!helper.getBlockState(relative).getValue(StationClosetBlock.OPEN),
                    expected.path() + " did not close");
            helper.setBlock(relative, Blocks.AIR);
            helper.setBlock(relative.above(), Blocks.AIR);
        }

        StationClosetBlock standard = (StationClosetBlock) Ssc14Blocks.CLOSET.get();
        BlockState standardOpen = standard.defaultBlockState()
                .setValue(StationClosetBlock.OPEN, true);
        helper.assertTrue(!standardOpen.getShape(helper.getLevel(), helper.absolutePos(relative))
                        .equals(standard.defaultBlockState().getShape(
                                helper.getLevel(), helper.absolutePos(relative))),
                "standard closet open shape changed");
        helper.succeed();
    }

    private static ExpectedBlockEntity expected(DeferredBlock<Block> holder, String path) {
        return new ExpectedBlockEntity(holder, path);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlockEntity(DeferredBlock<Block> holder, String path) { }
}
