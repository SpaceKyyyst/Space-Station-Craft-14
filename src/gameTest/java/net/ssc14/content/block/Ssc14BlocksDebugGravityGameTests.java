package net.ssc14.content.block;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.debug.DebugGravityGeneratorBlock;
import net.ssc14.content.block.debug.DebugGravityGeneratorBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksDebugGravityGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_debug_gravity_contract");

    private Ssc14BlocksDebugGravityGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksDebugGravityGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_debug_gravity_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        DebugGravityGeneratorBlock block =
                (DebugGravityGeneratorBlock) Ssc14Blocks.DEBUG_GRAV_GEN.get();
        helper.assertTrue(id("debug_grav_gen").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "debug gravity generator registry ID changed");
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block.defaultBlockState());
        var shape = block.defaultBlockState().getShape(helper.getLevel(), absolutePos);
        helper.assertTrue(!Shapes.joinIsNotEmpty(
                        Block.box(1, 0, 1, 15, 13, 15), shape, BooleanOp.NOT_SAME),
                "debug gravity generator shape changed");

        var blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof DebugGravityGeneratorBlockEntity,
                "debug gravity generator block entity changed");
        helper.assertTrue(id("debug_grav_gen").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "debug gravity generator block entity ID changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == DebugGravityGeneratorBlockEntity.SLOT_COUNT,
                "debug gravity generator item capability changed");
        Container container = (Container) blockEntity;
        container.setItem(0, new ItemStack(Items.REDSTONE));
        helper.assertTrue(block.getAnalogOutputSignal(block.defaultBlockState(),
                        helper.getLevel(), absolutePos, Direction.NORTH) > 0,
                "debug gravity generator comparator output changed");

        Ssc14ModVariables.station_gravity = false;
        block.tick(block.defaultBlockState(), helper.getLevel(), absolutePos,
                RandomSource.create(1L));
        helper.assertTrue(Ssc14ModVariables.station_gravity,
                "debug gravity generator tick did not enable station gravity");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        block.onDestroyedByPlayer(block.defaultBlockState(), helper.getLevel(), absolutePos,
                player, ItemStack.EMPTY, false, Fluids.EMPTY.defaultFluidState());
        helper.assertTrue(!Ssc14ModVariables.station_gravity,
                "destroying debug gravity generator did not disable station gravity");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
