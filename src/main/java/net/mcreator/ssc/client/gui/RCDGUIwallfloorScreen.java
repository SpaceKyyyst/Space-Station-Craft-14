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

import net.mcreator.ssc.world.inventory.RCDGUIwallfloorMenu;
import net.mcreator.ssc.network.RCDGUIwallfloorButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

public class RCDGUIwallfloorScreen extends AbstractContainerScreen<RCDGUIwallfloorMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_rcd_gui_exit_b;
	private ImageButton imagebutton_steel_wall_gui_texture;
	private ImageButton imagebutton_most_floor_gui_texture;
	private ImageButton imagebutton_tile_steel_gui_texture;
	private ImageButton imagebutton_floor_gui_texture;
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("ssc_14:textures/screens/rcd_gui_wallfloor.png");

	public RCDGUIwallfloorScreen(RCDGUIwallfloorMenu container, Inventory inventory, Component text) {
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
		imagebutton_rcd_gui_exit_b = new ImageButton(this.leftPos + 92, this.topPos + 92, 16, 16,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/rcd_gui_back_b.png"), ResourceLocation.parse("ssc_14:textures/screens/rcd_gui_back_b.png")), e -> {
					int x = RCDGUIwallfloorScreen.this.x;
					int y = RCDGUIwallfloorScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new RCDGUIwallfloorButtonMessage(0, x, y, z));
						RCDGUIwallfloorButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_rcd_gui_exit_b);
		imagebutton_steel_wall_gui_texture = new ImageButton(this.leftPos + 38, this.topPos + 38, 32, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/steel_wall_gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/steel_wall_gui_texture.png")), e -> {
					int x = RCDGUIwallfloorScreen.this.x;
					int y = RCDGUIwallfloorScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new RCDGUIwallfloorButtonMessage(1, x, y, z));
						RCDGUIwallfloorButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_steel_wall_gui_texture);
		imagebutton_most_floor_gui_texture = new ImageButton(this.leftPos + 130, this.topPos + 38, 32, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/most_floor__gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/most_floor__gui_texture.png")), e -> {
					int x = RCDGUIwallfloorScreen.this.x;
					int y = RCDGUIwallfloorScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new RCDGUIwallfloorButtonMessage(2, x, y, z));
						RCDGUIwallfloorButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_most_floor_gui_texture);
		imagebutton_tile_steel_gui_texture = new ImageButton(this.leftPos + 38, this.topPos + 130, 32, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/tile_steel__gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/tile_steel__gui_texture.png")), e -> {
					int x = RCDGUIwallfloorScreen.this.x;
					int y = RCDGUIwallfloorScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new RCDGUIwallfloorButtonMessage(3, x, y, z));
						RCDGUIwallfloorButtonMessage.handleButtonAction(entity, 3, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_tile_steel_gui_texture);
		imagebutton_floor_gui_texture = new ImageButton(this.leftPos + 130, this.topPos + 130, 32, 32,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/floor_gui_texture.png"), ResourceLocation.parse("ssc_14:textures/screens/floor_gui_texture.png")), e -> {
					int x = RCDGUIwallfloorScreen.this.x;
					int y = RCDGUIwallfloorScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new RCDGUIwallfloorButtonMessage(4, x, y, z));
						RCDGUIwallfloorButtonMessage.handleButtonAction(entity, 4, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_floor_gui_texture);
	}
}