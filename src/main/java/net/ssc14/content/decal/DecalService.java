package net.ssc14.content.decal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.ssc14.integration.neoforge.decal.DecalData;
import net.ssc14.integration.neoforge.decal.DecalNetwork;
import net.ssc14.integration.neoforge.decal.Ssc14DecalAttachments;

/** Authoritative mutations for the canonical decal attachment store. */
public final class DecalService {
    private DecalService() { }

    public static void addDecal(LevelChunk chunk, DecalData decal) {
        Ssc14DecalAttachments.add(chunk, decal);
        DecalNetwork.syncChunkToTrackingPlayers(chunk);
    }

    public static void removeDecalAt(LevelChunk chunk, BlockPos pos, Direction face) {
        if (Ssc14DecalAttachments.remove(chunk, pos, face)) {
            DecalNetwork.syncChunkToTrackingPlayers(chunk);
        }
    }

    public static void removeDecalsInRadius(LevelChunk chunk, BlockPos center, double radius) {
        if (chunk.getLevel() instanceof ServerLevel serverLevel) {
            removeDecalsInRadius(serverLevel, center, radius);
        } else {
            removeDecalsInRadiusFromChunk(chunk, center, radius);
        }
    }

    public static void removeDecalsInRadius(ServerLevel level, BlockPos center, double radius) {
        int chunkRadius = (int) Math.ceil(radius);
        int minChunkX = (center.getX() - chunkRadius) >> 4;
        int maxChunkX = (center.getX() + chunkRadius) >> 4;
        int minChunkZ = (center.getZ() - chunkRadius) >> 4;
        int maxChunkZ = (center.getZ() + chunkRadius) >> 4;

        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                ChunkAccess access = level.getChunk(chunkX, chunkZ, ChunkStatus.FULL, false);
                if (access instanceof LevelChunk chunk) {
                    removeDecalsInRadiusFromChunk(chunk, center, radius);
                }
            }
        }
    }

    public static void removeDecalsInRadiusFromChunk(
            LevelChunk chunk, BlockPos center, double radius) {
        if (Ssc14DecalAttachments.removeInRadius(chunk, center, radius)) {
            DecalNetwork.syncChunkToTrackingPlayers(chunk);
        }
    }
}
