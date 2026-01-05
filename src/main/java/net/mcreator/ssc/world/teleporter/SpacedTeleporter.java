package net.mcreator.ssc.world.teleporter;

import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.BlockUtil;

import net.mcreator.ssc.init.Ssc14ModBlocks;

import java.util.Optional;
import java.util.Comparator;

import com.google.common.collect.ImmutableSet;

@EventBusSubscriber
public class SpacedTeleporter {
	public static Holder<PoiType> poi = null;

	@SubscribeEvent
	public static void registerPointOfInterest(RegisterEvent event) {
		event.register(Registries.POINT_OF_INTEREST_TYPE, registerHelper -> {
			PoiType poiType = new PoiType(ImmutableSet.copyOf(Ssc14ModBlocks.SPACED_PORTAL.get().getStateDefinition().getPossibleStates()), 0, 1);
			registerHelper.register(ResourceLocation.parse("ssc_14:spaced_portal"), poiType);
			poi = BuiltInRegistries.POINT_OF_INTEREST_TYPE.wrapAsHolder(poiType);
		});
	}

	private final ServerLevel level;

	public SpacedTeleporter(ServerLevel level) {
		this.level = level;
	}

	public Optional<BlockPos> findClosestPortalPosition(BlockPos p_352378_, boolean p_352309_, WorldBorder p_352374_) {
		PoiManager poimanager = this.level.getPoiManager();
		int i = p_352309_ ? 16 : 128;
		poimanager.ensureLoadedAndValid(this.level, p_352378_, i);
		return poimanager.getInSquare(p_230634_ -> p_230634_.is(poi.unwrapKey().get()), p_352378_, i, PoiManager.Occupancy.ANY).map(PoiRecord::getPos).filter(p_352374_::isWithinBounds)
				.filter(p_352047_ -> this.level.getBlockState(p_352047_).hasProperty(BlockStateProperties.HORIZONTAL_AXIS)).min(Comparator.<BlockPos>comparingDouble(p_352046_ -> p_352046_.distSqr(p_352378_)).thenComparingInt(Vec3i::getY));
	}

	public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos p_77667_, Direction.Axis p_77668_) {
		Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, p_77668_);
		double d0 = -1.0;
		BlockPos blockpos = null;
		double d1 = -1.0;
		BlockPos blockpos1 = null;
		WorldBorder worldborder = this.level.getWorldBorder();
		int i = Math.min(this.level.getMaxY(), this.level.getMinY() + this.level.getLogicalHeight() - 1);
		int j = 1;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_77667_.mutable();
		for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.spiralAround(p_77667_, 16, Direction.EAST, Direction.SOUTH)) {
			int k = Math.min(i, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getZ()));
			if (worldborder.isWithinBounds(blockpos$mutableblockpos1) && worldborder.isWithinBounds(blockpos$mutableblockpos1.move(direction, 1))) {
				blockpos$mutableblockpos1.move(direction.getOpposite(), 1);
				for (int l = k; l >= this.level.getMinY(); l--) {
					blockpos$mutableblockpos1.setY(l);
					if (this.canPortalReplaceBlock(blockpos$mutableblockpos1)) {
						int i1 = l;
						while (l > this.level.getMinY() && this.canPortalReplaceBlock(blockpos$mutableblockpos1.move(Direction.DOWN))) {
							l--;
						}
						if (l + 4 <= i) {
							int j1 = i1 - l;
							if (j1 <= 0 || j1 >= 3) {
								blockpos$mutableblockpos1.setY(l);
								if (this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 0)) {
									double d2 = p_77667_.distSqr(blockpos$mutableblockpos1);
									if (this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, -1) && this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 1) && (d0 == -1.0 || d0 > d2)) {
										d0 = d2;
										blockpos = blockpos$mutableblockpos1.immutable();
									}
									if (d0 == -1.0 && (d1 == -1.0 || d1 > d2)) {
										d1 = d2;
										blockpos1 = blockpos$mutableblockpos1.immutable();
									}
								}
							}
						}
					}
				}
			}
		}
		if (d0 == -1.0 && d1 != -1.0) {
			blockpos = blockpos1;
			d0 = d1;
		}
		if (d0 == -1.0) {
			int k1 = Math.max(this.level.getMinY() - -1, 70);
			int i2 = i - 9;
			if (i2 < k1) {
				return Optional.empty();
			}
			blockpos = new BlockPos(p_77667_.getX() - direction.getStepX() * 1, Mth.clamp(p_77667_.getY(), k1, i2), p_77667_.getZ() - direction.getStepZ() * 1).immutable();
			blockpos = worldborder.clampToBounds(blockpos);
			Direction direction1 = direction.getClockWise();
			for (int i3 = -1; i3 < 2; i3++) {
				for (int j3 = 0; j3 < 2; j3++) {
					for (int k3 = -1; k3 < 3; k3++) {
						BlockState blockstate1 = k3 < 0 ? Ssc14ModBlocks.VOIDB.get().defaultBlockState() : Blocks.AIR.defaultBlockState();
						blockpos$mutableblockpos.setWithOffset(blockpos, j3 * direction.getStepX() + i3 * direction1.getStepX(), k3, j3 * direction.getStepZ() + i3 * direction1.getStepZ());
						this.level.setBlockAndUpdate(blockpos$mutableblockpos, blockstate1);
					}
				}
			}
		}
		for (int l1 = -1; l1 < 3; l1++) {
			for (int j2 = -1; j2 < 4; j2++) {
				if (l1 == -1 || l1 == 2 || j2 == -1 || j2 == 3) {
					blockpos$mutableblockpos.setWithOffset(blockpos, l1 * direction.getStepX(), j2, l1 * direction.getStepZ());
					this.level.setBlock(blockpos$mutableblockpos, Ssc14ModBlocks.VOIDB.get().defaultBlockState(), 3);
				}
			}
		}
		BlockState blockstate = Ssc14ModBlocks.SPACED_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, p_77668_);
		for (int k2 = 0; k2 < 2; k2++) {
			for (int l2 = 0; l2 < 3; l2++) {
				blockpos$mutableblockpos.setWithOffset(blockpos, k2 * direction.getStepX(), l2, k2 * direction.getStepZ());
				this.level.setBlock(blockpos$mutableblockpos, blockstate, 18);
				this.level.getPoiManager().add(blockpos$mutableblockpos, poi);
			}
		}
		return Optional.of(new BlockUtil.FoundRectangle(blockpos.immutable(), 2, 3));
	}

	private boolean canHostFrame(BlockPos p_77662_, BlockPos.MutableBlockPos p_77663_, Direction p_77664_, int p_77665_) {
		Direction direction = p_77664_.getClockWise();
		for (int i = -1; i < 3; i++) {
			for (int j = -1; j < 4; j++) {
				p_77663_.setWithOffset(p_77662_, p_77664_.getStepX() * i + direction.getStepX() * p_77665_, j, p_77664_.getStepZ() * i + direction.getStepZ() * p_77665_);
				if (j < 0 && !this.level.getBlockState(p_77663_).isSolid()) {
					return false;
				}
				if (j >= 0 && !this.canPortalReplaceBlock(p_77663_)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean canPortalReplaceBlock(BlockPos.MutableBlockPos pos) {
		BlockState blockstate = this.level.getBlockState(pos);
		return blockstate.canBeReplaced() && blockstate.getFluidState().isEmpty();
	}
}