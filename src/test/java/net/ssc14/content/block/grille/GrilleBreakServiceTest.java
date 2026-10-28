package net.ssc14.content.block.grille;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.Ssc14Blocks;
import org.junit.jupiter.api.Test;

class GrilleBreakServiceTest {
    @Test
    void writesDefaultBrokenStateThenAppliesSuppliedFacing() {
        BlockPos pos = new BlockPos(4, 5, 6);
        List<BlockState> writes = new ArrayList<>();
        AtomicReference<BlockState> current = new AtomicReference<>();
        LevelAccessor level = (LevelAccessor) Proxy.newProxyInstance(
                LevelAccessor.class.getClassLoader(),
                new Class<?>[] {LevelAccessor.class},
                (proxy, method, arguments) -> switch (method.getName()) {
                    case "setBlock" -> {
                        BlockState state = (BlockState) arguments[1];
                        writes.add(state);
                        current.set(state);
                        yield true;
                    }
                    case "getBlockState" -> current.get();
                    case "toString" -> "recording-level";
                    default -> throw new UnsupportedOperationException(method.toString());
                });

        GrilleBreakService.replaceWithBroken(level, pos, () -> Direction.UP);

        assertEquals(2, writes.size());
        assertSame(Ssc14Blocks.BROKEN_GRILLE.get(), writes.get(0).getBlock());
        assertEquals(Direction.NORTH, writes.get(0).getValue(BrokenGrilleBlock.FACING));
        assertSame(Ssc14Blocks.BROKEN_GRILLE.get(), writes.get(1).getBlock());
        assertEquals(Direction.UP, writes.get(1).getValue(BrokenGrilleBlock.FACING));
    }
}
