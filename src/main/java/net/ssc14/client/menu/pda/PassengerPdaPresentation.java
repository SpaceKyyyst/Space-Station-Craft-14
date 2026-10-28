package net.ssc14.client.menu.pda;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.ssc14.common.station.StationCodeService;
import net.ssc14.common.station.StationNameService;

/** Immutable passenger-PDA render state built from one held-item capability read. */
record PassengerPdaPresentation(
        boolean penPresent,
        boolean idPresent,
        boolean flashlight,
        int tab,
        String identity,
        String stationName,
        String alertText,
        int alertColor) {

    static PassengerPdaPresentation read(Player player, LevelAccessor level) {
        ItemStack pda = player.getMainHandItem();
        ItemStack idCard = ItemStack.EMPTY;
        ItemStack pen = ItemStack.EMPTY;
        var handler = pda.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(pda));
        if (handler != null) {
            if (handler.size() > 0) idCard = ItemUtil.getStack(handler, 0);
            if (handler.size() > 1) pen = ItemUtil.getStack(handler, 1);
        }

        var pdaData = pda.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        int tab = (int) pdaData.getDoubleOr("Tab", 0);
        String stationName = StationNameService.current(level);
        if (stationName.isEmpty()) stationName = "N/D";
        var alert = alertForCode(StationCodeService.current(level));
        return new PassengerPdaPresentation(
                !pen.isEmpty(), !idCard.isEmpty(), pdaData.getBooleanOr("flashlight", false),
                tab, identity(idCard), stationName, alert.text(), alert.color());
    }

    boolean mainTab() {
        return tab == 1;
    }

    boolean secondTab() {
        return tab == 2;
    }

    boolean thirdTab() {
        return tab == 3;
    }

    private static String identity(ItemStack idCard) {
        if (idCard.isEmpty()) return "N/D";
        var data = idCard.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        String username = data.getStringOr("ID_NBT_username", "");
        String job = data.getStringOr("ID_NBT_jobname", "");
        return username.isEmpty() || job.isEmpty() ? "N/D" : username + ", " + job;
    }

    static Alert alertForCode(int code) {
        return switch (code) {
            case 0 -> new Alert("N/D", -1);
            case 1 -> translated("translation.key.pda_text_danger_level_green", -14585048);
            case 2 -> translated("translation.key.pda_text_danger_level_yellow", -5668859);
            case 3 -> translated("translation.key.pda_text_danger_level_blue", -15907190);
            case 4 -> translated("translation.key.pda_text_danger_level_red", -7272183);
            case 5 -> translated("translation.key.pda_text_danger_level_purple", -6477674);
            case 6 -> translated("translation.key.pda_text_danger_level_gamma", -5157766);
            case 7 -> translated("translation.key.pda_text_danger_level_delta", -10878968);
            case 8 -> translated("translation.key.pda_text_danger_level_epsilon", -12316287);
            default -> new Alert("", -1);
        };
    }

    private static Alert translated(String key, int color) {
        return new Alert(Component.translatable(key).getString(), color);
    }

    record Alert(String text, int color) {
    }
}
