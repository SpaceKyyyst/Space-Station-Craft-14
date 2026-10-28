package net.ssc14.content.block;

import java.util.Map;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.explosives.C4ChargeBlock;
import net.ssc14.content.block.explosives.C4ChargeBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksC4GameTests {
    private static final Identifier TEST_ID = id("compatibility_block_c4_contract");
    private static final Map<Direction, VoxelShape> EXPECTED_SHAPES = Map.of(
            Direction.NORTH, Block.box(5, 3, 13, 11, 13, 16),
            Direction.EAST, Block.box(0, 3, 5, 3, 13, 11),
            Direction.WEST, Block.box(13, 3, 5, 16, 13, 11),
            Direction.UP, Block.box(5, 0, 3, 11, 3, 13),
            Direction.DOWN, Block.box(5, 13, 3, 11, 16, 13),
            Direction.SOUTH, Block.box(5, 3, 0, 11, 13, 3));

    private Ssc14BlocksC4GameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksC4GameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_c4_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        C4ChargeBlock block = (C4ChargeBlock) Ssc14Blocks.C_4.get();
        helper.assertTrue(id("c_4").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "C4 block registry ID changed");
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block.defaultBlockState());

        EXPECTED_SHAPES.forEach((direction, expected) -> {
            var state = block.defaultBlockState().setValue(C4ChargeBlock.FACING, direction);
            var actual = state.getShape(helper.getLevel(), absolutePos);
            helper.assertTrue(!Shapes.joinIsNotEmpty(expected, actual, BooleanOp.NOT_SAME),
                    "C4 shape changed for " + direction);
        });

        var blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof C4ChargeBlockEntity,
                "C4 block entity changed");
        helper.assertTrue(id("c_4").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "C4 block entity registry ID changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == C4ChargeBlockEntity.SLOT_COUNT,
                "C4 item capability must expose nine slots");
        Container container = (Container) blockEntity;
        container.setItem(0, new ItemStack(Items.REDSTONE));
        helper.assertTrue(block.getAnalogOutputSignal(block.defaultBlockState(),
                        helper.getLevel(), absolutePos, Direction.NORTH) > 0,
                "C4 comparator output changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
