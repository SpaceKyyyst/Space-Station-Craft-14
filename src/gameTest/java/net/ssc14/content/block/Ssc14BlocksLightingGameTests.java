package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.lighting.DebugLightBlock;
import net.ssc14.content.block.lighting.EmergencyLightBlock;
import net.ssc14.content.block.lighting.LightingBlockEntity;
import net.ssc14.content.block.lighting.ServiceableLightBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksLightingGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_lighting_contract");
    private static final List<Expected> EXPECTED = List.of(
            expected(Ssc14Blocks.LAMP, "lamp", ServiceableLightBlock.class, true, 5.0F, 0),
            expected(Ssc14Blocks.TECH_LAMP, "tech_lamp", ServiceableLightBlock.class,
                    false, 5.0F, 0),
            expected(Ssc14Blocks.DEBUG_LAMP, "debug_lamp", DebugLightBlock.class,
                    false, 10.0F, 15),
            expected(Ssc14Blocks.DEBUG_TECH_LAMP, "debug_tech_lamp", DebugLightBlock.class,
                    false, 10.0F, 15),
            expected(Ssc14Blocks.EMERGENCY_LIGHT, "emergency_light", EmergencyLightBlock.class,
                    true, 5.0F, 0));

    private Ssc14BlocksLightingGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID,
                        Ssc14BlocksLightingGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_lighting_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relative = new BlockPos(1, 2, 1);
        for (Expected expected : EXPECTED) {
            Block block = expected.holder().get();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(expected.implementation().isInstance(block),
                    expected.path() + " canonical implementation changed");
            helper.assertTrue(Float.compare(expected.destroyTime(),
                            block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(block.defaultBlockState().getLightEmission() == expected.lightLevel(),
                    expected.path() + " light level changed");

            helper.setBlock(relative, block.defaultBlockState());
            BlockEntity blockEntity = helper.getLevel().getBlockEntity(helper.absolutePos(relative));
            if (expected.hasBlockEntity()) {
                helper.assertTrue(blockEntity instanceof LightingBlockEntity,
                        expected.path() + " block entity implementation changed");
                helper.assertTrue(id(expected.path()).equals(
                                BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType())),
                        expected.path() + " block entity registry ID changed");
                helper.assertTrue(blockEntity instanceof Container container
                                && container.getContainerSize() == 0,
                        expected.path() + " zero-slot compatibility container changed");
                var capability = helper.getLevel().getCapability(
                        Capabilities.Item.BLOCK, helper.absolutePos(relative), null);
                IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
                helper.assertTrue(handler != null && handler.getSlots() == 0,
                        expected.path() + " item capability changed");
            } else {
                helper.assertTrue(blockEntity == null,
                        expected.path() + " unexpectedly gained a block entity");
            }
            helper.setBlock(relative, Blocks.AIR);
        }

        helper.assertTrue(!Ssc14Blocks.LAMP.get().defaultBlockState()
                        .getShape(helper.getLevel(), helper.absolutePos(relative))
                        .equals(Ssc14Blocks.TECH_LAMP.get().defaultBlockState()
                                .getShape(helper.getLevel(), helper.absolutePos(relative))),
                "tube and technical light geometries collapsed");
        helper.succeed();
    }

    private static Expected expected(DeferredBlock<Block> holder, String path,
            Class<? extends Block> implementation, boolean hasBlockEntity,
            float destroyTime, int lightLevel) {
        return new Expected(holder, path, implementation, hasBlockEntity, destroyTime, lightLevel);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record Expected(DeferredBlock<Block> holder, String path,
            Class<? extends Block> implementation, boolean hasBlockEntity,
            float destroyTime, int lightLevel) { }
}
