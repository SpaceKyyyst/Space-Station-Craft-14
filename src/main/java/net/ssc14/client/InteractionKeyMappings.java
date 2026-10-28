package net.ssc14.client;

import org.lwjgl.glfw.GLFW;
import net.minecraft.client.KeyMapping;

/** Canonical client key mapping for inventory slot interaction. */
public final class InteractionKeyMappings {
    public static final KeyMapping INTERACTION = new KeyMapping(
            "key.ssc_14.interaction", GLFW.GLFW_KEY_E, KeyMapping.Category.INVENTORY);

    private InteractionKeyMappings() { }
}
