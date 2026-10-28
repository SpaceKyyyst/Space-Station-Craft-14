package net.ssc14.content.item.tool;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
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

/** Canonical implementation shared by the inactive and active welder IDs. */
@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public class WelderItem extends Item {
    public static final float DESTROY_SPEED = 4.0F;
    private static final float TOOL_ATTACK_DAMAGE = -0.9F;
    private static final float TOOL_ATTACK_SPEED = -3.0F;

    private final WelderState state;

    public WelderItem(Properties properties, WelderState state) {
        super(state.toolMaterial().applyToolProperties(
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
        this.state = state;
    }

    public WelderState state() {
        return state;
    }

    @SubscribeEvent
    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        event.modify(Ssc14Items.WELDING.get(),
                (builder, _, _) -> builder.set(DataComponents.MAX_DAMAGE, null));
        event.modify(Ssc14Items.ACTIVE_WELDER.get(),
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
        WelderCombatService.apply(target.level(), target, state);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        InteractionResult result = super.use(level, player, hand);
        WelderStateService.toggle(
                level, player.getX(), player.getY(), player.getZ(), player);
        return result;
    }

    @Override
    public boolean shouldCauseReequipAnimation(
            ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!state.active()) {
            return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
        }
        return slotChanged || !ItemStack.isSameItem(oldStack, newStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity,
            @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTick(stack, level, entity, equipmentSlot);
        if (state.active() && equipmentSlot == EquipmentSlot.MAINHAND
                && entity instanceof net.minecraft.server.level.ServerPlayer player) {
            WelderStateService.tick(level, player, InteractionHand.MAIN_HAND, stack);
        }
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, Player player) {
        if (state.active()) {
            WelderStateService.dropInactive(
                    player.level(), player.getX(), player.getY(), player.getZ(), stack);
        }
        return true;
    }
}
