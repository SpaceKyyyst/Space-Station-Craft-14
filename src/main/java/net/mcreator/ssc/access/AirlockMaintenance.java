package net.mcreator.ssc.access;

import net.ssc14.common.airlock.AirlockProtection;
import net.ssc14.content.block.airlock.AirlockWorkFeedback;

import java.util.Map;
import java.util.WeakHashMap;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

/** Server-thread construction jobs; materials are charged only after revalidation. */
@EventBusSubscriber(modid = "ssc_14")
public final class AirlockMaintenance {
    public static final String ARMOR = "_ssc14_airlock_armor";
    public static final int BARE = AirlockProtection.BARE;
    public static final int ATTACHED = AirlockProtection.STEEL_ATTACHED;
    public static final int REINFORCED = AirlockProtection.STEEL_WELDED;
    private static final int MATERIAL_COUNT = 2;
    private static final double WELD_FUEL_COST = 100;
    private static final double REQUIRED_FUEL = 250;
    private static final double MAX_DISTANCE_SQUARED = 36;
    private static final double MAX_MOVEMENT_SQUARED = 0.01;
    private static final float GRILLE_SHOCK_DAMAGE = 7.5f;
    private static final int GRILLE_SHOCK_TICKS = 100;
    private static final Map<Player, Job> JOBS = new WeakHashMap<>();
    private record Job(BlockEntity door, ItemStack stack, Vec3 origin, int initialState,
                       AirlockProtection.Tool tool, AirlockProtection.Transition transition, long finish) { }
    private AirlockMaintenance() { }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        event.getPosition().ifPresent(pos -> {
            var door = event.getEntity().level().getBlockEntity(root(event.getEntity().level(), pos));
            if (door != null && door.getPersistentData().getIntOr(ARMOR, BARE) >= REINFORCED)
                event.setNewSpeed(event.getNewSpeed() / 3);
        });
    }

    public static BlockPos root(net.minecraft.world.level.BlockGetter level, BlockPos pos) {
        var state = level.getBlockState(pos);
        return state.is(Ssc14ModBlocks.AIRLOCK_UP_PLUG.get()) || state.is(Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get())
                ? pos.below() : pos;
    }

    public static boolean isArmored(net.minecraft.world.level.BlockGetter level, BlockPos pos) {
        var door = level.getBlockEntity(root(level, pos));
        return door != null && door.getPersistentData().getIntOr(ARMOR, BARE) != BARE;
    }

    public static boolean interact(Player player, BlockPos clicked) {
        if (!(player.level() instanceof ServerLevel level) || !level.hasChunkAt(clicked)) return false;
        var pos = root(level, clicked);
        var door = level.getBlockEntity(pos);
        if (door == null || door.getBlockState().getBlock().getStateDefinition().getProperty("arl_variat") == null) return false;
        var stack = player.getMainHandItem();
        var tool = tool(stack);
        if (tool == null || !(door.getBlockState().getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty panel)
                || !door.getBlockState().getValue(panel)) return false;
        int armor = door.getPersistentData().getIntOr(ARMOR, BARE);
        // Welding bare panels still welds the leaves; crowbars can still pry unpowered bare doors.
        if (armor == BARE && (tool == AirlockProtection.Tool.WELDER || tool == AirlockProtection.Tool.CROWBAR
                || tool == AirlockProtection.Tool.CUTTERS)) return false;
        var transition = AirlockProtection.transition(armor, tool);
        if (transition == null) return true;
        var job = new Job(door, stack, player.position(), armor, tool, transition, level.getGameTime() + transition.ticks());
        if (valid(player, job)) JOBS.putIfAbsent(player, job);
        return true;
    }

    private static boolean valid(Player player, Job job) {
        var level = player.level();
        var pos = job.door.getBlockPos();
        if (!player.isAlive() || player.isSpectator() || job.door.getLevel() != level || !level.hasChunkAt(pos)
                || level.getBlockEntity(pos) != job.door || !level.mayInteract(player, pos)
                || player.distanceToSqr(pos.getCenter()) > MAX_DISTANCE_SQUARED
                || player.position().distanceToSqr(job.origin) > MAX_MOVEMENT_SQUARED
                || player.getMainHandItem() != job.stack || tool(job.stack) != job.tool
                || job.door.getPersistentData().getIntOr(ARMOR, BARE) != job.initialState) return false;
        var state = job.door.getBlockState();
        if (!(state.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty panel) || !state.getValue(panel)
                || !(state.getBlock().getStateDefinition().getProperty("arl_variat") instanceof IntegerProperty frame)
                || state.getValue(frame) >= 11 && state.getValue(frame) <= 15) return false;
        if (job.transition.consumed() != null && job.stack.getCount() < MATERIAL_COUNT) return false;
        double fuel = fuel(job.stack);
        return job.tool != AirlockProtection.Tool.WELDER || Double.isFinite(fuel) && fuel >= REQUIRED_FUEL;
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) { advance(event.getEntity()); }

    public static void advance(Player player) {
        if (!(player.level() instanceof ServerLevel level)) return;
        var job = JOBS.get(player);
        if (job == null) return;
        if (!valid(player, job)) { JOBS.remove(player); return; }
        AirlockWorkFeedback.progress(player, job.door.getBlockPos(), job.finish - level.getGameTime(), job.tool == AirlockProtection.Tool.WELDER);
        if (level.getGameTime() < job.finish) return;
        JOBS.remove(player);
        var change = job.transition;
        if (change.consumed() != null) job.stack.shrink(MATERIAL_COUNT);
        if (job.tool == AirlockProtection.Tool.WELDER)
            CustomData.update(DataComponents.CUSTOM_DATA, job.stack, tag -> tag.putDouble("fuel", fuel(job.stack) - WELD_FUEL_COST));
        job.door.getPersistentData().putInt(ARMOR, change.target());
        job.door.setChanged();
        var pos = job.door.getBlockPos();
        level.sendBlockUpdated(pos, job.door.getBlockState(), job.door.getBlockState(), Block.UPDATE_CLIENTS);
        if (change.returned() != null) Block.popResource(level, pos, new ItemStack(material(change.returned()), MATERIAL_COUNT));
        if (change.shock() && job.door.getPersistentData().getBooleanOr("powered", true) && !insulated(player)
                && player.hurtServer(level, level.damageSources().lightningBolt(), GRILLE_SHOCK_DAMAGE))
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.SLOWNESS,
                    GRILLE_SHOCK_TICKS, 9, false, true));
    }

    private static boolean insulated(Player player) {
        return CuriosApi.getCuriosInventory(player).map(inventory -> inventory.getCurios().values().stream().anyMatch(handler -> {
            var slots = handler.getStacks();
            for (int i = 0; i < slots.getSlots(); i++) if (slots.getStackInSlot(i).is(Ssc14ModItems.ISOLATED_GLOVES.get())) return true;
            return false;
        })).orElse(false);
    }

    private static double fuel(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel", 10001);
    }

    private static Item material(AirlockProtection.Tool tool) {
        return switch (tool) {
            case STEEL -> Ssc14ModItems.STEEL.get();
            case PLASTEEL -> Ssc14ModItems.PLASTEEL.get();
            case RODS -> Ssc14ModItems.ROOD.get();
            default -> throw new IllegalArgumentException("Not a construction material: " + tool);
        };
    }

    private static AirlockProtection.Tool tool(ItemStack stack) {
        if (stack.is(Ssc14ModItems.STEEL.get())) return AirlockProtection.Tool.STEEL;
        if (stack.is(Ssc14ModItems.PLASTEEL.get())) return AirlockProtection.Tool.PLASTEEL;
        if (stack.is(Ssc14ModItems.ROOD.get())) return AirlockProtection.Tool.RODS;
        if (stack.is(Ssc14ModItems.ACTIVE_WELDER.get())) return AirlockProtection.Tool.WELDER;
        if (stack.is(Ssc14ModItems.CROWBAR.get())) return AirlockProtection.Tool.CROWBAR;
        if (stack.is(Ssc14ModItems.NIPPERS.get())) return AirlockProtection.Tool.CUTTERS;
        return null;
    }
}
