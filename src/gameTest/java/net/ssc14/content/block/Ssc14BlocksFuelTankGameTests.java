package net.ssc14.content.block;

import net.minecraft.core.BlockPos;
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
import net.ssc14.content.block.fuel.FuelTankBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksFuelTankGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_fuel_tank_contract");

    private Ssc14BlocksFuelTankGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(TEST_ID,
                Ssc14BlocksFuelTankGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_fuel_tank_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        helper.assertTrue(id("fuel_tank").equals(
                        BuiltInRegistries.BLOCK.getKey(Ssc14Blocks.FUEL_TANK.get())),
                "fuel tank registry ID changed");
        BlockPos relativePos = new BlockPos(1, 2, 1);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        helper.setBlock(relativePos, Ssc14Blocks.FUEL_TANK.get());
        var entity = helper.getLevel().getBlockEntity(absolutePos);
        helper.assertTrue(entity instanceof FuelTankBlockEntity,
                "fuel tank block entity type changed");
        var tank = (FuelTankBlockEntity) entity;
        helper.assertTrue(tank.getContainerSize() == ZeroSlotContainerBlockEntity.SLOT_COUNT,
                "fuel tank inventory size changed");
        helper.assertTrue("fuel_tank".equals(tank.getDefaultName().getString())
                        && "Fuel Tank".equals(tank.getDisplayName().getString()),
                "fuel tank menu names changed");
        helper.assertTrue(id("fuel_tank").equals(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(tank.getType())),
                "fuel tank block entity ID changed");
        var capability = helper.getLevel().getCapability(Capabilities.Item.BLOCK,
                absolutePos, null);
        IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
        helper.assertTrue(handler != null && handler.getSlots() == 0,
                "fuel tank zero-slot capability changed");
        helper.assertTrue(tank.getPersistentData().getBooleanOr("geNE_r_ate123", false),
                "fuel tank initialization marker changed");
        helper.assertTrue(tank.getPersistentData().getDoubleOr("fuel", 0.0) == 100000.0,
                "fuel tank initial fuel changed");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
