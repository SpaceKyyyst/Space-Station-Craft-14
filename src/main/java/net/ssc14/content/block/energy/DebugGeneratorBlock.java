package net.ssc14.content.block.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

/** Debug high-voltage source block. */
public class DebugGeneratorBlock extends Block implements EntityBlock {
    public DebugGeneratorBlock(BlockBehaviour.Properties properties) {
        super(properties.sound(SoundType.AMETHYST).strength(-1, 3_600_000)
                .lightLevel(state -> 1).noOcclusion().postProcess((state, level, pos) -> pos)
                .emissiveRendering((state, level, pos) -> true)
                .isRedstoneConductor((state, level, pos) -> false));
    }

    @Override public int getLightDampening(BlockState state) { return 15; }
    @Override public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) { return Shapes.empty(); }
    @Override public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof MenuProvider provider ? provider : null;
    }
    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DebugGeneratorBlockEntity(pos, state);
    }
    @Override public boolean triggerEvent(BlockState state, Level level, BlockPos pos,
            int eventId, int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity entity = level.getBlockEntity(pos);
        return entity != null && entity.triggerEvent(eventId, eventParam);
    }
    @Override protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level,
            BlockPos pos, boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }
    @Override public boolean hasAnalogOutputSignal(BlockState state) { return true; }
    @Override public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos,
            Direction direction) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof DebugGeneratorBlockEntity generator
                ? AbstractContainerMenu.getRedstoneSignalFromContainer(generator) : 0;
    }
}
