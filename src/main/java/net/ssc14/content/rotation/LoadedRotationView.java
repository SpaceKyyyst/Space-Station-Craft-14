package net.ssc14.content.rotation;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;

/** Every read, including shape-neighbor queries, is forbidden from loading chunks. */
final class LoadedRotationView implements BlockGetter {
    private final ServerLevel level;

    LoadedRotationView(ServerLevel level) {
        this.level = level;
    }

    private LevelChunk chunk(BlockPos pos) {
        var chunk = level.getChunkSource().getChunkNow(pos.getX() >> 4, pos.getZ() >> 4);
        if (chunk == null) throw UnavailableChunk.INSTANCE;
        return chunk;
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return isOutsideBuildHeight(pos) ? Blocks.VOID_AIR.defaultBlockState() : chunk(pos).getBlockState(pos);
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return getBlockState(pos).getFluidState();
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {
        // Avoid lazy block-entity construction/NBT hydration during a permission check.
        return isOutsideBuildHeight(pos) ? null : chunk(pos).getBlockEntities().get(pos);
    }

    @Override
    public int getHeight() {
        return level.getHeight();
    }

    @Override
    public int getMinY() {
        return level.getMinY();
    }

    /** Expected refusal, distinct from unexpected failures in modded block shapes. */
    static final class UnavailableChunk extends RuntimeException {
        private static final UnavailableChunk INSTANCE = new UnavailableChunk();

        private UnavailableChunk() {
            super(null, null, false, false);
        }
    }
}
