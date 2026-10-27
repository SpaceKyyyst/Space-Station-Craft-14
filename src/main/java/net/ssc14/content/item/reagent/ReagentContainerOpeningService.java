package net.ssc14.content.item.reagent;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Performs the sealed-to-open container transition. */
public final class ReagentContainerOpeningService {
    private static final float SOUND_VOLUME = 1.0F;
    private static final double MIN_SOUND_PITCH = 0.9;
    private static final double MAX_SOUND_PITCH = 1.1;

    private ReagentContainerOpeningService() { }

    public static void open(
            Level level,
            Player player,
            InteractionHand hand,
            ItemStack sealedStack,
            ReagentContainerDefinition definition,
            Supplier<? extends Item> openItem) {
        if (hand != InteractionHand.MAIN_HAND || sealedStack.isEmpty()) {
            return;
        }

        if (!level.isClientSide()) {
            BuiltInRegistries.SOUND_EVENT.get(definition.openingSoundId()).ifPresent(sound ->
                    level.playSound(null, player.blockPosition(), sound.value(), SoundSource.MASTER,
                            SOUND_VOLUME, (float) Mth.nextDouble(RandomSource.create(),
                                    MIN_SOUND_PITCH, MAX_SOUND_PITCH)));
        }

        sealedStack.shrink(1);
        ItemStack openedStack = new ItemStack(openItem.get());
        ReagentContainerAccess.add(
                openedStack, definition.reagentId(), definition.fillAmount());
        player.setItemInHand(InteractionHand.MAIN_HAND, openedStack);
        player.getInventory().setChanged();
    }
}
