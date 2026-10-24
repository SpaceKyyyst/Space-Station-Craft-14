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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.gravity.GravityGeneratorBlock;
import net.ssc14.content.block.gravity.GravityGeneratorBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksGravityGeneratorGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_block_gravity_generator_contract");

    private Ssc14BlocksGravityGeneratorGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksGravityGeneratorGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_gravity_generator_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        GravityGeneratorBlock block =
                (GravityGeneratorBlock) Ssc14Blocks.GRAVITY_GENERATOR.get();
        helper.assertTrue(id("gravity_generator").equals(
                        BuiltInRegistries.BLOCK.getKey(block)),
                "gravity generator registry ID changed");
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, block.defaultBlockState());
        helper.assertTrue(block.defaultBlockState().getValue(GravityGeneratorBlock.FACING)
                        == Direction.NORTH
                        && !block.defaultBlockState().getValue(GravityGeneratorBlock.GRAVITY)
                        && block.defaultBlockState().getValue(GravityGeneratorBlock.BLOCKSTATE) == 0,
                "gravity generator default state changed");

        var blockEntity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(blockEntity instanceof GravityGeneratorBlockEntity,
                "gravity generator block entity changed");
        helper.assertTrue(id("gravity_generator").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                "gravity generator block entity registry ID changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null
                        && handler.getSlots() == GravityGeneratorBlockEntity.SLOT_COUNT,
                "gravity generator item capability slot contract changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
