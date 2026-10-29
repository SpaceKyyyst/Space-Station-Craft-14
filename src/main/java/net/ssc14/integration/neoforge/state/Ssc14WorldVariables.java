package net.ssc14.integration.neoforge.state;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.ssc14.common.Ssc14Identity;
import net.ssc14.integration.neoforge.player.Ssc14PlayerVariablesSyncPayload;
import net.ssc14.integration.neoforge.network.Ssc14NetworkPayloads;

@EventBusSubscriber
public class Ssc14WorldVariables {

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		Ssc14NetworkPayloads.addClientbound(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC, SavedDataSyncMessage::handleData);
		Ssc14NetworkPayloads.addClientbound(Ssc14PlayerVariablesSyncPayload.TYPE, Ssc14PlayerVariablesSyncPayload.STREAM_CODEC, Ssc14PlayerVariablesSyncPayload::handleData);
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
		public static final Identifier DATA_ID = Identifier.parse("ssc_14:worldvars");
		public static final SavedDataType<WorldVariables> TYPE = new SavedDataType<>(DATA_ID, level -> new WorldVariables(), level -> CompoundTag.CODEC.xmap(tag -> {
			WorldVariables instance = new WorldVariables();
			instance.read(tag, level.registryAccess());
			return instance;
		}, instance -> instance.save(new CompoundTag(), level.registryAccess())));
		boolean _syncDirty = false;
		public double Station_Budget = 0;
		public String PalletRegistry = "\"\"";

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			Station_Budget = nbt.getDoubleOr("Station_Budget", 0);
			PalletRegistry = nbt.getStringOr("PalletRegistry", "");
		}

		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			nbt.putDouble("Station_Budget", Station_Budget);
			nbt.putString("PalletRegistry", PalletRegistry);
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
		public static final Identifier DATA_ID = Identifier.parse("ssc_14:mapvars");
		public static final SavedDataType<MapVariables> TYPE = new SavedDataType<>(DATA_ID, level -> new MapVariables(), level -> CompoundTag.CODEC.xmap(tag -> {
			MapVariables instance = new MapVariables();
			instance.read(tag, level.registryAccess());
			return instance;
		}, instance -> instance.save(new CompoundTag(), level.registryAccess())));
		boolean _syncDirty = false;
		public double station_code = 0.0;
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
		public static final Type<SavedDataSyncMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, "saved_data_sync"));
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

}
