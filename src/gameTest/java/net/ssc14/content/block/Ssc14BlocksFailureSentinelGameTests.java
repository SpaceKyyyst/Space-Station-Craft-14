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
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.debug.FailureSentinelBlock;
import net.ssc14.content.block.debug.FailureSentinelBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksFailureSentinelGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_failure_sentinel_contract");

    private Ssc14BlocksFailureSentinelGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksFailureSentinelGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_failure_sentinel_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        FailureSentinelBlock block =
                (FailureSentinelBlock) Ssc14Blocks.KOSTILNIBLOK.get();
        helper.assertTrue(id("kostilniblok").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "failure sentinel registry ID changed");

        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block.defaultBlockState());
        var actualShape = block.defaultBlockState().getShape(helper.getLevel(), absolutePos);
        helper.assertTrue(!Shapes.joinIsNotEmpty(
                        Block.box(0, -1, 0.2, 16, 15, 16.2), actualShape,
                        BooleanOp.NOT_SAME),
                "failure sentinel collision shape changed");
        helper.assertTrue(block.getLightDampening(block.defaultBlockState()) == 14,
                "failure sentinel light dampening changed");
        helper.assertTrue(block.defaultBlockState()
                        .getVisualShape(helper.getLevel(), absolutePos,
                                CollisionContext.empty()).isEmpty(),
                "failure sentinel visual shape is no longer empty");

        var blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof FailureSentinelBlockEntity,
                "failure sentinel block entity implementation changed");
        helper.assertTrue(id("kostilniblok").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "failure sentinel block entity ID changed");
        helper.assertTrue(blockEntity instanceof Container container
                        && container.getContainerSize() == FailureSentinelBlockEntity.SLOT_COUNT,
                "failure sentinel inventory size changed");

        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == FailureSentinelBlockEntity.SLOT_COUNT,
                "failure sentinel item capability changed");

        Container container = (Container) blockEntity;
        container.setItem(0, new ItemStack(Items.REDSTONE));
        helper.assertTrue(block.getAnalogOutputSignal(
                        block.defaultBlockState(), helper.getLevel(), absolutePos,
                        Direction.NORTH) > 0,
                "failure sentinel comparator output did not reflect inventory contents");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
