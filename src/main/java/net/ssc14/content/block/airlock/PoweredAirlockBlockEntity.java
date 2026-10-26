package net.ssc14.content.block.airlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.energy.CableTopologyService;

/** Shared energy-topology lifecycle for airlock block entities. */
public abstract class PoweredAirlockBlockEntity extends RandomizableContainerBlockEntity {
    protected PoweredAirlockBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override public void onLoad() {
        super.onLoad();
        CableTopologyService.queueUpdate(level, worldPosition);
    }

    @Override public void setRemoved() {
        super.setRemoved();
        CableTopologyService.queueUpdate(level, worldPosition);
    }

    @Override public void onChunkUnloaded() {
        super.onChunkUnloaded();
        CableTopologyService.queueUpdate(level, worldPosition);
    }
}
