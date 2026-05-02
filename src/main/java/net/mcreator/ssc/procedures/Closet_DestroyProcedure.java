package net.mcreator.ssc.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModBlocks;

import java.util.Comparator;

public class Closet_DestroyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(ResourceLocation.parse("ssc14:closets")))) {
			{
				BlockPos _pos = BlockPos.containing(x, y - 1, z);
				Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y - 0.5, z), null);
				world.destroyBlock(_pos, false);
			}
		} else if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Ssc14ModBlocks.UP_CLOSET_PLUG.get()) {
			world.setBlock(BlockPos.containing(x, y + 1, z), Blocks.AIR.defaultBlockState(), 3);
		} // 🔄 Разморозка игроков и лок-сущностей при открытии
		final Vec3 _center = new Vec3((x + 0.5), (y + 0.4), (z + 0.5));
		for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(0.4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
			// ✅ Игрок ИЛИ сущность с тегом "можно запереть"
			if (entityiterator instanceof Player player || entityiterator.getType().is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, ResourceLocation.parse("ssc14:lockableentitys")))) {
				if (entityiterator instanceof Player player) {
					player.setDeltaMovement(Vec3.ZERO);
					player.fallDistance = 0;
					player.setOnGround(true);
					player.setInvisible(false);
				} else {
					// Для сущностей с тегом: просто снимаем невидимость и сбрасываем движение
					entityiterator.setDeltaMovement(Vec3.ZERO);
					entityiterator.setInvisible(false);
				}
				if (world instanceof ServerLevel _level) {
				}
			}
		}
	}
}