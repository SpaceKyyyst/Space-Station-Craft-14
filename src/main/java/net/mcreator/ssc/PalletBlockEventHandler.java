
package net.mcreator.ssc;

import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.ssc.init.Ssc14ModBlocks;

@EventBusSubscriber
public class PalletBlockEventHandler {

	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		LevelAccessor world = event.getLevel();
		if (world.isClientSide()) return;
		
		BlockState state = event.getPlacedBlock();
		if (state.is(Ssc14ModBlocks.ORDER_PALLET.get())) {
			BlockPos pos = event.getPos();
			PalletSavedData.get(world).addPallet(pos);
		}
	}
	@SubscribeEvent
	public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
		LevelAccessor world = event.getLevel();
		if (world.isClientSide()) return;

		BlockPos pos = event.getPos();
		BlockState state = world.getBlockState(pos);
		
		if (!state.is(Ssc14ModBlocks.ORDER_PALLET.get())) {
			PalletSavedData.get(world).removePallet(pos);
		}
	}
}
