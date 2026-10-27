package net.ssc14.content.item.tool;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.core.registries.Registries;
import net.ssc14.common.Ssc14Identity;

/** Registry-backed operating state of the welding tool. */
public enum WelderState {
    INACTIVE("welding_repair_items", "ssc_14dmgblunt", 5.0F),
    ACTIVE("active_welder_repair_items", "ssc_14dmgheat", 8.0F);

    private final ToolMaterial toolMaterial;
    private final Identifier damageTypeId;
    private final float attackDamage;

    WelderState(String repairTagPath, String damageTypePath, float attackDamage) {
        TagKey<net.minecraft.world.item.Item> repairTag = TagKey.create(
                Registries.ITEM, id(repairTagPath));
        this.toolMaterial = new ToolMaterial(
                BlockTags.INCORRECT_FOR_WOODEN_TOOL, 0, 4.0F, 0.0F, 1, repairTag);
        this.damageTypeId = id(damageTypePath);
        this.attackDamage = attackDamage;
    }

    public ToolMaterial toolMaterial() {
        return toolMaterial;
    }

    public Identifier damageTypeId() {
        return damageTypeId;
    }

    public float attackDamage() {
        return attackDamage;
    }

    public boolean active() {
        return this == ACTIVE;
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
