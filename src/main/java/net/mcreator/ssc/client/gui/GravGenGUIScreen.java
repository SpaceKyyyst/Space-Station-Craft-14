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

import net.mcreator.ssc.world.inventory.GravGenGUIMenu;
import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.network.GravGenGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

import com.mojang.blaze3d.platform.InputConstants;

public class GravGenGUIScreen extends AbstractContainerScreen<GravGenGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_off_button;
	private ImageButton imagebutton_on_button;
	private static final Identifier BACKGROUND = Identifier.parse("ssc_14:textures/screens/grav_gen_gui.png");
	private static final Identifier IMAGE_0 = Identifier.parse("ssc_14:textures/screens/grav_gen_gui_2.png");
	private static final Identifier IMAGE_1 = Identifier.parse("ssc_14:textures/screens/gravgen_gui_rood.png");

	public GravGenGUIScreen(GravGenGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 200, 90);
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
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 200, 90, 200, 90);
		if (GravGenRoodGUIProcedure.execute(world, x, y, z)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_1, this.leftPos + 150, this.topPos + 37, 0, 0, 33, 16, 33, 16);
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
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.grav_gen_gui.label_pitaniie"), 5, 10, -1, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.grav_gen_gui.label_status"), 5, 35, -1, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.grav_gen_gui.label_eta"), 5, 49, -1, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.grav_gen_gui.label_zariad"), 5, 63, -1, false);
		if (GravGenStatusGUITextProcedure.execute(world, x, y, z))
			guiGraphics.text(this.font, Component.translatable("gui.ssc_14.grav_gen_gui.label_polnostiu_zariazhien"), 60, 35, -13663425, false);
		guiGraphics.text(this.font, GravGenGUIETAtextProcedure.execute(world, x, y, z), 60, 49, -1, false);
		guiGraphics.text(this.font, GravGenGUIpercentTextProcedure.execute(world, x, y, z), 60, 63, -1, false);
		if (GravGenStatusGUIText2Procedure.execute(world, x, y, z))
			guiGraphics.text(this.font, Component.translatable("gui.ssc_14.grav_gen_gui.label_empty"), 60, 35, -8226258, false);
		if (GravGenStatusGUIText3Procedure.execute(world, x, y, z))
			guiGraphics.text(this.font, Component.translatable("gui.ssc_14.grav_gen_gui.label_razriazhaietsia"), 60, 35, -8226514, false);
		if (GravGenStatusGUIText4Procedure.execute(world, x, y, z))
			guiGraphics.text(this.font, Component.translatable("gui.ssc_14.grav_gen_gui.label_vykliuchien"), 60, 35, -8245714, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_off_button = new ImageButton(this.leftPos + 98, this.topPos + 4, 25, 15, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/off_button.png"), Identifier.parse("ssc_14:textures/screens/off_button.png")), e -> {
			int x = GravGenGUIScreen.this.x;
			int y = GravGenGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new GravGenGUIButtonMessage(0, x, y, z));
				GravGenGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_off_button);
		imagebutton_on_button = new ImageButton(this.leftPos + 72, this.topPos + 4, 25, 15, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/on_button.png"), Identifier.parse("ssc_14:textures/screens/on_button.png")), e -> {
			int x = GravGenGUIScreen.this.x;
			int y = GravGenGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new GravGenGUIButtonMessage(1, x, y, z));
				GravGenGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_on_button);
	}
}