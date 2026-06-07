package net.mcreator.ssc.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
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
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.ProblemReporter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.mcreator.ssc.Ssc14Mod;

import java.util.function.Supplier;

@EventBusSubscriber
public class Ssc14ModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Ssc14Mod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());
	public static boolean station_gravity = false;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		Ssc14Mod.addNetworkMessage(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC, SavedDataSyncMessage::handleData);
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

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			SavedData mapdata = MapVariables.get(event.getEntity().level());
			SavedData worlddata = WorldVariables.get(event.getEntity().level());
			if (mapdata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(0, mapdata));
			if (worlddata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			SavedData worlddata = WorldVariables.get(event.getEntity().level());
			if (worlddata != null)
				PacketDistributor.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
		}
	}

	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		if (event.getLevel() instanceof ServerLevel level) {
			WorldVariables worldVariables = WorldVariables.get(level);
			if (worldVariables._syncDirty) {
				PacketDistributor.sendToPlayersInDimension(level, new SavedDataSyncMessage(1, worldVariables));
				worldVariables._syncDirty = false;
			}
			MapVariables mapVariables = MapVariables.get(level);
			if (mapVariables._syncDirty) {
				PacketDistributor.sendToAllPlayers(new SavedDataSyncMessage(0, mapVariables));
				mapVariables._syncDirty = false;
			}
		}
	}

	public static class WorldVariables extends SavedData {
		public static final SavedDataType<WorldVariables> TYPE = new SavedDataType<>("ssc_14_worldvars", ctx -> new WorldVariables(), ctx -> CompoundTag.CODEC.xmap(tag -> {
			WorldVariables instance = new WorldVariables();
			instance.read(tag, ctx.levelOrThrow().registryAccess());
			return instance;
		}, instance -> instance.save(new CompoundTag(), ctx.levelOrThrow().registryAccess())));
		boolean _syncDirty = false;

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		}

		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			return nbt;
		}

		public void markSyncDirty() {
			this.setDirty();
			this._syncDirty = true;
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(WorldVariables.TYPE);
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends SavedData {
		public static final SavedDataType<MapVariables> TYPE = new SavedDataType<>("ssc_14_mapvars", ctx -> new MapVariables(), ctx -> CompoundTag.CODEC.xmap(tag -> {
			MapVariables instance = new MapVariables();
			instance.read(tag, ctx.levelOrThrow().registryAccess());
			return instance;
		}, instance -> instance.save(new CompoundTag(), ctx.levelOrThrow().registryAccess())));
		boolean _syncDirty = false;
		public double station_code = 0;
		public String station_name = "\"\"";
		public boolean Base_Radio_Frequency = false;
		public boolean Command_Radio_Frequency = false;
		public boolean Engeneer_Radio_Frequency = false;
		public boolean Medical_Radio_Frequency = false;
		public boolean RND_Radio_Frequency = false;
		public boolean Sec_Radio_Frequency = false;
		public boolean Serv_Radio_Frequency = false;
		public boolean Cargo_Radio_Frequency = false;

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			station_code = nbt.getDoubleOr("station_code", 0);
			station_name = nbt.getStringOr("station_name", "");
			Base_Radio_Frequency = nbt.getBooleanOr("Base_Radio_Frequency", false);
			Command_Radio_Frequency = nbt.getBooleanOr("Command_Radio_Frequency", false);
			Engeneer_Radio_Frequency = nbt.getBooleanOr("Engeneer_Radio_Frequency", false);
			Medical_Radio_Frequency = nbt.getBooleanOr("Medical_Radio_Frequency", false);
			RND_Radio_Frequency = nbt.getBooleanOr("RND_Radio_Frequency", false);
			Sec_Radio_Frequency = nbt.getBooleanOr("Sec_Radio_Frequency", false);
			Serv_Radio_Frequency = nbt.getBooleanOr("Serv_Radio_Frequency", false);
			Cargo_Radio_Frequency = nbt.getBooleanOr("Cargo_Radio_Frequency", false);
		}

		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			nbt.putDouble("station_code", station_code);
			nbt.putString("station_name", station_name);
			nbt.putBoolean("Base_Radio_Frequency", Base_Radio_Frequency);
			nbt.putBoolean("Command_Radio_Frequency", Command_Radio_Frequency);
			nbt.putBoolean("Engeneer_Radio_Frequency", Engeneer_Radio_Frequency);
			nbt.putBoolean("Medical_Radio_Frequency", Medical_Radio_Frequency);
			nbt.putBoolean("RND_Radio_Frequency", RND_Radio_Frequency);
			nbt.putBoolean("Sec_Radio_Frequency", Sec_Radio_Frequency);
			nbt.putBoolean("Serv_Radio_Frequency", Serv_Radio_Frequency);
			nbt.putBoolean("Cargo_Radio_Frequency", Cargo_Radio_Frequency);
			return nbt;
		}

		public void markSyncDirty() {
			this.setDirty();
			this._syncDirty = true;
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAccessor) {
				return serverLevelAccessor.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(MapVariables.TYPE);
			} else {
				return clientSide;
			}
		}
	}

	public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
		public static final Type<SavedDataSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Ssc14Mod.MODID, "saved_data_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SavedDataSyncMessage message) -> {
			buffer.writeInt(message.dataType);
			if (message.data instanceof MapVariables mapVariables)
				buffer.writeNbt(mapVariables.save(new CompoundTag(), buffer.registryAccess()));
			else if (message.data instanceof WorldVariables worldVariables)
				buffer.writeNbt(worldVariables.save(new CompoundTag(), buffer.registryAccess()));
		}, (RegistryFriendlyByteBuf buffer) -> {
			int dataType = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			SavedData data = null;
			if (nbt != null) {
				data = dataType == 0 ? new MapVariables() : new WorldVariables();
				if (data instanceof MapVariables mapVariables)
					mapVariables.read(nbt, buffer.registryAccess());
				else if (data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt, buffer.registryAccess());
			}
			return new SavedDataSyncMessage(dataType, data);
		});

		@Override
		public Type<SavedDataSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final SavedDataSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					if (message.dataType == 0)
						MapVariables.clientSide.read(((MapVariables) message.data).save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
					else
						WorldVariables.clientSide.read(((WorldVariables) message.data).save(new CompoundTag(), context.player().registryAccess()), context.player().registryAccess());
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}

	public static class PlayerVariables implements ValueIOSerializable {
		boolean _syncDirty = false;
		public double sscCustomHealth = 0.0;
		public double ssc14_dmg_blunt = 0.0;
		public double ssc14_dmg_slash = 0.0;
		public double ssc14_dmg_piercing = 0.0;
		public double ssc14_dmg_heat = 0.0;
		public double ssc14_dmg_shock = 0.0;
		public double ssc14_dmg_cold = 0.0;
		public double ssc14_dmg_caustic = 0.0;
		public double ssc14_dmg_poison = 0.0;
		public double ssc14_dmg_radiation = 0.0;
		public double ssc14_dmg_asphyx = 0.0;
		public double ssc14_dmg_bloodloss = 0.0;
		public double ssc14_dmg_cellular = 0.0;

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