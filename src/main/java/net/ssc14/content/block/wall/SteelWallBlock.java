package net.ssc14.content.block.wall;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SteelWallBlock extends AbstractStationWallBlock {
    public SteelWallBlock(BlockBehaviour.Properties properties) {
        super(properties, 30.0f, 12.0f);
    }

    @Override
    public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
        super.wasExploded(level, pos, explosion);
        WallDamageService.scheduleSteelExplosion(level, pos.getX(), pos.getY(), pos.getZ());
    }
}
