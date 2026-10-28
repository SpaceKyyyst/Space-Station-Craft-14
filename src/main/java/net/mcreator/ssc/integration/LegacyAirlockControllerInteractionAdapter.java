package net.mcreator.ssc.integration;

import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.item.tool.AirlockControllerService;

/** Legacy airlock runtime behind the canonical controller interaction port. */
public final class LegacyAirlockControllerInteractionAdapter
        implements AirlockControllerService.Adapter {
    private static final double MAX_DISTANCE_SQUARED = 64.0D;

    @Override
    public void interact(LevelAccessor world, double x, double y, double z,
            BlockState ignored, Entity entity, ItemStack controller) {
        if (!(world instanceof ServerLevel level) || !(entity instanceof Player player)
                || player.level() != level || !player.isAlive() || player.isSpectator()
                || !controller.is(Ssc14ModItems.AIRLOCK_CONTROLLER.get())
                || controller != player.getMainHandItem()
                        && controller != player.getOffhandItem()) {
            return;
        }
        BlockPos pos = BlockPos.containing(x, y, z);
        if (!level.hasChunkAt(pos)) {
            return;
        }
        BlockState state = level.getBlockState(pos);
        if (state.is(Ssc14ModBlocks.AIRLOCK_UP_PLUG.get())
                || state.is(Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get())) {
            pos = pos.below();
            state = level.getBlockState(pos);
        }
        if (state.getBlock().getStateDefinition().getProperty("arl_variat") == null
                || !level.mayInteract(player, pos)
                || player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D,
                        pos.getZ() + 0.5D) > MAX_DISTANCE_SQUARED) {
            return;
        }
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) {
            return;
        }
        var data = blockEntity.getPersistentData();
        if (!data.getBooleanOr("powered", true)) {
            return;
        }
        int mode = (int) controller.getOrDefault(
                DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr("mode", 0.0D);
        if (mode == 2) {
            net.ssc14.content.block.airlock.AirlockLifecycleService.execute(
                    level, pos.getX(), pos.getY(), pos.getZ(), state, player);
            return;
        }
        String sound;
        if (mode == 0) {
            boolean bolted = data.getBooleanOr("bolted", false);
            if (bolted && data.getBooleanOr("_ssc14_bolt_wire_cut", false)) {
                return;
            }
            data.putBoolean("bolted", !bolted);
            sound = bolted ? "airlock_bolt_off" : "airlock_bolt_on";
        } else if (mode == 1) {
            boolean emergency = data.getBooleanOr("emergency_acs", false);
            data.putBoolean("emergency_acs", !emergency);
            sound = emergency ? "airlock_emergency_off" : "airlock_emergency_on";
        } else {
            return;
        }
        blockEntity.setChanged();
        net.ssc14.content.block.airlock.AirlockStateInitializer.execute(
                level, pos.getX(), pos.getY(), pos.getZ(), state);
        net.ssc14.content.block.airlock.AirlockLifecycleService.refreshAutoClose(level, pos);
        BlockState after = level.getBlockState(pos);
        if (state == after) {
            level.sendBlockUpdated(pos, after, after, 3);
        }
        var event = BuiltInRegistries.SOUND_EVENT.getOptional(
                Identifier.fromNamespaceAndPath("ssc_14", sound));
        if (event.isPresent()) {
            level.playSound(null, pos, event.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}
