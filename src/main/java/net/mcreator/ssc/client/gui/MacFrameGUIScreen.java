package net.mcreator.ssc.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.ssc.world.inventory.MacFrameGUIMenu;
import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.init.Ssc14ModScreens;

public class MacFrameGUIScreen extends AbstractContainerScreen<MacFrameGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;

	public MacFrameGUIScreen(MacFrameGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 300;
		this.imageHeight = 172;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("ssc_14:textures/screens/mac_frame_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/mac_rrame_gui_2.png"), this.leftPos + 0, this.topPos + 0, 0, 0, 300, 172, 300, 172);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/mac_fr_gui_r3.png"), this.leftPos + 136, this.topPos + 33, 0, 0, 28, 28, 28, 28);
		if (MacFrameGUIYellowProcedure.execute(world, x, y, z)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/mac_fr_gui_y2.png"), this.leftPos + 136, this.topPos + 33, 0, 0, 28, 28, 28, 28);
		}
		if (MacFrameGUIGreenProcedure.execute(world, x, y, z)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/mac_fr_gui_g1.png"), this.leftPos + 136, this.topPos + 33, 0, 0, 28, 28, 28, 28);
		}
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, MacFrameGUItext1Procedure.execute(world, x, y, z), 7, 8, -1, false);
		guiGraphics.drawString(this.font, MacFrameGUItext2Procedure.execute(world, x, y, z), 7, 36, -1, false);
		guiGraphics.drawString(this.font, MacFrameGUItext3Procedure.execute(world, x, y, z), 7, 50, -1, false);
		guiGraphics.drawString(this.font, MacFrameGUItext4Procedure.execute(world, x, y, z), 7, 64, -1, false);
		guiGraphics.drawString(this.font, MacFrameGUItext5Procedure.execute(world, x, y, z), 7, 78, -1, false);
	}

	@Override
	public void init() {
		super.init();
	}
}