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
import net.ssc14.content.block.airlock.AbstractAirlockUpperPlugBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksAirlockUpperPlugGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_airlock_upper_plugs_contract");

    private Ssc14BlocksAirlockUpperPlugGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksAirlockUpperPlugGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_airlock_upper_plugs_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyPlug(helper, new BlockPos(2, 2, 2), Ssc14Blocks.AIRLOCK_UP_PLUG.get(),
                id("airlock_up_plug"), "airlock_up_plug");
        verifyPlug(helper, new BlockPos(4, 2, 2), Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.get(),
                id("airlock_up_plug_open"), "airlock_up_plug_open");
        helper.succeed();
    }

    private static void verifyPlug(GameTestHelper helper, BlockPos relativePos,
            net.minecraft.world.level.block.Block block, Identifier expectedId,
            String expectedName) {
        helper.assertTrue(expectedId.equals(BuiltInRegistries.BLOCK.getKey(block)),
                expectedName + " block ID changed");
        helper.setBlock(relativePos, block.defaultBlockState());
        BlockPos absolutePos = helper.absolutePos(relativePos);
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof AbstractAirlockUpperPlugBlockEntity,
                expectedName + " block entity changed");
        var plug = (AbstractAirlockUpperPlugBlockEntity) entity;
        helper.assertTrue(expectedId.equals(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(
                        plug.getType())) && plug.getContainerSize() == 0
                        && plug.getMaxStackSize() == 1
                        && expectedName.equals(plug.getDefaultName().getString()),
                expectedName + " block entity contract changed");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(plug.createMenu(1, player.getInventory()) != null,
                expectedName + " menu contract changed");
        var capability = helper.getLevel().getCapability(
                Capabilities.Item.BLOCK, absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 0,
                expectedName + " item capability changed");
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
