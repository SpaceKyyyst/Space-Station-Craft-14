package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.network.Ssc14ModVariables;
import net.mcreator.ssc.init.Ssc14ModItems;

import javax.annotation.Nullable;

@EventBusSubscriber
public class TEMPORARYGRAVITYPLUGProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double i = 0;
		boolean enter = false;
		if ((entity.level().dimension()) == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("ssc_14:spaced"))) {
			if (Ssc14ModVariables.station_gravity == true) {
				for (int index0 = 0; index0 < 7; index0++) {
					if (!(world.getBlockState(BlockPos.containing(x, y - i, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))) {
						entity.setNoGravity(false);
						enter = true;
					} else if (enter == false) {
						entity.setNoGravity(true);
					}
					i = i + 1;
				}
			} else {
				if (hasEntityInInventory(entity, new ItemStack(Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get()))) {
					for (int index1 = 0; index1 < 7; index1++) {
						if (!(world.getBlockState(BlockPos.containing(x, y - i, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:airs")))) {
							entity.setNoGravity(false);
							enter = true;
						} else if (enter == false) {
							entity.setNoGravity(true);
						}
						i = i + 1;
					}
				} else {
					entity.setNoGravity(true);
				}
			}
		} else {
			entity.setNoGravity(false);
		}
	}

	private static boolean hasEntityInInventory(Entity entity, ItemStack itemstack) {
		if (entity instanceof Player player)
			return player.getInventory().contains(stack -> !stack.isEmpty() && ItemStack.isSameItem(stack, itemstack));
		return false;
	}
}