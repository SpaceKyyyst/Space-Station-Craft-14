package net.ssc14.content.item.container;

import net.minecraft.world.item.Item;

/** Stable roles and item properties for portable menu-backed items. */
public enum PortableMenuItemKind {
    ACCESS_CONFIG("Access Configurator", true, 1),
    BACKPACK("Backpack", false, 21),
    PASSENGER_PDA("PDA passanger", true, 2),
    EGG_CARTON("Egg Carton", false, 11);

    private final String menuTitle;
    private final boolean fireResistant;
    private final int inventorySlots;

    PortableMenuItemKind(String menuTitle, boolean fireResistant, int inventorySlots) {
        this.menuTitle = menuTitle;
        this.fireResistant = fireResistant;
        this.inventorySlots = inventorySlots;
    }

    public String menuTitle() {
        return menuTitle;
    }

    public boolean fireResistant() {
        return fireResistant;
    }

    public int inventorySlots() {
        return inventorySlots;
    }

    Item.Properties applyTo(Item.Properties properties) {
        properties.stacksTo(1);
        return fireResistant ? properties.fireResistant() : properties;
    }
}
