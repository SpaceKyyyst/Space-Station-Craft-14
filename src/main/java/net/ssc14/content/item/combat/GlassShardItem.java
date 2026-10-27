package net.ssc14.content.item.combat;

import java.util.Objects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/** Canonical shared implementation for standard and armored glass shards. */
public class GlassShardItem extends Item {
    private static final double ATTACK_DAMAGE_MODIFIER = -0.9D;
    private static final double ATTACK_SPEED_MODIFIER = -3.0D;

    private final GlassShardKind kind;

    public GlassShardItem(Properties properties, GlassShardKind kind) {
        super(configure(properties));
        this.kind = Objects.requireNonNull(kind, "kind");
    }

    public final GlassShardKind kind() {
        return kind;
    }

    @Override
    public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(itemStack, target, attacker);
        GlassShardInteractionService.hurt(kind, target);
    }

    private static Properties configure(Properties properties) {
        return properties.stacksTo(1).fireResistant().attributes(
                ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(BASE_ATTACK_DAMAGE_ID,
                                        ATTACK_DAMAGE_MODIFIER,
                                        AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(BASE_ATTACK_SPEED_ID,
                                        ATTACK_SPEED_MODIFIER,
                                        AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .build());
    }
}
