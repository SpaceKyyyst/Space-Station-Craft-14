package net.ssc14.content.block.commerce;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Cargo sale pallet with a one-pixel-high collision surface. */
public class SalePalletBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 1, 16);

    public SalePalletBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.COPPER_GRATE).strength(30.0F, 10.0F).noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
    }

    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return SHAPE; }
    @Override public boolean propagatesSkylightDown(BlockState state) { return true; }
    @Override public int getLightDampening(BlockState state) { return 0; }
    @Override public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return Shapes.empty(); }
    @Override public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof MenuProvider provider ? provider : null;
    }
    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SalePalletBlockEntity(pos, state);
    }
    @Override public boolean triggerEvent(BlockState state, Level level, BlockPos pos,
            int eventId, int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity entity = level.getBlockEntity(pos);
        return entity != null && entity.triggerEvent(eventId, eventParam);
    }
}
