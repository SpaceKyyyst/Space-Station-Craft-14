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

import net.mcreator.ssc.world.inventory.RCDGUIMenu;
import net.mcreator.ssc.network.RCDGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

public class RCDGUIScreen extends AbstractContainerScreen<RCDGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_deconstruct_gui_texture;
	private ImageButton imagebutton_airlock_gui_texture;
	private ImageButton imagebutton_floorwall_gui_texture;
	private ImageButton imagebutton_windowglill_gui_texture;
	private ImageButton imagebutton_cabels_gui_texture;
	private ImageButton imagebutton_lamps_gui_texture;
	private ImageButton imagebutton_rcd_gui_exit_b;
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("ssc_14:textures/screens/rcd_gui_2.png");

	public RCDGUIScreen(RCDGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 200;
		this.imageHeight = 200;
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
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 200, 200, 200, 200);
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
	}

	@Override
	public void init() {
		super.init();
		imagebutton_deconstruct_gui_texture = new ImageButton(this.leftPos + 125, this.topPos + 26, 16, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/deconstruct_gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/deconstruct_gui_texture.png")), e -> {
					int x = RCDGUIScreen.this.x;
					int y = RCDGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new RCDGUIButtonMessage(0, x, y, z));
						RCDGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_deconstruct_gui_texture);
		imagebutton_airlock_gui_texture = new ImageButton(this.leftPos + 57, this.topPos + 143, 16, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/airlock_gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/airlock_gui_texture.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_airlock_gui_texture);
		imagebutton_floorwall_gui_texture = new ImageButton(this.leftPos + 50, this.topPos + 26, 32, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/floorwall_gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/floorwall_gui_texture.png")), e -> {
					int x = RCDGUIScreen.this.x;
					int y = RCDGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new RCDGUIButtonMessage(2, x, y, z));
						RCDGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_floorwall_gui_texture);
		imagebutton_windowglill_gui_texture = new ImageButton(this.leftPos + 18, this.topPos + 84, 32, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/windowglill_gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/windowglill_gui_texture.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_windowglill_gui_texture);
		imagebutton_cabels_gui_texture = new ImageButton(this.leftPos + 150, this.topPos + 84, 32, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/cabels_gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/cabels_gui_texture.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_cabels_gui_texture);
		imagebutton_lamps_gui_texture = new ImageButton(this.leftPos + 118, this.topPos + 143, 32, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/lamps_gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/lamps_gui_texture.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_lamps_gui_texture);
		imagebutton_rcd_gui_exit_b = new ImageButton(this.leftPos + 92, this.topPos + 92, 16, 16,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/rcd_gui_exit_b.png"), ResourceLocation.parse("ssc_14:textures/screens/rcd_gui_exit_b.png")), e -> {
					int x = RCDGUIScreen.this.x;
					int y = RCDGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new RCDGUIButtonMessage(6, x, y, z));
						RCDGUIButtonMessage.handleButtonAction(entity, 6, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_rcd_gui_exit_b);
	}
}