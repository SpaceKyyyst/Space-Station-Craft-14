package net.mcreator.ssc.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import net.mcreator.ssc.world.inventory.PDApassangerGUIMenu;
import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.network.PDApassangerGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

import com.mojang.blaze3d.platform.InputConstants;

public class PDApassangerGUIScreen extends AbstractContainerScreen<PDApassangerGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_pen_button_active;
	private ImageButton imagebutton_pda_empty_button_texture;
	private ImageButton imagebutton_pda_empty_button_texture1;
	private static final Identifier BACKGROUND = Identifier.parse("ssc_14:textures/screens/pd_apassanger_gui.png");
	private static final Identifier IMAGE_0 = Identifier.parse("ssc_14:textures/screens/pda_passanger_gui_2.png");
	private static final Identifier IMAGE_1 = Identifier.parse("ssc_14:textures/screens/pen_button_active.png");
	private static final Identifier IMAGE_2 = Identifier.parse("ssc_14:textures/screens/id_eject_button.png");
	private static final Identifier IMAGE_3 = Identifier.parse("ssc_14:textures/screens/light_buttom_texture.png");

	public PDApassangerGUIScreen(PDApassangerGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 192, 150);
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
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 192, 150, 192, 150);
		if (PDApenButtonDisplayCondProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_1, this.leftPos + 152, this.topPos + 11, 0, 0, 11, 11, 11, 11);
		}
		if (PDAidButtonDisplayCondProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_2, this.leftPos + 163, this.topPos + 11, 0, 0, 11, 11, 11, 11);
		}
		if (PDAlightButtonDisplayCondProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_3, this.leftPos + 141, this.topPos + 11, 0, 0, 11, 11, 11, 11);
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
		guiGraphics.text(this.font, PDAtextidProcedure.execute(), 10, 26, -3026479, false);
		guiGraphics.text(this.font, PDAtextStationNameProcedure.execute(), 10, 35, -3223858, false);
		guiGraphics.text(this.font, PDAtextDangerLevelProcedure.execute(), 10, 44, -3223858, false);
		guiGraphics.text(this.font, PDAtextid2Procedure.execute(entity), 30, 26, -256, false);
		guiGraphics.text(this.font, PDAtextStationName2Procedure.execute(world), 62, 35, -1, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2Procedure.execute(world), 100, 44, -1, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2GProcedure.execute(world), 100, 44, -14585048, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2YProcedure.execute(world), 100, 44, -5668859, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2BProcedure.execute(world), 100, 44, -15907190, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2RProcedure.execute(world), 100, 44, -7272183, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2PProcedure.execute(world), 100, 44, -6477674, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2GamProcedure.execute(world), 100, 44, -5157766, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2DProcedure.execute(world), 100, 44, -10878968, false);
		guiGraphics.text(this.font, PDAtextDangerLevel2EProcedure.execute(world), 100, 44, -12316287, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_pen_button_active = new ImageButton(this.leftPos + 152, this.topPos + 11, 11, 11,
				new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png"), Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png")), e -> {
					int x = PDApassangerGUIScreen.this.x;
					int y = PDApassangerGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new PDApassangerGUIButtonMessage(0, x, y, z));
						PDApassangerGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pen_button_active);
		imagebutton_pda_empty_button_texture = new ImageButton(this.leftPos + 163, this.topPos + 11, 11, 11,
				new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png"), Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png")), e -> {
					int x = PDApassangerGUIScreen.this.x;
					int y = PDApassangerGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new PDApassangerGUIButtonMessage(1, x, y, z));
						PDApassangerGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_empty_button_texture);
		imagebutton_pda_empty_button_texture1 = new ImageButton(this.leftPos + 141, this.topPos + 11, 11, 11,
				new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png"), Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png")), e -> {
					int x = PDApassangerGUIScreen.this.x;
					int y = PDApassangerGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new PDApassangerGUIButtonMessage(2, x, y, z));
						PDApassangerGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_empty_button_texture1);
	}
}