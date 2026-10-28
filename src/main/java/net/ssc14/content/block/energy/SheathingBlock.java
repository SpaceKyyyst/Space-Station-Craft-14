package net.ssc14.content.block.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Cable sheathing that hosts low-, medium-, and high-voltage network layers. */
public class SheathingBlock extends Block implements EntityBlock {
    public static final BooleanProperty LV = BooleanProperty.create("lv");
    public static final BooleanProperty MV = BooleanProperty.create("mv");
    public static final BooleanProperty HV = BooleanProperty.create("hv");

    public SheathingBlock(BlockBehaviour.Properties properties) {
        super(properties.mapColor(MapColor.COLOR_GRAY).sound(SoundType.IRON)
                .strength(-1.0F, 20.0F).noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any()
                .setValue(LV, false).setValue(MV, false).setValue(HV, false));
    }

    @Override public int getLightDampening(BlockState state) { return 15; }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LV, MV, HV);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state == null ? null : state.setValue(LV, false)
                .setValue(MV, false).setValue(HV, false);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider provider ? provider : null;
    }

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SheathingBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos,
            int eventId, int eventParam) {
        super.triggerEvent(state, level, pos, eventId, eventParam);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventId, eventParam);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level,
            BlockPos pos, boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
        CableTopologyService.invalidate(level, pos);
    }

    @Override public boolean hasAnalogOutputSignal(BlockState state) { return true; }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos,
            Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof SheathingBlockEntity sheathing
                ? AbstractContainerMenu.getRedstoneSignalFromContainer(sheathing) : 0;
    }

    static boolean cableLayersChanged(BlockState previous, BlockState current) {
        return previous.getValue(LV) != current.getValue(LV)
                || previous.getValue(MV) != current.getValue(MV)
                || previous.getValue(HV) != current.getValue(HV);
    }
}
