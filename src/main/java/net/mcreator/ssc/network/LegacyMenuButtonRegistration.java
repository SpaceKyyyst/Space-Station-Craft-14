package net.mcreator.ssc.network;

import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.procedures.CargoBuyProcedure;
import net.mcreator.ssc.procedures.CommConsBLUEcodeProcedure;
import net.mcreator.ssc.procedures.CommConsGREENcodeProcedure;
import net.mcreator.ssc.procedures.CommConsPURPLEcodeProcedure;
import net.mcreator.ssc.procedures.CommConsREDcodeProcedure;
import net.mcreator.ssc.procedures.CommConsYELLOWcodeProcedure;
import net.mcreator.ssc.procedures.CPcabel1buttonProcedure;
import net.mcreator.ssc.procedures.CPcabel2buttonProcedure;
import net.mcreator.ssc.procedures.CPcabel3buttonProcedure;
import net.mcreator.ssc.procedures.CPcabel4buttonProcedure;
import net.mcreator.ssc.procedures.CPcabel5buttonProcedure;
import net.mcreator.ssc.procedures.CPcabel6buttonProcedure;
import net.mcreator.ssc.procedures.CPcabel7buttonProcedure;
import net.mcreator.ssc.procedures.CPcabel8buttonProcedure;
import net.mcreator.ssc.procedures.DECALSGUIacceptProcedure;
import net.mcreator.ssc.procedures.GravGenOFFbuttonProcedure;
import net.mcreator.ssc.procedures.GravGenONbuttonProcedure;
import net.mcreator.ssc.procedures.IDRemovePRProcedure;
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
import net.mcreator.ssc.procedures.PDA2TABbuttonPRProcedure;
import net.mcreator.ssc.procedures.PDA3TABbuttonPRProcedure;
import net.mcreator.ssc.procedures.PDAflashlightPRProcedure;
import net.mcreator.ssc.procedures.PDAmainButtonPRProcedure;
import net.mcreator.ssc.procedures.PenRemovePRProcedure;
import net.mcreator.ssc.procedures.RCDGUIarmWindowButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIbackButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIcatwalkButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIdeconstructButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIdirArmWindowButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIdirwindowButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIexitButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIgrilleButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIsheatingButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUItileButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIwallButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIwallfloorModeButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIwindowButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIwindowsModeButtonProcedure;
import net.mcreator.ssc.procedures.Sale_GUI_buttonSALEProcedure;
import net.mcreator.ssc.procedures.VendAutGUIButton1Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton2Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton3Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton4Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton5Procedure;
import net.mcreator.ssc.procedures.VendAutGUIButton6Procedure;
import net.mcreator.ssc.world.inventory.MicrowaweGUIMenu;
import net.mcreator.ssc.world.inventory.OrderGUIMenu;
import net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu;
import net.mcreator.ssc.world.inventory.CommunConsoleGUIMenu;
import net.mcreator.ssc.world.inventory.DECALScolorGUIMenu;
import net.mcreator.ssc.world.inventory.GravGenGUIMenu;
import net.mcreator.ssc.world.inventory.PDApassangerGUIMenu;
import net.ssc14.content.menu.construction.RCDGUIMenu;
import net.ssc14.content.menu.construction.RCDGUIwallfloorMenu;
import net.ssc14.content.menu.construction.RCDGUIwindowsMenu;
import net.ssc14.content.menu.commerce.SaleGUIMenu;
import net.ssc14.content.menu.commerce.VendingAut1GUIMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;
import net.ssc14.integration.neoforge.menu.network.MenuButtonDispatchBridge;
import net.ssc14.integration.neoforge.menu.network.MenuAccessGuard;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

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
            case AIRLOCK_PANEL -> handleAirlockPanel(player, message);
            case DECAL_COLOR -> handleDecalColor(player, message);
            case GRAVITY_GENERATOR -> handleGravityGenerator(player, message);
            case PDA_PASSENGER -> handlePdaPassenger(player, message);
            case COMMUNICATION_CONSOLE -> handleCommunicationConsole(player, message);
            case RCD_MAIN -> handleRcdMain(player, message);
            case RCD_WALL_FLOOR -> handleRcdWallFloor(player, message);
            case RCD_WINDOWS -> handleRcdWindows(player, message);
        }
    }

    private static boolean matches(Player player, AbstractContainerMenu expectedMenu, MenuButtonPayload message,
            int menuX, int menuY, int menuZ) {
        return player.containerMenu == expectedMenu
                && MenuAccessGuard.matches(player, expectedMenu,
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

    private static void handleAirlockPanel(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof CabelPannelAirlockMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> CPcabel1buttonProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 1 -> CPcabel2buttonProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 2 -> CPcabel3buttonProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 3 -> CPcabel4buttonProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 4 -> CPcabel5buttonProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 5 -> CPcabel6buttonProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 6 -> CPcabel7buttonProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 7 -> CPcabel8buttonProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            default -> { }
        }
    }

    private static void handleDecalColor(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof DECALScolorGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        if (message.buttonId() == 0) DECALSGUIacceptProcedure.execute(player);
    }

    private static void handleGravityGenerator(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof GravGenGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> GravGenOFFbuttonProcedure.execute(player.level(), message.x(), message.y(), message.z());
            case 1 -> GravGenONbuttonProcedure.execute(player.level(), message.x(), message.y(), message.z());
            default -> { }
        }
    }

    private static void handlePdaPassenger(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof PDApassangerGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> PenRemovePRProcedure.execute(player.level(), player);
            case 1 -> IDRemovePRProcedure.execute(player.level(), player);
            case 2 -> PDAflashlightPRProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 3 -> PDAmainButtonPRProcedure.execute(player);
            case 4 -> PDA2TABbuttonPRProcedure.execute(player);
            case 5 -> PDA3TABbuttonPRProcedure.execute(player);
            default -> { }
        }
    }

    private static void handleCommunicationConsole(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof CommunConsoleGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> CommConsREDcodeProcedure.execute(player.level());
            case 1 -> CommConsBLUEcodeProcedure.execute(player.level());
            case 2 -> CommConsPURPLEcodeProcedure.execute(player.level());
            case 3 -> CommConsYELLOWcodeProcedure.execute(player.level());
            case 4 -> CommConsGREENcodeProcedure.execute(player.level());
            default -> { }
        }
    }

    private static void handleRcdMain(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof RCDGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> RCDGUIdeconstructButtonProcedure.execute(player);
            case 2 -> RCDGUIwallfloorModeButtonProcedure.execute(
                    player.level(), message.x(), message.y(), message.z(), player);
            case 3 -> RCDGUIwindowsModeButtonProcedure.execute(
                    player.level(), message.x(), message.y(), message.z(), player);
            case 6 -> RCDGUIexitButtonProcedure.execute(player);
            default -> { }
        }
    }

    private static void handleRcdWallFloor(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof RCDGUIwallfloorMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> RCDGUIbackButtonProcedure.execute(
                    player.level(), message.x(), message.y(), message.z(), player);
            case 1 -> RCDGUIwallButtonProcedure.execute(player);
            case 2 -> RCDGUIcatwalkButtonProcedure.execute(player);
            case 3 -> RCDGUItileButtonProcedure.execute(player);
            case 4 -> RCDGUIsheatingButtonProcedure.execute(player);
            default -> { }
        }
    }

    private static void handleRcdWindows(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof RCDGUIwindowsMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> RCDGUIbackButtonProcedure.execute(
                    player.level(), message.x(), message.y(), message.z(), player);
            case 1 -> RCDGUIgrilleButtonProcedure.execute(player);
            case 2 -> RCDGUIarmWindowButtonProcedure.execute(player);
            case 3 -> RCDGUIwindowButtonProcedure.execute(player);
            case 4 -> RCDGUIdirArmWindowButtonProcedure.execute(player);
            case 5 -> RCDGUIdirwindowButtonProcedure.execute(player);
            default -> { }
        }
    }

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        MenuButtonDispatchBridge.install(LegacyMenuButtonRegistration::dispatch);
        for (MenuButtonPayload.Channel channel : MenuButtonPayload.Channel.values()) {
            if (channel == MenuButtonPayload.Channel.ID_CODE
                    || channel == MenuButtonPayload.Channel.NETWORK_CONFIG) continue;
            Ssc14NetworkPayloads.addServerbound(channel.type(), MenuButtonPayload.codec(channel),
                    LegacyMenuButtonRegistration::handle);
        }
    }
}
