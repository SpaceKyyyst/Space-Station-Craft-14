package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.ceiling.CeilingSupportPolicy;
import net.ssc14.content.block.ceiling.CeilingTileBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksCeilingTileGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_ceiling_tile_contract");
    private static final List<Expected> EXPECTED = List.of(
            new Expected("UP_TITLE", "up_title"),
            new Expected("UP_TITLE_STEEL", "up_title_steel"),
            new Expected("UP_TITLE_DARK", "up_title_dark"));

    private Ssc14BlocksCeilingTileGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID,
                        Ssc14BlocksCeilingTileGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(
                id("block_ceiling_tile_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos pos = helper.absolutePos(new BlockPos(1, 1, 1));
        BlockPos support = pos.above();
        var fullCube = Block.box(0, 0, 0, 16, 16, 16);

        for (Expected expected : EXPECTED) {
            CeilingTileBlock block = get(expected.field());
            var state = block.defaultBlockState();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(Float.compare(100.0f, block.defaultDestroyTime()) == 0
                            && Float.compare(5.0f, block.getExplosionResistance()) == 0,
                    expected.path() + " strength changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == 30
                            && block.asItem().getDefaultInstance()
                                    .has(DataComponents.DAMAGE_RESISTANT),
                    expected.path() + " item properties changed");
            helper.assertTrue(block.getLightDampening(state) == 15
                            && state.getVisualShape(level, pos, CollisionContext.empty()).isEmpty()
                            && !state.isRedstoneConductor(level, pos),
                    expected.path() + " transparency contract changed");
            helper.assertTrue(!Shapes.joinIsNotEmpty(fullCube,
                            state.getShape(level, pos), BooleanOp.NOT_SAME)
                            && !Shapes.joinIsNotEmpty(fullCube,
                                    state.getCollisionShape(level, pos), BooleanOp.NOT_SAME),
                    expected.path() + " full-cube shape changed");
            var sound = state.getSoundType();
            helper.assertTrue(Float.compare(1.0f, sound.getVolume()) == 0
                            && Float.compare(1.0f, sound.getPitch()) == 0,
                    expected.path() + " sound volume or pitch changed");
            assertSound(helper, sound.getBreakSound(), id("title_off"),
                    expected.path() + " break sound changed");
            assertSound(helper, sound.getStepSound(), vanilla("block.copper.step"),
                    expected.path() + " step sound changed");
            assertSound(helper, sound.getPlaceSound(), id("title_on"),
                    expected.path() + " place sound changed");
            assertSound(helper, sound.getHitSound(), vanilla("block.copper.hit"),
                    expected.path() + " hit sound changed");
            assertSound(helper, sound.getFallSound(), vanilla("block.copper.fall"),
                    expected.path() + " fall sound changed");
        }

        var tile = get("UP_TITLE").defaultBlockState();
        level.setBlock(support, Ssc14Blocks.CAVE_FLOOR_1.get().defaultBlockState(),
                Block.UPDATE_ALL);
        helper.assertTrue(CeilingSupportPolicy.canSupport(level, pos)
                        && tile.canSurvive(level, pos),
                "ceiling tile rejected suitable support above it");
        level.setBlock(pos, tile, Block.UPDATE_ALL);
        level.setBlock(support, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        helper.assertTrue(level.getBlockState(pos).isAir(),
                "ceiling tile remained after losing support above it");
        helper.succeed();
    }

    private static void assertSound(GameTestHelper helper, SoundEvent actual,
            Identifier expected, String message) {
        helper.assertTrue(expected.equals(BuiltInRegistries.SOUND_EVENT.getKey(actual)), message);
    }

    private static CeilingTileBlock get(String field) {
        try {
            Object value = Ssc14Blocks.class.getField(field).get(null);
            return (CeilingTileBlock) ((DeferredBlock<?>) value).get();
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException(error);
        }
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private static Identifier vanilla(String path) {
        return Identifier.fromNamespaceAndPath("minecraft", path);
    }

    private record Expected(String field, String path) { }
}
