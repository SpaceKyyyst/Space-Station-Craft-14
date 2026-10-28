package net.ssc14.content.rotation;

import java.util.Set;
import net.ssc14.content.block.Ssc14Blocks;
import net.minecraft.world.level.block.Block;

/** Explicit audit boundary for the command's one-state, no-neighbor-callback update contract. */
final class StateOnlyRotationEligibility {
    private StateOnlyRotationEligibility() { }

    static boolean supports(Block block) { return AuditedBlocks.BLOCKS.contains(block); }

    private static final class AuditedBlocks {
        // Exact registered instances, never classes or a second extensible datapack tag.
        // New entries require a state-only safety audit and a live rotation regression.
        // Multipart closets need a dedicated transactional adapter and remain excluded.
        private static final Set<Block> BLOCKS = Set.of(
                Ssc14Blocks.MACHINE_FRAME.get(), Ssc14Blocks.COMPUTER.get(),
                Ssc14Blocks.OFICE_BLACK_CHAIR.get(), Ssc14Blocks.OFICE_WHITE_CHAIR.get(),
                Ssc14Blocks.WOOD_CHAIR.get(), Ssc14Blocks.STEEL_CHAIR.get(),
                Ssc14Blocks.BAR_CHAIR.get(), Ssc14Blocks.CONSOLE_OF_ID.get(),
                Ssc14Blocks.PODSTATION.get(), Ssc14Blocks.SMES.get(),
                Ssc14Blocks.PORTABLE_SHEATER.get());
    }
}
