
package net.mcreator.ssc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PalletSavedData {
	private static final Map<ResourceKey<Level>, List<BlockPos>> DIMENSION_PALLETS = new ConcurrentHashMap<>();

	private static ResourceKey<Level> getDimensionKey(LevelAccessor world) {
		if (world instanceof net.minecraft.world.level.Level level) {
			return level.dimension();
		}
		return null;
	}

	public static PalletSavedData get(LevelAccessor world) {
		return new PalletSavedData(world);
	}

	private final ResourceKey<Level> dimKey;

	private PalletSavedData(LevelAccessor world) {
		this.dimKey = getDimensionKey(world);
		if (this.dimKey != null) {
			DIMENSION_PALLETS.computeIfAbsent(this.dimKey, k -> new ArrayList<>());
		}
	}

	public void addPallet(BlockPos pos) {
		if (this.dimKey == null) return;
		List<BlockPos> list = DIMENSION_PALLETS.get(this.dimKey);
		if (list != null && !list.contains(pos)) {
			list.add(pos);
		}
	}

	public void removePallet(BlockPos pos) {
		if (this.dimKey == null) return;
		List<BlockPos> list = DIMENSION_PALLETS.get(this.dimKey);
		if (list != null) {
			list.remove(pos);
		}
	}

	public List<BlockPos> getPallets() {
		if (this.dimKey == null) return new ArrayList<>();
		List<BlockPos> list = DIMENSION_PALLETS.get(this.dimKey);
		return list != null ? new ArrayList<>(list) : new ArrayList<>();
	}
}
