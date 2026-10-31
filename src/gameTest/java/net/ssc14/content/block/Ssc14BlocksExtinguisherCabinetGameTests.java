package net.ssc14.content.block;

import java.util.EnumMap;
import java.util.Map;
import net.ssc14.content.item.Ssc14Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.storage.ExtinguisherCabinetBlock;
import net.ssc14.content.block.storage.ExtinguisherCabinetInteractionService;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksExtinguisherCabinetGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_extinguisher_cabinet_contract");

    private Ssc14BlocksExtinguisherCabinetGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksExtinguisherCabinetGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_extinguisher_cabinet_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        ExtinguisherCabinetBlock cabinet = (ExtinguisherCabinetBlock)
                Ssc14Blocks.EXTINGUISHER_CABINET.get();
        helper.assertTrue(id("extinguisher_cabinet").equals(
                        BuiltInRegistries.BLOCK.getKey(cabinet)),
                "extinguisher cabinet registry ID changed");
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, cabinet.defaultBlockState());

        for (var entry : expectedShapes().entrySet()) {
            VoxelShape actual = cabinet.defaultBlockState()
                    .setValue(ExtinguisherCabinetBlock.FACING, entry.getKey())
                    .getShape(helper.getLevel(), absolutePos);
            helper.assertTrue(!Shapes.joinIsNotEmpty(
                            entry.getValue(), actual, BooleanOp.NOT_SAME),
                    "extinguisher cabinet shape changed for " + entry.getKey());
        }

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        interact(helper, absolutePos, player);
        helper.assertTrue(helper.getLevel().getBlockState(absolutePos)
                        .getValue(ExtinguisherCabinetBlock.OPEN),
                "closed extinguisher cabinet did not open");
        interact(helper, absolutePos, player);
        helper.assertTrue(helper.getLevel().getBlockState(absolutePos)
                        .getValue(ExtinguisherCabinetBlock.EMPTY)
                        && player.getMainHandItem().is(Ssc14Items.EXTINGUISHER.get()),
                "cabinet did not issue its stored extinguisher");
        interact(helper, absolutePos, player);
        helper.assertTrue(!helper.getLevel().getBlockState(absolutePos)
                        .getValue(ExtinguisherCabinetBlock.EMPTY)
                        && player.getMainHandItem().isEmpty(),
                "cabinet did not accept the regular extinguisher");

        helper.getLevel().setBlock(absolutePos, helper.getLevel().getBlockState(absolutePos)
                .setValue(ExtinguisherCabinetBlock.EMPTY, true), Block.UPDATE_ALL);
        player.setItemInHand(InteractionHand.MAIN_HAND,
                new ItemStack(Ssc14Items.EXTINGUISHER_OPEN.get()));
        interact(helper, absolutePos, player);
        helper.assertTrue(!helper.getLevel().getBlockState(absolutePos)
                        .getValue(ExtinguisherCabinetBlock.EMPTY)
                        && player.getMainHandItem().isEmpty(),
                "cabinet did not accept the open extinguisher variant");
        player.setShiftKeyDown(true);
        interact(helper, absolutePos, player);
        helper.assertTrue(!helper.getLevel().getBlockState(absolutePos)
                        .getValue(ExtinguisherCabinetBlock.OPEN),
                "shift interaction did not close the extinguisher cabinet");
        helper.succeed();
    }

    private static void interact(GameTestHelper helper, BlockPos pos,
            net.minecraft.world.entity.player.Player player) {
        ExtinguisherCabinetInteractionService.interact(
                helper.getLevel(), pos, helper.getLevel().getBlockState(pos), player);
    }

    private static Map<Direction, VoxelShape> expectedShapes() {
        Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
        shapes.put(Direction.NORTH, Block.box(5, 2, 11, 12, 12, 16));
        shapes.put(Direction.EAST, Block.box(0, 2, 5, 5, 12, 12));
        shapes.put(Direction.WEST, Block.box(11, 2, 4, 16, 12, 11));
        shapes.put(Direction.SOUTH, Block.box(4, 2, 0, 11, 12, 5));
        return shapes;
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
