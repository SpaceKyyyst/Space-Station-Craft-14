package net.mcreator.ssc.init;

import net.minecraft.client.model.player.PlayerModel;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.ssc14.client.Ssc14HumanoidModels;

/**
 * @deprecated Use {@link Ssc14HumanoidModels}. The canonical client registry
 * owns model construction and event registration; this type only preserves
 * the historical field and callback surface for external integrations.
 */
@Deprecated(forRemoval = true)
public final class Ssc14ModHumanoidModels {
	public static PlayerModel HUMAN_MODEL;
	public static PlayerModel UNATH_MODEL;
	public static PlayerModel FELINID_MODEL;

	private Ssc14ModHumanoidModels() {
	}

	public static void initModels(EntityRenderersEvent.AddLayers event) {
		HUMAN_MODEL = Ssc14HumanoidModels.HUMAN_MODEL;
		UNATH_MODEL = Ssc14HumanoidModels.UNATH_MODEL;
		FELINID_MODEL = Ssc14HumanoidModels.FELINID_MODEL;
	}
}
