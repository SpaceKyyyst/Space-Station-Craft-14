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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.geology.AsteroidStoneBaseBlock;
import net.ssc14.content.block.geology.AsteroidStoneCoalBlock;
import net.ssc14.content.block.geology.AsteroidStoneDiamondBlock;
import net.ssc14.content.block.geology.AsteroidStoneGoldBlock;
import net.ssc14.content.block.geology.AsteroidStoneIronBlock;
import net.ssc14.content.block.geology.AsteroidStonePlasmaBlock;
import net.ssc14.content.block.geology.AsteroidStoneQuartzBlock;
import net.ssc14.content.block.geology.AsteroidStoneSilverBlock;
import net.ssc14.content.block.geology.AsteroidStoneUranBlock;
import net.ssc14.content.block.geology.CaveFloor1Block;
import net.ssc14.content.block.geology.CaveFloor2Block;
import net.ssc14.content.block.geology.CaveFloor3Block;
import net.ssc14.content.block.geology.CaveFloor4Block;
import net.ssc14.content.block.geology.CaveFloor5Block;
import net.ssc14.content.block.geology.CaveFloor6Block;
import net.ssc14.content.block.geology.CaveFloor7Block;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksGeologyGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_geology_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            expected(Ssc14Blocks.ASTEROID_STONE_BASE, "asteroid_stone_base", AsteroidStoneBaseBlock.class, 20.0f, 10.0f),
            expected(Ssc14Blocks.ASTEROID_STONE_IRON, "asteroid_stone_iron", AsteroidStoneIronBlock.class, 25.0f, 15.0f),
            expected(Ssc14Blocks.ASTEROID_STONE_QUARTZ, "asteroid_stone_quartz", AsteroidStoneQuartzBlock.class, 25.0f, 15.0f),
            expected(Ssc14Blocks.ASTEROID_STONE_COAL, "asteroid_stone_coal", AsteroidStoneCoalBlock.class, 25.0f, 15.0f),
            expected(Ssc14Blocks.ASTEROID_STONE_GOLD, "asteroid_stone_gold", AsteroidStoneGoldBlock.class, 25.0f, 15.0f),
            expected(Ssc14Blocks.ASTEROID_STONE_PLASMA, "asteroid_stone_plasma", AsteroidStonePlasmaBlock.class, 25.0f, 15.0f),
            expected(Ssc14Blocks.ASTEROID_STONE_SILVER, "asteroid_stone_silver", AsteroidStoneSilverBlock.class, 25.0f, 15.0f),
            expected(Ssc14Blocks.ASTEROID_STONE_URAN, "asteroid_stone_uran", AsteroidStoneUranBlock.class, 25.0f, 15.0f),
            expected(Ssc14Blocks.ASTEROID_STONE_DIAMOND, "asteroid_stone_diamond", AsteroidStoneDiamondBlock.class, 25.0f, 15.0f),
            expected(Ssc14Blocks.CAVE_FLOOR_1, "cave_floor_1", CaveFloor1Block.class, -1.0f, 3_600_000.0f),
            expected(Ssc14Blocks.CAVE_FLOOR_2, "cave_floor_2", CaveFloor2Block.class, -1.0f, 3_600_000.0f),
            expected(Ssc14Blocks.CAVE_FLOOR_3, "cave_floor_3", CaveFloor3Block.class, -1.0f, 3_600_000.0f),
            expected(Ssc14Blocks.CAVE_FLOOR_4, "cave_floor_4", CaveFloor4Block.class, -1.0f, 3_600_000.0f),
            expected(Ssc14Blocks.CAVE_FLOOR_5, "cave_floor_5", CaveFloor5Block.class, -1.0f, 3_600_000.0f),
            expected(Ssc14Blocks.CAVE_FLOOR_6, "cave_floor_6", CaveFloor6Block.class, -1.0f, 3_600_000.0f),
            expected(Ssc14Blocks.CAVE_FLOOR_7, "cave_floor_7", CaveFloor7Block.class, -1.0f, 3_600_000.0f));

    private Ssc14BlocksGeologyGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksGeologyGameTests::verifyGeologyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_geology_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyGeologyContract(GameTestHelper helper) {
        for (ExpectedBlock expected : EXPECTED) {
            Block block = expected.holder().get();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(expected.implementation().isInstance(block),
                    expected.path() + " factory class changed to " + block.getClass().getName());
            assertFloat(helper, expected.destroyTime(), block.defaultDestroyTime(),
                    expected.path() + " destroy time");
            assertFloat(helper, expected.explosionResistance(), block.getExplosionResistance(),
                    expected.path() + " explosion resistance");
        }

        BlockPos absolute = helper.absolutePos(new BlockPos(1, 2, 1));
        for (Block cave : List.of(
                Ssc14Blocks.CAVE_FLOOR_2.get(), Ssc14Blocks.CAVE_FLOOR_3.get(),
                Ssc14Blocks.CAVE_FLOOR_4.get(), Ssc14Blocks.CAVE_FLOOR_5.get(),
                Ssc14Blocks.CAVE_FLOOR_6.get(), Ssc14Blocks.CAVE_FLOOR_7.get())) {
            var clone = cave.getCloneItemStack(helper.getLevel(), absolute, cave.defaultBlockState(), false, null);
            helper.assertTrue(clone.is(Ssc14Blocks.CAVE_FLOOR_1.get().asItem()),
                    BuiltInRegistries.BLOCK.getKey(cave) + " clone target changed");
        }

        helper.setBlock(1, 2, 1, Ssc14Blocks.CAVE_FLOOR_1.get());
        var caveTag = BlockTags.create(Identifier.fromNamespaceAndPath("ssc14", "cave_floor"));
        helper.assertTrue(helper.getBlockState(new BlockPos(1, 2, 1)).is(caveTag),
                "cave floor randomizer produced a block outside ssc14:cave_floor");
        helper.succeed();
    }

    private static void assertFloat(GameTestHelper helper, float expected, float actual, String label) {
        helper.assertTrue(Float.compare(expected, actual) == 0,
                label + " changed: expected " + expected + ", got " + actual);
    }

    private static ExpectedBlock expected(
            DeferredBlock<Block> holder,
            String path,
            Class<? extends Block> implementation,
            float destroyTime,
            float explosionResistance) {
        return new ExpectedBlock(holder, path, implementation, destroyTime, explosionResistance);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(
            DeferredBlock<Block> holder,
            String path,
            Class<? extends Block> implementation,
            float destroyTime,
            float explosionResistance) {
    }
}
