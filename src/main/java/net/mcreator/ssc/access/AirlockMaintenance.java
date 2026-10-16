package net.mcreator.ssc.access;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/** Reinforcement uses a reversible plate-attachment/welding sequence on a closed door. */
@EventBusSubscriber(modid = "ssc_14")
public final class AirlockMaintenance {
    public static final String ARMOR = "_ssc14_airlock_armor";
    public static final int BARE = 0;
    public static final int ATTACHED = 1;
    public static final int REINFORCED = 2;
    private static final int PLATE_COUNT = 2;
    private static final double WELD_FUEL_COST = 100;
    private static final double MIN_WELDER_FUEL = 150;
    private static final double INITIAL_WELDER_FUEL = 10001;
    private static final double MAX_DISTANCE_SQUARED = 64;
    private static final float REINFORCED_BREAK_FACTOR = 3;

    private AirlockMaintenance() { }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        event.getPosition().ifPresent(pos -> {
            var blockEntity = event.getEntity().level().getBlockEntity(pos);
            if (blockEntity != null && blockEntity.getPersistentData().getIntOr(ARMOR, BARE) == REINFORCED)
                event.setNewSpeed(event.getNewSpeed() / REINFORCED_BREAK_FACTOR);
        });
    }

    public static boolean isArmored(net.minecraft.world.level.BlockGetter level, BlockPos pos) {
        var blockEntity = level.getBlockEntity(pos);
        return blockEntity != null && blockEntity.getPersistentData().getIntOr(ARMOR, BARE) != BARE;
    }

    /** Returns true when the interaction belongs to maintenance, even if prerequisites fail. */
    public static boolean interact(Player player, BlockPos pos) {
        if (!(player.level() instanceof ServerLevel level) || !level.hasChunkAt(pos)) return false;
        var state = level.getBlockState(pos);
        if (!(state.getBlock().getStateDefinition().getProperty("arl_variat") instanceof IntegerProperty frame)) return false;
        var stack = player.getMainHandItem();
        if (isArmored(level, pos) && stack.is(Ssc14ModItems.SCREWDRIVER.get())) return true;
        if (!player.isShiftKeyDown() || !(stack.is(Ssc14ModItems.STEEL.get())
                || stack.is(Ssc14ModItems.ACTIVE_WELDER.get()) || stack.is(Ssc14ModItems.CROWBAR.get()))) return false;
        if (!player.isAlive() || player.isSpectator() || !level.mayInteract(player, pos)
                || player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > MAX_DISTANCE_SQUARED) return true;
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null || state.getValue(frame) >= 11 && state.getValue(frame) <= 15) return true;
        if (state.getBlock().getStateDefinition().getProperty("panel_open") instanceof BooleanProperty panel
                && state.getValue(panel)) return true;
        var data = blockEntity.getPersistentData();
        int armor = data.getIntOr(ARMOR, BARE);
        if (stack.is(Ssc14ModItems.STEEL.get()) && armor == BARE) {
            if (stack.getCount() < PLATE_COUNT) return true;
            stack.shrink(PLATE_COUNT);
            data.putInt(ARMOR, ATTACHED);
        } else if (stack.is(Ssc14ModItems.ACTIVE_WELDER.get()) && armor != BARE) {
            var fuel = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            double available = fuel.getDoubleOr("fuel", INITIAL_WELDER_FUEL);
            if (!Double.isFinite(available) || available < MIN_WELDER_FUEL + WELD_FUEL_COST) return true;
            fuel.putDouble("fuel", available - WELD_FUEL_COST);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(fuel));
            data.putInt(ARMOR, armor == ATTACHED ? REINFORCED : ATTACHED);
        } else if (stack.is(Ssc14ModItems.CROWBAR.get()) && armor == ATTACHED) {
            data.putInt(ARMOR, BARE);
            Block.popResource(level, pos, new ItemStack(Ssc14ModItems.STEEL.get(), PLATE_COUNT));
        } else return true;
        blockEntity.setChanged();
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
        return true;
    }
}
