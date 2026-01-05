package net.mcreator.ssc.block;

import org.slf4j.Logger;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.BlockUtil;

import net.mcreator.ssc.world.teleporter.SpacedTeleporter;
import net.mcreator.ssc.world.teleporter.SpacedPortalShape;

import javax.annotation.Nullable;

import java.util.Optional;

import com.mojang.logging.LogUtils;

public class SpacedPortalBlock extends NetherPortalBlock {
	private static final Logger LOGGER = LogUtils.getLogger();

	public static void portalSpawn(Level world, BlockPos pos) {
		Optional<SpacedPortalShape> optional = SpacedPortalShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
		if (optional.isPresent()) {
			optional.get().createPortalBlocks(world);
		}
	}

	public SpacedPortalBlock(BlockBehaviour.Properties properties) {
		super(properties.noCollission().randomTicks().pushReaction(PushReaction.BLOCK).strength(-1.0F).sound(SoundType.GLASS).lightLevel(s -> 0).noLootTable());
	}

	private SpacedTeleporter getTeleporter(ServerLevel level) {
		return new SpacedTeleporter(level);
	}

	@Override
	protected BlockState updateShape(BlockState p_54928_, LevelReader p_374413_, ScheduledTickAccess p_374339_, BlockPos p_54932_, Direction p_54929_, BlockPos p_54933_, BlockState p_54930_, RandomSource p_374242_) {
		Direction.Axis direction$axis = p_54929_.getAxis();
		Direction.Axis direction$axis1 = p_54928_.getValue(AXIS);
		boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
		return !flag && !p_54930_.is(this) && !SpacedPortalShape.findAnyShape(p_374413_, p_54932_, direction$axis1).isComplete()
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(p_54928_, p_374413_, p_374339_, p_54932_, p_54929_, p_54933_, p_54930_, p_374242_);
	}

	@Override
	@Nullable
	public TeleportTransition getPortalDestination(ServerLevel p_350444_, Entity p_350334_, BlockPos p_350764_) {
		ResourceKey<Level> resourcekey = p_350444_.dimension() == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("ssc_14:spaced")) ? Level.OVERWORLD : ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("ssc_14:spaced"));
		ServerLevel serverlevel = p_350444_.getServer().getLevel(resourcekey);
		if (serverlevel == null) {
			return null;
		} else {
			boolean flag = serverlevel.dimension() == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("ssc_14:spaced"));
			WorldBorder worldborder = serverlevel.getWorldBorder();
			double d0 = DimensionType.getTeleportationScale(p_350444_.dimensionType(), serverlevel.dimensionType());
			BlockPos blockpos = worldborder.clampToBounds(p_350334_.getX() * d0, p_350334_.getY(), p_350334_.getZ() * d0);
			return this.getExitPortal(serverlevel, p_350334_, p_350764_, blockpos, flag, worldborder);
		}
	}

	@Nullable
	private TeleportTransition getExitPortal(ServerLevel p_350564_, Entity p_350493_, BlockPos p_350379_, BlockPos p_350747_, boolean p_350326_, WorldBorder p_350718_) {
		Optional<BlockPos> optional = getTeleporter(p_350564_).findClosestPortalPosition(p_350747_, p_350326_, p_350718_);
		BlockUtil.FoundRectangle blockutil$foundrectangle;
		TeleportTransition.PostTeleportTransition teleporttransition$postteleporttransition;
		if (optional.isPresent()) {
			BlockPos blockpos = optional.get();
			BlockState blockstate = p_350564_.getBlockState(blockpos);
			blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(blockpos, blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, p_351970_ -> p_350564_.getBlockState(p_351970_) == blockstate);
			teleporttransition$postteleporttransition = TeleportTransition.PLAY_PORTAL_SOUND.then(p_351967_ -> p_351967_.placePortalTicket(blockpos));
		} else {
			Direction.Axis direction$axis = p_350493_.level().getBlockState(p_350379_).getOptionalValue(AXIS).orElse(Direction.Axis.X);
			Optional<BlockUtil.FoundRectangle> optional1 = getTeleporter(p_350564_).createPortal(p_350747_, direction$axis);
			if (optional1.isEmpty()) {
				LOGGER.error("Unable to create a portal, likely target out of worldborder");
				return null;
			}
			blockutil$foundrectangle = optional1.get();
			teleporttransition$postteleporttransition = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
		}
		return getDimensionTransitionFromExit(p_350493_, p_350379_, blockutil$foundrectangle, p_350564_, teleporttransition$postteleporttransition);
	}

	private static TeleportTransition getDimensionTransitionFromExit(Entity p_350906_, BlockPos p_350376_, BlockUtil.FoundRectangle p_350428_, ServerLevel p_350928_, TeleportTransition.PostTeleportTransition p_379530_) {
		BlockState blockstate = p_350906_.level().getBlockState(p_350376_);
		Direction.Axis direction$axis;
		Vec3 vec3;
		if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
			direction$axis = blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
			BlockUtil.FoundRectangle blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(p_350376_, direction$axis, 21, Direction.Axis.Y, 21, p_351016_ -> p_350906_.level().getBlockState(p_351016_) == blockstate);
			vec3 = p_350906_.getRelativePortalPosition(direction$axis, blockutil$foundrectangle);
		} else {
			direction$axis = Direction.Axis.X;
			vec3 = new Vec3(0.5, 0.0, 0.0);
		}
		return createDimensionTransition(p_350928_, p_350428_, direction$axis, vec3, p_350906_, p_379530_);
	}

	private static TeleportTransition createDimensionTransition(ServerLevel p_350955_, BlockUtil.FoundRectangle p_350865_, Direction.Axis p_351013_, Vec3 p_351020_, Entity p_350578_, TeleportTransition.PostTeleportTransition p_379531_) {
		BlockPos blockpos = p_350865_.minCorner;
		BlockState blockstate = p_350955_.getBlockState(blockpos);
		Direction.Axis direction$axis = blockstate.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
		double d0 = p_350865_.axis1Size;
		double d1 = p_350865_.axis2Size;
		EntityDimensions entitydimensions = p_350578_.getDimensions(p_350578_.getPose());
		int i = p_351013_ == direction$axis ? 0 : 90;
		double d2 = entitydimensions.width() / 2.0 + (d0 - entitydimensions.width()) * p_351020_.x();
		double d3 = (d1 - entitydimensions.height()) * p_351020_.y();
		double d4 = 0.5 + p_351020_.z();
		boolean flag = direction$axis == Direction.Axis.X;
		Vec3 vec3 = new Vec3(blockpos.getX() + (flag ? d2 : d4), blockpos.getY() + d3, blockpos.getZ() + (flag ? d4 : d2));
		Vec3 vec31 = SpacedPortalShape.findCollisionFreePosition(vec3, p_350955_, p_350578_, entitydimensions);
		return new TeleportTransition(p_350955_, vec31, Vec3.ZERO, i, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), p_379531_);
	}

	@Override
	public int getPortalTransitionTime(ServerLevel world, Entity entity) {
		return 0;
	}

	@Override
	public Portal.Transition getLocalTransition() {
		return Portal.Transition.NONE;
	}

	@Override
	public void randomTick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		for (int i = 0; i < 4; i++) {
			double px = pos.getX() + random.nextFloat();
			double py = pos.getY() + random.nextFloat();
			double pz = pos.getZ() + random.nextFloat();
			double vx = (random.nextFloat() - 0.5) / 2.;
			double vy = (random.nextFloat() - 0.5) / 2.;
			double vz = (random.nextFloat() - 0.5) / 2.;
			int j = random.nextInt(4) - 1;
			if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
				px = pos.getX() + 0.5 + 0.25 * j;
				vx = random.nextFloat() * 2 * j;
			} else {
				pz = pos.getZ() + 0.5 + 0.25 * j;
				vz = random.nextFloat() * 2 * j;
			}
			world.addParticle(ParticleTypes.ASH, px, py, pz, vx, vy, vz);
		}
	}
}