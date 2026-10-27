package net.ssc14.content.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.Ssc14Items;

/** Canonical gas analyser item implementation. */
@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public class GasAnalyserItem extends Item {
    public static final float DESTROY_SPEED = 1.0F;
    private static final float TOOL_ATTACK_DAMAGE = -0.5F;
    private static final float TOOL_ATTACK_SPEED = -3.0F;
    private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, 0, DESTROY_SPEED, 0.0F, 1,
            TagKey.create(Registries.ITEM,
                    Identifier.fromNamespaceAndPath(
                            Ssc14Identity.MOD_ID, "gas_analyser_repair_items")));

    public GasAnalyserItem(Properties properties) {
        super(TOOL_MATERIAL.applyToolProperties(
                        properties, BlockTags.MINEABLE_WITH_PICKAXE,
                        TOOL_ATTACK_DAMAGE, TOOL_ATTACK_SPEED, 0)
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(BASE_ATTACK_DAMAGE_ID,
                                        TOOL_ATTACK_DAMAGE,
                                        AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(BASE_ATTACK_SPEED_ID,
                                        TOOL_ATTACK_SPEED,
                                        AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .build())
                .fireResistant());
    }

    @SubscribeEvent
    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        event.modify(Ssc14Items.GAS_ANALYSER.get(),
                (builder, _, _) -> builder.set(DataComponents.MAX_DAMAGE, null));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState blockState) {
        return !blockState.is(BlockTags.NEEDS_STONE_TOOL)
                && !blockState.is(BlockTags.NEEDS_IRON_TOOL)
                && !blockState.is(BlockTags.NEEDS_DIAMOND_TOOL);
    }

    @Override
    public boolean canPerformAction(ItemInstance stack, ItemAbility action) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(action)
                || ItemAbilities.DEFAULT_HOE_ACTIONS.contains(action)
                || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(action)
                || action == ItemAbilities.SWORD_SWEEP;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState blockState) {
        return DESTROY_SPEED;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState blockState,
            BlockPos pos, LivingEntity entity) {
        stack.hurtAndBreak(1, entity, entity.getUsedItemHand().asEquipmentSlot());
        return true;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, target, target.getUsedItemHand().asEquipmentSlot());
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        GasAnalyserOverlayService.activate(player);
        return result;
    }
}
