package net.mcreator.ssc.network;

import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.procedures.CargoBuyProcedure;
import net.mcreator.ssc.procedures.MicrwGUIbut10Procedure;
import net.mcreator.ssc.procedures.MicrwGUIbut15Procedure;
import net.mcreator.ssc.procedures.MicrwGUIbut1Procedure;
import net.mcreator.ssc.procedures.MicrwGUIbut20Procedure;
import net.mcreator.ssc.procedures.MicrwGUIbut25Procedure;
import net.mcreator.ssc.procedures.MicrwGUIbut30Procedure;
import net.mcreator.ssc.procedures.MicrwGUIbut5Procedure;
import net.mcreator.ssc.procedures.MicrwGUIbutCProcedure;
import net.mcreator.ssc.procedures.MicrwGUIbutEProcedure;
import net.mcreator.ssc.procedures.OrderGUISwitchTabProcedure;
import net.mcreator.ssc.procedures.Sale_GUI_buttonSALEProcedure;
import net.mcreator.ssc.procedures.VendAutGUIButton1Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton2Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton3Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton4Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton5Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton6Procedure;
import net.mcreator.ssc.world.inventory.MicrowaweGUIMenu;
import net.mcreator.ssc.world.inventory.OrderGUIMenu;
import net.mcreator.ssc.world.inventory.SaleGUIMenu;
import net.mcreator.ssc.world.inventory.VendingAut1GUIMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;

/** Transitional dispatcher for generated menus and procedures. */
@EventBusSubscriber
public final class LegacyMenuButtonRegistration {
    private LegacyMenuButtonRegistration() { }

    public static void handle(MenuButtonPayload message, IPayloadContext context) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;
        context.enqueueWork(() -> dispatch(context.player(), message)).exceptionally(error -> {
            Ssc14Mod.LOGGER.error("Failed to handle menu button payload", error);
            context.connection().disconnect(Component.literal("Invalid menu button payload"));
            return null;
        });
    }

    public static void dispatch(Player player, MenuButtonPayload message) {
        switch (message.channel()) {
            case VENDING -> handleVending(player, message);
            case SALE -> handleSale(player, message);
            case ORDER -> handleOrder(player, message);
            case MICROWAVE -> handleMicrowave(player, message);
        }
    }

    private static boolean matches(Player player, AbstractContainerMenu expectedMenu, MenuButtonPayload message,
            int menuX, int menuY, int menuZ) {
        return player.containerMenu == expectedMenu
                && MenuPacketGuard.matches(player, expectedMenu,
                        message.x(), message.y(), message.z(), menuX, menuY, menuZ);
    }

    private static void handleVending(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof VendingAut1GUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> VendAutGUIButton1Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 1 -> VendAutGUIButton2Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 2 -> VendAutGUIButton3Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 3 -> VendAutGUIButton4Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 4 -> VendAutGUIButton5Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 5 -> VendAutGUIButton6Procedure.execute(player.level(), message.x(), message.y(), message.z());
            default -> { }
        }
    }

    private static void handleSale(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof SaleGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        if (!player.level().isClientSide() && message.buttonId() == 0) {
            Sale_GUI_buttonSALEProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
        }
    }

    private static void handleOrder(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof OrderGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)
                || player.level().isClientSide()) return;
        int buttonId = message.buttonId();
        if (buttonId >= 0 && buttonId <= 14) {
            OrderGUISwitchTabProcedure.execute(player.level(), message.x(), message.y(), message.z(), buttonId);
        } else if (buttonId >= 100 && buttonId <= 109) {
            CargoBuyProcedure.execute(player.level(), message.x(), message.y(), message.z(), player, buttonId - 100);
        }
    }

    private static void handleMicrowave(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof MicrowaweGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> MicrwGUIbut1Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 1 -> MicrwGUIbut5Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 2 -> MicrwGUIbut10Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 3 -> MicrwGUIbut15Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 4 -> MicrwGUIbut20Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 5 -> MicrwGUIbut25Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 6 -> MicrwGUIbutCProcedure.execute(player.level(), message.x(), message.y(), message.z());
            case 7 -> MicrwGUIbut30Procedure.execute(player.level(), message.x(), message.y(), message.z());
            case 8 -> MicrwGUIbutEProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            default -> { }
        }
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        for (MenuButtonPayload.Channel channel : MenuButtonPayload.Channel.values()) {
            Ssc14Mod.addServerboundNetworkMessage(channel.type(), MenuButtonPayload.codec(channel),
                    LegacyMenuButtonRegistration::handle);
        }
    }
}
