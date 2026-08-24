package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
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
		double BulletChamber = 0;
		double MagazBullets = 0;
		if (!(entity instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack))) {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 10);
			if (itemstack.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem()) {
				BulletChamber = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("BulletChamber", 0);
				MagazBullets = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("MagazBullets", 0);
				if ((Ssc14ModItems.MK_58.get() == itemstack.getItem() || Ssc14ModItems.MK_58NOMAGZ.get() == itemstack.getItem()) && 0 < itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("BulletChamber", 0)) {
					{
						Entity _shootFrom = entity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = initArrowProjectile(new Bullet35Entity(Ssc14ModEntities.BULLET_35.get(), projectileLevel), entity, 16, true, false, false, AbstractArrow.Pickup.DISALLOWED);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 4, (float) 0.1);
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
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY() + entity.getBbHeight() / 3d), (entity.getZ()), new ItemStack(Ssc14ModItems.BULLET_35_CARTRIDGE.get()));
						entityToSpawn.setPickUpDelay(0);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (0 < itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("MagazBullets", 0)) {
						{
							final String _tagName = "MagazBullets";
							final double _tagValue = (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("MagazBullets", 0) - 1);
							CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
						}
					} else {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_open")), SoundSource.MASTER, (float) 1.5,
										(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_open")), SoundSource.MASTER, (float) 1.5,
										(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
							}
						}
						if (Ssc14ModItems.MK_58.get() == itemstack.getItem()) {
							itemstack.shrink(1);
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack44 = new ItemStack(Ssc14ModItems.MK_58OPEN.get()).copy();
								_setstack44.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack44);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							{
								final String _tagName = "GenEraTE";
								final boolean _tagValue = true;
								CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
							}
							{
								final String _tagName = "BulletChamber";
								final double _tagValue = 0;
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
							}
						} else if (Ssc14ModItems.MK_58NOMAGZ.get() == itemstack.getItem()) {
							itemstack.shrink(1);
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack53 = new ItemStack(Ssc14ModItems.MK_58OPEN_NO_MAGZ.get()).copy();
								_setstack53.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack53);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							{
								final String _tagName = "GenEraTE";
								final boolean _tagValue = true;
								CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
							}
							{
								final String _tagName = "BulletChamber";
								final double _tagValue = 0;
								CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
							}
						}
					}
				} else if (Ssc14ModItems.MK_58OPEN.get() == itemstack.getItem()) {
					if (0 < itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("MagazBullets", 0)) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_closed")), SoundSource.MASTER, (float) 1.5,
										(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
							} else {
								_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_closed")), SoundSource.MASTER, (float) 1.5,
										(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
							}
						}
						itemstack.shrink(1);
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack69 = new ItemStack(Ssc14ModItems.MK_58.get()).copy();
							_setstack69.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack69);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							final String _tagName = "GenEraTE";
							final boolean _tagValue = true;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
						}
						{
							final String _tagName = "MagazBullets";
							final double _tagValue = (MagazBullets - 1);
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
						{
							final String _tagName = "BulletChamber";
							final double _tagValue = 1;
							CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
						}
					}
				} else if (Ssc14ModItems.MK_58OPEN_NO_MAGZ.get() == itemstack.getItem()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_closed")), SoundSource.MASTER, (float) 1.5,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("ssc_14:guns_bolt_closed")), SoundSource.MASTER, (float) 1.5,
									(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
						}
					}
					itemstack.shrink(1);
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack85 = new ItemStack(Ssc14ModItems.MK_58NOMAGZ.get()).copy();
						_setstack85.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack85);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						final String _tagName = "GenEraTE";
						final boolean _tagValue = true;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
					}
					{
						final String _tagName = "BulletChamber";
						final double _tagValue = BulletChamber;
						CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putDouble(_tagName, _tagValue));
					}
				}
			}
		}
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