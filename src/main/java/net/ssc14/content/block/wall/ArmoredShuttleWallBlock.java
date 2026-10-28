package net.ssc14.content.block.wall;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ArmoredShuttleWallBlock extends AbstractStationWallBlock {
    public ArmoredShuttleWallBlock(BlockBehaviour.Properties properties) {
        super(properties, 600.0f, 18.0f);
    }

    @Override
    public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
        super.wasExploded(level, pos, explosion);
        WallDamageService.schedulePlasteelExplosion(level, pos.getX(), pos.getY(), pos.getZ());
    }
}
