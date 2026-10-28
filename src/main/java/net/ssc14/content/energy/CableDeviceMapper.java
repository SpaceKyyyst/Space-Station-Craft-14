package net.ssc14.content.energy;

import net.ssc14.content.energy.CableType;
import net.ssc14.content.energy.EnergyNetwork;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.block.airlock.AirlockPowerService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/** Maps the existing device attachment volumes without loading neighboring chunks. */
public final class CableDeviceMapper {
    private CableDeviceMapper() { }
    private static boolean isLoaded(LevelAccessor level, BlockPos pos) {
        return !(level instanceof Level world) || world.isLoaded(pos);
    }
    public static void attach(LevelAccessor level, EnergyNetwork network, BlockPos cablePos) {
        network.addCable(cablePos);

        if (network.getType() == CableType.LV) {
            for (int y = 1; y <= 5; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (!isLoaded(level, checkPos)) continue;
                if (level.getBlockState(checkPos).is(Ssc14Blocks.APC.get())) {
                    network.getSources().add(checkPos.immutable());
                    break;
                }
            }

            for (int yOffset = 1; yOffset <= 6; yOffset++) {
                for (int xOffset = -2; xOffset <= 2; xOffset++) {
                    for (int zOffset = -2; zOffset <= 2; zOffset++) {
                        BlockPos checkPos = cablePos.offset(xOffset, yOffset, zOffset);
                        if (!isLoaded(level, checkPos)) continue;
                        BlockState state = level.getBlockState(checkPos);
                        if (state.is(Ssc14Blocks.LAMP.get()) || AirlockPowerService.isDoor(state)) {
                            network.getConsumers().add(checkPos.immutable());
                        }
                    }
                }
            }
        }
        else if (network.getType() == CableType.HV) {
            for (int y = 1; y <= 2; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (!isLoaded(level, checkPos)) continue;
                BlockState state = level.getBlockState(checkPos);
                if (state.is(Ssc14Blocks.DEBU_GGENERATOR.get())) {
                    network.getSources().add(checkPos.immutable());
                } else if (state.is(Ssc14Blocks.PODSTATION.get())) {
                    network.getConsumers().add(checkPos.immutable());
                }
            }
        }
        else if (network.getType() == CableType.MV) {
            for (int y = 1; y <= 2; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (!isLoaded(level, checkPos)) continue;
                if (level.getBlockState(checkPos).is(Ssc14Blocks.PODSTATION.get())) {
                    network.getSources().add(checkPos.immutable());
                }
            }
            for (int y = 1; y <= 5; y++) {
                BlockPos checkPos = cablePos.above(y);
                if (!isLoaded(level, checkPos)) continue;
                if (level.getBlockState(checkPos).is(Ssc14Blocks.APC.get())) {
                    network.getConsumers().add(checkPos.immutable());
                }
            }
        }
    }
}
