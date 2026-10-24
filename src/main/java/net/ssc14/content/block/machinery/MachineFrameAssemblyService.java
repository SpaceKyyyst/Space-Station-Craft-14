package net.ssc14.content.block.machinery;

import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

/** Canonical transition from an empty frame to its assembled inventory stage. */
public final class MachineFrameAssemblyService {
    private static volatile AssemblyParts parts;

    private MachineFrameAssemblyService() { }

    public static void install(Supplier<? extends Item> cable,
            Supplier<? extends Block> assembledFrame) {
        parts = new AssemblyParts(Objects.requireNonNull(cable, "cable"),
                Objects.requireNonNull(assembledFrame, "assembledFrame"));
    }

    public static boolean assemble(LevelAccessor level, BlockPos pos, Entity entity) {
        AssemblyParts configured = parts;
        if (configured == null || !(entity instanceof LivingEntity living)) {
            return false;
        }
        ItemStack held = living.getMainHandItem();
        if (!held.is(configured.cable().get())) {
            return false;
        }

        BlockState source = level.getBlockState(pos);
        BlockState target = copySharedProperties(source,
                configured.assembledFrame().get().defaultBlockState());
        held.shrink(1);
        return level.setBlock(pos, target, Block.UPDATE_ALL);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static BlockState copySharedProperties(BlockState source, BlockState target) {
        BlockState copied = target;
        for (Property<?> sourceProperty : source.getProperties()) {
            Property targetProperty = copied.getBlock().getStateDefinition()
                    .getProperty(sourceProperty.getName());
            if (targetProperty != null) {
                try {
                    copied = copied.setValue(targetProperty, source.getValue(sourceProperty));
                } catch (IllegalArgumentException ignored) {
                    // Same-name properties with incompatible domains are intentionally skipped.
                }
            }
        }
        return copied;
    }

    private record AssemblyParts(Supplier<? extends Item> cable,
            Supplier<? extends Block> assembledFrame) { }
}
