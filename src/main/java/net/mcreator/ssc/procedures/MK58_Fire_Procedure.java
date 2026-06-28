package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModEntities;
import net.mcreator.ssc.entity.Bullet35Entity;

public class MK58_Fire_Procedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (Ssc14ModItems.MK_58.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() && Ssc14ModItems.BULLET_35ITEM.get() == (getItemStackFromItemStackSlot(0, itemstack)).getItem()) {
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					Projectile _entityToSpawn = initArrowProjectile(new Bullet35Entity(Ssc14ModEntities.BULLET_35.get(), projectileLevel), entity, 16, true, false, false, AbstractArrow.Pickup.DISALLOWED);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 5, (float) 0.1);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:gunshots_mk58")), SoundSource.MASTER, (float) 1.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:gunshots_mk58")), SoundSource.MASTER, (float) 1.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
				}
			}
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 4);
			if (0 < (getItemStackFromItemStackSlot(1, itemstack)).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0)) {
				{
					final String _tagName = "Bullets";
					final double _tagValue = ((getItemStackFromItemStackSlot(1, itemstack)).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0) - 1);
					CustomData.update(DataComponents.CUSTOM_DATA, (getItemStackFromItemStackSlot(1, itemstack)), tag -> tag.putDouble(_tagName, _tagValue));
				}
				if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()).copy();
					_setstack.setCount(1);
					_modHandlerItemSetSlot.setStackInSlot(0, _setstack);
				}
			} else {
				if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()).copy();
					_setstack.setCount(-1);
					_modHandlerItemSetSlot.setStackInSlot(0, _setstack);
				}
			}
		} else if ((Ssc14ModItems.MK_58.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()
				|| Ssc14ModItems.MK_58NOMAGZ.get() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) && ItemStack.EMPTY.getItem() == (getItemStackFromItemStackSlot(0, itemstack)).getItem()) {
			if (0 < (getItemStackFromItemStackSlot(1, itemstack)).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0)) {
				{
					final String _tagName = "Bullets";
					final double _tagValue = ((getItemStackFromItemStackSlot(1, itemstack)).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("Bullets", 0) - 1);
					CustomData.update(DataComponents.CUSTOM_DATA, (getItemStackFromItemStackSlot(1, itemstack)), tag -> tag.putDouble(_tagName, _tagValue));
				}
				if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()).copy();
					_setstack.setCount(1);
					_modHandlerItemSetSlot.setStackInSlot(0, _setstack);
				}
			} else {
				if (itemstack.getCapability(Capabilities.ItemHandler.ITEM, null) instanceof IItemHandlerModifiable _modHandlerItemSetSlot) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.BULLET_35ITEM.get()).copy();
					_setstack.setCount(-1);
					_modHandlerItemSetSlot.setStackInSlot(0, _setstack);
				}
			}
		}
	}

	private static ItemStack getItemStackFromItemStackSlot(int slotID, ItemStack itemStack) {
		IItemHandler itemHandler = itemStack.getCapability(Capabilities.ItemHandler.ITEM, null);
		if (itemHandler != null)
			return itemHandler.getStackInSlot(slotID).copy();
		return ItemStack.EMPTY;
	}

	private static AbstractArrow initArrowProjectile(AbstractArrow entityToSpawn, Entity shooter, float damage, boolean silent, boolean fire, boolean particles, AbstractArrow.Pickup pickup) {
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage(damage);
		if (silent)
			entityToSpawn.setSilent(true);
		if (fire)
			entityToSpawn.igniteForSeconds(100);
		if (particles)
			entityToSpawn.setCritArrow(true);
		entityToSpawn.pickup = pickup;
		return entityToSpawn;
	}
}