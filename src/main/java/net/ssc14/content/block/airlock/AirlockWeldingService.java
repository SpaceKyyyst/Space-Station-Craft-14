package net.ssc14.content.block.airlock;

import net.ssc14.content.block.airlock.AirlockWorkFeedback;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.Ssc14Items;

import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** A single cancellable server-side welding job per player, charged only on completion. */
@EventBusSubscriber(modid = "ssc_14")
public final class AirlockWeldingService {
    public static final int DURATION_TICKS = 100;
    private static final double FUEL_COST = 100;
    private static final double REQUIRED_FUEL = 250;
    private static final Map<Player, Job> JOBS = new WeakHashMap<>();
    private record Job(BlockEntity door, ItemStack tool, Vec3 origin, boolean welded, long finish) { }

    private AirlockWeldingService() { }

    public static boolean interact(Player player, BlockPos clicked) {
        if (!(player.level() instanceof ServerLevel level) || player.isShiftKeyDown()
                || !player.getMainHandItem().is(Ssc14Items.ACTIVE_WELDER.get())) return false;
        BlockPos pos = clicked;
        if (!level.hasChunkAt(pos)) return false;
        if (level.getBlockState(pos).getBlock().getStateDefinition().getProperty("arl_variat") == null) {
            if (!level.getBlockState(pos).is(Ssc14Blocks.AIRLOCK_UP_PLUG.get())
                    && !level.getBlockState(pos).is(Ssc14Blocks.AIRLOCK_UP_PLUG_OPEN.get())) return false;
            pos = pos.below();
        }
        var door = level.getBlockEntity(pos);
        if (door == null || door.getBlockState().getBlock().getStateDefinition().getProperty("arl_variat") == null) return false;
        var job = new Job(door, player.getMainHandItem(), player.position(),
                door.getPersistentData().getBooleanOr("welded", false), level.getGameTime() + DURATION_TICKS);
        if (valid(player, job)) JOBS.putIfAbsent(player, job);
        return true;
    }

    private static boolean valid(Player player, Job job) {
        var level = player.level();
        var pos = job.door.getBlockPos();
        if (!player.isAlive() || player.isSpectator() || job.door.getLevel() != level
                || !level.hasChunkAt(pos) || level.getBlockEntity(pos) != job.door
                || !level.mayInteract(player, pos) || player.position().distanceToSqr(job.origin) > 0.01
                || player.distanceToSqr(pos.getCenter()) > 36 || player.getMainHandItem() != job.tool
                || !job.tool.is(Ssc14Items.ACTIVE_WELDER.get())) return false;
        var state = job.door.getBlockState();
        if (!(state.getBlock().getStateDefinition().getProperty("arl_variat") instanceof IntegerProperty frame)) return false;
        int value = state.getValue(frame);
        double fuel = job.tool.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel", 10001);
        return (value < 11 || value > 15) && Double.isFinite(fuel) && fuel >= REQUIRED_FUEL
                && job.door.getPersistentData().getBooleanOr("welded", false) == job.welded;
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) { advance(event.getEntity()); }

    public static void advance(Player player) {
        if (!(player.level() instanceof ServerLevel level)) return;
        var job = JOBS.get(player);
        if (job == null) return;
        if (!valid(player, job)) { JOBS.remove(player); return; }
        var pos = job.door.getBlockPos();
        AirlockWorkFeedback.progress(player, pos, job.finish - level.getGameTime(), true);
        if (level.getGameTime() < job.finish) return;
        JOBS.remove(player);
        var fuel = job.tool.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        fuel.putDouble("fuel", fuel.getDoubleOr("fuel", 10001) - FUEL_COST);
        job.tool.set(DataComponents.CUSTOM_DATA, CustomData.of(fuel));
        job.door.getPersistentData().putBoolean("welded", !job.welded);
        job.door.setChanged();
        AirlockStateInitializer.execute(level, pos.getX(), pos.getY(), pos.getZ(), job.door.getBlockState());
        level.sendBlockUpdated(pos, job.door.getBlockState(), job.door.getBlockState(), 3);
    }
}
