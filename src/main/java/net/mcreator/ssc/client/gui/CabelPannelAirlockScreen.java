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

import net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu;
import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.network.CabelPannelAirlockButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

public class CabelPannelAirlockScreen extends AbstractContainerScreen<CabelPannelAirlockMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_cable_button6;
	private ImageButton imagebutton_cable_button5;
	private ImageButton imagebutton_cable_button4;
	private ImageButton imagebutton_cabel_4;
	private ImageButton imagebutton_cable_button;
	private ImageButton imagebutton_cable_button1;
	private ImageButton imagebutton_cable_button2;
	private ImageButton imagebutton_cable_button3;

	public CabelPannelAirlockScreen(CabelPannelAirlockMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 160;
		this.imageHeight = 1;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("ssc_14:textures/screens/cabel_pannel_airlock.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cabel_pannel_airlock_copy.png"), this.leftPos + 0, this.topPos + -50, 0, 0, 160, 100, 160, 100);
		if (CPcabel1Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cable_1.png"), this.leftPos + 36, this.topPos + 5, 0, 0, 6, 19, 6, 19);
		}
		if (CPcabel2Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cable_2.png"), this.leftPos + 48, this.topPos + 5, 0, 0, 5, 19, 5, 19);
		}
		if (CPcabel3Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cable_3.png"), this.leftPos + 58, this.topPos + 5, 0, 0, 6, 19, 6, 19);
		}
		if (CPcabel4Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cabel_4.png"), this.leftPos + 72, this.topPos + 5, 0, 0, 7, 19, 7, 19);
		}
		if (CPcabel5Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cable_5.png"), this.leftPos + 83, this.topPos + 5, 0, 0, 5, 19, 5, 19);
		}
		if (CPcabel6Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cable_6.png"), this.leftPos + 91, this.topPos + 5, 0, 0, 8, 19, 8, 19);
		}
		if (CPcabel7Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cable_7.png"), this.leftPos + 105, this.topPos + 5, 0, 0, 4, 19, 4, 19);
		}
		if (CPcabel8Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/cable_8.png"), this.leftPos + 115, this.topPos + 5, 0, 0, 7, 19, 7, 19);
		}
		if (CPlampind1Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lamp_indicator_yellow.png"), this.leftPos + 31, this.topPos + -28, 0, 0, 9, 9, 9, 9);
		}
		if (CPlampind2Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lamp_indicator_red.png"), this.leftPos + 69, this.topPos + -28, 0, 0, 9, 9, 9, 9);
		}
		if (CPlampind3Procedure.execute(world, entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lamp_indicator_white.png"), this.leftPos + 107, this.topPos + -28, 0, 0, 9, 9, 9, 9);
		}
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lamp_indicator_cyane.png"), this.leftPos + 133, this.topPos + -28, 0, 0, 9, 9, 9, 9);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lamp_indicator_blue.png"), this.leftPos + 31, this.topPos + -14, 0, 0, 9, 9, 9, 9);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lamp_indicator_green.png"), this.leftPos + 69, this.topPos + -14, 0, 0, 9, 9, 9, 9);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lamp_indicator_red.png"), this.leftPos + 107, this.topPos + -14, 0, 0, 9, 9, 9, 9);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.cabel_pannel_airlock.label_pitn"), 6, -27, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.cabel_pannel_airlock.label_log"), 12, -13, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.cabel_pannel_airlock.label_bolt"), 44, -27, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.cabel_pannel_airlock.label_empty"), 44, -13, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.cabel_pannel_airlock.label_empty1"), 82, -27, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.cabel_pannel_airlock.label_biezp"), 82, -13, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.cabel_pannel_airlock.label_ii"), 120, -27, -1, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_cable_button6 = new ImageButton(this.leftPos + 35, this.topPos + 5, 8, 19, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cable_button.png"), ResourceLocation.parse("ssc_14:textures/screens/cable_button.png")),
				e -> {
					int x = CabelPannelAirlockScreen.this.x;
					int y = CabelPannelAirlockScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CabelPannelAirlockButtonMessage(0, x, y, z));
						CabelPannelAirlockButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cable_button6);
		imagebutton_cable_button5 = new ImageButton(this.leftPos + 47, this.topPos + 5, 8, 19, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cable_button.png"), ResourceLocation.parse("ssc_14:textures/screens/cable_button.png")),
				e -> {
					int x = CabelPannelAirlockScreen.this.x;
					int y = CabelPannelAirlockScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CabelPannelAirlockButtonMessage(1, x, y, z));
						CabelPannelAirlockButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cable_button5);
		imagebutton_cable_button4 = new ImageButton(this.leftPos + 58, this.topPos + 5, 8, 19, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cable_button.png"), ResourceLocation.parse("ssc_14:textures/screens/cable_button.png")),
				e -> {
					int x = CabelPannelAirlockScreen.this.x;
					int y = CabelPannelAirlockScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CabelPannelAirlockButtonMessage(2, x, y, z));
						CabelPannelAirlockButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cable_button4);
		imagebutton_cabel_4 = new ImageButton(this.leftPos + 71, this.topPos + 5, 8, 19, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cable_button.png"), ResourceLocation.parse("ssc_14:textures/screens/cable_button.png")), e -> {
			int x = CabelPannelAirlockScreen.this.x;
			int y = CabelPannelAirlockScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new CabelPannelAirlockButtonMessage(3, x, y, z));
				CabelPannelAirlockButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cabel_4);
		imagebutton_cable_button = new ImageButton(this.leftPos + 81, this.topPos + 5, 8, 19, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cable_button.png"), ResourceLocation.parse("ssc_14:textures/screens/cable_button.png")),
				e -> {
					int x = CabelPannelAirlockScreen.this.x;
					int y = CabelPannelAirlockScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CabelPannelAirlockButtonMessage(4, x, y, z));
						CabelPannelAirlockButtonMessage.handleButtonAction(entity, 4, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cable_button);
		imagebutton_cable_button1 = new ImageButton(this.leftPos + 91, this.topPos + 5, 8, 19, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cable_button.png"), ResourceLocation.parse("ssc_14:textures/screens/cable_button.png")),
				e -> {
					int x = CabelPannelAirlockScreen.this.x;
					int y = CabelPannelAirlockScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CabelPannelAirlockButtonMessage(5, x, y, z));
						CabelPannelAirlockButtonMessage.handleButtonAction(entity, 5, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cable_button1);
		imagebutton_cable_button2 = new ImageButton(this.leftPos + 103, this.topPos + 5, 8, 19, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cable_button.png"), ResourceLocation.parse("ssc_14:textures/screens/cable_button.png")),
				e -> {
					int x = CabelPannelAirlockScreen.this.x;
					int y = CabelPannelAirlockScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CabelPannelAirlockButtonMessage(6, x, y, z));
						CabelPannelAirlockButtonMessage.handleButtonAction(entity, 6, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cable_button2);
		imagebutton_cable_button3 = new ImageButton(this.leftPos + 114, this.topPos + 5, 8, 19, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cable_button.png"), ResourceLocation.parse("ssc_14:textures/screens/cable_button.png")),
				e -> {
					int x = CabelPannelAirlockScreen.this.x;
					int y = CabelPannelAirlockScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CabelPannelAirlockButtonMessage(7, x, y, z));
						CabelPannelAirlockButtonMessage.handleButtonAction(entity, 7, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cable_button3);
	}
}