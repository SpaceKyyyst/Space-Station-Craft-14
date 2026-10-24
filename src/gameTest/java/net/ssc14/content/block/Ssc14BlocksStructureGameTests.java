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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.structure.PlasteelWallCarcaseBlock;
import net.ssc14.content.block.structure.RodFloorBlock;
import net.ssc14.content.block.structure.RodUpFloorBlock;
import net.ssc14.content.block.structure.TransparentStructureBlock;
import net.ssc14.content.block.structure.UperSheathingBlock;
import net.ssc14.content.block.structure.WallCarcaseBlock;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksStructureGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_structure_contract");
    private static final List<ExpectedBlock> EXPECTED = List.of(
            expected(Ssc14Blocks.ROD_FLOOR, "rod_floor", RodFloorBlock.class,
                    -1.0f, 10.0f, SoundType.COPPER_GRATE, 1, true),
            expected(Ssc14Blocks.ROD_UP_FLOOR, "rod_up_floor", RodUpFloorBlock.class,
                    -1.0f, 10.0f, SoundType.COPPER_GRATE, 1, true),
            expected(Ssc14Blocks.UPER_SHEATHING, "uper_sheathing", UperSheathingBlock.class,
                    -1.0f, 20.0f, SoundType.IRON, 1, false),
            expected(Ssc14Blocks.WALL_CARCASE, "wall_carcase", WallCarcaseBlock.class,
                    10.0f, 5.0f, SoundType.COPPER_GRATE, 30, true),
            expected(Ssc14Blocks.PLASTEEL_WALL_CARCASE, "plasteel_wall_carcase",
                    PlasteelWallCarcaseBlock.class, 20.0f, 7.5f, SoundType.COPPER_GRATE, 30, true));

    private Ssc14BlocksStructureGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14BlocksStructureGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_structure_compatibility_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relative = new BlockPos(1, 2, 1);
        BlockPos absolute = helper.absolutePos(relative);
        for (ExpectedBlock expected : EXPECTED) {
            Block block = expected.holder().get();
            BlockState state = block.defaultBlockState();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(expected.type().isInstance(block), expected.path() + " implementation changed");
            helper.assertTrue(Float.compare(expected.destroyTime(), block.defaultDestroyTime()) == 0,
                    expected.path() + " destroy time changed");
            helper.assertTrue(Float.compare(expected.resistance(), block.getExplosionResistance()) == 0,
                    expected.path() + " explosion resistance changed");
            helper.assertTrue(state.getSoundType() == expected.sound(), expected.path() + " sound changed");
            helper.assertTrue(block.asItem().getDefaultInstance().getMaxStackSize() == expected.stackSize(),
                    expected.path() + " stack size changed");
            if (expected.transparent()) {
                TransparentStructureBlock frame = (TransparentStructureBlock) block;
                helper.assertTrue(state.propagatesSkylightDown(), expected.path() + " skylight changed");
                helper.assertTrue(frame.getLightDampening(state) == 0,
                        expected.path() + " light dampening changed");
                helper.assertTrue(state.getVisualShape(helper.getLevel(), absolute, CollisionContext.empty()).isEmpty(),
                        expected.path() + " visual shape changed");
                helper.assertTrue(!state.isRedstoneConductor(helper.getLevel(), absolute),
                        expected.path() + " became redstone conductor");
            }
        }

        BlockState rod = Ssc14Blocks.ROD_FLOOR.get().defaultBlockState();
        helper.assertTrue("blockstate".equals(RodFloorBlock.BLOCKSTATE.getName()),
                "rod floor property name changed");
        helper.assertTrue(RodFloorBlock.BLOCKSTATE.getPossibleValues().size() == 8
                        && rod.getValue(RodFloorBlock.BLOCKSTATE) == 0,
                "rod floor property range/default changed");
        helper.assertTrue(Ssc14Blocks.ROD_UP_FLOOR.get().defaultBlockState()
                        .getProperties().stream().noneMatch(property -> "blockstate".equals(property.getName())),
                "rod up floor gained legacy-incompatible blockstate property");
        helper.assertTrue(rod.getMapColor(helper.getLevel(), absolute) == MapColor.COLOR_BLACK,
                "rod floor map color changed");
        helper.assertTrue(Ssc14Blocks.ROD_UP_FLOOR.get().defaultBlockState()
                        .getMapColor(helper.getLevel(), absolute) == MapColor.COLOR_BLACK,
                "rod up floor map color changed");
        helper.succeed();
    }

    private static ExpectedBlock expected(DeferredBlock<Block> holder, String path,
            Class<? extends Block> type, float destroyTime, float resistance, SoundType sound,
            int stackSize, boolean transparent) {
        return new ExpectedBlock(holder, path, type, destroyTime, resistance, sound, stackSize, transparent);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedBlock(DeferredBlock<Block> holder, String path, Class<? extends Block> type,
            float destroyTime, float resistance, SoundType sound, int stackSize, boolean transparent) { }
}
