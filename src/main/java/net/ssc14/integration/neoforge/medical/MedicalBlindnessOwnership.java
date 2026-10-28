package net.ssc14.integration.neoforge.medical;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Persists identity ownership of SSC14's blindness effect chain. */
@EventBusSubscriber(modid = "ssc_14")
public final class MedicalBlindnessOwnership {
    private static final String CHECKPOINT = "owned_chain";
    private static final AttachmentType<MedicalBlindnessOwnership> TYPE = AttachmentType
            .builder(holder -> new MedicalBlindnessOwnership((Player) holder))
            .serialize(new Serializer()).build();

    private final Player player;
    private MobEffectInstance instance;
    private CompoundTag checkpoint;

    private MedicalBlindnessOwnership(Player player) {
        this.player = player;
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES,
                Identifier.parse("ssc_14:medical_blindness_owner"), () -> TYPE);
    }

    public static void acquire(Player player, MobEffectInstance effect) {
        var ownership = player.getData(TYPE);
        ownership.instance = effect;
        ownership.checkpoint = null;
    }

    public static boolean owns(Player player, MobEffectInstance current) {
        return player.hasData(TYPE) && player.getData(TYPE).matches(current);
    }

    public static void clear(Player player) {
        player.removeData(TYPE);
    }

    private boolean matches(MobEffectInstance current) {
        if (instance == null && checkpoint != null) {
            if (current != null && checkpoint.equals(encode(current))) {
                instance = current;
            }
            checkpoint = null;
        }
        return current != null && current == instance;
    }

    private static CompoundTag encode(MobEffectInstance effect) {
        return (CompoundTag) MobEffectInstance.CODEC
                .encodeStart(NbtOps.INSTANCE, effect).getOrThrow();
    }

    private static final class Serializer implements IAttachmentSerializer<MedicalBlindnessOwnership> {
        @Override
        public MedicalBlindnessOwnership read(IAttachmentHolder holder, ValueInput input) {
            var ownership = new MedicalBlindnessOwnership((Player) holder);
            ownership.checkpoint = input.read(CHECKPOINT, CompoundTag.CODEC).orElse(null);
            return ownership;
        }

        @Override
        public boolean write(MedicalBlindnessOwnership ownership, ValueOutput output) {
            var current = ownership.player.getEffect(MobEffects.BLINDNESS);
            if (ownership.matches(current)) {
                output.store(CHECKPOINT, CompoundTag.CODEC, encode(current));
            }
            return true;
        }
    }
}
