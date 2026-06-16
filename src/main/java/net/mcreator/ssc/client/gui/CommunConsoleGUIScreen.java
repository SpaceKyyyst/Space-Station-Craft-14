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

import net.mcreator.ssc.world.inventory.CommunConsoleGUIMenu;
import net.mcreator.ssc.procedures.CommConsGUITextCurrentCodeProcedure;
import net.mcreator.ssc.network.CommunConsoleGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

public class CommunConsoleGUIScreen extends AbstractContainerScreen<CommunConsoleGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_comm_cons_gui_button;
	private ImageButton imagebutton_comm_cons_gui_button1;
	private ImageButton imagebutton_comm_cons_gui_button2;
	private ImageButton imagebutton_comm_cons_gui_button3;
	private ImageButton imagebutton_comm_cons_gui_button4;
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("ssc_14:textures/screens/commun_console_gui.png");
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("ssc_14:textures/screens/commun_console_gui_2.png");

	public CommunConsoleGUIScreen(CommunConsoleGUIMenu container, Inventory inventory, Component text) {
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
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + -121, this.topPos + -34, 0, 0, 416, 235, 416, 235);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_otpravka_soobshchieniia"), 96, -3, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_kod_ughrozy"), -96, -3, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_krasnyi"), -84, 76, -3397592, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_sinii"), -84, 90, -14139956, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_fiolietovyi"), -84, 104, -6412084, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_zhioltyi"), -84, 118, -3359704, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_zielionyi"), -84, 132, -14103471, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_pri_nalichii_ughroz"), -98, 12, -1710619, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_na_stantsii_vybieritie"), -98, 22, -1710619, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_sootvietstvuiushchii_kod"), -98, 32, -1710619, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_ughrozy"), -98, 42, -1710619, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.ssc_14.commun_console_gui.label_vvieditie_obiavlieniie"), 94, 12, -1710619, false);
		guiGraphics.drawString(this.font, CommConsGUITextCurrentCodeProcedure.execute(world), -98, 54, -1710619, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_comm_cons_gui_button = new ImageButton(this.leftPos + -94, this.topPos + 74, 122, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png")), e -> {
					int x = CommunConsoleGUIScreen.this.x;
					int y = CommunConsoleGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CommunConsoleGUIButtonMessage(0, x, y, z));
						CommunConsoleGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_comm_cons_gui_button);
		imagebutton_comm_cons_gui_button1 = new ImageButton(this.leftPos + -94, this.topPos + 88, 122, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png")), e -> {
					int x = CommunConsoleGUIScreen.this.x;
					int y = CommunConsoleGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CommunConsoleGUIButtonMessage(1, x, y, z));
						CommunConsoleGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_comm_cons_gui_button1);
		imagebutton_comm_cons_gui_button2 = new ImageButton(this.leftPos + -94, this.topPos + 102, 122, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png")), e -> {
					int x = CommunConsoleGUIScreen.this.x;
					int y = CommunConsoleGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CommunConsoleGUIButtonMessage(2, x, y, z));
						CommunConsoleGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_comm_cons_gui_button2);
		imagebutton_comm_cons_gui_button3 = new ImageButton(this.leftPos + -94, this.topPos + 116, 122, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png")), e -> {
					int x = CommunConsoleGUIScreen.this.x;
					int y = CommunConsoleGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CommunConsoleGUIButtonMessage(3, x, y, z));
						CommunConsoleGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_comm_cons_gui_button3);
		imagebutton_comm_cons_gui_button4 = new ImageButton(this.leftPos + -94, this.topPos + 130, 122, 11,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png"), ResourceLocation.parse("ssc_14:textures/screens/comm_cons_gui_button.png")), e -> {
					int x = CommunConsoleGUIScreen.this.x;
					int y = CommunConsoleGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new CommunConsoleGUIButtonMessage(4, x, y, z));
						CommunConsoleGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_comm_cons_gui_button4);
	}
}