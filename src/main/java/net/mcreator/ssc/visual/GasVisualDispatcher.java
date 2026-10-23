package net.mcreator.ssc.visual;

import java.util.List;
import net.mcreator.ssc.AtmosRegion.GasVisualData;
import net.mcreator.ssc.GasType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.ssc14.content.particle.Ssc14ParticleTypes;

/** One directed send per recipient; never broadcasts again inside the player loop. */
public final class GasVisualDispatcher {
    private static final double MAX_DISTANCE_SQUARED = 64 * 64;
    private static final float BASE_CHANCE = 0.25f;
    private static final float FLAMMABLE_MULTIPLIER = 1.3f;
    private static final float SECOND_PARTICLE_CHANCE = 0.5f;
    private static final float POSITION_SPREAD = 0.5f;
    private GasVisualDispatcher() { }

    public static void send(ServerLevel level, List<GasVisualData> visuals) {
        for (var data : visuals) {
            var gas = GasType.byIndex(data.gasIndex);
            if (gas == null || gas == GasType.OXYGEN || gas == GasType.NITROGEN
                    || gas == GasType.CARBON_DIOXIDE || gas == GasType.NITROUS_OXIDE) continue;
            var position = BlockPos.containing(data.x, data.y, data.z);
            for (var player : level.players()) {
                double distance = player.blockPosition().distSqr(position);
                if (distance >= MAX_DISTANCE_SQUARED) continue;
                double fraction = distance / MAX_DISTANCE_SQUARED;
                float multiplier = gas == GasType.PLASMA || gas == GasType.TRITIUM ? FLAMMABLE_MULTIPLIER : 1;
                double chance = BASE_CHANCE * (1 - fraction * fraction) * Math.clamp(data.density, 0, 1) * multiplier;
                if (level.getRandom().nextFloat() >= chance) continue;
                boolean fire = gas == GasType.PLASMA || gas == GasType.TRITIUM;
                int variant = fire ? 0 : level.getRandom().nextInt(4);
                var first = particle(gas, variant);
                emit(level, player, data, first);
                if (level.getRandom().nextFloat() < SECOND_PARTICLE_CHANCE) {
                    var second = particle(gas, fire ? 1 : level.getRandom().nextInt(4));
                    if (second != first) emit(level, player, data, second);
                }
            }
        }
    }

    private static void emit(ServerLevel level, ServerPlayer player, GasVisualData data, ParticleOptions particle) {
        if (particle == null) return;
        var random = level.getRandom();
        level.sendParticles(player, particle, false, false,
                data.x + (random.nextFloat() - 0.5f) * POSITION_SPREAD,
                data.y + (random.nextFloat() - 0.5f) * POSITION_SPREAD,
                data.z + (random.nextFloat() - 0.5f) * POSITION_SPREAD,
                1, 0, 0, 0, 0);
    }

    private static ParticleOptions particle(GasType gas, int variant) {
        return switch (gas) {
            case PLASMA, TRITIUM -> variant == 0 ? Ssc14ParticleTypes.PLASMA_PARTICLES_1.get() : Ssc14ParticleTypes.PLASMA_PARTICLES_2.get();
            case WATER_VAPOR -> switch (variant) {
                case 0 -> Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_1.get();
                case 1 -> Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_2.get();
                case 2 -> Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_3.get();
                default -> Ssc14ParticleTypes.WATER_VAPOR_PARTICLES_4.get();
            };
            case AMMONIA -> switch (variant) {
                case 0 -> Ssc14ParticleTypes.AMMONIA_PARTICLES_1.get();
                case 1 -> Ssc14ParticleTypes.AMMONIA_PARTICLES_2.get();
                case 2 -> Ssc14ParticleTypes.AMMONIA_PARTICLES_3.get();
                default -> Ssc14ParticleTypes.AMMONIA_PARTICLES_4.get();
            };
            case FREON -> switch (variant) {
                case 0 -> Ssc14ParticleTypes.FREON_PARTICLES_1.get();
                case 1 -> Ssc14ParticleTypes.FREON_PARTICLES_2.get();
                case 2 -> Ssc14ParticleTypes.FREON_PARTICLES_3.get();
                default -> Ssc14ParticleTypes.FREON_PARTICLES_4.get();
            };
            case HELIUM -> switch (variant) {
                case 0 -> Ssc14ParticleTypes.HELIUM_PARTICLES_1.get();
                case 1 -> Ssc14ParticleTypes.HELIUM_PARTICLES_2.get();
                case 2 -> Ssc14ParticleTypes.HELIUM_PARTICLES_3.get();
                default -> Ssc14ParticleTypes.HELIUM_PARTICLES_4.get();
            };
            default -> null;
        };
    }
}
