package net.mcreator.ssc.rotation;

import java.util.Optional;
import net.mcreator.ssc.block.ClosetBlock;
import net.mcreator.ssc.block.ClosetSecureBlock;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

/** Pure state transition: no world access, authorization, or mutation. */
public final class RotationPolicy {
    private static final TagKey<Block> ROTATABLE = BlockTags.create(Identifier.parse("ssc14:rotatable"));

    private RotationPolicy() { }

    public static Optional<BlockState> rotate(BlockState state) {
        // Both multipart blocks align an upper plug from onPlace. A one-target
        // state change would leave the pair inconsistent; require a dedicated adapter.
        if (state.getBlock() instanceof ClosetBlock || state.getBlock() instanceof ClosetSecureBlock) return Optional.empty();
        return state.is(ROTATABLE) ? rotateOrientation(state) : Optional.empty();
    }

    static Optional<BlockState> rotateOrientation(BlockState state) {
        var facing = state.getBlock().getStateDefinition().getProperty("facing");
        if (facing instanceof EnumProperty<?> && state.getValue(facing) instanceof Direction direction) {
            if (!direction.getAxis().isHorizontal()) return Optional.empty();
            return withValue(state, facing, direction.getClockWise().getSerializedName());
        }
        var axis = state.getBlock().getStateDefinition().getProperty("axis");
        if (axis instanceof EnumProperty<?> && state.getValue(axis) instanceof Direction.Axis directionAxis) {
            return switch (directionAxis) {
                case X -> withValue(state, axis, "z");
                case Z -> withValue(state, axis, "x");
                case Y -> Optional.empty();
            };
        }
        return Optional.empty();
    }

    private static <T extends Comparable<T>> Optional<BlockState> withValue(BlockState state, Property<T> property, String value) {
        return property.getValue(value).map(next -> state.setValue(property, next)).filter(next -> next != state);
    }
}
