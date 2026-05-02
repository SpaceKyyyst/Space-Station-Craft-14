package net.mcreator.ssc.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.ProblemReporter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.mcreator.ssc.Ssc14Mod;

import java.util.function.Supplier;

@EventBusSubscriber
public class Ssc14ModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Ssc14Mod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());
	public static boolean station_gravity = false;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@SubscribeEvent
	public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			for (Entity entityiterator : player.level().players())
				if (entityiterator != player && entityiterator instanceof ServerPlayer playeriterator)
					PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(playeriterator.getData(PLAYER_VARIABLES), playeriterator.getId()));
			PacketDistributor.sendToPlayersInDimension(player.level(), new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES), player.getId()));
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			for (Entity entityiterator : player.level().players())
				if (entityiterator != player && entityiterator instanceof ServerPlayer playeriterator)
					PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(playeriterator.getData(PLAYER_VARIABLES), playeriterator.getId()));
			PacketDistributor.sendToPlayersInDimension(player.level(), new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES), player.getId()));
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			for (Entity entityiterator : player.level().players())
				if (entityiterator != player && entityiterator instanceof ServerPlayer playeriterator)
					PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(playeriterator.getData(PLAYER_VARIABLES), playeriterator.getId()));
			PacketDistributor.sendToPlayersInDimension(player.level(), new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES), player.getId()));
		}
	}

	@SubscribeEvent
	public static void onPlayerTickUpdateSyncPlayerVariables(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player && player.getData(PLAYER_VARIABLES)._syncDirty) {
			PacketDistributor.sendToPlayersInDimension(player.level(), new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES), player.getId()));
			player.getData(PLAYER_VARIABLES)._syncDirty = false;
		}
	}

	@SubscribeEvent
	public static void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
		PlayerVariables clone = new PlayerVariables();
		if (!event.isWasDeath()) {
			clone.sscCustomHealth = original.sscCustomHealth;
			clone.ssc14_dmg_blunt = original.ssc14_dmg_blunt;
			clone.ssc14_dmg_slash = original.ssc14_dmg_slash;
			clone.ssc14_dmg_piercing = original.ssc14_dmg_piercing;
			clone.ssc14_dmg_heat = original.ssc14_dmg_heat;
			clone.ssc14_dmg_shock = original.ssc14_dmg_shock;
			clone.ssc14_dmg_cold = original.ssc14_dmg_cold;
			clone.ssc14_dmg_caustic = original.ssc14_dmg_caustic;
			clone.ssc14_dmg_poison = original.ssc14_dmg_poison;
			clone.ssc14_dmg_radiation = original.ssc14_dmg_radiation;
			clone.ssc14_dmg_asphyx = original.ssc14_dmg_asphyx;
			clone.ssc14_dmg_bloodloss = original.ssc14_dmg_bloodloss;
			clone.ssc14_dmg_cellular = original.ssc14_dmg_cellular;
		}
		event.getEntity().setData(PLAYER_VARIABLES, clone);
	}

	public static class PlayerVariables implements ValueIOSerializable {
		boolean _syncDirty = false;
		public double sscCustomHealth = 0.0;
		public double ssc14_dmg_blunt = 0.0;
		public double ssc14_dmg_slash = 0.0;
		public double ssc14_dmg_piercing = 0.0;
		public double ssc14_dmg_heat = 0;
		public double ssc14_dmg_shock = 0;
		public double ssc14_dmg_cold = 0;
		public double ssc14_dmg_caustic = 0;
		public double ssc14_dmg_poison = 0;
		public double ssc14_dmg_radiation = 0;
		public double ssc14_dmg_asphyx = 0.0;
		public double ssc14_dmg_bloodloss = 0;
		public double ssc14_dmg_cellular = 0;

		@Override
		public void serialize(ValueOutput output) {
			output.putDouble("sscCustomHealth", sscCustomHealth);
			output.putDouble("ssc14_dmg_blunt", ssc14_dmg_blunt);
			output.putDouble("ssc14_dmg_slash", ssc14_dmg_slash);
			output.putDouble("ssc14_dmg_piercing", ssc14_dmg_piercing);
			output.putDouble("ssc14_dmg_heat", ssc14_dmg_heat);
			output.putDouble("ssc14_dmg_shock", ssc14_dmg_shock);
			output.putDouble("ssc14_dmg_cold", ssc14_dmg_cold);
			output.putDouble("ssc14_dmg_caustic", ssc14_dmg_caustic);
			output.putDouble("ssc14_dmg_poison", ssc14_dmg_poison);
			output.putDouble("ssc14_dmg_radiation", ssc14_dmg_radiation);
			output.putDouble("ssc14_dmg_asphyx", ssc14_dmg_asphyx);
			output.putDouble("ssc14_dmg_bloodloss", ssc14_dmg_bloodloss);
			output.putDouble("ssc14_dmg_cellular", ssc14_dmg_cellular);
		}

		@Override
		public void deserialize(ValueInput input) {
			sscCustomHealth = input.getDoubleOr("sscCustomHealth", 0);
			ssc14_dmg_blunt = input.getDoubleOr("ssc14_dmg_blunt", 0);
			ssc14_dmg_slash = input.getDoubleOr("ssc14_dmg_slash", 0);
			ssc14_dmg_piercing = input.getDoubleOr("ssc14_dmg_piercing", 0);
			ssc14_dmg_heat = input.getDoubleOr("ssc14_dmg_heat", 0);
			ssc14_dmg_shock = input.getDoubleOr("ssc14_dmg_shock", 0);
			ssc14_dmg_cold = input.getDoubleOr("ssc14_dmg_cold", 0);
			ssc14_dmg_caustic = input.getDoubleOr("ssc14_dmg_caustic", 0);
			ssc14_dmg_poison = input.getDoubleOr("ssc14_dmg_poison", 0);
			ssc14_dmg_radiation = input.getDoubleOr("ssc14_dmg_radiation", 0);
			ssc14_dmg_asphyx = input.getDoubleOr("ssc14_dmg_asphyx", 0);
			ssc14_dmg_bloodloss = input.getDoubleOr("ssc14_dmg_bloodloss", 0);
			ssc14_dmg_cellular = input.getDoubleOr("ssc14_dmg_cellular", 0);
		}

		public void markSyncDirty() {
			_syncDirty = true;
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data, int player) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> {
			TagValueOutput output = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
			message.data.serialize(output);
			buffer.writeInt(message.player());
			buffer.writeNbt(output.buildResult());
		}, (RegistryFriendlyByteBuf buffer) -> {
			PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables(), buffer.readInt());
			message.data.deserialize(TagValueInput.create(ProblemReporter.DISCARDING, buffer.registryAccess(), buffer.readNbt()));
			return message;
		});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					Entity player = context.player().level().getEntity(message.player);
					if (player == null)
						return;
					TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, context.player().registryAccess());
					message.data.serialize(output);
					player.getData(PLAYER_VARIABLES).deserialize(TagValueInput.create(ProblemReporter.DISCARDING, context.player().registryAccess(), output.buildResult()));
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}