package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.ssc.init.Ssc14ModItems;

public class OuterclothingSalvageCAPIBaubleIsUnequippedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity) {
			_entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ResourceLocation.parse("ssc_14:salvage"));
		}
		if (hasEntityInInventory(entity, new ItemStack(Ssc14ModItems.HARDSUIT_SALVAGE_HELMET.get()))) {
			if (entity instanceof net.minecraft.server.level.ServerPlayer _serverPlayer) {
				net.minecraft.world.item.Item _targetHelmet = net.mcreator.ssc.init.Ssc14ModItems.HARDSUIT_SALVAGE_HELMET.get();
				// 1. Очистка основного инвентаря игрока (включая броню и левую руку)
				for (int _i = 0; _i < _serverPlayer.getInventory().getContainerSize(); _i++) {
					net.minecraft.world.item.ItemStack _stack = _serverPlayer.getInventory().getItem(_i);
					if (!_stack.isEmpty() && _stack.is(_targetHelmet)) {
						_serverPlayer.getInventory().setItem(_i, net.minecraft.world.item.ItemStack.EMPTY);
					}
				}
				// 2. Очистка абсолютно всех слотов Curios (на случай десинхронизации слотов)
				top.theillusivec4.curios.api.CuriosApi.getCuriosInventory(_serverPlayer).ifPresent(_curiosInv -> {
					_curiosInv.getCurios().forEach((_identifier, _stacksHandler) -> {
						var _resultStacks = _stacksHandler.getStacks();
						for (int _j = 0; _j < _resultStacks.getSlots(); _j++) {
							net.minecraft.world.item.ItemStack _curioStack = _resultStacks.getStackInSlot(_j);
							if (!_curioStack.isEmpty() && _curioStack.is(_targetHelmet)) {
								_resultStacks.setStackInSlot(_j, net.minecraft.world.item.ItemStack.EMPTY);
							}
						}
					});
				});
				// 3. Синхронизируем изменения с клиентом, чтобы предметы моментально исчезли из интерфейса
				_serverPlayer.containerMenu.broadcastChanges();
			}
		}
	}

	private static boolean hasEntityInInventory(Entity entity, ItemStack itemstack) {
		if (entity instanceof Player player)
			return player.getInventory().contains(stack -> !stack.isEmpty() && ItemStack.isSameItem(stack, itemstack));
		return false;
	}
}