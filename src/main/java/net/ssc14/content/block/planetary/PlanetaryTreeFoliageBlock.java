package net.ssc14.content.block.planetary;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class PlanetaryTreeFoliageBlock extends TintedParticleLeavesBlock {
    public PlanetaryTreeFoliageBlock(BlockBehaviour.Properties properties) {
        super(0.0F, properties.sound(SoundType.CHERRY_LEAVES).strength(1.0F).noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false));
    }

    @Override
    public int getLightDampening(BlockState state) {
        return 2;
    }
}
