package net.ssc14.content.item.tool;

import java.util.List;
import net.minecraft.resources.Identifier;
import net.ssc14.common.Ssc14Identity;

/** Immutable combat and mining configuration for persisted mechanical-tool IDs. */
public enum MechanicalToolProfile {
    CROWBAR("crowbar_repair_items", -0.9D, -3.0D,
            damage("ssc_14dmgblunt", 8.0F)),
    NIPPERS("nippers_repair_items", -0.9D, -2.0D,
            damage("ssc_14dmgpiercing", 2.0F)),
    FIRE_AXE("fire_axe_repair_items", -0.9D, -3.25D,
            damage("ssc_14dmgblunt", 5.0F), damage("ssc_14dmgslash", 10.0F)),
    SCREWDRIVER("screwdriver_repair_items", 0.0D, -2.0D,
            damage("ssc_14dmgpiercing", 6.0F)),
    SPANNER("spanner_repair_items", -0.9D, -2.5D,
            damage("ssc_14dmgblunt", 4.5F)),
    PICK("pick_repair_items", -0.9D, -3.3D,
            damage("ssc_14dmgpiercing", 5.0F));

    private final String repairTagPath;
    private final double attackDamage;
    private final double attackSpeed;
    private final List<DamageEffect> damageEffects;

    MechanicalToolProfile(
            String repairTagPath,
            double attackDamage,
            double attackSpeed,
            DamageEffect... damageEffects) {
        this.repairTagPath = repairTagPath;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.damageEffects = List.of(damageEffects);
    }

    public String repairTagPath() {
        return repairTagPath;
    }

    public double attackDamage() {
        return attackDamage;
    }

    public double attackSpeed() {
        return attackSpeed;
    }

    public List<DamageEffect> damageEffects() {
        return damageEffects;
    }

    private static DamageEffect damage(String path, float amount) {
        return new DamageEffect(
                Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path), amount);
    }

    public record DamageEffect(Identifier damageType, float amount) { }
}
