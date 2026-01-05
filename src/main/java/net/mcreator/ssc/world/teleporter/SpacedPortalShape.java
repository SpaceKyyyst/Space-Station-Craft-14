package net.mcreator.ssc.world.teleporter;

import org.apache.commons.lang3.mutable.MutableInt;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.BlockUtil;

import net.mcreator.ssc.init.Ssc14ModBlocks;

import javax.annotation.Nullable;

import java.util.function.Predicate;
import java.util.Optional;

public class SpacedPortalShape {
	private static final int MIN_WIDTH = 2;
	public static final int MAX_WIDTH = 21;
	private static final int MIN_HEIGHT = 3;
	public static final int MAX_HEIGHT = 21;
	private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) -> state.getBlock() == Ssc14ModBlocks.VOIDB.get();
	private static final float SAFE_TRAVEL_MAX_ENTITY_XY = 4.0F;
	private static final double SAFE_TRAVEL_MAX_VERTICAL_DELTA = 1.0;
	private final Direction.Axis axis;
	private final Direction rightDir;
	private final int numPortalBlocks;
	private final BlockPos bottomLeft;
	private final int height;
	private final int width;

	public SpacedPortalShape(Direction.Axis p_77697_, int p_374222_, Direction p_374407_, BlockPos p_77696_, int p_374218_, int p_374477_) {
		this.axis = p_77697_;
		this.numPortalBlocks = p_374222_;
		this.rightDir = p_374407_;
		this.bottomLeft = p_77696_;
		this.width = p_374218_;
		this.height = p_374477_;
	}

	public static Optional<SpacedPortalShape> findEmptyPortalShape(LevelAccessor p_77709_, BlockPos p_77710_, Direction.Axis p_77711_) {
		return findPortalShape(p_77709_, p_77710_, p_77727_ -> p_77727_.isValid() && p_77727_.numPortalBlocks == 0, p_77711_);
	}

	public static Optional<SpacedPortalShape> findPortalShape(LevelAccessor p_77713_, BlockPos p_77714_, Predicate<SpacedPortalShape> p_77715_, Direction.Axis p_77716_) {
		Optional<SpacedPortalShape> optional = Optional.of(findAnyShape(p_77713_, p_77714_, p_77716_)).filter(p_77715_);
		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis direction$axis = p_77716_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
			return Optional.of(findAnyShape(p_77713_, p_77714_, direction$axis)).filter(p_77715_);
		}
	}

	public static SpacedPortalShape findAnyShape(BlockGetter p_374054_, BlockPos p_374346_, Direction.Axis p_374516_) {
		Direction direction = p_374516_ == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos blockpos = calculateBottomLeft(p_374054_, direction, p_374346_);
		if (blockpos == null) {
			return new SpacedPortalShape(p_374516_, 0, direction, p_374346_, 0, 0);
		} else {
			int i = calculateWidth(p_374054_, blockpos, direction);
			if (i == 0) {
				return new SpacedPortalShape(p_374516_, 0, direction, blockpos, 0, 0);
			} else {
				MutableInt mutableint = new MutableInt();
				int j = calculateHeight(p_374054_, blockpos, direction, i, mutableint);
				return new SpacedPortalShape(p_374516_, mutableint.getValue(), direction, blockpos, i, j);
			}
		}
	}

	@Nullable
	private static BlockPos calculateBottomLeft(BlockGetter p_374347_, Direction p_374365_, BlockPos p_77734_) {
		int i = Math.max(p_374347_.getMinY(), p_77734_.getY() - 21);
		while (p_77734_.getY() > i && isEmpty(p_374347_.getBlockState(p_77734_.below()))) {
			p_77734_ = p_77734_.below();
		}
		Direction direction = p_374365_.getOpposite();
		int j = getDistanceUntilEdgeAboveFrame(p_374347_, p_77734_, direction) - 1;
		return j < 0 ? null : p_77734_.relative(direction, j);
	}

	private static int calculateWidth(BlockGetter p_374528_, BlockPos p_374039_, Direction p_374180_) {
		int i = getDistanceUntilEdgeAboveFrame(p_374528_, p_374039_, p_374180_);
		return i >= 2 && i <= 21 ? i : 0;
	}

	private static int getDistanceUntilEdgeAboveFrame(BlockGetter p_374084_, BlockPos p_77736_, Direction p_77737_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		for (int i = 0; i <= 21; i++) {
			blockpos$mutableblockpos.set(p_77736_).move(p_77737_, i);
			BlockState blockstate = p_374084_.getBlockState(blockpos$mutableblockpos);
			if (!isEmpty(blockstate)) {
				if (FRAME.test(blockstate, p_374084_, blockpos$mutableblockpos)) {
					return i;
				}
				break;
			}
			BlockState blockstate1 = p_374084_.getBlockState(blockpos$mutableblockpos.move(Direction.DOWN));
			if (!FRAME.test(blockstate1, p_374084_, blockpos$mutableblockpos)) {
				break;
			}
		}
		return 0;
	}

	private static int calculateHeight(BlockGetter p_374198_, BlockPos p_374414_, Direction p_374486_, int p_374126_, MutableInt p_374165_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		int i = getDistanceUntilTop(p_374198_, p_374414_, p_374486_, blockpos$mutableblockpos, p_374126_, p_374165_);
		return i >= 3 && i <= 21 && hasTopFrame(p_374198_, p_374414_, p_374486_, blockpos$mutableblockpos, p_374126_, i) ? i : 0;
	}

	private static boolean hasTopFrame(BlockGetter p_374223_, BlockPos p_374398_, Direction p_374129_, BlockPos.MutableBlockPos p_77731_, int p_77732_, int p_374112_) {
		for (int i = 0; i < p_77732_; i++) {
			BlockPos.MutableBlockPos blockpos$mutableblockpos = p_77731_.set(p_374398_).move(Direction.UP, p_374112_).move(p_374129_, i);
			if (!FRAME.test(p_374223_.getBlockState(blockpos$mutableblockpos), p_374223_, blockpos$mutableblockpos)) {
				return false;
			}
		}
		return true;
	}

	private static int getDistanceUntilTop(BlockGetter p_374443_, BlockPos p_374231_, Direction p_374062_, BlockPos.MutableBlockPos p_77729_, int p_374313_, MutableInt p_374330_) {
		for (int i = 0; i < 21; i++) {
			p_77729_.set(p_374231_).move(Direction.UP, i).move(p_374062_, -1);
			if (!FRAME.test(p_374443_.getBlockState(p_77729_), p_374443_, p_77729_)) {
				return i;
			}
			p_77729_.set(p_374231_).move(Direction.UP, i).move(p_374062_, p_374313_);
			if (!FRAME.test(p_374443_.getBlockState(p_77729_), p_374443_, p_77729_)) {
				return i;
			}
			for (int j = 0; j < p_374313_; j++) {
				p_77729_.set(p_374231_).move(Direction.UP, i).move(p_374062_, j);
				BlockState blockstate = p_374443_.getBlockState(p_77729_);
				if (!isEmpty(blockstate)) {
					return i;
				}
				if (blockstate.getBlock() == Ssc14ModBlocks.SPACED_PORTAL.get()) {
					p_374330_.increment();
				}
			}
		}
		return 21;
	}

	private static boolean isEmpty(BlockState p_77718_) {
		return p_77718_.isAir() || p_77718_.getBlock() == Ssc14ModBlocks.SPACED_PORTAL.get();
	}

	public boolean isValid() {
		return this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
	}

	public void createPortalBlocks(LevelAccessor p_374419_) {
		BlockState blockstate = Ssc14ModBlocks.SPACED_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
		BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach(p_374024_ -> p_374419_.setBlock(p_374024_, blockstate, 18));
	}

	public boolean isComplete() {
		return this.isValid() && this.numPortalBlocks == this.width * this.height;
	}

	public static Vec3 getRelativePosition(BlockUtil.FoundRectangle p_77739_, Direction.Axis p_77740_, Vec3 p_77741_, EntityDimensions p_77742_) {
		double d0 = (double) p_77739_.axis1Size - p_77742_.width();
		double d1 = (double) p_77739_.axis2Size - p_77742_.height();
		BlockPos blockpos = p_77739_.minCorner;
		double d2;
		if (d0 > 0.0) {
			double d3 = blockpos.get(p_77740_) + p_77742_.width() / 2.0;
			d2 = Mth.clamp(Mth.inverseLerp(p_77741_.get(p_77740_) - d3, 0.0, d0), 0.0, 1.0);
		} else {
			d2 = 0.5;
		}
		double d5;
		if (d1 > 0.0) {
			Direction.Axis direction$axis = Direction.Axis.Y;
			d5 = Mth.clamp(Mth.inverseLerp(p_77741_.get(direction$axis) - blockpos.get(direction$axis), 0.0, d1), 0.0, 1.0);
		} else {
			d5 = 0.0;
		}
		Direction.Axis direction$axis1 = p_77740_ == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
		double d4 = p_77741_.get(direction$axis1) - (blockpos.get(direction$axis1) + 0.5);
		return new Vec3(d2, d5, d4);
	}

	public static Vec3 findCollisionFreePosition(Vec3 p_260315_, ServerLevel p_259704_, Entity p_259626_, EntityDimensions p_259816_) {
		if (!(p_259816_.width() > 4.0F) && !(p_259816_.height() > 4.0F)) {
			double d0 = p_259816_.height() / 2.0;
			Vec3 vec3 = p_260315_.add(0.0, d0, 0.0);
			VoxelShape voxelshape = Shapes.create(AABB.ofSize(vec3, p_259816_.width(), 0.0, p_259816_.width()).expandTowards(0.0, 1.0, 0.0).inflate(1.0E-6));
			Optional<Vec3> optional = p_259704_.findFreePosition(p_259626_, voxelshape, vec3, p_259816_.width(), p_259816_.height(), p_259816_.width());
			Optional<Vec3> optional1 = optional.map(p_259019_ -> p_259019_.subtract(0.0, d0, 0.0));
			return optional1.orElse(p_260315_);
		} else {
			return p_260315_;
		}
	}
}