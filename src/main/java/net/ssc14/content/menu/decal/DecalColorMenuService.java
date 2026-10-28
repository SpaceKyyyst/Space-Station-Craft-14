package net.ssc14.content.menu.decal;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.ssc14.core.network.MenuStateAccess;

/** Validates and applies the decal tool's RGB menu state. */
public final class DecalColorMenuService {
    private static final String RED = "R";
    private static final String GREEN = "G";
    private static final String BLUE = "B";
    private static final TagKey<Item> COLORED_DECALS =
            ItemTags.create(Identifier.parse("ssc14:colored_decals"));

    private DecalColorMenuService() { }

    public static void closeWhenToolIsMissing(Player player) {
        if (!isToolHeld(player)) {
            player.closeContainer();
        }
    }

    public static boolean isValid(MenuStateAccess menu) {
        return DecalColorInput.isValid(value(menu, RED), value(menu, GREEN), value(menu, BLUE));
    }

    public static void apply(Player player, MenuStateAccess menu) {
        if (!isToolHeld(player) || !isValid(menu)) {
            return;
        }
        ItemStack tool = player.getMainHandItem();
        CustomData.update(DataComponents.CUSTOM_DATA, tool, tag -> {
            tag.putDouble(RED, DecalColorInput.parse(value(menu, RED)));
            tag.putDouble(GREEN, DecalColorInput.parse(value(menu, GREEN)));
            tag.putDouble(BLUE, DecalColorInput.parse(value(menu, BLUE)));
        });
    }

    private static String value(MenuStateAccess menu, String name) {
        return menu.getMenuState(0, name, "");
    }

    private static boolean isToolHeld(Player player) {
        return player.getMainHandItem().is(COLORED_DECALS);
    }
}
