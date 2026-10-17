package net.mcreator.ssc.block.entity;

import net.mcreator.ssc.EnergyNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Shared topology lifecycle for every airlock variant. */
public abstract class PoweredAirlockBlockEntity extends RandomizableContainerBlockEntity {
    protected PoweredAirlockBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }
    @Override public void onLoad() { super.onLoad(); EnergyNetworkManager.queueUpdate(level, worldPosition); }
    @Override public void setRemoved() { super.setRemoved(); EnergyNetworkManager.queueUpdate(level, worldPosition); }
    @Override public void onChunkUnloaded() { super.onChunkUnloaded(); EnergyNetworkManager.queueUpdate(level, worldPosition); }
}
