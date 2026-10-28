package net.mcreator.ssc.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.chat.Component;

import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.world.inventory.IDcodeMenu;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

@EventBusSubscriber
public final class IDcodeButtonRegistration {
	private IDcodeButtonRegistration() { }

	public static void handleData(final MenuButtonPayload message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonId(), message.x(), message.y(), message.z())).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!(entity.containerMenu instanceof IDcodeMenu menu) || !MenuPacketGuard.matches(entity, menu, x, y, z, menu.x, menu.y, menu.z))
            return;
		if (buttonID == 0) {

			CapitanJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			PNTJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 2) {

			HoPJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 3) {

			HoSJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 4) {

			CMOJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 5) {

			QmJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 6) {

			LoaderJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 7) {

			UtilizatorJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 8) {

			PassangerJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 9) {

			RDJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 10) {

			VSJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 11) {

			ScientistJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 12) {

			ScienceAssistentJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 13) {

			CEJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 14) {

			AtmosJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 15) {

			VEJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 16) {

			TechicalAssistentJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 17) {

			EngeneerJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 18) {

			ParamedJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 19) {

			ChemistJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 20) {

			MedicJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 21) {

			VVJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 22) {

			InternJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 23) {

			KadetJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 24) {

			ISBJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 25) {

			OficerJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 26) {

			DetectiveJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 27) {

			PsichologJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 28) {

			BrigMedJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 29) {

			MimeJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 30) {

			WardJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 31) {

			BlueShJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 32) {

			AdutantJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 33) {

			ClownJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 34) {

			MusicantJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 35) {

			ZootechnikJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 36) {

			BibliotecarJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 37) {

			ServicerJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 38) {

			CleanerJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 39) {

			ChiefPovarJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 40) {

			BotanicJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 41) {

			BarmenJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 42) {

			BoxerJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 43) {

			CharchManJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 44) {

			ReporterJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 45) {

			AVDJOBProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 46) {

			IDCodeALLONProcedure.execute(entity);
		}
		if (buttonID == 47) {

			IDCodeALLOFFProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		var channel = MenuButtonPayload.Channel.ID_CODE;
		Ssc14NetworkPayloads.addServerbound(channel.type(), MenuButtonPayload.codec(channel), IDcodeButtonRegistration::handleData);
	}
}
