package net.mcreator.ssc.integration;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.minecraft.core.BlockPos;
import net.ssc14.content.block.access.IdConsoleIntegrationService;
import net.ssc14.content.access.IdCodeCheckService;
import net.ssc14.content.menu.access.IDcodeMenu;

/** Compatibility event adapter kept outside the canonical menu container. */
@EventBusSubscriber
public final class LegacyIdCodeMenuEvents {
    private LegacyIdCodeMenuEvents() { }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().containerMenu instanceof IDcodeMenu menu) {
            IdCodeCheckService.execute(menu.world, BlockPos.containing(menu.x, menu.y, menu.z), event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        if (event.getContainer() instanceof IDcodeMenu menu) {
            IdConsoleIntegrationService.resetBuffer((net.minecraft.world.level.Level) menu.world,
                    BlockPos.containing(menu.x, menu.y, menu.z));
        }
    }
}
