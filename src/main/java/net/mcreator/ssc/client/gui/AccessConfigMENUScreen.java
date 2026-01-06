package net.mcreator.ssc.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.ssc.world.inventory.AccessConfigMENUMenu;
import net.mcreator.ssc.init.Ssc14ModScreens;

import java.security.Security;

import java.lang.ref.Cleaner;

import com.mojang.brigadier.Command;

import com.google.common.util.concurrent.Service;

public class AccessConfigMENUScreen extends AbstractContainerScreen<AccessConfigMENUMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Checkbox gun_room;
	private Checkbox HoS;
	private Checkbox Brig;
	private Checkbox Security;
	private Checkbox Detective;
	private Checkbox PNT;
	private Checkbox Crio;
	private Checkbox CE;
	private Checkbox Atmos;
	private Checkbox Ingeneer;
	private Checkbox Out;
	private Checkbox Qm;
	private Checkbox Utilizat;
	private Checkbox Supply_Deportament;
	private Checkbox CMO;
	private Checkbox Chemistry;
	private Checkbox Medical;
	private Checkbox RD;
	private Checkbox Scientist;
	private Checkbox Technical;
	private Checkbox Church;
	private Checkbox HoP;
	private Checkbox Service;
	private Checkbox Kitchen;
	private Checkbox Gidroponic;
	private Checkbox Bar;
	private Checkbox Teatre;
	private Checkbox Cleaner;
	private Checkbox Capitan;
	private Checkbox Command;
	private Checkbox Blue_Sh;
	private Checkbox Uridic;

	public AccessConfigMENUScreen(AccessConfigMENUMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 416;
		this.imageHeight = 153;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 1 && elementState instanceof Boolean logicState) {
			if (name.equals("gun_room")) {
				if (gun_room.selected() != logicState)
					gun_room.onPress();
			} else if (name.equals("HoS")) {
				if (HoS.selected() != logicState)
					HoS.onPress();
			} else if (name.equals("Brig")) {
				if (Brig.selected() != logicState)
					Brig.onPress();
			} else if (name.equals("Security")) {
				if (Security.selected() != logicState)
					Security.onPress();
			} else if (name.equals("Detective")) {
				if (Detective.selected() != logicState)
					Detective.onPress();
			} else if (name.equals("PNT")) {
				if (PNT.selected() != logicState)
					PNT.onPress();
			} else if (name.equals("Crio")) {
				if (Crio.selected() != logicState)
					Crio.onPress();
			} else if (name.equals("CE")) {
				if (CE.selected() != logicState)
					CE.onPress();
			} else if (name.equals("Atmos")) {
				if (Atmos.selected() != logicState)
					Atmos.onPress();
			} else if (name.equals("Ingeneer")) {
				if (Ingeneer.selected() != logicState)
					Ingeneer.onPress();
			} else if (name.equals("Out")) {
				if (Out.selected() != logicState)
					Out.onPress();
			} else if (name.equals("Qm")) {
				if (Qm.selected() != logicState)
					Qm.onPress();
			} else if (name.equals("Utilizat")) {
				if (Utilizat.selected() != logicState)
					Utilizat.onPress();
			} else if (name.equals("Supply_Deportament")) {
				if (Supply_Deportament.selected() != logicState)
					Supply_Deportament.onPress();
			} else if (name.equals("CMO")) {
				if (CMO.selected() != logicState)
					CMO.onPress();
			} else if (name.equals("Chemistry")) {
				if (Chemistry.selected() != logicState)
					Chemistry.onPress();
			} else if (name.equals("Medical")) {
				if (Medical.selected() != logicState)
					Medical.onPress();
			} else if (name.equals("RD")) {
				if (RD.selected() != logicState)
					RD.onPress();
			} else if (name.equals("Scientist")) {
				if (Scientist.selected() != logicState)
					Scientist.onPress();
			} else if (name.equals("Technical")) {
				if (Technical.selected() != logicState)
					Technical.onPress();
			} else if (name.equals("Church")) {
				if (Church.selected() != logicState)
					Church.onPress();
			} else if (name.equals("HoP")) {
				if (HoP.selected() != logicState)
					HoP.onPress();
			} else if (name.equals("Service")) {
				if (Service.selected() != logicState)
					Service.onPress();
			} else if (name.equals("Kitchen")) {
				if (Kitchen.selected() != logicState)
					Kitchen.onPress();
			} else if (name.equals("Gidroponic")) {
				if (Gidroponic.selected() != logicState)
					Gidroponic.onPress();
			} else if (name.equals("Bar")) {
				if (Bar.selected() != logicState)
					Bar.onPress();
			} else if (name.equals("Teatre")) {
				if (Teatre.selected() != logicState)
					Teatre.onPress();
			} else if (name.equals("Cleaner")) {
				if (Cleaner.selected() != logicState)
					Cleaner.onPress();
			} else if (name.equals("Capitan")) {
				if (Capitan.selected() != logicState)
					Capitan.onPress();
			} else if (name.equals("Command")) {
				if (Command.selected() != logicState)
					Command.onPress();
			} else if (name.equals("Blue_Sh")) {
				if (Blue_Sh.selected() != logicState)
					Blue_Sh.onPress();
			} else if (name.equals("Uridic")) {
				if (Uridic.selected() != logicState)
					Uridic.onPress();
			}
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("ssc_14:textures/screens/access_config_menu.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/access_config_menu__copy.png"), this.leftPos + 0, this.topPos + 0, 0, 0, 416, 153, 416, 153);
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
		gun_room = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.gun_room"), this.font).pos(this.leftPos + 5, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "gun_room", value, false);
		}).build();
		this.addRenderableWidget(gun_room);
		HoS = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.HoS"), this.font).pos(this.leftPos + 5, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "HoS", value, false);
		}).build();
		this.addRenderableWidget(HoS);
		Brig = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Brig"), this.font).pos(this.leftPos + 5, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Brig", value, false);
		}).build();
		this.addRenderableWidget(Brig);
		Security = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Security"), this.font).pos(this.leftPos + 5, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Security", value, false);
		}).build();
		this.addRenderableWidget(Security);
		Detective = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Detective"), this.font).pos(this.leftPos + 5, this.topPos + 89).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Detective", value, false);
		}).build();
		this.addRenderableWidget(Detective);
		PNT = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.PNT"), this.font).pos(this.leftPos + 5, this.topPos + 110).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "PNT", value, false);
		}).build();
		this.addRenderableWidget(PNT);
		Crio = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Crio"), this.font).pos(this.leftPos + 5, this.topPos + 131).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Crio", value, false);
		}).build();
		this.addRenderableWidget(Crio);
		CE = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.CE"), this.font).pos(this.leftPos + 83, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "CE", value, false);
		}).build();
		this.addRenderableWidget(CE);
		Atmos = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Atmos"), this.font).pos(this.leftPos + 83, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Atmos", value, false);
		}).build();
		this.addRenderableWidget(Atmos);
		Ingeneer = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Ingeneer"), this.font).pos(this.leftPos + 83, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Ingeneer", value, false);
		}).build();
		this.addRenderableWidget(Ingeneer);
		Out = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Out"), this.font).pos(this.leftPos + 83, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Out", value, false);
		}).build();
		this.addRenderableWidget(Out);
		Qm = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Qm"), this.font).pos(this.leftPos + 83, this.topPos + 89).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Qm", value, false);
		}).build();
		this.addRenderableWidget(Qm);
		Utilizat = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Utilizat"), this.font).pos(this.leftPos + 83, this.topPos + 110).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Utilizat", value, false);
		}).build();
		this.addRenderableWidget(Utilizat);
		Supply_Deportament = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Supply_Deportament"), this.font).pos(this.leftPos + 83, this.topPos + 131).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Supply_Deportament", value, false);
		}).build();
		this.addRenderableWidget(Supply_Deportament);
		CMO = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.CMO"), this.font).pos(this.leftPos + 170, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "CMO", value, false);
		}).build();
		this.addRenderableWidget(CMO);
		Chemistry = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Chemistry"), this.font).pos(this.leftPos + 170, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Chemistry", value, false);
		}).build();
		this.addRenderableWidget(Chemistry);
		Medical = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Medical"), this.font).pos(this.leftPos + 170, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Medical", value, false);
		}).build();
		this.addRenderableWidget(Medical);
		RD = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.RD"), this.font).pos(this.leftPos + 170, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "RD", value, false);
		}).build();
		this.addRenderableWidget(RD);
		Scientist = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Scientist"), this.font).pos(this.leftPos + 170, this.topPos + 89).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Scientist", value, false);
		}).build();
		this.addRenderableWidget(Scientist);
		Technical = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Technical"), this.font).pos(this.leftPos + 170, this.topPos + 110).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Technical", value, false);
		}).build();
		this.addRenderableWidget(Technical);
		Church = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Church"), this.font).pos(this.leftPos + 170, this.topPos + 131).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Church", value, false);
		}).build();
		this.addRenderableWidget(Church);
		HoP = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.HoP"), this.font).pos(this.leftPos + 257, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "HoP", value, false);
		}).build();
		this.addRenderableWidget(HoP);
		Service = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Service"), this.font).pos(this.leftPos + 257, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Service", value, false);
		}).build();
		this.addRenderableWidget(Service);
		Kitchen = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Kitchen"), this.font).pos(this.leftPos + 257, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Kitchen", value, false);
		}).build();
		this.addRenderableWidget(Kitchen);
		Gidroponic = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Gidroponic"), this.font).pos(this.leftPos + 257, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Gidroponic", value, false);
		}).build();
		this.addRenderableWidget(Gidroponic);
		Bar = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Bar"), this.font).pos(this.leftPos + 257, this.topPos + 89).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Bar", value, false);
		}).build();
		this.addRenderableWidget(Bar);
		Teatre = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Teatre"), this.font).pos(this.leftPos + 257, this.topPos + 110).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Teatre", value, false);
		}).build();
		this.addRenderableWidget(Teatre);
		Cleaner = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Cleaner"), this.font).pos(this.leftPos + 257, this.topPos + 131).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Cleaner", value, false);
		}).build();
		this.addRenderableWidget(Cleaner);
		Capitan = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Capitan"), this.font).pos(this.leftPos + 335, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Capitan", value, false);
		}).build();
		this.addRenderableWidget(Capitan);
		Command = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Command"), this.font).pos(this.leftPos + 335, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Command", value, false);
		}).build();
		this.addRenderableWidget(Command);
		Blue_Sh = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Blue_Sh"), this.font).pos(this.leftPos + 335, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Blue_Sh", value, false);
		}).build();
		this.addRenderableWidget(Blue_Sh);
		Uridic = Checkbox.builder(Component.translatable("gui.ssc_14.access_config_menu.Uridic"), this.font).pos(this.leftPos + 335, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Uridic", value, false);
		}).build();
		this.addRenderableWidget(Uridic);
	}
}