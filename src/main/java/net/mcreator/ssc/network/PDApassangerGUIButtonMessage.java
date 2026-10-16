package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.world.inventory.PDApassangerGUIMenu;

@EventBusSubscriber
public record PDApassangerGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<PDApassangerGUIButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, "pd_apassanger_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, PDApassangerGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, PDApassangerGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new PDApassangerGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<PDApassangerGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final PDApassangerGUIButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!(entity.containerMenu instanceof PDApassangerGUIMenu menu) || !MenuPacketGuard.matches(entity, menu, x, y, z, menu.x, menu.y, menu.z))
            return;
		if (buttonID == 0) {

			PenRemovePRProcedure.execute(world, entity);
		}
		if (buttonID == 1) {

			IDRemovePRProcedure.execute(world, entity);
		}
		if (buttonID == 2) {

			PDAflashlightPRProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 3) {

			PDAmainButtonPRProcedure.execute(entity);
		}
		if (buttonID == 4) {

			PDA2TABbuttonPRProcedure.execute(entity);
		}
		if (buttonID == 5) {

			PDA3TABbuttonPRProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Ssc14Mod.addServerboundNetworkMessage(PDApassangerGUIButtonMessage.TYPE, PDApassangerGUIButtonMessage.STREAM_CODEC, PDApassangerGUIButtonMessage::handleData);
	}
}