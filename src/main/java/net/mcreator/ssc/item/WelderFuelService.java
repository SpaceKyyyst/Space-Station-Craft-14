package net.mcreator.ssc.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.entity.player.Player;
import net.mcreator.ssc.init.Ssc14ModItems;

/** Owns the single server-side fuel tick for an active welder. */
public final class WelderFuelService {
    private static final double INITIAL_FUEL = 10001.0;
    private static final double FUEL_PER_TICK = 2.0;
    private static final double MINIMUM_FUEL = 150.0;
    private static final int HUD_INTERVAL_TICKS = 10;

    private WelderFuelService() {
    }

    public static void tick(ServerLevel level, Player player, InteractionHand hand, ItemStack stack) {
        if (!stack.is(Ssc14ModItems.ACTIVE_WELDER.get())) return;

        double fuel = fuel(stack);
        if (!hasFuel(stack)) {
            fuel = INITIAL_FUEL;
            writeFuel(stack, fuel);
        }
        if (fuel <= MINIMUM_FUEL) {
            deactivate(level, player, hand, stack, fuel);
            return;
        }

        double nextFuel = Math.max(MINIMUM_FUEL, fuel - FUEL_PER_TICK);
        if (Double.compare(nextFuel, fuel) != 0) writeFuel(stack, nextFuel);
        if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer
                && level.getGameTime() % HUD_INTERVAL_TICKS == 0)
            serverPlayer.sendSystemMessage(Component.literal("Fuel: " + Math.round((nextFuel - 1.0) / 100.0)), true);
    }

    private static void deactivate(ServerLevel level, Player player, InteractionHand hand, ItemStack active, double fuel) {
        ItemStack inactive = new ItemStack(Ssc14ModItems.WELDING.get());
        inactive.applyComponents(active.getComponentsPatch());
        writeFuel(inactive, fuel);
        inactive.setCount(active.getCount());
        player.setItemInHand(hand, inactive);
        var sound = BuiltInRegistries.SOUND_EVENT.getOptional(Identifier.fromNamespaceAndPath("ssc_14", "welder_off"));
        sound.ifPresent(value -> level.playSound(null, BlockPos.containing(player.position()), value, SoundSource.NEUTRAL, 1.0F, 1.0F));
    }

    private static boolean hasFuel(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().contains("fuel");
    }

    private static double fuel(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("fuel", 0.0);
    }

    private static void writeFuel(ItemStack stack, double fuel) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("fuel", fuel));
    }
}
