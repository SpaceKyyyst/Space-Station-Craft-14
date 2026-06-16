
package net.mcreator.ssc.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.mcreator.ssc.world.inventory.NetConfigGUIMenu;
import net.mcreator.ssc.network.NetConfigGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

import java.util.Map;

public class NetConfigGUIScreen extends AbstractContainerScreen<NetConfigGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("ssc_14:textures/screens/net_config_gui_2.png");
    private static final ResourceLocation BUTTON_TEX = ResourceLocation.parse("ssc_14:textures/screens/net_config_gui_button.png");

    public NetConfigGUIScreen(NetConfigGUIMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 250;
        this.imageHeight = 160;
    }

    @Override
    public void updateMenuState(int elementType, String name, Object elementState) {
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        // Сначала рисуем всё стандартное (фон, кнопки и т.д.)
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        
        // Теперь рисуем текст ПОВЕРХ кнопок
        drawAllText(guiGraphics);
        
        // Рисуем линии связей
        drawConnections(guiGraphics);
        
        // Тултипы в самом конце
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

	private void drawAllText(GuiGraphics guiGraphics) {
	    Map<String, Object> state = this.menu.getMenuState();
	
	    // Левая колонка - добавляем leftPos и topPos
	    drawStateText(guiGraphics, state, "L1_text", this.leftPos + 15, this.topPos + 20);
	    drawStateText(guiGraphics, state, "L2_text", this.leftPos + 15, this.topPos + 36);
	    drawStateText(guiGraphics, state, "L3_text", this.leftPos + 15, this.topPos + 52);
	    drawStateText(guiGraphics, state, "L4_text", this.leftPos + 15, this.topPos + 68);
	    drawStateText(guiGraphics, state, "L5_text", this.leftPos + 15, this.topPos + 84);
	    drawStateText(guiGraphics, state, "L6_text", this.leftPos + 15, this.topPos + 100);
	    drawStateText(guiGraphics, state, "L7_text", this.leftPos + 15, this.topPos + 116);
	    drawStateText(guiGraphics, state, "L8_text", this.leftPos + 15, this.topPos + 132);
	
	    // Правая колонка
	    drawStateText(guiGraphics, state, "R1_text", this.leftPos + 165, this.topPos + 20);
	    drawStateText(guiGraphics, state, "R2_text", this.leftPos + 165, this.topPos + 36);
	    drawStateText(guiGraphics, state, "R3_text", this.leftPos + 165, this.topPos + 52);
	    drawStateText(guiGraphics, state, "R4_text", this.leftPos + 165, this.topPos + 68);
	    drawStateText(guiGraphics, state, "R5_text", this.leftPos + 165, this.topPos + 84);
	    drawStateText(guiGraphics, state, "R6_text", this.leftPos + 165, this.topPos + 100);
	    drawStateText(guiGraphics, state, "R7_text", this.leftPos + 165, this.topPos + 116);
	    drawStateText(guiGraphics, state, "R8_text", this.leftPos + 165, this.topPos + 132);
	}

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos, this.topPos, 0, 0, 250, 160, 250, 160);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // ПУСТО! Текст теперь рисуется в render() после кнопок
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    private void drawStateText(GuiGraphics guiGraphics, Map<String, Object> state, String key, int x, int y) {
        Object val = state.get(key);
        if (val instanceof String text && !text.isEmpty()) {
            // БЕЛЫЙ ТЕКСТ НА КНОПКАХ
            guiGraphics.drawString(this.font, text, x, y, 0xFFFFFFFF, false);
        }
    }

    private boolean isButtonActive(Map<String, Object> state, String key) {
        Object val = state.get(key + "_active");
        return val instanceof Boolean b && b;
    }

    private void drawConnections(GuiGraphics guiGraphics) {
        Map<String, Object> state = this.menu.getMenuState();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Boolean hasConnection = (Boolean) state.get("conn_" + i + "_" + j);
                if (Boolean.TRUE.equals(hasConnection)) {
                    int x1 = this.leftPos + 89;
                    int y1 = this.topPos + 23 + (i * 16) + 5;
                    int x2 = this.leftPos + 161;
                    int y2 = this.topPos + 23 + (j * 16) + 5;
                    
                    drawLine(guiGraphics, x1, y1, x2, y2, 0xFFFF5A00);
                }
            }
        }
    }

    private void drawLine(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            guiGraphics.fill(RenderPipelines.GUI, x1, y1, x1 + 2, y1 + 2, color);
            
            if (x1 == x2 && y1 == y2) break;
            
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    @Override
    public void init() {
        super.init();
        Map<String, Object> state = this.menu.getMenuState();

        addButton(11, 18, 0, "L1", state);
        addButton(11, 34, 1, "L2", state);
        addButton(11, 50, 2, "L3", state);
        addButton(11, 66, 3, "L4", state);
        addButton(11, 82, 4, "L5", state);
        addButton(11, 98, 5, "L6", state);
        addButton(11, 114, 6, "L7", state);
        addButton(11, 130, 7, "L8", state);

        addButton(161, 18, 8, "R1", state);
        addButton(161, 34, 9, "R2", state);
        addButton(161, 50, 10, "R3", state);
        addButton(161, 66, 11, "R4", state);
        addButton(161, 82, 12, "R5", state);
        addButton(161, 98, 13, "R6", state);
        addButton(161, 114, 14, "R7", state);
        addButton(161, 130, 15, "R8", state);
    }

    private void addButton(int posX, int posY, int buttonID, String stateKey, Map<String, Object> state) {
        WidgetSprites sprites = new WidgetSprites(BUTTON_TEX, BUTTON_TEX);

        ImageButton btn = new ImageButton(this.leftPos + posX, this.topPos + posY, 78, 11, sprites, e -> {
            if (isButtonActive(this.menu.getMenuState(), stateKey)) {
                ClientPacketDistributor.sendToServer(new NetConfigGUIButtonMessage(buttonID, x, y, z));
            }
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                if (isButtonActive(NetConfigGUIScreen.this.menu.getMenuState(), stateKey)) {
                    guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
                }
            }
        };
        this.addRenderableWidget(btn);
    }
}
