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

import net.mcreator.ssc.world.inventory.BaseUplinkUIMenu;
import net.mcreator.ssc.procedures.UplinkBalanceSystemProcedure;
import net.mcreator.ssc.network.BaseUplinkUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

public class BaseUplinkUIScreen extends AbstractContainerScreen<BaseUplinkUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_diamond_sword;

	public BaseUplinkUIScreen(BaseUplinkUIMenu container, Inventory inventory, Component text) {
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

	private static final ResourceLocation texture = ResourceLocation.parse("ssc_14:textures/screens/base_uplink_ui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		boolean customTooltipShown = false;
		if (mouseX > leftPos + 3 && mouseX < leftPos + 27 && mouseY > topPos + 27 && mouseY < topPos + 51) {
			guiGraphics.setTooltipForNextFrame(font, Component.translatable("gui.ssc_14.base_uplink_ui.tooltip_diamond_sword_price_4_tc"), mouseX, mouseY);
			customTooltipShown = true;
		}
		if (!customTooltipShown)
			this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/old-radio.png"), this.leftPos + -1, this.topPos + -3, 0, 0, 32, 32, 32, 32);
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
		guiGraphics.drawString(this.font, UplinkBalanceSystemProcedure.execute(entity, itemstack), 53, 8, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_diamond_sword = new ImageButton(this.leftPos + 6, this.topPos + 31, 16, 16,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/diamond_sword.png"), ResourceLocation.parse("ssc_14:textures/screens/diamond_sword.png")), e -> {
					int x = BaseUplinkUIScreen.this.x;
					int y = BaseUplinkUIScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new BaseUplinkUIButtonMessage(0, x, y, z));
						BaseUplinkUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_diamond_sword);
	}
}