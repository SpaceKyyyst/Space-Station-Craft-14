package net.ssc14.content.item.poster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.ssc14.content.entity.PosterEntity;
import net.ssc14.content.entity.Ssc14EntityTypes;

/** Server-authoritative poster placement behavior. */
public final class PosterPlacementService {
    private static final double WALL_OFFSET = 0.465D;
    private static final double POSTER_THICKNESS = 0.03D;
    private static final double POSTER_HALF_WIDTH = 0.5D;
    private static final double POSTER_HEIGHT = 1.0D;
    private static final double LOW_SNAP_THRESHOLD = 0.25D;
    private static final double HIGH_SNAP_THRESHOLD = 0.75D;
    private static final double CENTER_OFFSET = 0.5D;

    private PosterPlacementService() { }

    public static boolean place(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        if (!(stack.getItem() instanceof PosterItem posterItem)) {
            return false;
        }
        return place(context, posterItem.posterType());
    }

    public static boolean place(UseOnContext context, String posterType) {
        Level level = context.getLevel();
        if (level.isClientSide()) {
            return false;
        }

        Direction clickedFace = context.getClickedFace();
        Player player = context.getPlayer();
        if (player == null || clickedFace.getAxis().isVertical()) {
            return false;
        }

        BlockPos clickedPos = context.getClickedPos();
        BlockPos spawnPos = clickedPos.relative(clickedFace);
        Vec3 clickLocation = context.getClickLocation();
        double hitX = normalizeHitCoordinate(clickLocation.x - clickedPos.getX());
        double hitY = clickLocation.y - clickedPos.getY();
        double hitZ = normalizeHitCoordinate(clickLocation.z - clickedPos.getZ());

        double x = spawnPos.getX() + CENTER_OFFSET - clickedFace.getStepX() * WALL_OFFSET;
        double y = spawnPos.getY() + smartOffset(hitY);
        double z = spawnPos.getZ() + CENTER_OFFSET - clickedFace.getStepZ() * WALL_OFFSET;
        if (clickedFace.getAxis() == Direction.Axis.Z) {
            x = spawnPos.getX() + smartOffset(hitX);
        } else if (clickedFace.getAxis() == Direction.Axis.X) {
            z = spawnPos.getZ() + smartOffset(hitZ);
        }

        PosterEntity poster = new PosterEntity(Ssc14EntityTypes.POSTER.get(), level);
        poster.absSnapTo(x, y, z, 0.0F, 0.0F);
        poster.setFacingDirection(clickedFace);
        poster.setPosterType(posterType);
        poster.setBoundingBox(boundingBox(clickedFace, x, y, z));

        level.playSound(null, BlockPos.containing(x, y, z),
                SoundEvents.PAINTING_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.addFreshEntity(poster);
        if (!player.getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }
        return true;
    }

    static double smartOffset(double hitValue) {
        if (hitValue <= LOW_SNAP_THRESHOLD) {
            return 0.0D;
        }
        if (hitValue > HIGH_SNAP_THRESHOLD) {
            return 1.0D;
        }
        return CENTER_OFFSET;
    }

    private static double normalizeHitCoordinate(double coordinate) {
        return coordinate != 0.0D && coordinate < 0.0D ? coordinate + 1.0D : coordinate;
    }

    private static AABB boundingBox(Direction face, double x, double y, double z) {
        double xRadius = face.getAxis() == Direction.Axis.Z ? POSTER_HALF_WIDTH : POSTER_THICKNESS;
        double zRadius = face.getAxis() == Direction.Axis.X ? POSTER_HALF_WIDTH : POSTER_THICKNESS;
        return new AABB(x - xRadius, y, z - zRadius, x + xRadius, y + POSTER_HEIGHT, z + zRadius);
    }
}
