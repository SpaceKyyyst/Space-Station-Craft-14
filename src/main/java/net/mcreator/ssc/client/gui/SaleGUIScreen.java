
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

import net.mcreator.ssc.world.inventory.SaleGUIMenu;
import net.mcreator.ssc.procedures.SaleGUITotalRevenueProcedure;
import net.mcreator.ssc.procedures.OrderGUIbudgetTEXTProcedure;
import net.mcreator.ssc.network.SaleGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

import com.mojang.blaze3d.platform.InputConstants;

public class SaleGUIScreen extends AbstractContainerScreen<SaleGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_gui_sale_button;
	private static final Identifier BACKGROUND = Identifier.parse("ssc_14:textures/screens/sale_gui.png");
	private static final Identifier IMAGE_0 = Identifier.parse("ssc_14:textures/screens/gui_cargo_2.png");

	public SaleGUIScreen(SaleGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 1, 1);
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
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + -207, this.topPos + -119, 0, 0, 416, 235, 416, 235);
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
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.sale_gui.label_balans"), -190, -82, -1, false);
		guiGraphics.text(this.font, OrderGUIbudgetTEXTProcedure.execute(world), -190, -68, -13369549, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.sale_gui.label_prodazha"), -69, -70, -1, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.sale_gui.label_summarnaia_vyruchka"), -45, -51, -1, false);
		
		guiGraphics.text(this.font, SaleGUITotalRevenueProcedure.execute(world, x, y, z), -55, -15, -13369549, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_gui_sale_button = new ImageButton(this.leftPos + -35, this.topPos + 26, 67, 18, new WidgetSprites(Identifier.parse("ssc_14:textures/screens/gui_sale_button.png"), Identifier.parse("ssc_14:textures/screens/gui_sale_button.png")),
				e -> {
					int x = SaleGUIScreen.this.x;
					int y = SaleGUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new SaleGUIButtonMessage(0, x, y, z));
						SaleGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_gui_sale_button);
	}
}