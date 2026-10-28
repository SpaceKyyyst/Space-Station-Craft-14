package net.ssc14.content.equipment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import top.theillusivec4.curios.api.CuriosApi;

/** Owns suit movement modifiers and deterministic equipment cleanup. */
public final class SuitEquipmentService {
    private static final Identifier EQUIP_SOUND = Identifier.parse("ssc_14:equip_base_equip");

    private SuitEquipmentService() {
    }

    public static void equipMovementModifier(Entity entity, Identifier modifierId, double slowdownFactor,
            LevelAccessor world, double x, double y, double z) {
        if (entity instanceof LivingEntity living) {
            var attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                double baseSpeed = attribute.getBaseValue();
                var modifier = new AttributeModifier(modifierId, -baseSpeed * slowdownFactor,
                        AttributeModifier.Operation.ADD_VALUE);
                if (!attribute.hasModifier(modifier.id())) {
                    attribute.addPermanentModifier(modifier);
                }
            }
        }
        playEquipSound(world, x, y, z);
    }

    public static void removeSalvageSuit(ServerPlayer player, Item helmet, Identifier modifierId) {
        if (player == null) {
            return;
        }
        var attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(modifierId);
        }
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (!stack.isEmpty() && stack.is(helmet)) {
                player.getInventory().setItem(slot, ItemStack.EMPTY);
            }
        }
        CuriosApi.getCuriosInventory(player).ifPresent(inventory -> inventory.getCurios().values().forEach(handler -> {
            var stacks = handler.getStacks();
            for (int slot = 0; slot < stacks.getSlots(); slot++) {
                if (stacks.getStackInSlot(slot).is(helmet)) {
                    stacks.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }
        }));
        player.containerMenu.broadcastChanges();
    }

    private static void playEquipSound(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level)) {
            return;
        }
        var sound = BuiltInRegistries.SOUND_EVENT.getValue(EQUIP_SOUND);
        if (level.isClientSide()) {
            level.playLocalSound(x, y, z, sound, SoundSource.PLAYERS, 1.0F, 1.0F, false);
        } else {
            level.playSound(null, BlockPos.containing(x, y, z), sound, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
