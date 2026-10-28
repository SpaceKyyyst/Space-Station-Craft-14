package net.mcreator.ssc.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.storage.ClosetBlockEntity;

/** @deprecated Use {@link ClosetBlockEntity}. */
@Deprecated(forRemoval = false)
public class ClosetSecureBlockEntity extends ClosetBlockEntity {
    public ClosetSecureBlockEntity(BlockPos position, BlockState state) {
        super("closet_secure", "Closet Secure", position, state);
    }

    @Override
    public Component getDefaultName() {
        return super.getDefaultName();
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return super.createMenu(id, inventory);
    }
}
