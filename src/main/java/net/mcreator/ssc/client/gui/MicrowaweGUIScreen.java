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

import net.mcreator.ssc.world.inventory.MicrowaweGUIMenu;
import net.mcreator.ssc.procedures.MicrowaweGUITextTimeProcedure;
import net.mcreator.ssc.network.MicrowaweGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

public class MicrowaweGUIScreen extends AbstractContainerScreen<MicrowaweGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_microwawe_gui_button;
	private ImageButton imagebutton_microwawe_gui_button1;
	private ImageButton imagebutton_microwawe_gui_button2;
	private ImageButton imagebutton_microwawe_gui_button3;
	private ImageButton imagebutton_microwawe_gui_button4;
	private ImageButton imagebutton_microwawe_gui_button5;
	private ImageButton imagebutton_microwawe_gui_button6;
	private ImageButton imagebutton_microwawe_gui_button7;
	private ImageButton imagebutton_microwawe_gui_button8;
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui.png");
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_2.png");

	public MicrowaweGUIScreen(MicrowaweGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 176, 166, 176, 166);
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
		guiGraphics.drawString(this.font, MicrowaweGUITextTimeProcedure.execute(world, x, y, z), 110, 14, -1, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_microwawe_gui_button = new ImageButton(this.leftPos + 107, this.topPos + 29, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(0, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button);
		imagebutton_microwawe_gui_button1 = new ImageButton(this.leftPos + 125, this.topPos + 29, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(1, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button1);
		imagebutton_microwawe_gui_button2 = new ImageButton(this.leftPos + 143, this.topPos + 29, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(2, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button2);
		imagebutton_microwawe_gui_button3 = new ImageButton(this.leftPos + 107, this.topPos + 43, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(3, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button3);
		imagebutton_microwawe_gui_button4 = new ImageButton(this.leftPos + 125, this.topPos + 43, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(4, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button4);
		imagebutton_microwawe_gui_button5 = new ImageButton(this.leftPos + 143, this.topPos + 43, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(5, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 5, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button5);
		imagebutton_microwawe_gui_button6 = new ImageButton(this.leftPos + 107, this.topPos + 57, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(6, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 6, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button6);
		imagebutton_microwawe_gui_button7 = new ImageButton(this.leftPos + 125, this.topPos + 57, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(7, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 7, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button7);
		imagebutton_microwawe_gui_button8 = new ImageButton(this.leftPos + 143, this.topPos + 57, 15, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/microwawe_gui_button.png")), e -> {
					int x = MicrowaweGUIScreen.this.x;
					int y = MicrowaweGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new MicrowaweGUIButtonMessage(8, x, y, z));
						MicrowaweGUIButtonMessage.handleButtonAction(entity, 8, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_microwawe_gui_button8);
	}
}