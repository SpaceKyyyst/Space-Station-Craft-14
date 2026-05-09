package net.mcreator.ssc.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.procedures.PickDAMAGEProcedure;
import net.mcreator.ssc.init.Ssc14ModBlocks;

import java.util.List;

public class PickItem extends Item {
	public PickItem(Item.Properties properties) {
		super(properties.stacksTo(1).fireResistant().attributes(ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, -0.9, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).enchantable(1));
	}

	@Override
	public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
		return List.of(Ssc14ModBlocks.ASTEROID_STONE_BASE.get(), Ssc14ModBlocks.ASTEROID_STONE_IRON.get(), Ssc14ModBlocks.ASTEROID_STONE_QUARTZ.get(), Ssc14ModBlocks.ASTEROID_STONE_COAL.get(), Ssc14ModBlocks.ASTEROID_STONE_GOLD.get(),
				Ssc14ModBlocks.ASTEROID_STONE_PLASMA.get(), Ssc14ModBlocks.ASTEROID_STONE_SILVER.get(), Ssc14ModBlocks.ASTEROID_STONE_URAN.get(), Ssc14ModBlocks.ASTEROID_STONE_DIAMOND.get()).contains(blockstate.getBlock()) ? 40f : 1;
	}

	@Override
	public boolean mineBlock(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
		itemstack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
		return true;
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		itemstack.hurtAndBreak(2, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
		PickDAMAGEProcedure.execute(entity.level(), entity);
	}
}