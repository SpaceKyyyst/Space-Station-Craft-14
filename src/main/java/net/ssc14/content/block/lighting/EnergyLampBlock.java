package net.ssc14.content.block.lighting;

import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** Serviceable tube light backed by the energy-network block entity. */
public class EnergyLampBlock extends ServiceableLightBlock implements EntityBlock {
    private final BiFunction<BlockPos, BlockState, ? extends BlockEntity> blockEntityFactory;

    public EnergyLampBlock(BlockBehaviour.Properties properties,
            BiFunction<BlockPos, BlockState, ? extends BlockEntity> blockEntityFactory) {
        super(properties, Geometry.TUBE);
        this.blockEntityFactory = blockEntityFactory;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityFactory.apply(pos, state);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider provider ? provider : null;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int eventId,
            int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventId, eventParam);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos,
            boolean moved) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos,
            Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof Container container
                ? AbstractContainerMenu.getRedstoneSignalFromContainer(container)
                : 0;
    }
}
