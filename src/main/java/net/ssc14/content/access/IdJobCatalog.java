package net.ssc14.content.access;

import java.util.List;

/** Immutable legacy-compatible job names and access masks for ID console buttons. */
public final class IdJobCatalog {
    public record Definition(int buttonId, String name, long accessMask) {
        public boolean grants(int accessIndex) {
            return accessIndex >= 0 && accessIndex < ACCESS_KEYS.size()
                    && (accessMask & (1L << accessIndex)) != 0;
        }
    }

    private static final List<String> ACCESS_KEYS = List.of(
            "gun_room", "HoS", "Brig", "Security", "Detective", "PNT", "Crio", "CE",
            "Atmos", "Ingeneer", "Out", "Qm", "Utilizat", "Supply_Deportament", "CMO",
            "Chemistry", "Medical", "RD", "Scientist", "Technical", "Church", "HoP",
            "Service", "Kitchen", "Gidroponic", "Bar", "Teatre", "Cleaner", "Capitan",
            "Command", "Blue_Sh", "Uridic");

    private static final Definition[] DEFINITIONS = {
            definition(0, "Капитан", 0xFFFFFFFFL),
            definition(1, "Представитель NT", 0xAFDDB37CL),
            definition(2, "Глава персонала", 0xAFFDB75CL),
            definition(3, "Глава СБ", 0x2040045FL),
            definition(4, "Главврач", 0x2009C044L),
            definition(5, "Квартирмейстер", 0x20083C44L),
            definition(6, "Грузчик", 0x00082000L),
            definition(7, "Утилизатор", 0x00083400L),
            definition(8, "Пассажир", 0x00080000L),
            definition(9, "Научный руководитель", 0x200E0044L),
            definition(10, "Ведущий учёный", 0x000C0000L),
            definition(11, "Учёный", 0x000C0000L),
            definition(12, "Научный ассистент", 0x000C0000L),
            definition(13, "Старший инженер", 0x200807C4L),
            definition(14, "Атмосферный техник", 0x00080700L),
            definition(15, "Ведущий инженер", 0x00080700L),
            definition(16, "Технический ассистент", 0x00080600L),
            definition(17, "Инженер", 0x00080600L),
            definition(18, "Парамедик", 0x00090000L),
            definition(19, "Химик", 0x00098000L),
            definition(20, "Врач", 0x00090000L),
            definition(21, "Ведущий врач", 0x00090000L),
            definition(22, "Интерн", 0x00090000L),
            definition(23, "Кадет СБ", 0x0040044CL),
            definition(24, "Инструктор СБ", 0x0040044CL),
            definition(25, "Офицер СБ", 0x0040044CL),
            definition(26, "Детектив", 0x0048045CL),
            definition(27, "Психолог", 0x00090000L),
            definition(28, "Бригмедик", 0x0009044CL),
            definition(29, "Мим", 0x04080000L),
            definition(30, "Смотритель", 0x0040045DL),
            definition(31, "Офицер \"Синий щит\"", 0x6FDDB71CL),
            definition(32, "Адъютант", 0x2FDD2204L),
            definition(33, "Клоун", 0x04080000L),
            definition(34, "Музыкант", 0x04080000L),
            definition(35, "Зоотехник", 0x00480000L),
            definition(36, "Библиотекарь", 0x00480000L),
            definition(37, "Сервисный работник", 0x02C80000L),
            definition(38, "Уборщик", 0x08480000L),
            definition(39, "Шеф-повар", 0x00C80000L),
            definition(40, "Ботаник", 0x01480000L),
            definition(41, "Бармен", 0x02480000L),
            definition(42, "Боксёр", 0x00480000L),
            definition(43, "Священник", 0x00180000L),
            definition(44, "Репортёр", 0x00480000L),
            definition(45, "Агент внутренних дел", 0xA0480404L)
    };

    private IdJobCatalog() { }

    public static Definition definition(int buttonId) {
        return buttonId >= 0 && buttonId < DEFINITIONS.length ? DEFINITIONS[buttonId] : null;
    }

    public static List<String> accessKeys() {
        return ACCESS_KEYS;
    }

    public static int size() {
        return DEFINITIONS.length;
    }

    private static Definition definition(int buttonId, String name, long accessMask) {
        return new Definition(buttonId, name, accessMask);
    }
}
