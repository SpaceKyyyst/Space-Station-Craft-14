package net.ssc14.content.block.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared transparent rendering contract for structural frame blocks. */
public class TransparentStructureBlock extends Block {
    protected TransparentStructureBlock(BlockBehaviour.Properties properties, SoundType sound,
            float destroyTime, float resistance) {
        super(properties.sound(sound).strength(destroyTime, resistance).noOcclusion()
                .isRedstoneConductor((state, getter, pos) -> false));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public int getLightDampening(BlockState state) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter getter, BlockPos pos,
            CollisionContext context) {
        return Shapes.empty();
    }
}
