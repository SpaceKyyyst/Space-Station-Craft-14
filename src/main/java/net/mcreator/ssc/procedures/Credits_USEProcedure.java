package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.Ssc14Mod;

public class Credits_USEProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (Ssc14ModBlocks.ORDER_CONSOLE.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() || Ssc14ModBlocks.SALE_CONSOLE.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			Ssc14Mod.queueServerWork(1, () -> {
				if (Ssc14ModItems.CREDITS.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
					Ssc14ModVariables.WorldVariables.get(world).Station_Budget = Ssc14ModVariables.WorldVariables.get(world).Station_Budget + (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount();
					Ssc14ModVariables.WorldVariables.get(world).markSyncDirty();
					itemstack.setCount(0);
				} else if (Ssc14ModItems.CREDITS_50.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
					Ssc14ModVariables.WorldVariables.get(world).Station_Budget = Ssc14ModVariables.WorldVariables.get(world).Station_Budget + (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() * 50;
					Ssc14ModVariables.WorldVariables.get(world).markSyncDirty();
					itemstack.setCount(0);
				} else if (Ssc14ModItems.DEBU_GCREDITS_DELETER.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
					Ssc14ModVariables.WorldVariables.get(world).Station_Budget = 0;
					Ssc14ModVariables.WorldVariables.get(world).markSyncDirty();
					if (entity instanceof ServerPlayer _player)
						_player.sendSystemMessage(Component.literal("[DEBUG]  \u0411\u042E\u0414\u0416\u0415\u0422  \u0421\u0422\u0410\u041D\u0426\u0418\u0418  \u041E\u041D\u0423\u041B\u0418\u0420\u041E\u0412\u0410\u041D"), false);
				}
			});
		} else {
			Credits_RMCProcedure.execute(entity);
		}
	}
}