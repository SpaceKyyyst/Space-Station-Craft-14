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
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.explosives.NuclearWarheadBlock;
import net.ssc14.content.block.explosives.NuclearWarheadBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksNuclearWarheadGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_nuclear_warhead_contract");

    private Ssc14BlocksNuclearWarheadGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksNuclearWarheadGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_nuclear_warhead_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        NuclearWarheadBlock block =
                (NuclearWarheadBlock) Ssc14Blocks.NUCLEAR_WARHEAD.get();
        helper.assertTrue(id("nuclear_warhead").equals(
                        BuiltInRegistries.BLOCK.getKey(block)),
                "nuclear warhead registry ID changed");
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block.defaultBlockState());
        var shape = block.defaultBlockState().getShape(helper.getLevel(), absolutePos);
        helper.assertTrue(!Shapes.joinIsNotEmpty(Block.box(0, 0, 0, 16, 16, 16),
                        shape, BooleanOp.NOT_SAME),
                "nuclear warhead full-cube shape changed");

        var blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof NuclearWarheadBlockEntity,
                "nuclear warhead block entity changed");
        NuclearWarheadBlockEntity warheadEntity = (NuclearWarheadBlockEntity) blockEntity;
        helper.assertTrue("nuclear_warhead".equals(warheadEntity.getDefaultName().getString())
                        && "Nuclear Warhead".equals(warheadEntity.getDisplayName().getString()),
                "nuclear warhead container names changed");
        helper.assertTrue(id("nuclear_warhead").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "nuclear warhead block entity registry ID changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == NuclearWarheadBlockEntity.SLOT_COUNT,
                "nuclear warhead item capability must expose nine slots");
        Container container = warheadEntity;
        container.setItem(0, new ItemStack(Items.REDSTONE));
        helper.assertTrue(block.getAnalogOutputSignal(block.defaultBlockState(),
                        helper.getLevel(), absolutePos, Direction.NORTH) > 0,
                "nuclear warhead comparator output changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
