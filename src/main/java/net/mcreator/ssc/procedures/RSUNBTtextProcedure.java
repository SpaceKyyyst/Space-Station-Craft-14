package net.mcreator.ssc.procedures;

import net.minecraft.world.item.ItemStack;
import net.ssc14.content.item.tool.RsuStateService;

/** @deprecated Compatibility entry point for generated callers. */
@Deprecated(forRemoval = false)
public final class RSUNBTtextProcedure {
	private RSUNBTtextProcedure() { }

	public static String execute(ItemStack itemstack) {
		return RsuStateService.tooltip(itemstack);
	}
}
