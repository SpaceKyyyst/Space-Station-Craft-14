package net.ssc14.content.block;

import java.util.EnumMap;
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
import net.ssc14.content.block.machinery.PortableSheaterBlock;
import net.ssc14.content.block.machinery.PortableSheaterBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksPortableSheaterGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_portable_sheater_contract");

    private Ssc14BlocksPortableSheaterGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksPortableSheaterGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_portable_sheater_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        PortableSheaterBlock block =
                (PortableSheaterBlock) Ssc14Blocks.PORTABLE_SHEATER.get();
        helper.assertTrue(id("portable_sheater").equals(
                        BuiltInRegistries.BLOCK.getKey(block)),
                "portable sheater registry ID changed");
        helper.assertTrue(PortableSheaterBlock.BLOCKSTATE.getPossibleValues().size() == 5,
                "portable sheater no longer exposes five machine states");

        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block.defaultBlockState());
        for (var entry : expectedShapes().entrySet()) {
            VoxelShape actual = block.defaultBlockState()
                    .setValue(PortableSheaterBlock.FACING, entry.getKey())
                    .getShape(helper.getLevel(), absolutePos);
            helper.assertTrue(!Shapes.joinIsNotEmpty(entry.getValue(), actual,
                            BooleanOp.NOT_SAME),
                    "portable sheater shape changed for " + entry.getKey());
        }

        var blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof PortableSheaterBlockEntity,
                "portable sheater block entity implementation changed");
        helper.assertTrue(id("portable_sheater").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "portable sheater block entity ID changed");
        helper.assertTrue(blockEntity instanceof Container container
                        && container.getContainerSize() == PortableSheaterBlockEntity.SLOT_COUNT,
                "portable sheater inventory size changed");

        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == PortableSheaterBlockEntity.SLOT_COUNT,
                "portable sheater item capability changed");

        Container container = (Container) blockEntity;
        container.setItem(0, new ItemStack(Items.IRON_INGOT));
        helper.assertTrue(block.getAnalogOutputSignal(
                        block.defaultBlockState(), helper.getLevel(), absolutePos,
                        Direction.NORTH) > 0,
                "portable sheater comparator output did not reflect inventory contents");
        helper.succeed();
    }

    private static Map<Direction, VoxelShape> expectedShapes() {
        Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
        shapes.put(Direction.NORTH, Block.box(4, 0, 6, 12, 24, 11));
        shapes.put(Direction.EAST, Block.box(5, 0, 4, 10, 24, 12));
        shapes.put(Direction.WEST, Block.box(6, 0, 4, 11, 24, 12));
        shapes.put(Direction.SOUTH, Block.box(4, 0, 5, 12, 24, 10));
        return shapes;
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
