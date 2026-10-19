package net.mcreator.ssc.rotation;

import java.util.Set;
import net.mcreator.ssc.init.Ssc14ModBlocks;
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
                Ssc14ModBlocks.MACHINE_FRAME.get(), Ssc14ModBlocks.COMPUTER.get(),
                Ssc14ModBlocks.OFICE_BLACK_CHAIR.get(), Ssc14ModBlocks.OFICE_WHITE_CHAIR.get(),
                Ssc14ModBlocks.WOOD_CHAIR.get(), Ssc14ModBlocks.STEEL_CHAIR.get(),
                Ssc14ModBlocks.BAR_CHAIR.get(), Ssc14ModBlocks.CONSOLE_OF_ID.get(),
                Ssc14ModBlocks.PODSTATION.get(), Ssc14ModBlocks.SMES.get(),
                Ssc14ModBlocks.PORTABLE_SHEATER.get());
    }
}
