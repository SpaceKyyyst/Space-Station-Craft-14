package net.ssc14.content.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.Ssc14Items;

/** Owns welder state transitions, active fuel consumption, and safe active-item drops. */
public final class WelderStateService {
    public static final double INITIAL_FUEL = 10001.0D;
    public static final double FUEL_PER_TICK = 2.0D;
    public static final double MINIMUM_ACTIVE_FUEL = 150.0D;
    public static final double ACTIVATION_FUEL_THRESHOLD = 600.0D;
    public static final double ACTIVATION_FUEL_COST = 350.0D;
    public static final int HUD_INTERVAL_TICKS = 10;
    public static final int DROP_PICKUP_DELAY_TICKS = 10;

    private static final String FUEL_TAG = "fuel";
    private static final Identifier WELDER_ON_SOUND = id("welder_on");
    private static final Identifier WELDER_OFF_SOUND = id("welder_off");

    private WelderStateService() { }

    public static void toggle(LevelAccessor level, double x, double y, double z,
            Entity entity) {
        if (entity == null || level.isClientSide() || !(entity instanceof LivingEntity living)) {
            return;
        }
        ItemStack held = living.getMainHandItem();
        if (entity.isShiftKeyDown() && held.is(Ssc14Items.WELDING.get())
                && fuel(held) > ACTIVATION_FUEL_THRESHOLD) {
            double remainingFuel = fuel(held) - ACTIVATION_FUEL_COST;
            replaceMainHand(living, held, Ssc14Items.ACTIVE_WELDER.get().getDefaultInstance());
            writeFuel(living.getMainHandItem(), remainingFuel);
            playSound(level, x, y, z, WELDER_ON_SOUND);
        } else if (held.is(Ssc14Items.ACTIVE_WELDER.get())) {
            double remainingFuel = fuel(held);
            replaceMainHand(living, held, Ssc14Items.WELDING.get().getDefaultInstance());
            writeFuel(living.getMainHandItem(), remainingFuel);
            playSound(level, x, y, z, WELDER_OFF_SOUND);
        }
    }

    public static void tick(ServerLevel level, Player player, InteractionHand hand,
            ItemStack stack) {
        if (!stack.is(Ssc14Items.ACTIVE_WELDER.get())) {
            return;
        }
        double currentFuel = fuel(stack);
        if (!hasFuel(stack)) {
            currentFuel = INITIAL_FUEL;
            writeFuel(stack, currentFuel);
        }
        if (currentFuel <= MINIMUM_ACTIVE_FUEL) {
            deactivate(level, player, hand, stack, currentFuel);
            return;
        }
        double nextFuel = Math.max(MINIMUM_ACTIVE_FUEL, currentFuel - FUEL_PER_TICK);
        if (Double.compare(nextFuel, currentFuel) != 0) {
            writeFuel(stack, nextFuel);
        }
        if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer
                && level.getGameTime() % HUD_INTERVAL_TICKS == 0) {
            serverPlayer.sendSystemMessage(
                    Component.literal("Fuel: " + Math.round((nextFuel - 1.0D) / 100.0D)), true);
        }
    }

    public static void dropInactive(LevelAccessor level, double x, double y, double z,
            ItemStack activeStack) {
        if (activeStack.isEmpty() || level.isClientSide()) {
            return;
        }
        ItemStack inactive = Ssc14Items.WELDING.get().getDefaultInstance();
        inactive.applyComponents(activeStack.getComponentsPatch());
        inactive.setCount(1);
        activeStack.shrink(1);
        if (level instanceof ServerLevel serverLevel) {
            ItemEntity dropped = new ItemEntity(serverLevel, x, y, z, inactive);
            dropped.setPickUpDelay(DROP_PICKUP_DELAY_TICKS);
            dropped.setUnlimitedLifetime();
            serverLevel.addFreshEntity(dropped);
        }
    }

    private static void deactivate(ServerLevel level, Player player, InteractionHand hand,
            ItemStack active, double remainingFuel) {
        ItemStack inactive = Ssc14Items.WELDING.get().getDefaultInstance();
        inactive.applyComponents(active.getComponentsPatch());
        writeFuel(inactive, remainingFuel);
        inactive.setCount(active.getCount());
        player.setItemInHand(hand, inactive);
        playSound(level, player.getX(), player.getY(), player.getZ(), WELDER_OFF_SOUND);
    }

    private static void replaceMainHand(LivingEntity entity, ItemStack source,
            ItemStack replacement) {
        replacement.applyComponents(source.getComponentsPatch());
        replacement.setCount(source.getCount());
        entity.setItemInHand(InteractionHand.MAIN_HAND, replacement);
        if (entity instanceof Player player) {
            player.getInventory().setChanged();
        }
    }

    private static void playSound(LevelAccessor levelAccessor, double x, double y,
            double z, Identifier soundId) {
        if (!(levelAccessor instanceof Level level)) {
            return;
        }
        BuiltInRegistries.SOUND_EVENT.getOptional(soundId).ifPresent(sound ->
                level.playSound(null, BlockPos.containing(x, y, z), sound,
                        SoundSource.NEUTRAL, 1.0F, 1.0F));
    }

    private static boolean hasFuel(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().contains(FUEL_TAG);
    }

    private static double fuel(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr(FUEL_TAG, 0.0D);
    }

    private static void writeFuel(ItemStack stack, double fuel) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble(FUEL_TAG, fuel));
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
