package net.ssc14.content.item.currency;

import net.minecraft.world.item.Rarity;

/** Immutable behavior and item-property definition for station currency. */
public enum CurrencyKind {
    CREDIT(1, 50, Rarity.COMMON, false, true),
    CREDIT_50(50, 50, Rarity.COMMON, false, false),
    DEBUG_DELETER(0, 1, Rarity.EPIC, true, false);

    private final int unitValue;
    private final int maxStackSize;
    private final Rarity rarity;
    private final boolean fireResistant;
    private final boolean consolidatesOnUse;

    CurrencyKind(
            int unitValue,
            int maxStackSize,
            Rarity rarity,
            boolean fireResistant,
            boolean consolidatesOnUse) {
        this.unitValue = unitValue;
        this.maxStackSize = maxStackSize;
        this.rarity = rarity;
        this.fireResistant = fireResistant;
        this.consolidatesOnUse = consolidatesOnUse;
    }

    public int unitValue() {
        return unitValue;
    }

    public int maxStackSize() {
        return maxStackSize;
    }

    public Rarity rarity() {
        return rarity;
    }

    public boolean fireResistant() {
        return fireResistant;
    }

    public boolean consolidatesOnUse() {
        return consolidatesOnUse;
    }
}
