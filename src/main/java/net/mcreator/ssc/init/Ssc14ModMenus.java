package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.world.inventory.*;
import net.ssc14.content.menu.crafting.DopCraftMenu;
import net.ssc14.content.menu.headset.HeadsetMenu;
import net.ssc14.content.menu.access.AccessConfigItemMenu;
import net.ssc14.integration.neoforge.menu.network.MenuStateUpdateMessage;
import net.ssc14.client.menu.MenuStateReceiver;
import net.ssc14.content.menu.Ssc14MenuTypes;

import java.util.Map;

@Deprecated(forRemoval = false)
public class Ssc14ModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = Ssc14MenuTypes.REGISTRY;
	public static final DeferredHolder<MenuType<?>, MenuType<DopCraftMenu>> DOP_CRAFT_MENU = alias(Ssc14MenuTypes.DOP_CRAFT_MENU);
	public static final DeferredHolder<MenuType<?>, MenuType<IDcodeMenu>> I_DCODE = alias(Ssc14MenuTypes.I_DCODE);
	public static final DeferredHolder<MenuType<?>, MenuType<AccessConfigMENUMenu>> ACCESS_CONFIG_MENU = alias(Ssc14MenuTypes.ACCESS_CONFIG_MENU);
	public static final DeferredHolder<MenuType<?>, MenuType<AccessConfigItemMenu>> ACCESS_CONFIG_GUI = alias(Ssc14MenuTypes.ACCESS_CONFIG_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<CabelPannelAirlockMenu>> CABEL_PANNEL_AIRLOCK = alias(Ssc14MenuTypes.CABEL_PANNEL_AIRLOCK);
	public static final DeferredHolder<MenuType<?>, MenuType<GravGenGUIMenu>> GRAV_GEN_GUI = alias(Ssc14MenuTypes.GRAV_GEN_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<MacFrameGUIMenu>> MAC_FRAME_GUI = alias(Ssc14MenuTypes.MAC_FRAME_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<PDApassangerGUIMenu>> PD_APASSANGER_GUI = alias(Ssc14MenuTypes.PD_APASSANGER_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<RCDGUIMenu>> RCDGUI = alias(Ssc14MenuTypes.RCDGUI);
	public static final DeferredHolder<MenuType<?>, MenuType<RCDGUIwallfloorMenu>> RCDGU_IWALLFLOOR = alias(Ssc14MenuTypes.RCDGU_IWALLFLOOR);
	public static final DeferredHolder<MenuType<?>, MenuType<HeadsetMenu>> HEADSET_GU_IKOSTIL = alias(Ssc14MenuTypes.HEADSET_GU_IKOSTIL);
	public static final DeferredHolder<MenuType<?>, MenuType<CommunConsoleGUIMenu>> COMMUN_CONSOLE_GUI = alias(Ssc14MenuTypes.COMMUN_CONSOLE_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<VendingAut1GUIMenu>> VENDING_AUT_1_GUI = alias(Ssc14MenuTypes.VENDING_AUT_1_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<RCDGUIwindowsMenu>> RCDGU_IWINDOWS = alias(Ssc14MenuTypes.RCDGU_IWINDOWS);
	public static final DeferredHolder<MenuType<?>, MenuType<NetConfigGUIMenu>> NET_CONFIG_GUI = alias(Ssc14MenuTypes.NET_CONFIG_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<EggCartonGUIMenu>> EGG_CARTON_GUI = alias(Ssc14MenuTypes.EGG_CARTON_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<MicrowaweGUIMenu>> MICROWAWE_GUI = alias(Ssc14MenuTypes.MICROWAWE_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<DECALScolorGUIMenu>> DECAL_SCOLOR_GUI = alias(Ssc14MenuTypes.DECAL_SCOLOR_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<BackpackGUIMenu>> BACKPACK_GUI = alias(Ssc14MenuTypes.BACKPACK_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<OrderGUIMenu>> ORDER_GUI = alias(Ssc14MenuTypes.ORDER_GUI);
	public static final DeferredHolder<MenuType<?>, MenuType<SaleGUIMenu>> SALE_GUI = alias(Ssc14MenuTypes.SALE_GUI);

	static {
		Ssc14MenuTypes.bindFactory("dop_craft_menu", DopCraftMenu::new);
		Ssc14MenuTypes.bindFactory("i_dcode", IDcodeMenu::new);
		Ssc14MenuTypes.bindFactory("access_config_menu", AccessConfigMENUMenu::new);
		Ssc14MenuTypes.bindFactory("access_config_gui", AccessConfigItemMenu::new);
		Ssc14MenuTypes.bindFactory("cabel_pannel_airlock", CabelPannelAirlockMenu::new);
		Ssc14MenuTypes.bindFactory("grav_gen_gui", GravGenGUIMenu::new);
		Ssc14MenuTypes.bindFactory("mac_frame_gui", MacFrameGUIMenu::new);
		Ssc14MenuTypes.bindFactory("pd_apassanger_gui", PDApassangerGUIMenu::new);
		Ssc14MenuTypes.bindFactory("rcdgui", RCDGUIMenu::new);
		Ssc14MenuTypes.bindFactory("rcdgu_iwallfloor", RCDGUIwallfloorMenu::new);
		Ssc14MenuTypes.bindFactory("headset_gu_ikostil", HeadsetMenu::new);
		Ssc14MenuTypes.bindFactory("commun_console_gui", CommunConsoleGUIMenu::new);
		Ssc14MenuTypes.bindFactory("vending_aut_1_gui", VendingAut1GUIMenu::new);
		Ssc14MenuTypes.bindFactory("rcdgu_iwindows", RCDGUIwindowsMenu::new);
		Ssc14MenuTypes.bindFactory("net_config_gui", NetConfigGUIMenu::new);
		Ssc14MenuTypes.bindFactory("egg_carton_gui", EggCartonGUIMenu::new);
		Ssc14MenuTypes.bindFactory("microwawe_gui", MicrowaweGUIMenu::new);
		Ssc14MenuTypes.bindFactory("decal_scolor_gui", DECALScolorGUIMenu::new);
		Ssc14MenuTypes.bindFactory("backpack_gui", BackpackGUIMenu::new);
		Ssc14MenuTypes.bindFactory("order_gui", OrderGUIMenu::new);
		Ssc14MenuTypes.bindFactory("sale_gui", SaleGUIMenu::new);
		Ssc14MenuTypes.validateFactoryBindings();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static <T extends AbstractContainerMenu>
			DeferredHolder<MenuType<?>, MenuType<T>> alias(
					DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> holder) {
		return (DeferredHolder) holder;
	}

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide()) {
				if (Minecraft.getInstance().screen instanceof MenuStateReceiver accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				ClientPacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}
