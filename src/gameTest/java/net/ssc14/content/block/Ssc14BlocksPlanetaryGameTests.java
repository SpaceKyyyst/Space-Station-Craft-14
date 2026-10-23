package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.TriState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.planetary.PlanetDirtBlock;
import net.ssc14.content.block.planetary.PlanetGrassPlantBlock;
import net.ssc14.content.block.planetary.PlanetGrassSoilBlock;
import net.ssc14.content.block.planetary.PlanetaryTreeFoliageBlock;
import net.ssc14.content.block.planetary.PlanetaryTreeLogBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksPlanetaryGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_planetary_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            expected(Ssc14Blocks.DIRT, "dirt", PlanetDirtBlock.class, 50.0F, 50.0F, SoundType.GRAVEL),
            expected(Ssc14Blocks.GRASS_LIGHT_BLOCK, "grass_light_block", PlanetGrassSoilBlock.class, 1.0F, 10.0F, SoundType.MOSS),
            expected(Ssc14Blocks.LOW_PLANET_GRASS, "low_planet_grass", PlanetGrassPlantBlock.class, 0.1F, 1.0F, SoundType.GRASS),
            expected(Ssc14Blocks.MED_PLANET_GRASS, "med_planet_grass", PlanetGrassPlantBlock.class, 0.1F, 1.0F, SoundType.GRASS),
            expected(Ssc14Blocks.PLANETARY_TREE_LOG, "planetary_tree_log", PlanetaryTreeLogBlock.class, 10.0F, 10.0F, SoundType.CHERRY_WOOD),
            expected(Ssc14Blocks.PLANETARY_TREE_FOLIAGE, "planetary_tree_foliage", PlanetaryTreeFoliageBlock.class, 1.0F, 1.0F, SoundType.CHERRY_LEAVES));

    private Ssc14BlocksPlanetaryGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksPlanetaryGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_planetary_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (ExpectedBlock expected : EXPECTED) {
            Block block = expected.holder().get();
            BlockState state = block.defaultBlockState();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(expected.implementation().isInstance(block),
                    expected.path() + " implementation changed");
            assertFloat(helper, expected.destroyTime(), block.defaultDestroyTime(),
                    expected.path() + " destroy time");
            assertFloat(helper, expected.explosionResistance(), block.getExplosionResistance(),
                    expected.path() + " explosion resistance");
            helper.assertTrue(state.getSoundType() == expected.sound(), expected.path() + " sound changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 1,
                    expected.path() + " item stack limit changed");
        }

        BlockState dirt = Ssc14Blocks.DIRT.get().defaultBlockState();
        BlockState soil = Ssc14Blocks.GRASS_LIGHT_BLOCK.get().defaultBlockState();
        helper.assertTrue(dirt.getValue(PlanetDirtBlock.FACING) == Direction.NORTH,
                "dirt default facing changed");
        helper.assertTrue(dirt.rotate(Rotation.CLOCKWISE_90).getValue(PlanetDirtBlock.FACING) == Direction.EAST,
                "dirt rotation changed");
        helper.assertTrue(soil.getValue(PlanetGrassSoilBlock.FACING) == Direction.NORTH,
                "grass soil default facing changed");
        helper.assertTrue(soil.getBlock().canSustainPlant(
                soil, helper.getLevel(), helper.absolutePos(new BlockPos(1, 1, 1)), Direction.UP,
                Ssc14Blocks.LOW_PLANET_GRASS.get().defaultBlockState()) == TriState.TRUE,
                "grass soil no longer sustains plants");

        BlockState log = Ssc14Blocks.PLANETARY_TREE_LOG.get().defaultBlockState();
        helper.assertTrue(log.getValue(PlanetaryTreeLogBlock.AXIS) == Direction.Axis.Y,
                "planetary log default axis changed");
        helper.assertTrue(log.setValue(PlanetaryTreeLogBlock.AXIS, Direction.Axis.X)
                        .rotate(Rotation.CLOCKWISE_90).getValue(PlanetaryTreeLogBlock.AXIS) == Direction.Axis.Z,
                "planetary log rotation changed");
        PlanetaryTreeFoliageBlock foliage =
                (PlanetaryTreeFoliageBlock) Ssc14Blocks.PLANETARY_TREE_FOLIAGE.get();
        helper.assertTrue(foliage.getLightDampening(foliage.defaultBlockState()) == 2,
                "planetary foliage light dampening changed");

        BlockPos soilRelative = new BlockPos(1, 1, 1);
        BlockPos plantRelative = soilRelative.above();
        BlockPos plantAbsolute = helper.absolutePos(plantRelative);
        helper.setBlock(soilRelative, soil);
        for (Block plant : List.of(Ssc14Blocks.LOW_PLANET_GRASS.get(), Ssc14Blocks.MED_PLANET_GRASS.get())) {
            BlockState plantState = plant.defaultBlockState();
            helper.setBlock(plantRelative, plantState);
            helper.assertTrue(plantState.canSurvive(helper.getLevel(), plantAbsolute),
                    BuiltInRegistries.BLOCK.getKey(plant) + " substrate rule changed");
            helper.assertTrue(!Shapes.joinIsNotEmpty(
                            plantState.getShape(helper.getLevel(), plantAbsolute),
                            Block.box(4, 0, 4, 12, 12, 12).move(plantState.getOffset(plantAbsolute)),
                            BooleanOp.NOT_SAME),
                    BuiltInRegistries.BLOCK.getKey(plant) + " shape changed");
            helper.assertTrue(plantState.getCollisionShape(
                            helper.getLevel(), plantAbsolute, CollisionContext.empty()).isEmpty(),
                    BuiltInRegistries.BLOCK.getKey(plant) + " gained collision");
            helper.assertTrue(plantState.getVisualShape(
                            helper.getLevel(), plantAbsolute, CollisionContext.empty()).isEmpty(),
                    BuiltInRegistries.BLOCK.getKey(plant) + " visual shape changed");
            helper.setBlock(soilRelative, net.minecraft.world.level.block.Blocks.AIR);
            helper.assertTrue(!plantState.canSurvive(helper.getLevel(), plantAbsolute),
                    BuiltInRegistries.BLOCK.getKey(plant) + " survives without grass soil");
            helper.setBlock(soilRelative, soil);
        }
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
            float explosionResistance,
            SoundType sound) {
        return new ExpectedBlock(holder, path, implementation, destroyTime, explosionResistance, sound);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(
            DeferredBlock<Block> holder,
            String path,
            Class<? extends Block> implementation,
            float destroyTime,
            float explosionResistance,
            SoundType sound) {
    }
}
