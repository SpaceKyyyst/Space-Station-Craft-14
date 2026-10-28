package net.ssc14.content.menu.pda;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.ssc14.integration.neoforge.server.ServerWorkScheduler;

public final class PdaFlashlightService {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (!(world instanceof net.minecraft.server.level.ServerLevel serverOwner)) return;
		if (entity == null)
			return;
		if (false == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("flashlight", false)) {
			{
				final String _tagName = "flashlight";
				final boolean _tagValue = true;
				CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
			}
		} else {
			{
				final String _tagName = "flashlight";
				final boolean _tagValue = false;
				CustomData.update(DataComponents.CUSTOM_DATA, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY), tag -> tag.putBoolean(_tagName, _tagValue));
			}
			if (Blocks.LIGHT == (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()))).getBlock()) {
				world.setBlock(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), Blocks.AIR.defaultBlockState(), 3);
				ServerWorkScheduler.schedule(serverOwner, 1, () -> {
					if (Blocks.LIGHT == (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()))).getBlock()) {
						world.setBlock(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), Blocks.AIR.defaultBlockState(), 3);
					}
				});
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ssc_14:items_flashlight_pda")), SoundSource.PLAYERS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("ssc_14:items_flashlight_pda")), SoundSource.PLAYERS, 1, 1, false);
			}
		}
	}
}
