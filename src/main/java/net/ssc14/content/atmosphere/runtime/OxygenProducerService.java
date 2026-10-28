package net.ssc14.content.atmosphere.runtime;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.ssc14.common.atmosphere.model.GasType;
import net.ssc14.integration.neoforge.atmosphere.AtmosCell;
public final class OxygenProducerService {
 private static final float MAX_MOLES_PER_CELL=1000f, PRODUCTION_RATE=100f;
 private OxygenProducerService() { }
 public static void tick(ServerLevel level, BlockPos position){AtmosCell cell=AtmosphereManager.get(level).getCellAt(position);if(cell==null)return;float capacity=Math.max(0f,MAX_MOLES_PER_CELL-cell.getTotalMoles());cell.addMoles(GasType.OXYGEN.ordinal(),Math.min(PRODUCTION_RATE,capacity));}
}
