package net.ssc14.integration.neoforge.player;

import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/** Owns SSC14 player attachment registration. */
public final class Ssc14PlayerAttachments {
    private static final DeferredRegister<AttachmentType<?>> TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "ssc_14");

    public static final Supplier<AttachmentType<Ssc14PlayerVariables>> PLAYER_VARIABLES =
            TYPES.register("player_variables", () -> AttachmentType
                    .serializable(Ssc14PlayerVariables::new).build());

    private Ssc14PlayerAttachments() { }

    public static void register(IEventBus modEventBus) {
        TYPES.register(modEventBus);
    }
}
