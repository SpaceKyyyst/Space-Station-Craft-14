package net.mcreator.ssc.access;

import java.util.Map;
import java.util.WeakHashMap;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Manual opening bypasses access, never bolts, welding or a restored power supply. */
@EventBusSubscriber(modid = "ssc_14")
public final class AirlockManualOpening {
    public static final int CROWBAR_TICKS = 40;
    public static final int HAND_TICKS = 600;
    private static final Map<Player, Job> JOBS = new WeakHashMap<>();
    private record Job(BlockEntity door, ItemStack tool, Vec3 origin, int initialFrame, long finish) { }
    private AirlockManualOpening() { }

    public static boolean interact(Player player, BlockPos pos) {
        if (!(player.level() instanceof ServerLevel level) || player.isShiftKeyDown() || !level.hasChunkAt(pos)) return false;
        var tool = player.getMainHandItem();
        if (!tool.isEmpty() && !tool.is(Ssc14ModItems.CROWBAR.get())) return false;
        var clicked = level.getBlockState(pos);
        if (clicked.is(Ssc14ModBlocks.AIRLOCK_UP_PLUG.get()) || clicked.is(Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get())) pos = pos.below();
        var door = level.getBlockEntity(pos);
        if (door == null || door.getBlockState().getBlock().getStateDefinition().getProperty("arl_variat") == null) return false;
        if (door.getPersistentData().getBooleanOr("powered", true)) return false;
        var panel = door.getBlockState().getBlock().getStateDefinition().getProperty("panel_open");
        if (tool.isEmpty() && panel instanceof net.minecraft.world.level.block.state.properties.BooleanProperty property
                && door.getBlockState().getValue(property)) return false;
        int initialFrame = door.getBlockState().getValue((IntegerProperty) door.getBlockState().getBlock().getStateDefinition().getProperty("arl_variat"));
        if (initialFrame == 11 && tool.isEmpty()) return false;
        var job = new Job(door, tool, player.position(), initialFrame, level.getGameTime() + (tool.isEmpty() ? HAND_TICKS : CROWBAR_TICKS));
        if (valid(player, job)) JOBS.putIfAbsent(player, job);
        return true;
    }

    private static boolean valid(Player player, Job job) {
        var level = player.level();
        var pos = job.door.getBlockPos();
        if (!player.isAlive() || player.isSpectator() || job.door.getLevel() != level || !level.hasChunkAt(pos)
                || level.getBlockEntity(pos) != job.door || !level.mayInteract(player, pos)
                || player.position().distanceToSqr(job.origin) > 0.01 || player.distanceToSqr(pos.getCenter()) > 36
                || player.getMainHandItem() != job.tool) return false;
        var state = job.door.getBlockState();
        if (!(state.getBlock().getStateDefinition().getProperty("arl_variat") instanceof IntegerProperty frame)) return false;
        int value = state.getValue(frame);
        var data = job.door.getPersistentData();
        return value == job.initialFrame && (value <= 11 || value > 15) && !data.getBooleanOr("powered", true)
                && !data.getBooleanOr("bolted", false) && !data.getBooleanOr("welded", false);
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) { advance(event.getEntity()); }

    public static void advance(Player player) {
        if (!(player.level() instanceof ServerLevel level)) return;
        var job = JOBS.get(player);
        if (job == null) return;
        if (!valid(player, job)) { JOBS.remove(player); return; }
        AirlockWorkFeedback.progress(player, job.door.getBlockPos(), job.finish - level.getGameTime(), false);
        if (level.getGameTime() < job.finish) return;
        JOBS.remove(player);
        BaseAirlockOpenCloseProcedure.openManually(level, job.door.getBlockPos());
    }
}
