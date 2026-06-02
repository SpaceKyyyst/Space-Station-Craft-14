package net.mcreator.ssc.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;

import net.mcreator.ssc.world.inventory.PDApassangerGUIMenu;
import net.mcreator.ssc.procedures.PDA_generateProcedure;
import net.mcreator.ssc.procedures.PDAGUIOpeningConditionsProcedure;

import javax.annotation.Nullable;

import io.netty.buffer.Unpooled;

public class PDApassangerItem extends Item {
	public PDApassangerItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant());
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		if (entity instanceof ServerPlayer serverPlayer) {
			if (PDAGUIOpeningConditionsProcedure.execute(serverPlayer, entity.getItemInHand(hand))) {
				serverPlayer.openMenu(new MenuProvider() {
					@Override
					public Component getDisplayName() {
						return Component.literal("PDA passanger");
					}

					@Override
					public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
						FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
						packetBuffer.writeBlockPos(entity.blockPosition());
						packetBuffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
						return new PDApassangerGUIMenu(id, inventory, packetBuffer);
					}
				}, buf -> {
					buf.writeBlockPos(entity.blockPosition());
					buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
				});
			}
		}
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		PDA_generateProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
	}
}