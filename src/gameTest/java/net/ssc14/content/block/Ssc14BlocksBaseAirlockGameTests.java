package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.airlock.BaseAirlockD1Block;
import net.ssc14.content.block.airlock.BaseAirlockD1BlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksBaseAirlockGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_base_airlock_contract");

    private Ssc14BlocksBaseAirlockGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksBaseAirlockGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_base_airlock_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relativePos = new BlockPos(2, 2, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        BaseAirlockD1Block block = (BaseAirlockD1Block) Ssc14Blocks.BASE_AIRLOCK_D_1.get();
        helper.assertTrue(id("base_airlock_d_1").equals(BuiltInRegistries.BLOCK.getKey(block)),
                "base airlock registry ID changed");
        helper.setBlock(relativePos, block.defaultBlockState());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof BaseAirlockD1BlockEntity,
                "base airlock block entity changed");
        BaseAirlockD1BlockEntity airlock = (BaseAirlockD1BlockEntity) entity;
        helper.assertTrue(id("base_airlock_d_1").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(airlock.getType()))
                        && airlock.getContainerSize() == 9
                        && "base_airlock_d_1".equals(airlock.getDefaultName().getString())
                        && "Base Airlock".equals(airlock.getDisplayName().getString()),
                "base airlock block entity contract changed");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(airlock.createMenu(1, player.getInventory()) != null,
                "base airlock menu contract changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 9,
                "base airlock item capability changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
