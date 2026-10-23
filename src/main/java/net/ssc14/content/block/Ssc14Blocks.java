package net.ssc14.content.block;

import java.util.function.Function;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
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

/** Canonical owner of SSC14 block registrations. */
public final class Ssc14Blocks {
    public static final DeferredRegister.Blocks REGISTRY =
            DeferredRegister.createBlocks(Ssc14Identity.MOD_ID);

    public static final DeferredBlock<Block> ASTEROID_STONE_BASE =
            register("asteroid_stone_base", AsteroidStoneBaseBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_IRON =
            register("asteroid_stone_iron", AsteroidStoneIronBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_QUARTZ =
            register("asteroid_stone_quartz", AsteroidStoneQuartzBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_COAL =
            register("asteroid_stone_coal", AsteroidStoneCoalBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_GOLD =
            register("asteroid_stone_gold", AsteroidStoneGoldBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_PLASMA =
            register("asteroid_stone_plasma", AsteroidStonePlasmaBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_SILVER =
            register("asteroid_stone_silver", AsteroidStoneSilverBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_URAN =
            register("asteroid_stone_uran", AsteroidStoneUranBlock::new);
    public static final DeferredBlock<Block> ASTEROID_STONE_DIAMOND =
            register("asteroid_stone_diamond", AsteroidStoneDiamondBlock::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_1 =
            register("cave_floor_1", CaveFloor1Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_2 =
            register("cave_floor_2", CaveFloor2Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_3 =
            register("cave_floor_3", CaveFloor3Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_4 =
            register("cave_floor_4", CaveFloor4Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_5 =
            register("cave_floor_5", CaveFloor5Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_6 =
            register("cave_floor_6", CaveFloor6Block::new);
    public static final DeferredBlock<Block> CAVE_FLOOR_7 =
            register("cave_floor_7", CaveFloor7Block::new);

    private Ssc14Blocks() {
    }

    private static <B extends Block> DeferredBlock<B> register(
            String name, Function<BlockBehaviour.Properties, ? extends B> factory) {
        return REGISTRY.registerBlock(name, factory);
    }
}
