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
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.mcreator.ssc.world.inventory.IDcodeMenu;
import net.mcreator.ssc.procedures.*;
import net.mcreator.ssc.network.IDcodeButtonMessage;
import net.mcreator.ssc.init.Ssc14ModScreens;

import java.security.Security;

import java.lang.ref.Cleaner;

import com.mojang.brigadier.Command;

import com.google.common.util.concurrent.Service;

public class IDcodeScreen extends AbstractContainerScreen<IDcodeMenu> implements Ssc14ModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox Job_Name;
	private Checkbox gun_room;
	private Checkbox HoS;
	private Checkbox Brig;
	private Checkbox Security;
	private Checkbox Detective;
	private Checkbox Blue_Sh;
	private Checkbox CE;
	private Checkbox CMO;
	private Checkbox RD;
	private Checkbox PNT;
	private Checkbox HoP;
	private Checkbox Atmos;
	private Checkbox Ingeneer;
	private Checkbox Qm;
	private Checkbox Utilizat;
	private Checkbox Supply_Deportament;
	private Checkbox Technical;
	private Checkbox Chemistry;
	private Checkbox Medical;
	private Checkbox Scientist;
	private Checkbox Out;
	private Checkbox Crio;
	private Checkbox Capitan;
	private Checkbox Service;
	private Checkbox Kitchen;
	private Checkbox Gidroponic;
	private Checkbox Bar;
	private Checkbox Teatre;
	private Checkbox Cleaner;
	private Checkbox Command;
	private Checkbox Uridic;
	private Checkbox Church;
	private Checkbox Job_Name_Readact;
	private ImageButton imagebutton_kapitan;
	private ImageButton imagebutton_nt;
	private ImageButton imagebutton_gp;
	private ImageButton imagebutton_gsb;
	private ImageButton imagebutton_gv;
	private ImageButton imagebutton_km;
	private ImageButton imagebutton_gruzchik;
	private ImageButton imagebutton_utilizator;
	private ImageButton imagebutton_passazhir;
	private ImageButton imagebutton_nr;
	private ImageButton imagebutton_viedushchii_uchionyi;
	private ImageButton imagebutton_uchionyi;
	private ImageButton imagebutton_nauchnyi_assistient;
	private ImageButton imagebutton_si;
	private ImageButton imagebutton_atmos;
	private ImageButton imagebutton_viedushchii_inzhienier;
	private ImageButton imagebutton_tiekhnichieskii_assistient;
	private ImageButton imagebutton_inzhienier;
	private ImageButton imagebutton_paramiedik;
	private ImageButton imagebutton_khimik;
	private ImageButton imagebutton_vrach;
	private ImageButton imagebutton_viedushchii_vrach;
	private ImageButton imagebutton_intiern;
	private ImageButton imagebutton_kadiet;
	private ImageButton imagebutton_instruktor_sb;
	private ImageButton imagebutton_ofitsier;
	private ImageButton imagebutton_dietiektiv;
	private ImageButton imagebutton_psikhologh;
	private ImageButton imagebutton_brighmied;
	private ImageButton imagebutton_mim;
	private ImageButton imagebutton_smotritiel;
	private ImageButton imagebutton_osh;
	private ImageButton imagebutton_adutant;
	private ImageButton imagebutton_kloun;
	private ImageButton imagebutton_muzykant;
	private ImageButton imagebutton_zootiekhnik;
	private ImageButton imagebutton_bibliotiekar;
	private ImageButton imagebutton_siervisnyi_rabotnik;
	private ImageButton imagebutton_uborshchik;
	private ImageButton imagebutton_povar;
	private ImageButton imagebutton_botanik;
	private ImageButton imagebutton_barmien;
	private ImageButton imagebutton_boksior;
	private ImageButton imagebutton_sviashchiennik;
	private ImageButton imagebutton_rieportior;
	private ImageButton imagebutton_avd;
	private ImageButton imagebutton_idcode_all_on;
	private ImageButton imagebutton_idcode_all_off;

	public IDcodeScreen(IDcodeMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 416;
		this.imageHeight = 235;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("Job_Name"))
				Job_Name.setValue(stringState);
		}
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
			} else if (name.equals("Blue_Sh")) {
				if (Blue_Sh.selected() != logicState)
					Blue_Sh.onPress();
			} else if (name.equals("CE")) {
				if (CE.selected() != logicState)
					CE.onPress();
			} else if (name.equals("CMO")) {
				if (CMO.selected() != logicState)
					CMO.onPress();
			} else if (name.equals("RD")) {
				if (RD.selected() != logicState)
					RD.onPress();
			} else if (name.equals("PNT")) {
				if (PNT.selected() != logicState)
					PNT.onPress();
			} else if (name.equals("HoP")) {
				if (HoP.selected() != logicState)
					HoP.onPress();
			} else if (name.equals("Atmos")) {
				if (Atmos.selected() != logicState)
					Atmos.onPress();
			} else if (name.equals("Ingeneer")) {
				if (Ingeneer.selected() != logicState)
					Ingeneer.onPress();
			} else if (name.equals("Qm")) {
				if (Qm.selected() != logicState)
					Qm.onPress();
			} else if (name.equals("Utilizat")) {
				if (Utilizat.selected() != logicState)
					Utilizat.onPress();
			} else if (name.equals("Supply_Deportament")) {
				if (Supply_Deportament.selected() != logicState)
					Supply_Deportament.onPress();
			} else if (name.equals("Technical")) {
				if (Technical.selected() != logicState)
					Technical.onPress();
			} else if (name.equals("Chemistry")) {
				if (Chemistry.selected() != logicState)
					Chemistry.onPress();
			} else if (name.equals("Medical")) {
				if (Medical.selected() != logicState)
					Medical.onPress();
			} else if (name.equals("Scientist")) {
				if (Scientist.selected() != logicState)
					Scientist.onPress();
			} else if (name.equals("Out")) {
				if (Out.selected() != logicState)
					Out.onPress();
			} else if (name.equals("Crio")) {
				if (Crio.selected() != logicState)
					Crio.onPress();
			} else if (name.equals("Capitan")) {
				if (Capitan.selected() != logicState)
					Capitan.onPress();
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
			} else if (name.equals("Command")) {
				if (Command.selected() != logicState)
					Command.onPress();
			} else if (name.equals("Uridic")) {
				if (Uridic.selected() != logicState)
					Uridic.onPress();
			} else if (name.equals("Church")) {
				if (Church.selected() != logicState)
					Church.onPress();
			} else if (name.equals("Job_Name_Readact")) {
				if (Job_Name_Readact.selected() != logicState)
					Job_Name_Readact.onPress();
			}
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("ssc_14:textures/screens/i_dcode.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		Job_Name.render(guiGraphics, mouseX, mouseY, partialTicks);
		boolean customTooltipShown = false;
		if (mouseX > leftPos + 224 && mouseX < leftPos + 248 && mouseY > topPos + 177 && mouseY < topPos + 201) {
			guiGraphics.setTooltipForNextFrame(font, Component.translatable("gui.ssc_14.i_dcode.tooltip_riedaktirovat_imia_dolzhnosti"), mouseX, mouseY);
			customTooltipShown = true;
		}
		if (!customTooltipShown)
			this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/i_dcode___copy.png"), this.leftPos + 0, this.topPos + 0, 0, 0, 416, 235, 416, 235);
		if (IDcmdecor1Procedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/id_code_menu_decor1.png"), this.leftPos + 87, this.topPos + 180, 0, 0, 17, 3, 17, 3);
		}
		if (IDcmdecor2Procedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/id_code_menu_decor2.png"), this.leftPos + 90, this.topPos + 184, 0, 0, 9, 10, 9, 10);
		}
		if (IDcmdecor3Procedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/id_code_menu_decor3.png"), this.leftPos + 90, this.topPos + 184, 0, 0, 9, 10, 9, 10);
		}
		if (GunroomIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 4, this.topPos + 4, 0, 0, 19, 19, 19, 19);
		}
		if (HoSIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 4, this.topPos + 25, 0, 0, 19, 19, 19, 19);
		}
		if (BrigIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 4, this.topPos + 46, 0, 0, 19, 19, 19, 19);
		}
		if (SecurityIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 4, this.topPos + 67, 0, 0, 19, 19, 19, 19);
		}
		if (DetectiveIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 4, this.topPos + 88, 0, 0, 19, 19, 19, 19);
		}
		if (PNTIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 4, this.topPos + 109, 0, 0, 19, 19, 19, 19);
		}
		if (CrioIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 4, this.topPos + 130, 0, 0, 19, 19, 19, 19);
		}
		if (CEIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 82, this.topPos + 4, 0, 0, 19, 19, 19, 19);
		}
		if (AtmosIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 82, this.topPos + 25, 0, 0, 19, 19, 19, 19);
		}
		if (IngeneerIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 82, this.topPos + 46, 0, 0, 19, 19, 19, 19);
		}
		if (OutIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 82, this.topPos + 67, 0, 0, 19, 19, 19, 19);
		}
		if (QmIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 82, this.topPos + 88, 0, 0, 19, 19, 19, 19);
		}
		if (UtilizatIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 82, this.topPos + 109, 0, 0, 19, 19, 19, 19);
		}
		if (SupplyDeportamentIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 82, this.topPos + 130, 0, 0, 19, 19, 19, 19);
		}
		if (CMOIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 169, this.topPos + 4, 0, 0, 19, 19, 19, 19);
		}
		if (ChemistryIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 169, this.topPos + 25, 0, 0, 19, 19, 19, 19);
		}
		if (MedicalIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 169, this.topPos + 46, 0, 0, 19, 19, 19, 19);
		}
		if (RDIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 169, this.topPos + 67, 0, 0, 19, 19, 19, 19);
		}
		if (ScientistIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 169, this.topPos + 88, 0, 0, 19, 19, 19, 19);
		}
		if (TechnicalIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 169, this.topPos + 109, 0, 0, 19, 19, 19, 19);
		}
		if (ChurchIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 169, this.topPos + 130, 0, 0, 19, 19, 19, 19);
		}
		if (HoPIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 256, this.topPos + 4, 0, 0, 19, 19, 19, 19);
		}
		if (ServiceIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 256, this.topPos + 25, 0, 0, 19, 19, 19, 19);
		}
		if (KitchenIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 256, this.topPos + 46, 0, 0, 19, 19, 19, 19);
		}
		if (GidroponicIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 256, this.topPos + 67, 0, 0, 19, 19, 19, 19);
		}
		if (BarIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 256, this.topPos + 88, 0, 0, 19, 19, 19, 19);
		}
		if (TeatreIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 256, this.topPos + 109, 0, 0, 19, 19, 19, 19);
		}
		if (CleanerIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 256, this.topPos + 130, 0, 0, 19, 19, 19, 19);
		}
		if (CapitanIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 334, this.topPos + 4, 0, 0, 19, 19, 19, 19);
		}
		if (CommandIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 334, this.topPos + 25, 0, 0, 19, 19, 19, 19);
		}
		if (BlueShIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 334, this.topPos + 46, 0, 0, 19, 19, 19, 19);
		}
		if (UridicIDCLockGUIProcedure.execute(entity)) {
			guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("ssc_14:textures/screens/lock_gui.png"), this.leftPos + 334, this.topPos + 67, 0, 0, 19, 19, 19, 19);
		}
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		if (Job_Name.isFocused())
			return Job_Name.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String Job_NameValue = Job_Name.getValue();
		super.resize(minecraft, width, height);
		Job_Name.setValue(Job_NameValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		Job_Name = new EditBox(this.font, this.leftPos + 106, this.topPos + 180, 118, 18, Component.translatable("gui.ssc_14.i_dcode.Job_Name"));
		Job_Name.setMaxLength(8192);
		Job_Name.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "Job_Name", content, false);
		});
		this.addWidget(this.Job_Name);
		imagebutton_kapitan = new ImageButton(this.leftPos + 13, this.topPos + 163, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/kapitan.png"), ResourceLocation.parse("ssc_14:textures/screens/kapitan.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(0, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_kapitan);
		imagebutton_nt = new ImageButton(this.leftPos + 22, this.topPos + 163, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/nt.png"), ResourceLocation.parse("ssc_14:textures/screens/nt.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(1, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_nt);
		imagebutton_gp = new ImageButton(this.leftPos + 13, this.topPos + 172, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/gp.png"), ResourceLocation.parse("ssc_14:textures/screens/gp.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(2, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_gp);
		imagebutton_gsb = new ImageButton(this.leftPos + 13, this.topPos + 199, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/gsb.png"), ResourceLocation.parse("ssc_14:textures/screens/gsb.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(3, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_gsb);
		imagebutton_gv = new ImageButton(this.leftPos + 13, this.topPos + 208, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/gv.png"), ResourceLocation.parse("ssc_14:textures/screens/gv.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(4, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_gv);
		imagebutton_km = new ImageButton(this.leftPos + 13, this.topPos + 181, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/km.png"), ResourceLocation.parse("ssc_14:textures/screens/km.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(5, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_km);
		imagebutton_gruzchik = new ImageButton(this.leftPos + 31, this.topPos + 181, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/gruzchik.png"), ResourceLocation.parse("ssc_14:textures/screens/gruzchik.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(6, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 6, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_gruzchik);
		imagebutton_utilizator = new ImageButton(this.leftPos + 22, this.topPos + 181, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/utilizator.png"), ResourceLocation.parse("ssc_14:textures/screens/utilizator.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(7, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 7, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_utilizator);
		imagebutton_passazhir = new ImageButton(this.leftPos + 58, this.topPos + 190, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/passazhir.png"), ResourceLocation.parse("ssc_14:textures/screens/passazhir.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(8, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 8, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_passazhir);
		imagebutton_nr = new ImageButton(this.leftPos + 13, this.topPos + 217, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/nr.png"), ResourceLocation.parse("ssc_14:textures/screens/nr.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(9, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 9, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_nr);
		imagebutton_viedushchii_uchionyi = new ImageButton(this.leftPos + 22, this.topPos + 217, 8, 8,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/viedushchii_uchionyi.png"), ResourceLocation.parse("ssc_14:textures/screens/viedushchii_uchionyi.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(10, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 10, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_viedushchii_uchionyi);
		imagebutton_uchionyi = new ImageButton(this.leftPos + 31, this.topPos + 217, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/uchionyi.png"), ResourceLocation.parse("ssc_14:textures/screens/uchionyi.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(11, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 11, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_uchionyi);
		imagebutton_nauchnyi_assistient = new ImageButton(this.leftPos + 40, this.topPos + 217, 8, 8,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/nauchnyi_assistient.png"), ResourceLocation.parse("ssc_14:textures/screens/nauchnyi_assistient.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(12, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 12, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_nauchnyi_assistient);
		imagebutton_si = new ImageButton(this.leftPos + 13, this.topPos + 190, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/si.png"), ResourceLocation.parse("ssc_14:textures/screens/si.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(13, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 13, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_si);
		imagebutton_atmos = new ImageButton(this.leftPos + 31, this.topPos + 190, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/atmos.png"), ResourceLocation.parse("ssc_14:textures/screens/atmos.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(14, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 14, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_atmos);
		imagebutton_viedushchii_inzhienier = new ImageButton(this.leftPos + 22, this.topPos + 190, 8, 8,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/viedushchii_inzhienier.png"), ResourceLocation.parse("ssc_14:textures/screens/viedushchii_inzhienier.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(15, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 15, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_viedushchii_inzhienier);
		imagebutton_tiekhnichieskii_assistient = new ImageButton(this.leftPos + 49, this.topPos + 190, 8, 8,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/tiekhnichieskii_assistient.png"), ResourceLocation.parse("ssc_14:textures/screens/tiekhnichieskii_assistient.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(16, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 16, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_tiekhnichieskii_assistient);
		imagebutton_inzhienier = new ImageButton(this.leftPos + 40, this.topPos + 190, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/inzhienier.png"), ResourceLocation.parse("ssc_14:textures/screens/inzhienier.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(17, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 17, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_inzhienier);
		imagebutton_paramiedik = new ImageButton(this.leftPos + 40, this.topPos + 208, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/paramiedik.png"), ResourceLocation.parse("ssc_14:textures/screens/paramiedik.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(18, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 18, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_paramiedik);
		imagebutton_khimik = new ImageButton(this.leftPos + 31, this.topPos + 208, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/khimik.png"), ResourceLocation.parse("ssc_14:textures/screens/khimik.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(19, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 19, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_khimik);
		imagebutton_vrach = new ImageButton(this.leftPos + 49, this.topPos + 208, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/vrach.png"), ResourceLocation.parse("ssc_14:textures/screens/vrach.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(20, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 20, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vrach);
		imagebutton_viedushchii_vrach = new ImageButton(this.leftPos + 22, this.topPos + 208, 8, 8,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/viedushchii_vrach.png"), ResourceLocation.parse("ssc_14:textures/screens/viedushchii_vrach.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(21, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 21, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_viedushchii_vrach);
		imagebutton_intiern = new ImageButton(this.leftPos + 67, this.topPos + 208, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/intiern.png"), ResourceLocation.parse("ssc_14:textures/screens/intiern.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(22, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 22, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_intiern);
		imagebutton_kadiet = new ImageButton(this.leftPos + 67, this.topPos + 199, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/kadiet.png"), ResourceLocation.parse("ssc_14:textures/screens/kadiet.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(23, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 23, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_kadiet);
		imagebutton_instruktor_sb = new ImageButton(this.leftPos + 22, this.topPos + 199, 8, 8,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/instruktor_sb.png"), ResourceLocation.parse("ssc_14:textures/screens/instruktor_sb.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(24, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 24, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_instruktor_sb);
		imagebutton_ofitsier = new ImageButton(this.leftPos + 58, this.topPos + 199, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/ofitsier.png"), ResourceLocation.parse("ssc_14:textures/screens/ofitsier.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(25, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 25, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_ofitsier);
		imagebutton_dietiektiv = new ImageButton(this.leftPos + 40, this.topPos + 199, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/dietiektiv.png"), ResourceLocation.parse("ssc_14:textures/screens/dietiektiv.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(26, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 26, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_dietiektiv);
		imagebutton_psikhologh = new ImageButton(this.leftPos + 58, this.topPos + 208, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/psikhologh.png"), ResourceLocation.parse("ssc_14:textures/screens/psikhologh.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(27, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 27, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_psikhologh);
		imagebutton_brighmied = new ImageButton(this.leftPos + 49, this.topPos + 199, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/brighmied.png"), ResourceLocation.parse("ssc_14:textures/screens/brighmied.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(28, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 28, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_brighmied);
		imagebutton_mim = new ImageButton(this.leftPos + 58, this.topPos + 163, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/mim.png"), ResourceLocation.parse("ssc_14:textures/screens/mim.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(29, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 29, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_mim);
		imagebutton_smotritiel = new ImageButton(this.leftPos + 31, this.topPos + 199, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/smotritiel.png"), ResourceLocation.parse("ssc_14:textures/screens/smotritiel.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(30, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 30, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_smotritiel);
		imagebutton_osh = new ImageButton(this.leftPos + 49, this.topPos + 163, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/osh.png"), ResourceLocation.parse("ssc_14:textures/screens/osh.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(31, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 31, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_osh);
		imagebutton_adutant = new ImageButton(this.leftPos + 31, this.topPos + 163, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/adutant.png"), ResourceLocation.parse("ssc_14:textures/screens/adutant.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(32, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 32, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_adutant);
		imagebutton_kloun = new ImageButton(this.leftPos + 67, this.topPos + 163, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/kloun.png"), ResourceLocation.parse("ssc_14:textures/screens/kloun.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(33, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 33, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_kloun);
		imagebutton_muzykant = new ImageButton(this.leftPos + 67, this.topPos + 190, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/muzykant.png"), ResourceLocation.parse("ssc_14:textures/screens/muzykant.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(34, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 34, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_muzykant);
		imagebutton_zootiekhnik = new ImageButton(this.leftPos + 67, this.topPos + 181, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/zootiekhnik.png"), ResourceLocation.parse("ssc_14:textures/screens/zootiekhnik.png")),
				e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(35, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 35, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_zootiekhnik);
		imagebutton_bibliotiekar = new ImageButton(this.leftPos + 58, this.topPos + 181, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/bibliotiekar.png"), ResourceLocation.parse("ssc_14:textures/screens/bibliotiekar.png")),
				e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(36, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 36, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_bibliotiekar);
		imagebutton_siervisnyi_rabotnik = new ImageButton(this.leftPos + 22, this.topPos + 172, 8, 8,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/siervisnyi_rabotnik.png"), ResourceLocation.parse("ssc_14:textures/screens/siervisnyi_rabotnik.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(37, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 37, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_siervisnyi_rabotnik);
		imagebutton_uborshchik = new ImageButton(this.leftPos + 31, this.topPos + 172, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/uborshchik.png"), ResourceLocation.parse("ssc_14:textures/screens/uborshchik.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(38, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 38, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_uborshchik);
		imagebutton_povar = new ImageButton(this.leftPos + 40, this.topPos + 172, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/povar.png"), ResourceLocation.parse("ssc_14:textures/screens/povar.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(39, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 39, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_povar);
		imagebutton_botanik = new ImageButton(this.leftPos + 49, this.topPos + 172, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/botanik.png"), ResourceLocation.parse("ssc_14:textures/screens/botanik.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(40, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 40, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_botanik);
		imagebutton_barmien = new ImageButton(this.leftPos + 58, this.topPos + 172, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/barmien.png"), ResourceLocation.parse("ssc_14:textures/screens/barmien.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(41, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 41, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_barmien);
		imagebutton_boksior = new ImageButton(this.leftPos + 49, this.topPos + 181, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/boksior.png"), ResourceLocation.parse("ssc_14:textures/screens/boksior.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(42, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 42, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_boksior);
		imagebutton_sviashchiennik = new ImageButton(this.leftPos + 67, this.topPos + 172, 8, 8,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/sviashchiennik.png"), ResourceLocation.parse("ssc_14:textures/screens/sviashchiennik.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(43, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 43, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_sviashchiennik);
		imagebutton_rieportior = new ImageButton(this.leftPos + 40, this.topPos + 181, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/rieportior.png"), ResourceLocation.parse("ssc_14:textures/screens/rieportior.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(44, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 44, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_rieportior);
		imagebutton_avd = new ImageButton(this.leftPos + 40, this.topPos + 163, 8, 8, new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/avd.png"), ResourceLocation.parse("ssc_14:textures/screens/avd.png")), e -> {
			int x = IDcodeScreen.this.x;
			int y = IDcodeScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(45, x, y, z));
				IDcodeButtonMessage.handleButtonAction(entity, 45, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_avd);
		imagebutton_idcode_all_on = new ImageButton(this.leftPos + 86, this.topPos + 220, 24, 9,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/idcode_all_on.png"), ResourceLocation.parse("ssc_14:textures/screens/idcode_all_on_2.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(46, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 46, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_idcode_all_on);
		imagebutton_idcode_all_off = new ImageButton(this.leftPos + 113, this.topPos + 220, 25, 9,
				new WidgetSprites(ResourceLocation.parse("ssc_14:textures/screens/idcode_all_off.png"), ResourceLocation.parse("ssc_14:textures/screens/idcode_all_off_2.png")), e -> {
					int x = IDcodeScreen.this.x;
					int y = IDcodeScreen.this.y;
					if (true) {
						ClientPacketDistributor.sendToServer(new IDcodeButtonMessage(47, x, y, z));
						IDcodeButtonMessage.handleButtonAction(entity, 47, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(RenderPipelines.GUI_TEXTURED, sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_idcode_all_off);
		gun_room = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.gun_room"), this.font).pos(this.leftPos + 5, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "gun_room", value, false);
		}).build();
		this.addRenderableWidget(gun_room);
		HoS = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.HoS"), this.font).pos(this.leftPos + 5, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "HoS", value, false);
		}).build();
		this.addRenderableWidget(HoS);
		Brig = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Brig"), this.font).pos(this.leftPos + 5, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Brig", value, false);
		}).build();
		this.addRenderableWidget(Brig);
		Security = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Security"), this.font).pos(this.leftPos + 5, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Security", value, false);
		}).build();
		this.addRenderableWidget(Security);
		Detective = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Detective"), this.font).pos(this.leftPos + 5, this.topPos + 89).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Detective", value, false);
		}).build();
		this.addRenderableWidget(Detective);
		Blue_Sh = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Blue_Sh"), this.font).pos(this.leftPos + 335, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Blue_Sh", value, false);
		}).build();
		this.addRenderableWidget(Blue_Sh);
		CE = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.CE"), this.font).pos(this.leftPos + 83, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "CE", value, false);
		}).build();
		this.addRenderableWidget(CE);
		CMO = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.CMO"), this.font).pos(this.leftPos + 170, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "CMO", value, false);
		}).build();
		this.addRenderableWidget(CMO);
		RD = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.RD"), this.font).pos(this.leftPos + 170, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "RD", value, false);
		}).build();
		this.addRenderableWidget(RD);
		PNT = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.PNT"), this.font).pos(this.leftPos + 5, this.topPos + 110).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "PNT", value, false);
		}).build();
		this.addRenderableWidget(PNT);
		HoP = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.HoP"), this.font).pos(this.leftPos + 257, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "HoP", value, false);
		}).build();
		this.addRenderableWidget(HoP);
		Atmos = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Atmos"), this.font).pos(this.leftPos + 83, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Atmos", value, false);
		}).build();
		this.addRenderableWidget(Atmos);
		Ingeneer = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Ingeneer"), this.font).pos(this.leftPos + 83, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Ingeneer", value, false);
		}).build();
		this.addRenderableWidget(Ingeneer);
		Qm = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Qm"), this.font).pos(this.leftPos + 83, this.topPos + 89).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Qm", value, false);
		}).build();
		this.addRenderableWidget(Qm);
		Utilizat = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Utilizat"), this.font).pos(this.leftPos + 83, this.topPos + 110).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Utilizat", value, false);
		}).build();
		this.addRenderableWidget(Utilizat);
		Supply_Deportament = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Supply_Deportament"), this.font).pos(this.leftPos + 83, this.topPos + 131).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Supply_Deportament", value, false);
		}).build();
		this.addRenderableWidget(Supply_Deportament);
		Technical = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Technical"), this.font).pos(this.leftPos + 170, this.topPos + 110).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Technical", value, false);
		}).build();
		this.addRenderableWidget(Technical);
		Chemistry = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Chemistry"), this.font).pos(this.leftPos + 170, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Chemistry", value, false);
		}).build();
		this.addRenderableWidget(Chemistry);
		Medical = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Medical"), this.font).pos(this.leftPos + 170, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Medical", value, false);
		}).build();
		this.addRenderableWidget(Medical);
		Scientist = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Scientist"), this.font).pos(this.leftPos + 170, this.topPos + 89).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Scientist", value, false);
		}).build();
		this.addRenderableWidget(Scientist);
		Out = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Out"), this.font).pos(this.leftPos + 83, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Out", value, false);
		}).build();
		this.addRenderableWidget(Out);
		Crio = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Crio"), this.font).pos(this.leftPos + 5, this.topPos + 131).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Crio", value, false);
		}).build();
		this.addRenderableWidget(Crio);
		Capitan = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Capitan"), this.font).pos(this.leftPos + 335, this.topPos + 5).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Capitan", value, false);
		}).build();
		this.addRenderableWidget(Capitan);
		Service = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Service"), this.font).pos(this.leftPos + 257, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Service", value, false);
		}).build();
		this.addRenderableWidget(Service);
		Kitchen = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Kitchen"), this.font).pos(this.leftPos + 257, this.topPos + 47).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Kitchen", value, false);
		}).build();
		this.addRenderableWidget(Kitchen);
		Gidroponic = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Gidroponic"), this.font).pos(this.leftPos + 257, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Gidroponic", value, false);
		}).build();
		this.addRenderableWidget(Gidroponic);
		Bar = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Bar"), this.font).pos(this.leftPos + 257, this.topPos + 89).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Bar", value, false);
		}).build();
		this.addRenderableWidget(Bar);
		Teatre = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Teatre"), this.font).pos(this.leftPos + 257, this.topPos + 110).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Teatre", value, false);
		}).build();
		this.addRenderableWidget(Teatre);
		Cleaner = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Cleaner"), this.font).pos(this.leftPos + 257, this.topPos + 131).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Cleaner", value, false);
		}).build();
		this.addRenderableWidget(Cleaner);
		Command = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Command"), this.font).pos(this.leftPos + 335, this.topPos + 26).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Command", value, false);
		}).build();
		this.addRenderableWidget(Command);
		Uridic = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Uridic"), this.font).pos(this.leftPos + 335, this.topPos + 68).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Uridic", value, false);
		}).build();
		this.addRenderableWidget(Uridic);
		Church = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Church"), this.font).pos(this.leftPos + 170, this.topPos + 131).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Church", value, false);
		}).build();
		this.addRenderableWidget(Church);
		Job_Name_Readact = Checkbox.builder(Component.translatable("gui.ssc_14.i_dcode.Job_Name_Readact"), this.font).pos(this.leftPos + 226, this.topPos + 180).onValueChange((checkbox, value) -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 1, "Job_Name_Readact", value, false);
		}).build();
		this.addRenderableWidget(Job_Name_Readact);
	}
}