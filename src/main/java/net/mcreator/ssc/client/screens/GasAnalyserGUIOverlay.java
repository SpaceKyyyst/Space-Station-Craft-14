package net.mcreator.ssc.client.screens;

import net.ssc14.common.atmosphere.model.GasType;
import net.mcreator.ssc.client.GasAnalyserReadings;
import net.ssc14.content.item.tool.GasAnalyserOverlayService;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = "ssc_14")
public final class GasAnalyserGUIOverlay {
    private static final int PANEL_WIDTH = 236;
    private static final int ROW_HEIGHT = 12;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private GasAnalyserGUIOverlay() { }

    @SubscribeEvent
    public static void render(RenderGuiEvent.Pre event) {
        var minecraft = Minecraft.getInstance();
        if (!GasAnalyserOverlayService.isVisible(minecraft.player)) return;
        var graphics = event.getGuiGraphics();
        int x = 6;
        int y = Math.max(6, (graphics.guiHeight() - 186) / 2);
        graphics.fill(x, y, x + PANEL_WIDTH, y + 186, 0xD0101420);
        graphics.text(minecraft.font, "V: 1000 L", x + 6, y + 6, TEXT_COLOR, false);
        graphics.text(minecraft.font, GasAnalyserReadings.pressure(), x + 94, y + 6, TEXT_COLOR, false);
        graphics.text(minecraft.font, GasAnalyserReadings.temperature(), x + 158, y + 6, TEXT_COLOR, false);
        graphics.text(minecraft.font, "Gas", x + 6, y + 24, TEXT_COLOR, false);
        graphics.text(minecraft.font, "mol", x + 132, y + 24, TEXT_COLOR, false);
        graphics.text(minecraft.font, "%", x + 190, y + 24, TEXT_COLOR, false);
        for (GasType gas : GasType.values()) {
            int rowY = y + 42 + gas.ordinal() * ROW_HEIGHT;
            graphics.text(minecraft.font, gas.getName(), x + 6, rowY, TEXT_COLOR, false);
            graphics.text(minecraft.font, GasAnalyserReadings.amount(gas.ordinal()), x + 132, rowY, TEXT_COLOR, false);
            graphics.text(minecraft.font, GasAnalyserReadings.percent(gas.ordinal()), x + 190, rowY, TEXT_COLOR, false);
        }
    }
}
