package net.mcreator.ssc.network;

import net.mcreator.ssc.Ssc14Mod;
import net.mcreator.ssc.procedures.CommConsBLUEcodeProcedure;
import net.mcreator.ssc.procedures.CommConsGREENcodeProcedure;
import net.mcreator.ssc.procedures.CommConsPURPLEcodeProcedure;
import net.mcreator.ssc.procedures.CommConsREDcodeProcedure;
import net.mcreator.ssc.procedures.CommConsYELLOWcodeProcedure;
import net.minecraft.core.BlockPos;
import net.ssc14.content.menu.decal.DecalColorMenuService;
import net.mcreator.ssc.procedures.GravGenOFFbuttonProcedure;
import net.mcreator.ssc.procedures.GravGenONbuttonProcedure;
import net.mcreator.ssc.procedures.IDRemovePRProcedure;
import net.mcreator.ssc.procedures.MicrwGUIbutEProcedure;
import net.ssc14.content.menu.pda.PdaTabService;
import net.ssc14.content.menu.pda.PdaFlashlightService;
import net.mcreator.ssc.procedures.PenRemovePRProcedure;
import net.ssc14.content.item.tool.RcdModeService;
import net.mcreator.ssc.procedures.RCDGUIbackButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIcatwalkButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIdirwindowButtonProcedure;
import net.ssc14.content.menu.MenuCloseService;
import net.mcreator.ssc.procedures.RCDGUIgrilleButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIsheatingButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUItileButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIwallfloorModeButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIwindowButtonProcedure;
import net.mcreator.ssc.procedures.RCDGUIwindowsModeButtonProcedure;
import net.ssc14.content.commerce.VendingMachineRuntimeService;
import net.ssc14.content.menu.food.MicrowaweGUIMenu;
import net.ssc14.content.menu.food.MicrowaveButtonIds;
import net.ssc14.content.menu.food.MicrowaveControlService;
import net.ssc14.content.commerce.cargo.CargoOrderButtonIds;
import net.ssc14.content.commerce.cargo.CargoOrderService;
import net.ssc14.content.commerce.cargo.CargoPurchaseService;
import net.ssc14.content.menu.commerce.OrderGUIMenu;
import net.ssc14.content.menu.airlock.CabelPannelAirlockMenu;
import net.ssc14.content.menu.airlock.AirlockPanelButtonIds;
import net.mcreator.ssc.access.AirlockWireService;
import net.ssc14.content.menu.station.CommunConsoleGUIMenu;
import net.ssc14.content.menu.decal.DECALScolorGUIMenu;
import net.ssc14.content.menu.station.GravGenGUIMenu;
import net.ssc14.content.menu.pda.PDApassangerGUIMenu;
import net.ssc14.content.menu.construction.RCDGUIMenu;
import net.ssc14.content.menu.construction.RCDGUIwallfloorMenu;
import net.ssc14.content.menu.construction.RCDGUIwindowsMenu;
import net.ssc14.content.menu.commerce.SaleGUIMenu;
import net.ssc14.content.commerce.SaleTransactionService;
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
        VendingMachineRuntimeService.vend(player.level(),
                new BlockPos(message.x(), message.y(), message.z()), message.buttonId());
    }

    private static void handleSale(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof SaleGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        if (!player.level().isClientSide() && message.buttonId() == 0) {
            SaleTransactionService.sellNearby(player.level(),
                    new BlockPos(message.x(), message.y(), message.z()), player);
        }
    }

    private static void handleOrder(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof OrderGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)
                || player.level().isClientSide()) return;
        int buttonId = message.buttonId();
        int tab = CargoOrderButtonIds.tabForButton(buttonId);
        int itemIndex = CargoOrderButtonIds.itemIndexForButton(buttonId);
        if (tab > 0) {
            CargoOrderService.selectTab(player.level(), new BlockPos(message.x(), message.y(), message.z()), buttonId);
            CargoOrderService.refreshPreview(menu, player);
        } else if (itemIndex >= 0) {
            CargoPurchaseService.purchase(player.level(),
                    new BlockPos(message.x(), message.y(), message.z()), player, itemIndex);
        }
    }

    private static void handleMicrowave(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof MicrowaweGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        BlockPos pos = new BlockPos(message.x(), message.y(), message.z());
        if (message.buttonId() == MicrowaveButtonIds.START) {
            MicrwGUIbutEProcedure.execute(player.level(), message.x(), message.y(), message.z(), player);
        } else {
            MicrowaveControlService.setTimer(player.level(), pos, message.buttonId());
        }
    }

    private static void handleAirlockPanel(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof CabelPannelAirlockMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
		int wire = AirlockPanelButtonIds.wireForButton(message.buttonId());
		if (wire > 0) {
			AirlockWireService.toggle(player, new BlockPos(message.x(), message.y(), message.z()), wire);
		}
    }

    private static void handleDecalColor(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof DECALScolorGUIMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        if (message.buttonId() == 0) DecalColorMenuService.apply(player, menu);
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
            case 2 -> PdaFlashlightService.execute(player.level(), message.x(), message.y(), message.z(), player);
            case 3 -> PdaTabService.setTab(player, 1);
            case 4 -> PdaTabService.execute(player);
            case 5 -> PdaTabService.setTab(player, 3);
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
            case 0 -> RcdModeService.deconstruct(player);
            case 2 -> RCDGUIwallfloorModeButtonProcedure.execute(
                    player.level(), message.x(), message.y(), message.z(), player);
            case 3 -> RCDGUIwindowsModeButtonProcedure.execute(
                    player.level(), message.x(), message.y(), message.z(), player);
            case 6 -> MenuCloseService.execute(player);
            default -> { }
        }
    }

    private static void handleRcdWallFloor(Player player, MenuButtonPayload message) {
        if (!(player.containerMenu instanceof RCDGUIwallfloorMenu menu)
                || !matches(player, menu, message, menu.x, menu.y, menu.z)) return;
        switch (message.buttonId()) {
            case 0 -> RCDGUIbackButtonProcedure.execute(
                    player.level(), message.x(), message.y(), message.z(), player);
            case 1 -> RcdModeService.wall(player);
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
            case 2 -> RcdModeService.armWindow(player);
            case 3 -> RCDGUIwindowButtonProcedure.execute(player);
            case 4 -> RcdModeService.directionalArmWindow(player);
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
