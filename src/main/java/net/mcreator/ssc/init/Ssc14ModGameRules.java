/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.ssc.init;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber
public class Ssc14ModGameRules {
	public static GameRules.Key<GameRules.BooleanValue> SS_CDEBUGMODE;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		SS_CDEBUGMODE = GameRules.register("sSCdebugmode", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
	}
}