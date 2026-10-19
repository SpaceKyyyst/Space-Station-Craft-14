package net.mcreator.ssc.client;

import net.mcreator.ssc.GasType;
import net.mcreator.ssc.network.GasAnalyserData;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;

public final class GasAnalyserReadings {
    private static final long MAX_AGE_TICKS = 20;
    private GasAnalyserReadings() { }

    public static CompoundTag snapshot() {
        var player = Minecraft.getInstance().player;
        if (player == null) return null;
        var tag = player.getPersistentData().getCompound(GasAnalyserData.CACHE_KEY).orElse(null);
        if (tag == null) return null;
        long age = player.level().getGameTime() - tag.getLongOr("received", -MAX_AGE_TICKS);
        if (age < 0 || age > MAX_AGE_TICKS
                || !tag.getStringOr("dimension", "").equals(player.level().dimension().identifier().toString())
                || tag.getLongOr("position", Long.MIN_VALUE) != player.blockPosition().asLong()) return null;
        return tag;
    }

    public static String amount(int gas) {
        var tag = snapshot();
        return tag == null ? "—" : format(tag.getFloatOr("gas_" + gas, 0));
    }

    public static String percent(int gas) {
        var tag = snapshot();
        if (tag == null) return "—";
        double total = 0;
        for (int index = 0; index < GasType.COUNT; index++) total += tag.getFloatOr("gas_" + index, 0);
        return format(total > 0 ? 100 * tag.getFloatOr("gas_" + gas, 0) / total : 0) + "%";
    }

    public static String temperature() {
        var tag = snapshot();
        return tag == null ? "—" : format(tag.getFloatOr("temperature", 0) - 273.15) + " °C";
    }

    public static String pressure() {
        var tag = snapshot();
        return tag == null ? "—" : format(tag.getFloatOr("pressure", 0) / 1000) + " kPa";
    }

    private static String format(double value) { return String.format(java.util.Locale.ROOT, "%.1f", value); }
}
