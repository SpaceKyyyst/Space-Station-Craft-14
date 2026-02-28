/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.world.inventory.*;
import net.mcreator.ssc.network.MenuStateUpdateMessage;
import net.mcreator.ssc.Ssc14Mod;

import java.util.Map;

public class Ssc14ModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, Ssc14Mod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<DopCraftMenuMenu>> DOP_CRAFT_MENU = REGISTRY.register("dop_craft_menu", () -> IMenuTypeExtension.create(DopCraftMenuMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<IDcodeMenu>> I_DCODE = REGISTRY.register("i_dcode", () -> IMenuTypeExtension.create(IDcodeMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<AccessConfigMENUMenu>> ACCESS_CONFIG_MENU = REGISTRY.register("access_config_menu", () -> IMenuTypeExtension.create(AccessConfigMENUMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<AccessConfigGUIMenu>> ACCESS_CONFIG_GUI = REGISTRY.register("access_config_gui", () -> IMenuTypeExtension.create(AccessConfigGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CabelPannelAirlockMenu>> CABEL_PANNEL_AIRLOCK = REGISTRY.register("cabel_pannel_airlock", () -> IMenuTypeExtension.create(CabelPannelAirlockMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<GravGenGUIMenu>> GRAV_GEN_GUI = REGISTRY.register("grav_gen_gui", () -> IMenuTypeExtension.create(GravGenGUIMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof Ssc14ModScreens.ScreenAccessor accessor && needClientUpdate)
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