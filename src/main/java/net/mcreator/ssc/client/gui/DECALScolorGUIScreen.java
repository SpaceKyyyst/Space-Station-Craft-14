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
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import net.mcreator.ssc.world.inventory.DECALScolorGUIMenu;
import net.mcreator.ssc.procedures.DECALSGUIaccept2Procedure;
import net.mcreator.ssc.network.DECALScolorGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

import com.mojang.blaze3d.platform.InputConstants;

public class DECALScolorGUIScreen extends AbstractContainerScreen<DECALScolorGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox R;
	private EditBox G;
	private EditBox B;
	private Button button_priniat;
	private static final Identifier BACKGROUND = Identifier.parse("ssc_14:textures/screens/decal_scolor_gui.png");
	private static final Identifier IMAGE_0 = Identifier.parse("ssc_14:textures/screens/decals_gui_2.png");

	public DECALScolorGUIScreen(DECALScolorGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 166, 166);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("R"))
				R.setValue(stringState);
			else if (name.equals("G"))
				G.setValue(stringState);
			else if (name.equals("B"))
				B.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		R.extractWidgetRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		G.extractWidgetRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		B.extractWidgetRenderState(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 166, 166, 166, 166);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		int key = InputConstants.getKey(event).getValue();
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		if (R.isFocused())
			return R.keyPressed(event);
		if (G.isFocused())
			return G.keyPressed(event);
		if (B.isFocused())
			return B.keyPressed(event);
		return super.keyPressed(event);
	}

	@Override
	public void resize(int width, int height) {
		String RValue = R.getValue();
		String GValue = G.getValue();
		String BValue = B.getValue();
		super.resize(width, height);
		R.setValue(RValue);
		G.setValue(GValue);
		B.setValue(BValue);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.decal_scolor_gui.label_na_etoi_koordinatie_razmiestit_tsv"), 79, 116, -1, false);
	}

	@Override
	public void init() {
		super.init();
		R = new EditBox(this.font, this.leftPos + 24, this.topPos + 19, 120, 20, Component.translatable("gui.ssc_14.decal_scolor_gui.R"));
		R.setMaxLength(8192);
		R.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "R", content, false);
		});
		this.addWidget(this.R);
		G = new EditBox(this.font, this.leftPos + 24, this.topPos + 51, 120, 20, Component.translatable("gui.ssc_14.decal_scolor_gui.G"));
		G.setMaxLength(8192);
		G.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "G", content, false);
		});
		this.addWidget(this.G);
		B = new EditBox(this.font, this.leftPos + 24, this.topPos + 83, 120, 20, Component.translatable("gui.ssc_14.decal_scolor_gui.B"));
		B.setMaxLength(8192);
		B.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "B", content, false);
		});
		this.addWidget(this.B);
		button_priniat = Button.builder(Component.translatable("gui.ssc_14.decal_scolor_gui.button_priniat"), e -> {
			int x = DECALScolorGUIScreen.this.x;
			int y = DECALScolorGUIScreen.this.y;
			if (DECALSGUIaccept2Procedure.execute(entity)) {
				ClientPacketDistributor.sendToServer(new DECALScolorGUIButtonMessage(0, x, y, z));
				DECALScolorGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 51, this.topPos + 142, 60, 20).build();
		this.addRenderableWidget(button_priniat);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.button_priniat.visible = DECALSGUIaccept2Procedure.execute(entity);
	}
}