
package net.mcreator.ssc.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.mcreator.ssc.init.Ssc14ModItems;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

@EventBusSubscriber(modid = "ssc_14")
public class IDCardAccessProcedure {

    private static final Logger LOGGER = LoggerFactory.getLogger(IDCardAccessProcedure.class);

    private static final List<String> ALL_ACCESSES = List.of(
            "gun_room", "HoS", "Brig", "Security", "Detective", "PNT", "Crio", "CE",
            "Atmos", "Ingeneer", "Out", "Qm", "Utilizat", "Supply_Deportament", "CMO",
            "Chemistry", "Medical", "RD", "Scientist", "Technical", "Church", "HoP",
            "Service", "Kitchen", "Gidroponic", "Bar", "Teatre", "Cleaner", "Capitan",
            "Command", "Blue_Sh", "Uridic"
    );

    private static final Map<Supplier<Item>, String[]> CARD_ACCESS_MAP = new HashMap<>();
    private static boolean mapInitialized = false;

    private static void initCardMap() {
        if (mapInitialized) return;
        
        addMap(Ssc14ModItems.ID_CARD_CAPITAN, "gun_room", "HoS", "Brig", "Security", "Detective", "PNT", "Crio", "CE", "Atmos", "Ingeneer", "Out", "Qm", "Utilizat", "Supply_Deportament", "CMO", "Chemistry", "Medical", "RD", "Scientist", "Technical", "Church", "HoP", "Service", "Kitchen", "Gidroponic", "Bar", "Teatre", "Cleaner", "Capitan", "Command", "Blue_Sh", "Uridic");
        addMap(Ssc14ModItems.ID_CARD_PNT, "Brig", "Security", "Detective", "PNT", "Crio", "Atmos", "Ingeneer", "Utilizat", "Supply_Deportament", "Chemistry", "Medical", "Scientist", "Technical", "Church", "Service", "Kitchen", "Gidroponic", "Bar", "Teatre", "Cleaner", "Command", "Uridic");
        addMap(Ssc14ModItems.ID_CARD_ADUTANT, "Brig", "Ingeneer", "Supply_Deportament", "Medical", "Scientist", "Technical", "Church", "Service", "Kitchen", "Gidroponic", "Bar", "Teatre", "Cleaner", "Command");
        addMap(Ssc14ModItems.ID_CARD_AVD, "Brig", "Out", "Technical", "Service", "Command", "Uridic");
        addMap(Ssc14ModItems.ID_CARD_OSH, "Brig", "Security", "Detective", "Atmos", "Ingeneer", "Out", "Utilizat", "Supply_Deportament", "Chemistry", "Medical", "Scientist", "Technical", "Church", "Service", "Kitchen", "Gidroponic", "Bar", "Teatre", "Cleaner", "Command", "Blue_Sh");
        addMap(Ssc14ModItems.ID_CARD_HO_S, "gun_room", "HoS", "Brig", "Security", "Detective", "Crio", "Out", "Service", "Command");
        addMap(Ssc14ModItems.ID_CARD_WARD, "gun_room", "Brig", "Security", "Detective", "Crio", "Out", "Service");
        addMap(Ssc14ModItems.ID_CARD_INSTRUCTOR_S, "Brig", "Security", "Crio", "Out", "Service");
        addMap(Ssc14ModItems.ID_CARD_BRIG_MED, "Brig", "Security", "Crio", "Out", "Medical", "Technical");
        addMap(Ssc14ModItems.ID_CARD_DETECTIVE, "Brig", "Security", "Detective", "Crio", "Out", "Technical", "Service");
        addMap(Ssc14ModItems.ID_CARD_OFICER_S, "Brig", "Security", "Crio", "Out", "Service");
        addMap(Ssc14ModItems.ID_CARD_KADET_S, "Brig", "Security", "Crio", "Out", "Service");
        addMap(Ssc14ModItems.ID_CARD_HO_P, "Brig", "Security", "Detective", "Crio", "Atmos", "Ingeneer", "Out", "Utilizat", "Supply_Deportament", "Chemistry", "Medical", "Scientist", "Technical", "Church", "HoP", "Service", "Kitchen", "Gidroponic", "Bar", "Teatre", "Cleaner", "Command", "Uridic");
        addMap(Ssc14ModItems.ID_CARD_CLOWN, "Technical", "Teatre");
        addMap(Ssc14ModItems.ID_CARD_MIME, "Technical", "Teatre");
        addMap(Ssc14ModItems.ID_CARD_MUSICANT, "Technical", "Teatre");
        addMap(Ssc14ModItems.ID_CARD_CHEF, "Technical", "Service", "Kitchen");
        addMap(Ssc14ModItems.ID_CARD_BARMEN, "Technical", "Service", "Bar");
        addMap(Ssc14ModItems.ID_CARD_BIBLIOTEKAR, "Technical", "Service");
        addMap(Ssc14ModItems.ID_CARD_BOTANIK, "Technical", "Service", "Gidroponic");
        addMap(Ssc14ModItems.ID_CARD_BOXER, "Technical", "Service");
        addMap(Ssc14ModItems.ID_CARD_REPORTER, "Technical", "Service");
        addMap(Ssc14ModItems.ID_CARD_PRIEST, "Technical", "Church");
        addMap(Ssc14ModItems.ID_CARD_ZOOTECHNIK, "Technical", "Service");
        addMap(Ssc14ModItems.ID_CARD_CLEANER, "Technical", "Service", "Cleaner");
        addMap(Ssc14ModItems.ID_CARD_SERVICE_W, "Technical", "Service", "Kitchen", "Bar");
        addMap(Ssc14ModItems.ID_CARD_CMO, "Brig", "Crio", "CMO", "Chemistry", "Medical", "Technical", "Command");
        addMap(Ssc14ModItems.ID_CARD_VM, "Medical", "Technical");
        addMap(Ssc14ModItems.ID_CARD_CHIMIC, "Chemistry", "Medical", "Technical");
        addMap(Ssc14ModItems.ID_CARD_PARAMED, "Medical", "Technical");
        addMap(Ssc14ModItems.ID_CARD_MEDIC, "Medical", "Technical");
        addMap(Ssc14ModItems.ID_CARD_PSIHOLOG, "Medical", "Technical");
        addMap(Ssc14ModItems.ID_CARD_INTERN, "Medical", "Technical");
        addMap(Ssc14ModItems.ID_CARD_CE, "Brig", "Crio", "Technical", "Command", "CE", "Atmos", "Ingeneer", "Out");
        addMap(Ssc14ModItems.ID_CARD_VE, "Atmos", "Ingeneer", "Out", "Technical");
        addMap(Ssc14ModItems.ID_CARD_ATMOS, "Atmos", "Ingeneer", "Out", "Technical");
        addMap(Ssc14ModItems.ID_CARD_ENGENEER, "Ingeneer", "Out", "Technical");
        addMap(Ssc14ModItems.ID_CARD_TECH_ASSISTENT, "Ingeneer", "Out", "Technical");
        addMap(Ssc14ModItems.ID_CARD_RD, "Brig", "Crio", "RD", "Scientist", "Technical", "Command");
        addMap(Ssc14ModItems.ID_CARD_VS, "Scientist", "Technical");
        addMap(Ssc14ModItems.ID_CARD_SCIENTIST, "Scientist", "Technical");
        addMap(Ssc14ModItems.ID_CARD_SCI_ASSISTENT, "Scientist", "Technical");
        addMap(Ssc14ModItems.ID_CARD_QM, "Brig", "Crio", "Out", "Qm", "Utilizat", "Supply_Deportament", "Technical", "Command");
        addMap(Ssc14ModItems.ID_CARD_UTILIZATOR, "Out", "Utilizat", "Supply_Deportament", "Technical");
        addMap(Ssc14ModItems.ID_CARD_LOADER, "Supply_Deportament", "Technical");
        addMap(Ssc14ModItems.ID_CARD_PASSANGER, "Technical");
        
        mapInitialized = true;
    }

    private static void addMap(Supplier<Item> itemSupplier, String... accesses) {
        CARD_ACCESS_MAP.put(itemSupplier, accesses);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            initCardMap();
            Set<String> currentAccesses = new HashSet<>();

            // 1. Vanilla Inventory (руки, броня, основной инвентарь)
            Inventory inv = player.getInventory();
            for (int i = 0; i < inv.getContainerSize(); i++) {
                collectAccesses(inv.getItem(i), currentAccesses);
            }

            // 2. Curios API (PDA слот и другие кастомные слоты)
			try {
			    CuriosApi.getCuriosInventory(player).ifPresent(invCurios -> {
			        for (ICurioStacksHandler handler : invCurios.getCurios().values()) {
			            // В Curios 12.x получаем стандартный IItemHandler из слота
			            IItemHandler curiosHandler = handler.getStacks();
			            if (curiosHandler != null) {
			                for (int i = 0; i < curiosHandler.getSlots(); i++) {
			                    collectAccesses(curiosHandler.getStackInSlot(i), currentAccesses);
			                }
			            }
			        }
			    });
			} catch (Exception e) {
                // Игнорируем ошибки Curios API для стабильности
            }

            // 3. Синхронизация с NBT игрока
            CompoundTag data = player.getPersistentData();
            boolean changed = false;
            for (String key : ALL_ACCESSES) {
                boolean shouldBe = currentAccesses.contains(key);
                boolean current = data.getBoolean(key).orElse(false);
                if (current != shouldBe) {
                    data.putBoolean(key, shouldBe);
                    changed = true;
                }
            }
            if (changed) {
                LOGGER.debug("[SSC14-ID] Access synced for {} | Active: {}", player.getName().getString(), currentAccesses);
            }
        }
    }

    /**
     * Рекурсивный сбор доступов. Проверяет сам предмет и вложенные контейнеры.
     */
    private static void collectAccesses(ItemStack stack, Set<String> collected) {
        if (stack.isEmpty()) return;

        // Инициализация NBT карты
        initCardNBT(stack);

        // Если это ID-карта, читаем доступы
        if (isIDCard(stack)) {
            collected.addAll(getAccesses(stack));
        }

        // Проверка стандартных контейнеров (бандлы, шалкеры)
        ItemContainerContents container = stack.get(DataComponents.CONTAINER);
        if (container != null) {
            container.stream().forEach(item -> collectAccesses(item, collected));
        }

        // 🔧 TODO: Когда создашь КПК, добавь сюда проверку его инвентаря.
        // Пример:
        // if (stack.getItem() == Ssc14ModItems.PDA_ITEM.get()) {
        //     PdaComponent pdaData = stack.get(Ssc14Components.PDA_DATA.get());
        //     if (pdaData != null) pdaData.getInventory().forEach(item -> collectAccesses(item, collected));
        // }
    }

    private static boolean isIDCard(ItemStack stack) {
        return CARD_ACCESS_MAP.keySet().stream().anyMatch(supplier -> supplier.get() == stack.getItem());
    }

    private static void initCardNBT(ItemStack stack) {
        if (stack.isEmpty() || !isIDCard(stack)) return;

        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();

        if (tag.getBoolean("spawned").orElse(false)) return;

        String[] accesses = null;
        for (Map.Entry<Supplier<Item>, String[]> entry : CARD_ACCESS_MAP.entrySet()) {
            if (entry.getKey().get() == stack.getItem()) {
                accesses = entry.getValue();
                break;
            }
        }
        if (accesses == null) return;

        CompoundTag newTag = tag.copy();
        newTag.putBoolean("spawned", true);
        for (String access : accesses) {
            newTag.putBoolean(access, true);
        }

        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(newTag));
    }

    private static Set<String> getAccesses(ItemStack stack) {
        if (stack.isEmpty()) return Collections.emptySet();

        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!tag.getBoolean("spawned").orElse(false)) return Collections.emptySet();

        Set<String> result = new HashSet<>();
        for (String key : ALL_ACCESSES) {
            if (tag.getBoolean(key).orElse(false)) {
                result.add(key);
            }
        }
        return result;
    }
}
