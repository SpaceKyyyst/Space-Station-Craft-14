package net.ssc14.client.menu.pda;

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

import net.ssc14.client.menu.MenuButtonClientActions;
import net.ssc14.client.menu.MenuStateReceiver;
import net.ssc14.content.menu.pda.PDApassangerGUIMenu;
import net.ssc14.integration.neoforge.menu.network.MenuButtonPayload;

import com.mojang.blaze3d.platform.InputConstants;

public class PDApassangerGUIScreen extends AbstractContainerScreen<PDApassangerGUIMenu> implements MenuStateReceiver {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private PassengerPdaPresentation presentation;
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
		this.presentation = PassengerPdaPresentation.read(entity, world);
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		presentation = PassengerPdaPresentation.read(entity, world);
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 192, 150, 192, 150);
		if (presentation.penPresent()) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_1, this.leftPos + 152, this.topPos + 11, 0, 0, 11, 11, 11, 11);
		}
		if (presentation.idPresent()) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_2, this.leftPos + 163, this.topPos + 11, 0, 0, 11, 11, 11, 11);
		}
		if (presentation.flashlight()) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_3, this.leftPos + 141, this.topPos + 11, 0, 0, 11, 11, 11, 11);
		}
		if (presentation.mainTab()) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_4, this.leftPos + 8, this.topPos + 11, 0, 0, 14, 12, 14, 12);
		}
		if (presentation.secondTab()) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_5, this.leftPos + 21, this.topPos + 11, 0, 0, 15, 12, 15, 12);
		}
		if (presentation.thirdTab()) {
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
		if (presentation.mainTab()) {
			guiGraphics.text(this.font, "ID:  ", 10, 26, -3026479, false);
			guiGraphics.text(this.font, Component.translatable("translation.key.pda_text_stationname"), 10, 35, -3223858, false);
			guiGraphics.text(this.font, Component.translatable("translation.key.pda_text_danger_level"), 10, 44, -3223858, false);
			guiGraphics.text(this.font, presentation.identity(), 30, 26, -256, false);
			guiGraphics.text(this.font, presentation.stationName(), 62, 35, -1, false);
			guiGraphics.text(this.font, presentation.alertText(), 100, 44, presentation.alertColor(), false);
		}
	}

	@Override
	public void init() {
		super.init();
		imagebutton_pen_button_active = new ImageButton(this.leftPos + 152, this.topPos + 11, 11, 11,
				new WidgetSprites(Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png"), Identifier.parse("ssc_14:textures/screens/pda_empty_button_texture.png")), e -> {
					int x = PDApassangerGUIScreen.this.x;
					int y = PDApassangerGUIScreen.this.y;
					{
						MenuButtonClientActions.send(entity, MenuButtonPayload.pdaPassenger(0, x, y, z));
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
						MenuButtonClientActions.send(entity, MenuButtonPayload.pdaPassenger(1, x, y, z));
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
						MenuButtonClientActions.send(entity, MenuButtonPayload.pdaPassenger(2, x, y, z));
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
				MenuButtonClientActions.send(entity, MenuButtonPayload.pdaPassenger(3, x, y, z));
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
				MenuButtonClientActions.send(entity, MenuButtonPayload.pdaPassenger(4, x, y, z));
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
				MenuButtonClientActions.send(entity, MenuButtonPayload.pdaPassenger(5, x, y, z));
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
				if (presentation.secondTab())
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
				if (presentation.secondTab())
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
				if (presentation.secondTab())
					guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_pda_button_tab22);
	}
}
