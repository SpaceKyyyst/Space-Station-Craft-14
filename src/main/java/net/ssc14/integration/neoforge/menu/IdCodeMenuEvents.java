package net.ssc14.integration.neoforge.menu;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.minecraft.core.BlockPos;
import net.ssc14.content.block.access.IdConsoleIntegrationService;
import net.ssc14.content.access.IdCodeCheckService;
import net.ssc14.content.menu.access.IDcodeMenu;

/** Server event hooks for the canonical ID-code menu. */
@EventBusSubscriber
public final class IdCodeMenuEvents {
    private IdCodeMenuEvents() { }

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
