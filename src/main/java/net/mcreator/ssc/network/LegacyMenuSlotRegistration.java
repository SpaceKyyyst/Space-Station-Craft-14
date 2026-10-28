package net.mcreator.ssc.network;

import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.procedures.IDCode0CellprProcedure;
import net.mcreator.ssc.procedures.MacFrameGUIBlockUpdateProcedure;
import net.mcreator.ssc.world.inventory.IDcodeMenu;
import net.mcreator.ssc.world.inventory.MacFrameGUIMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.menu.network.MenuSlotPayload;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

/** Transitional dispatcher for generated slot callbacks. */
@EventBusSubscriber
public final class LegacyMenuSlotRegistration {
    private LegacyMenuSlotRegistration() { }

    public static void handle(MenuSlotPayload message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> dispatch(context.player(), message)).exceptionally(error -> {
            Ssc14Mod.LOGGER.error("Failed to handle menu slot payload", error);
            context.connection().disconnect(Component.literal("Invalid menu slot payload"));
            return null;
        });
    }

    public static void dispatch(Player player, MenuSlotPayload message) {
        switch (message.channel()) {
            case ID_CODE -> handleIdCode(player, message);
            case MACHINE_FRAME -> handleMachineFrame(player, message);
        }
    }

    private static void handleIdCode(Player player, MenuSlotPayload message) {
        if (!(player.containerMenu instanceof IDcodeMenu menu)
                || !MenuPacketGuard.matches(player, menu, message.x(), message.y(), message.z(),
                        menu.x, menu.y, menu.z)) return;
        if ((message.slotId() == 0 || message.slotId() == 1) && message.changeType() == 0) {
            IDCode0CellprProcedure.execute(player);
        }
    }

    private static void handleMachineFrame(Player player, MenuSlotPayload message) {
        if (!(player.containerMenu instanceof MacFrameGUIMenu menu)
                || !MenuPacketGuard.matches(player, menu, message.x(), message.y(), message.z(),
                        menu.x, menu.y, menu.z)) return;
        if (message.slotId() >= 0 && message.slotId() <= 8 && message.changeType() == 0) {
            MacFrameGUIBlockUpdateProcedure.execute(player.level(), message.x(), message.y(), message.z());
        }
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        for (MenuSlotPayload.Channel channel : MenuSlotPayload.Channel.values()) {
            Ssc14NetworkPayloads.addServerbound(channel.type(), MenuSlotPayload.codec(channel),
                    LegacyMenuSlotRegistration::handle);
        }
    }
}
