package net.ssc14.client.menu.machinery;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import net.ssc14.client.menu.MenuStateReceiver;
import net.ssc14.content.menu.machinery.MacFrameGUIMenu;
import net.ssc14.content.menu.machinery.MachineFrameDisplayState;
import net.minecraft.core.BlockPos;

import com.mojang.blaze3d.platform.InputConstants;

public class MacFrameGUIScreen extends AbstractContainerScreen<MacFrameGUIMenu> implements MenuStateReceiver {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private MachineFrameDisplayState displayState = MachineFrameDisplayState.forBoard(
			MachineFrameDisplayState.BoardType.NONE, false);
	private static final Identifier BACKGROUND = Identifier.parse("ssc_14:textures/screens/mac_frame_gui.png");
	private static final Identifier IMAGE_0 = Identifier.parse("ssc_14:textures/screens/mac_rrame_gui_2.png");
	private static final Identifier IMAGE_1 = Identifier.parse("ssc_14:textures/screens/mac_fr_gui_r3.png");
	private static final Identifier IMAGE_2 = Identifier.parse("ssc_14:textures/screens/mac_fr_gui_y2.png");
	private static final Identifier IMAGE_3 = Identifier.parse("ssc_14:textures/screens/mac_fr_gui_g1.png");

	public MacFrameGUIScreen(MacFrameGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 300, 172);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		displayState = MachineFrameDisplayState.read(world, new BlockPos(x, y, z));
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 300, 172, 300, 172);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_1, this.leftPos + 136, this.topPos + 33, 0, 0, 28, 28, 28, 28);
		if (displayState.hasBoard()) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_2, this.leftPos + 136, this.topPos + 33, 0, 0, 28, 28, 28, 28);
		}
		if (displayState.ready()) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_3, this.leftPos + 136, this.topPos + 33, 0, 0, 28, 28, 28, 28);
		}
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		int key = InputConstants.getKey(event).getValue();
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		guiGraphics.text(this.font, displayState.title(), 7, 8, -1, false);
		guiGraphics.text(this.font, displayState.requirement1(), 7, 36, -1, false);
		guiGraphics.text(this.font, displayState.requirement2(), 7, 50, -1, false);
		guiGraphics.text(this.font, displayState.requirement3(), 7, 64, -1, false);
		guiGraphics.text(this.font, displayState.requirement4(), 7, 78, -1, false);
	}

	@Override
	public void init() {
		super.init();
	}
}
