
package net.mcreator.ssc.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.util.Mth;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import net.mcreator.ssc.world.inventory.OrderGUIMenu;
import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.network.OrderGUIButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

import com.mojang.blaze3d.platform.InputConstants;

public class OrderGUIScreen extends AbstractContainerScreen<OrderGUIMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_sale_cons_gui_button_1;
	private ImageButton imagebutton_sale_cons_gui_button_11;
	private ImageButton imagebutton_sale_cons_gui_button_12;
	private ImageButton imagebutton_sale_cons_gui_button_13;
	private ImageButton imagebutton_sale_cons_gui_button_14;
	private ImageButton imagebutton_sale_cons_gui_button_15;
	private ImageButton imagebutton_sale_cons_gui_button_16;
	private ImageButton imagebutton_sale_cons_gui_button_17;
	private ImageButton imagebutton_sale_cons_gui_button_18;
	private ImageButton imagebutton_sale_cons_gui_button_19;
	private ImageButton imagebutton_sale_cons_gui_button_110;
	private ImageButton imagebutton_sale_cons_gui_button_111;
	private ImageButton imagebutton_sale_cons_gui_button_112;
	private ImageButton imagebutton_sale_cons_gui_button_113;
	private ImageButton imagebutton_sale_cons_gui_button_114;
	private static final Identifier BACKGROUND = Identifier.parse("ssc_14:textures/screens/order_gui.png");
	private static final Identifier IMAGE_0 = Identifier.parse("ssc_14:textures/screens/gui_cargo_.png");
	private static final Identifier SPRITE_0 = Identifier.parse("ssc_14:textures/screens/order_cons_gui_buttons_sprite.png");

	public OrderGUIScreen(OrderGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 1, 1);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override public void updateMenuState(int t, String n, Object s) {}
	@Override public void extractRenderState(GuiGraphicsExtractor g, int mx, int my, float pt) { super.extractRenderState(g, mx, my, pt); }
	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMAGE_0, this.leftPos + -208, this.topPos + -119, 0, 0, 416, 235, 416, 235);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SPRITE_0, this.leftPos + -199, this.topPos + -43, 0, Mth.clamp((int) OrdGUIspriteProcedure.execute(world, x, y, z) * 150, 0, 2100), 104, 150, 104, 2250);
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
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_balans"), -199, -93, -1, false);

		guiGraphics.text(this.font, OrderGUIbudgetTEXTProcedure.execute(world), -199, -79, -10944735, false);
		
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_katieghorii"), -197, -56, -1, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_tovary"), -87, -92, -1, false);
		
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_arsienal"), -198, -42, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_atmosfiera"), -198, -32, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_snabzhieniie"), -198, -22, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_ekstriennyie"), -198, -12, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_inzhienieriia"), -198, -2, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_ieda"), -198, 8, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_razvliechieniia"), -198, 18, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_gidroponika"), -198, 28, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_zhivotnyie"), -198, 38, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_matierialy"), -198, 48, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_mieditsiny"), -198, 58, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_nauka"), -198, 68, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_sb"), -198, 78, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_siervis"), -198, 88, -1710619, false);
		guiGraphics.text(this.font, Component.translatable("gui.ssc_14.order_gui.label_shattl"), -198, 98, -1710619, false);

		int currentTab = 0;
		net.minecraft.world.level.block.entity.BlockEntity be = world.getBlockEntity(new net.minecraft.core.BlockPos(x, y, z));
		if (be != null) {
			currentTab = (int) be.getPersistentData().getDoubleOr("TAB", 0);
		}

		java.util.List tabItems = net.mcreator.ssc.CargoRegistry.getItemsForTab(currentTab);
		int rowYName = -79;
		int rowYPrice = -70;
		int size = tabItems.size();
		if (size >= 10) size = 10;

		for (int i = 0; i != size; i++) {
			net.mcreator.ssc.CargoRegistry.CargoItem item = (net.mcreator.ssc.CargoRegistry.CargoItem) tabItems.get(i);
			
			// Вычисляем реальное общее количество штук: пачки * размер стака
			int totalItems = item.getCount() * item.getStack().getMaxStackSize();
			String displayName = item.getStack().getHoverName().getString();
			
			// Если штук больше 1, красиво дописываем их количество, например: "Сталь (90)"
			if (totalItems > 1) {
				displayName = displayName + " (" + totalItems + ")";
			}
			
			guiGraphics.text(this.font, displayName, -72, rowYName, -1, false);

			String priceText = "$:  " + item.getPrice();
			guiGraphics.text(this.font, priceText, -72, rowYPrice, -16711936, false);

			rowYName += 18;
			rowYPrice += 18;
		}
	}
	@Override
	public void init() {
		super.init();
		WidgetSprites ws = new WidgetSprites(Identifier.parse("ssc_14:textures/screens/sale_cons_gui_button_1.png"), Identifier.parse("ssc_14:textures/screens/sale_cons_gui_button_1.png"));
		
		imagebutton_sale_cons_gui_button_1 = new ImageButton(this.leftPos + -199, this.topPos + -43, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(0, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_1);

		imagebutton_sale_cons_gui_button_11 = new ImageButton(this.leftPos + -199, this.topPos + -33, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(1, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_11);

		imagebutton_sale_cons_gui_button_12 = new ImageButton(this.leftPos + -199, this.topPos + -23, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(2, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_12);

		imagebutton_sale_cons_gui_button_13 = new ImageButton(this.leftPos + -199, this.topPos + -13, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(3, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_13);

		imagebutton_sale_cons_gui_button_14 = new ImageButton(this.leftPos + -199, this.topPos + -3, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(4, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_14);

		imagebutton_sale_cons_gui_button_15 = new ImageButton(this.leftPos + -199, this.topPos + 7, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(5, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_15);

		imagebutton_sale_cons_gui_button_16 = new ImageButton(this.leftPos + -199, this.topPos + 17, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(6, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_16);

		imagebutton_sale_cons_gui_button_17 = new ImageButton(this.leftPos + -199, this.topPos + 27, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(7, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_17);

		imagebutton_sale_cons_gui_button_18 = new ImageButton(this.leftPos + -199, this.topPos + 37, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(8, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_18);

		imagebutton_sale_cons_gui_button_19 = new ImageButton(this.leftPos + -199, this.topPos + 47, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(9, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_19);

		imagebutton_sale_cons_gui_button_110 = new ImageButton(this.leftPos + -199, this.topPos + 57, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(10, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_110);

		imagebutton_sale_cons_gui_button_111 = new ImageButton(this.leftPos + -199, this.topPos + 67, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(11, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_111);

		imagebutton_sale_cons_gui_button_112 = new ImageButton(this.leftPos + -199, this.topPos + 77, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(12, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_112);

		imagebutton_sale_cons_gui_button_113 = new ImageButton(this.leftPos + -199, this.topPos + 87, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(13, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_113);

		imagebutton_sale_cons_gui_button_114 = new ImageButton(this.leftPos + -199, this.topPos + 97, 104, 10, ws, e -> {
			ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(14, x, y, z));
		}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) { g.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height); } };
		this.addRenderableWidget(imagebutton_sale_cons_gui_button_114);

		WidgetSprites invisibleSprites = new WidgetSprites(Identifier.parse("ssc_14:textures/screens/sale_cons_gui_button_1.png"), Identifier.parse("ssc_14:textures/screens/sale_cons_gui_button_1.png"));
		int btnY = -83;
		for (int b = 0; b != 10; b++) {
			final int currentBtnId = 100 + b;
			ImageButton invBtn = new ImageButton(this.leftPos + -75, this.topPos + btnY, 270, 16, invisibleSprites, btn -> {
				ClientPacketDistributor.sendToServer(new OrderGUIButtonMessage(currentBtnId, x, y, z));
			}) { @Override public void extractContents(GuiGraphicsExtractor g, int mx, int my, float pt) {} };
			this.addRenderableWidget(invBtn);
			btnY += 18;
		}
	}
}
