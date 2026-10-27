package net.ssc14.client.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.particle.ParticleResources;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

/** Owns client particle provider bindings independently from provider implementations. */
public final class Ssc14ParticleProviderBinder {
    private static final List<Consumer<RegisterParticleProvidersEvent>> BINDINGS =
            new ArrayList<>();

    private Ssc14ParticleProviderBinder() { }

    public static <T extends ParticleOptions> void bindSpriteSet(
            Supplier<? extends ParticleType<T>> particleType,
            ParticleResources.SpriteParticleRegistration<T> registration) {
        BINDINGS.add(event -> event.registerSpriteSet(
                particleType.get(), registration));
    }

    public static void registerProviders(RegisterParticleProvidersEvent event) {
        BINDINGS.forEach(binding -> binding.accept(event));
    }

    public static int bindingCount() {
        return BINDINGS.size();
    }
}
