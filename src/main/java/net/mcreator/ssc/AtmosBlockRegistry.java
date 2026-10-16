package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/** Resolves permeability without loading chunks; legacy tag identifiers remain supported. */
public final class AtmosBlockRegistry {
    private static final int FIRST_OPEN_AIRLOCK_FRAME = 11;
    private static final int LAST_OPEN_AIRLOCK_FRAME = 15;
    private static final TagKey<Block> NON_HERMETIC = TagKey.create(Registries.BLOCK,
            Identifier.fromNamespaceAndPath("ssc_14", "non-hermetic"));
    private static final TagKey<Block> HERMETIC = TagKey.create(Registries.BLOCK,
            Identifier.fromNamespaceAndPath("ssc14", "hermetic"));

    private AtmosBlockRegistry() { }

    public static float getPermeability(ServerLevel level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos) || !level.hasChunkAt(pos)) return 0;
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof IAtmosBlock block) {
            float value = block.getPermeability(state);
            return Float.isFinite(value) ? Math.clamp(value, 0f, 1f) : 0f;
        }
        if (BuiltInRegistries.BLOCK.getKey(state.getBlock()).getNamespace().equals("ssc_14")
                && state.getBlock().getStateDefinition().getProperty("arl_variat") instanceof IntegerProperty frame) {
            int value = state.getValue(frame);
            return value >= FIRST_OPEN_AIRLOCK_FRAME && value <= LAST_OPEN_AIRLOCK_FRAME ? 1f : 0f;
        }
        if (state.is(HERMETIC)) return 0;
        if (state.is(IAtmosBlock.NON_HERMETIC_TAG) || state.is(NON_HERMETIC)) return 1;
        return state.isAir() || state.getCollisionShape(level, pos).isEmpty() ? 1f : 0f;
    }
}
