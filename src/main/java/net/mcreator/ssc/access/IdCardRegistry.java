package net.mcreator.ssc.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.world.item.Item;

/** Default permissions for registered ID card items; initialized after item registration. */
public final class IdCardRegistry {
    public static final List<String> KEYS = List.of(
            "gun_room", "HoS", "Brig", "Security", "Detective", "PNT", "Crio", "CE",
            "Atmos", "Ingeneer", "Out", "Qm", "Utilizat", "Supply_Deportament", "CMO",
            "Chemistry", "Medical", "RD", "Scientist", "Technical", "Church", "HoP",
            "Service", "Kitchen", "Gidroponic", "Bar", "Teatre", "Cleaner", "Capitan",
            "Command", "Blue_Sh", "Uridic"
    );


    private static final Map<Item, Long> DEFAULTS = new HashMap<>();
    private static boolean initialized;
    private IdCardRegistry() { }

    public static Long defaults(Item item) {
        initialize();
        return DEFAULTS.get(item);
    }

    private static void initialize() {
        if (initialized) return;
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

        initialized = true;
    }

    private static void addMap(Supplier<Item> supplier, String... accesses) {
        long mask = 0;
        for (String access : accesses) {
            int index = KEYS.indexOf(access);
            if (index < 0 || index >= Long.SIZE) throw new IllegalArgumentException("Unknown access: " + access);
            mask |= 1L << index;
        }
        DEFAULTS.put(supplier.get(), mask);
    }
}
