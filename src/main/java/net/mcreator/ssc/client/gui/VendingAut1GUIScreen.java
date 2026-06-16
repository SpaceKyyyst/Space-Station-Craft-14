package net.mcreator.ssc.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.ssc.world.inventory.VendingAut1GUIMenu;
import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.network.VendingAut1GUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

public class VendingAut1GUIScreen extends AbstractContainerScreen<VendingAut1GUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_vendind_gui_button1;
	private ImageButton imagebutton_vendind_gui_button2;
	private ImageButton imagebutton_vendind_gui_button3;
	private ImageButton imagebutton_vendind_gui_button4;
	private ImageButton imagebutton_vendind_gui_button5;
	private ImageButton imagebutton_vendind_gui_button6;
	private ImageButton imagebutton_vendind_gui_button7;
	private ImageButton imagebutton_vendind_gui_button8;
	private ImageButton imagebutton_vendind_gui_button9;
	private ImageButton imagebutton_vendind_gui_button10;
	private ImageButton imagebutton_vendind_gui_button11;
	private ImageButton imagebutton_vendind_gui_button12;
	private ImageButton imagebutton_vendind_gui_button13;
	private ImageButton imagebutton_vendind_gui_button14;
	private ImageButton imagebutton_vendind_gui_button15;
	private ImageButton imagebutton_vendind_gui_button16;
	private ImageButton imagebutton_vendind_gui_button17;
	private ImageButton imagebutton_vendind_gui_button18;
	private ImageButton imagebutton_vendind_gui_button19;
	private ImageButton imagebutton_vendind_gui_button20;
	private ImageButton imagebutton_vendind_gui_button21;
	private ImageButton imagebutton_vendind_gui_button22;
	private ImageButton imagebutton_vendind_gui_button23;
	private ImageButton imagebutton_vendind_gui_button24;
	private ImageButton imagebutton_vendind_gui_button25;
	private ImageButton imagebutton_vendind_gui_button26;
	private ImageButton imagebutton_vendind_gui_button27;
	private ImageButton imagebutton_vendind_gui_button28;
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("ssc_14:textures/screens/vending_aut_1_gui.png");
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("ssc_14:textures/screens/vending_aut_2_gui.png");
	private static final ResourceLocation SPRITE_0 = ResourceLocation.parse("ssc_14:textures/screens/vend_amount_sprites.png");
	private static final ResourceLocation SPRITE_1 = ResourceLocation.parse("ssc_14:textures/screens/vend_amount_sprites.png");
	private static final ResourceLocation SPRITE_2 = ResourceLocation.parse("ssc_14:textures/screens/vend_amount_sprites.png");
	private static final ResourceLocation SPRITE_3 = ResourceLocation.parse("ssc_14:textures/screens/vend_amount_sprites.png");
	private static final ResourceLocation SPRITE_4 = ResourceLocation.parse("ssc_14:textures/screens/vend_amount_sprites.png");
	private static final ResourceLocation SPRITE_5 = ResourceLocation.parse("ssc_14:textures/screens/vend_amount_sprites.png");

	public VendingAut1GUIScreen(VendingAut1GUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 150;
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
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 150, 200, 150, 200);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SPRITE_0, this.leftPos + 23, this.topPos + 39, 0, Mth.clamp((int) VendGUIindicator1Procedure.execute(world, x, y, z) * 5, 0, 95), 5, 5, 5, 100);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SPRITE_1, this.leftPos + 41, this.topPos + 39, 0, Mth.clamp((int) VendGUIindicator2Procedure.execute(world, x, y, z) * 5, 0, 95), 5, 5, 5, 100);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SPRITE_2, this.leftPos + 59, this.topPos + 39, 0, Mth.clamp((int) VendGUIindicator3Procedure.execute(world, x, y, z) * 5, 0, 95), 5, 5, 5, 100);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SPRITE_3, this.leftPos + 77, this.topPos + 39, 0, Mth.clamp((int) VendGUIindicator4Procedure.execute(world, x, y, z) * 5, 0, 95), 5, 5, 5, 100);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SPRITE_4, this.leftPos + 95, this.topPos + 39, 0, Mth.clamp((int) VendGUIindicator5Procedure.execute(world, x, y, z) * 5, 0, 95), 5, 5, 5, 100);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SPRITE_5, this.leftPos + 23, this.topPos + 62, 0, Mth.clamp((int) VendGUIindicator6Procedure.execute(world, x, y, z) * 5, 0, 95), 5, 5, 5, 100);
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
		imagebutton_vendind_gui_button1 = new ImageButton(this.leftPos + 114, this.topPos + 21, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
					int x = VendingAut1GUIScreen.this.x;
					int y = VendingAut1GUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new VendingAut1GUIButtonMessage(0, x, y, z));
						VendingAut1GUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button1);
		imagebutton_vendind_gui_button2 = new ImageButton(this.leftPos + 114, this.topPos + 30, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
					int x = VendingAut1GUIScreen.this.x;
					int y = VendingAut1GUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new VendingAut1GUIButtonMessage(1, x, y, z));
						VendingAut1GUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button2);
		imagebutton_vendind_gui_button3 = new ImageButton(this.leftPos + 114, this.topPos + 39, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
					int x = VendingAut1GUIScreen.this.x;
					int y = VendingAut1GUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new VendingAut1GUIButtonMessage(2, x, y, z));
						VendingAut1GUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button3);
		imagebutton_vendind_gui_button4 = new ImageButton(this.leftPos + 114, this.topPos + 48, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
					int x = VendingAut1GUIScreen.this.x;
					int y = VendingAut1GUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new VendingAut1GUIButtonMessage(3, x, y, z));
						VendingAut1GUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button4);
		imagebutton_vendind_gui_button5 = new ImageButton(this.leftPos + 114, this.topPos + 57, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
					int x = VendingAut1GUIScreen.this.x;
					int y = VendingAut1GUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new VendingAut1GUIButtonMessage(4, x, y, z));
						VendingAut1GUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button5);
		imagebutton_vendind_gui_button6 = new ImageButton(this.leftPos + 114, this.topPos + 66, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
					int x = VendingAut1GUIScreen.this.x;
					int y = VendingAut1GUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new VendingAut1GUIButtonMessage(5, x, y, z));
						VendingAut1GUIButtonMessage.handleButtonAction(entity, 5, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button6);
		imagebutton_vendind_gui_button7 = new ImageButton(this.leftPos + 114, this.topPos + 75, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button7);
		imagebutton_vendind_gui_button8 = new ImageButton(this.leftPos + 114, this.topPos + 84, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button8);
		imagebutton_vendind_gui_button9 = new ImageButton(this.leftPos + 114, this.topPos + 93, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button9);
		imagebutton_vendind_gui_button10 = new ImageButton(this.leftPos + 114, this.topPos + 102, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button10);
		imagebutton_vendind_gui_button11 = new ImageButton(this.leftPos + 114, this.topPos + 111, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button11);
		imagebutton_vendind_gui_button12 = new ImageButton(this.leftPos + 114, this.topPos + 120, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button12);
		imagebutton_vendind_gui_button13 = new ImageButton(this.leftPos + 114, this.topPos + 129, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button13);
		imagebutton_vendind_gui_button14 = new ImageButton(this.leftPos + 114, this.topPos + 138, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button14);
		imagebutton_vendind_gui_button15 = new ImageButton(this.leftPos + 129, this.topPos + 21, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button15);
		imagebutton_vendind_gui_button16 = new ImageButton(this.leftPos + 129, this.topPos + 30, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button16);
		imagebutton_vendind_gui_button17 = new ImageButton(this.leftPos + 129, this.topPos + 39, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button17);
		imagebutton_vendind_gui_button18 = new ImageButton(this.leftPos + 129, this.topPos + 48, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button18);
		imagebutton_vendind_gui_button19 = new ImageButton(this.leftPos + 129, this.topPos + 57, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button19);
		imagebutton_vendind_gui_button20 = new ImageButton(this.leftPos + 129, this.topPos + 66, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button20);
		imagebutton_vendind_gui_button21 = new ImageButton(this.leftPos + 129, this.topPos + 75, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button21);
		imagebutton_vendind_gui_button22 = new ImageButton(this.leftPos + 129, this.topPos + 84, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button22);
		imagebutton_vendind_gui_button23 = new ImageButton(this.leftPos + 129, this.topPos + 93, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button23);
		imagebutton_vendind_gui_button24 = new ImageButton(this.leftPos + 129, this.topPos + 102, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button24);
		imagebutton_vendind_gui_button25 = new ImageButton(this.leftPos + 129, this.topPos + 111, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button25);
		imagebutton_vendind_gui_button26 = new ImageButton(this.leftPos + 129, this.topPos + 120, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button26);
		imagebutton_vendind_gui_button27 = new ImageButton(this.leftPos + 129, this.topPos + 129, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button27);
		imagebutton_vendind_gui_button28 = new ImageButton(this.leftPos + 129, this.topPos + 138, 13, 7,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/vendind_gui_button.png")), e -> {
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vendind_gui_button28);
	}
}