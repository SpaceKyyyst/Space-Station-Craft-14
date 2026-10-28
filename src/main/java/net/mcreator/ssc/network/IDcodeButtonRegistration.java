package net.mcreator.ssc.network;

import net.mcreator.ssc.procedures.IDCodeALLOFFProcedure;
import net.mcreator.ssc.procedures.IDCodeALLONProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.content.access.IdJobAssignmentService;
import net.ssc14.content.menu.access.IDcodeMenu;
import net.ssc14.integration.neoforge.menu.network.MenuAccessGuard;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

@EventBusSubscriber
public final class IDcodeButtonRegistration {
    private IDcodeButtonRegistration() { }

    public static void handleData(final MenuButtonPayload message, final IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonId(),
                    message.x(), message.y(), message.z())).exceptionally(error -> {
                context.connection().disconnect(Component.literal(error.getMessage()));
                return null;
            });
        }
    }

    public static void handleButtonAction(Player player, int buttonId, int x, int y, int z) {
        if (!(player.containerMenu instanceof IDcodeMenu menu)
                || !MenuAccessGuard.matches(player, menu, x, y, z, menu.x, menu.y, menu.z)) {
            return;
        }

        if (buttonId >= 0 && buttonId < 46) {
            IdJobAssignmentService.assign(player, new BlockPos(x, y, z), buttonId);
        } else if (buttonId == 46) {
            IDCodeALLONProcedure.execute(player);
        } else if (buttonId == 47) {
            IDCodeALLOFFProcedure.execute(player);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        var channel = MenuButtonPayload.Channel.ID_CODE;
        Ssc14NetworkPayloads.addServerbound(channel.type(), MenuButtonPayload.codec(channel),
                IDcodeButtonRegistration::handleData);
    }
}
