package net.ssc14.content.item.combat;

/** Immutable combat profile for glass-shard weapons. */
public enum GlassShardKind {
    STANDARD(2.5F),
    ARMORED(4.5F);

    private final float bonusDamage;

    GlassShardKind(float bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    public float bonusDamage() {
        return bonusDamage;
    }
}
