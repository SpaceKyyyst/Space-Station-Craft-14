package net.mcreator.ssc.network;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;

/** @deprecated Use the canonical ID-code menu handler. */
@Deprecated(forRemoval = false)
public final class IDcodeButtonRegistration {
    private IDcodeButtonRegistration() {
    }

    public static void handleData(MenuButtonPayload message, IPayloadContext context) {
        net.ssc14.content.menu.access.IDcodeButtonRegistration.handleData(message, context);
    }

    public static void handleButtonAction(Player player, int buttonId, int x, int y, int z) {
        net.ssc14.content.menu.access.IDcodeButtonRegistration.handleButtonAction(
                player, buttonId, x, y, z);
    }
}
