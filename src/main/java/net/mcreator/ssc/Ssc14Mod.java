package net.mcreator.ssc;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.integration.Ssc14Bootstrap;
import net.ssc14.integration.neoforge.server.ServerTaskSubmission;
import net.ssc14.integration.neoforge.server.ServerWorkScheduler;
import net.neoforged.bus.api.IEventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** @deprecated Use {@code net.ssc14.integration.neoforge.Ssc14ModEntrypoint}. */
@Deprecated(forRemoval = false)
public class Ssc14Mod {
    public static final Logger LOGGER = LogManager.getLogger(Ssc14Mod.class);
    public static final String MODID = Ssc14Identity.MOD_ID;

    /** Preserves the historical constructor contract for external integrations. */
    public Ssc14Mod(IEventBus modEventBus) {
        Ssc14Bootstrap.initialize(modEventBus);
    }

    /** Worker-safe admission; only the passed server's tick thread executes the action. */
    public static ServerTaskSubmission queueServerWork(
            MinecraftServer owner, int delay, Runnable action) {
        return ServerWorkScheduler.submit(owner, delay, action);
    }

    public static ServerTaskSubmission queueServerWork(
            ServerLevel owner, int delay, Runnable action) {
        ServerWorkScheduler.validateSubmission(delay, action);
        if (owner == null) return ServerTaskSubmission.INVALID_OWNER;
        return queueServerWork(owner.getServer(), delay, action);
    }

    /** Explicit side rejection for integrations that only have a LevelAccessor. */
    public static ServerTaskSubmission queueServerWork(
            LevelAccessor owner, int delay, Runnable action) {
        ServerWorkScheduler.validateSubmission(delay, action);
        if (owner == null) return ServerTaskSubmission.INVALID_OWNER;
        if (owner.isClientSide()) return ServerTaskSubmission.CLIENT_SIDE;
        if (owner instanceof ServerLevel level) return queueServerWork(level, delay, action);
        return ServerTaskSubmission.INVALID_OWNER;
    }

    private static Object minecraft;
    private static MethodHandle playerHandle;

    @Nullable
    public static Player clientPlayer() {
        if (!FMLEnvironment.getDist().isClient()) return null;
        try {
            if (minecraft == null || playerHandle == null) {
                Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
                minecraft = MethodHandles.publicLookup().findStatic(
                        minecraftClass, "getInstance", MethodType.methodType(minecraftClass))
                        .invoke();
                playerHandle = MethodHandles.publicLookup().findGetter(
                        minecraftClass, "player",
                        Class.forName("net.minecraft.client.player.LocalPlayer"));
            }
            return (Player) playerHandle.invoke(minecraft);
        } catch (Throwable error) {
            LOGGER.error("Failed to get client player", error);
            return null;
        }
    }

    /** Compatibility access to the optional Curios inventory capability. */
    public static final class CuriosApiHelper {
        private static final EntityCapability<ResourceHandler, Void> CURIOS_INVENTORY =
                EntityCapability.createVoid(
                        Identifier.fromNamespaceAndPath("curios", "item_handler"),
                        ResourceHandler.class);

        private CuriosApiHelper() { }

        public static ResourceHandler<ItemResource> getCuriosInventory(Player player) {
            if (ModList.get().isLoaded("curios")) {
                return player.getCapability(CURIOS_INVENTORY);
            }
            return null;
        }

        public static boolean isCurioItem(ItemStack itemStack) {
            return BuiltInRegistries.ITEM.getTags()
                    .filter(named -> named.key().location().getNamespace().equals("curios"))
                    .anyMatch(named -> itemStack.is(named.key()));
        }
    }
}
