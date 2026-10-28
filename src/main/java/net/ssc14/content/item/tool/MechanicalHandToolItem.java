package net.ssc14.content.item.tool;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.ssc14.common.Ssc14Identity;

/** Canonical implementation shared by the station's simple mechanical hand tools. */
public class MechanicalHandToolItem extends Item {
    private static final float MINING_SPEED = 4.0F;

    private final MechanicalToolProfile profile;

    public MechanicalHandToolItem(Properties properties, MechanicalToolProfile profile) {
        super(applyProperties(properties, Objects.requireNonNull(profile, "profile")));
        if (profile == MechanicalToolProfile.PICK) {
            throw new IllegalArgumentException("PICK requires AsteroidPickItem");
        }
        this.profile = profile;
    }

    public MechanicalToolProfile profile() {
        return profile;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return !state.is(BlockTags.NEEDS_STONE_TOOL)
                && !state.is(BlockTags.NEEDS_IRON_TOOL)
                && !state.is(BlockTags.NEEDS_DIAMOND_TOOL);
    }

    @Override
    public boolean canPerformAction(ItemInstance stack, ItemAbility ability) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_HOE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(ability)
                || ability == ItemAbilities.SWORD_SWEEP;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return MINING_SPEED;
    }

    @Override
    public boolean mineBlock(
            ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        stack.hurtAndBreak(1, entity, entity.getUsedItemHand().asEquipmentSlot());
        return true;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, target, target.getUsedItemHand().asEquipmentSlot());
        MechanicalToolDamageService.apply(target.level(), target, profile);
    }

    private static Properties applyProperties(
            Properties properties, MechanicalToolProfile profile) {
        TagKey<Item> repairItems = TagKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(
                        Ssc14Identity.MOD_ID, profile.repairTagPath()));
        ToolMaterial material = new ToolMaterial(
                BlockTags.INCORRECT_FOR_WOODEN_TOOL, 0, MINING_SPEED, 0.0F, 1, repairItems);
        return material.applyToolProperties(
                        properties,
                        BlockTags.MINEABLE_WITH_PICKAXE,
                        (float) profile.attackDamage(),
                        (float) profile.attackSpeed(),
                        0)
                .attributes(attributes(profile))
                .fireResistant();
    }

    static ItemAttributeModifiers attributes(MechanicalToolProfile profile) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID,
                                profile.attackDamage(),
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                BASE_ATTACK_SPEED_ID,
                                profile.attackSpeed(),
                                AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }
}
