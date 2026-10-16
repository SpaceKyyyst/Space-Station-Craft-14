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
	private ImageButton imagebutton_pda_1_button;
	private ImageButton imagebutton_pda_1_button1;
	private ImageButton imagebutton_pda_1_button2;
	private ImageButton imagebutton_pda_button_tab2;
	private ImageButton imagebutton_pda_button_tab21;
	private ImageButton imagebutton_pda_button_tab22;
	private static final Identifier BACKGROUND = Identifier.parse("ssc_14:textures/screens/pd_apassanger_gui.png");
	private static final Identifier IMAGE_0 = Identifier.parse("ssc_14:textures/screens/pda_passanger_gui_2.png");
	private static final Identifier IMAGE_1 = Identifier.parse("ssc_14:textures/screens/pen_button_active.png");
	private static final Identifier IMAGE_2 = Identifier.parse("ssc_14:textures/screens/id_eject_button.png");
	private static final Identifier IMAGE_3 = Identifier.parse("ssc_14:textures/screens/light_buttom_texture.png");
	private static final Identifier IMAGE_4 = Identifier.parse("ssc_14:textures/screens/pda_main_tab.png");
	private static final Identifier IMAGE_5 = Identifier.parse("ssc_14:textures/screens/pda_2_tab.png");
	private static final Identifier IMAGE_6 = Identifier.parse("ssc_14:textures/screens/pda_3_tab.png");

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
		if (PDA_main_TAB_condProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_4, this.leftPos + 8, this.topPos + 11, 0, 0, 14, 12, 14, 12);
		}
		if (PDA2TABcondProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_5, this.leftPos + 21, this.topPos + 11, 0, 0, 15, 12, 15, 12);
		}
		if (PDA3TABcondProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_6, this.leftPos + 35, this.topPos + 11, 0, 0, 15, 12, 15, 12);
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
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextidProcedure.execute(), 10, 26, -3026479, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextStationNameProcedure.execute(), 10, 35, -3223858, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevelProcedure.execute(), 10, 44, -3223858, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextid2Procedure.execute(entity), 30, 26, -256, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextStationName2Procedure.execute(world), 62, 35, -1, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2Procedure.execute(world), 100, 44, -1, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2GProcedure.execute(world), 100, 44, -14585048, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2YProcedure.execute(world), 100, 44, -5668859, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2BProcedure.execute(world), 100, 44, -15907190, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2RProcedure.execute(world), 100, 44, -7272183, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2PProcedure.execute(world), 100, 44, -6477674, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2GamProcedure.execute(world), 100, 44, -5157766, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2DProcedure.execute(world), 100, 44, -10878968, false);
		if (PDA_main_TAB_condProcedure.execute(entity))
			guiGraphics.text(this.font, PDAtextDangerLevel2EProcedure.execute(world), 100, 44, -12316287, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_pen_button_active = new ImageButton(this.leftPos + 152, this.topPos + 11, 11, 11,
				new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png"), Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png")), e -> {
					int x = PDApassangerGUIScreen.this.x;
					int y = PDApassangerGUIScreen.this.y;
					{
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
					{
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
					{
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
		imagebutton_pda_1_button = new ImageButton(this.leftPos + 8, this.topPos + 11, 13, 11, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_1_button.png"), Identifier.parse("ssc_14:textures/screens/pda_1_button.png")), e -> {
			int x = PDApassangerGUIScreen.this.x;
			int y = PDApassangerGUIScreen.this.y;
			{
				ClientPacketDistributor.sendToServer(new PDApassangerGUIButtonMessage(3, x, y, z));
				PDApassangerGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_1_button);
		imagebutton_pda_1_button1 = new ImageButton(this.leftPos + 22, this.topPos + 11, 13, 11, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_1_button.png"), Identifier.parse("ssc_14:textures/screens/pda_1_button.png")), e -> {
			int x = PDApassangerGUIScreen.this.x;
			int y = PDApassangerGUIScreen.this.y;
			{
				ClientPacketDistributor.sendToServer(new PDApassangerGUIButtonMessage(4, x, y, z));
				PDApassangerGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_1_button1);
		imagebutton_pda_1_button2 = new ImageButton(this.leftPos + 36, this.topPos + 11, 13, 11, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_1_button.png"), Identifier.parse("ssc_14:textures/screens/pda_1_button.png")), e -> {
			int x = PDApassangerGUIScreen.this.x;
			int y = PDApassangerGUIScreen.this.y;
			{
				ClientPacketDistributor.sendToServer(new PDApassangerGUIButtonMessage(5, x, y, z));
				PDApassangerGUIButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_1_button2);
		imagebutton_pda_button_tab2 = new ImageButton(this.leftPos + 11, this.topPos + 26, 83, 11, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_button_tab2.png"), Identifier.parse("ssc_14:textures/screens/pda_button_tab2.png")),
				e -> {
				}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				int x = PDApassangerGUIScreen.this.x;
				int y = PDApassangerGUIScreen.this.y;
				if (PDA2TABcondProcedure.execute(entity))
					guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_button_tab2);
		imagebutton_pda_button_tab21 = new ImageButton(this.leftPos + 99, this.topPos + 26, 83, 11, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_button_tab2.png"), Identifier.parse("ssc_14:textures/screens/pda_button_tab2.png")),
				e -> {
				}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				int x = PDApassangerGUIScreen.this.x;
				int y = PDApassangerGUIScreen.this.y;
				if (PDA2TABcondProcedure.execute(entity))
					guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_button_tab21);
		imagebutton_pda_button_tab22 = new ImageButton(this.leftPos + 11, this.topPos + 42, 83, 11, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_button_tab2.png"), Identifier.parse("ssc_14:textures/screens/pda_button_tab2.png")),
				e -> {
				}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				int x = PDApassangerGUIScreen.this.x;
				int y = PDApassangerGUIScreen.this.y;
				if (PDA2TABcondProcedure.execute(entity))
					guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_button_tab22);
	}
}