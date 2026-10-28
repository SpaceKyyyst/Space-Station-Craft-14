package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.energy.SheathingBlock;
import net.ssc14.content.block.energy.SheathingBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksSheathingGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_sheathing_contract");

    private Ssc14BlocksSheathingGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID,
                        Ssc14BlocksSheathingGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_sheathing_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        helper.assertTrue(id("sheathing").equals(
                        BuiltInRegistries.BLOCK.getKey(Ssc14Blocks.SHEATHING.get())),
                "sheathing registry ID changed");
        helper.assertTrue(Ssc14Blocks.SHEATHING.get() instanceof SheathingBlock,
                "sheathing canonical implementation changed");

        BlockPos relative = new BlockPos(1, 2, 1);
        BlockPos absolute = helper.absolutePos(relative);
        helper.setBlock(relative, Ssc14Blocks.SHEATHING.get().defaultBlockState());
        var state = helper.getLevel().getBlockState(absolute);
        helper.assertTrue(!state.getValue(SheathingBlock.LV)
                        && !state.getValue(SheathingBlock.MV)
                        && !state.getValue(SheathingBlock.HV),
                "sheathing cable-layer defaults changed");

        BlockEntity raw = helper.getLevel().getBlockEntity(absolute);
        helper.assertTrue(raw instanceof SheathingBlockEntity,
                "sheathing block entity implementation changed");
        helper.assertTrue(id("sheathing").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(raw.getType())),
                "sheathing block entity ID changed");
        helper.assertTrue(raw instanceof Container container
                        && container.getContainerSize() == SheathingBlockEntity.SLOT_COUNT
                        && container.getMaxStackSize() == SheathingBlockEntity.MAX_STACK_SIZE,
                "sheathing zero-slot container contract changed");

        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK, absolute, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 0,
                "sheathing item capability changed");

        SheathingBlockEntity sheathing = (SheathingBlockEntity) raw;
        sheathing.setLayerNetworkData(2, 11L, 22L, 33L, 44L, 55L);
        var tag = sheathing.saveWithFullMetadata(helper.getLevel().registryAccess());
        helper.assertTrue(tag.getIntOr("activeLayerMode", -1) == 2
                        && tag.getLongOr("currentPower", -1L) == 11L
                        && tag.getLongOr("theoreticalSupply", -1L) == 22L
                        && tag.getLongOr("idealConsumption", -1L) == 33L
                        && tag.getLongOr("outputStored", -1L) == 44L
                        && tag.getLongOr("outputMax", -1L) == 55L,
                "sheathing telemetry serialization changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
