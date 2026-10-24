package net.mcreator.ssc.gravity.ui;

import com.lowdragmc.lowdraglib2.gui.sync.bindings.impl.DataBindingBuilder;
import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.lowdragmc.lowdraglib2.gui.ui.UI;
import com.lowdragmc.lowdraglib2.gui.ui.UIElement;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Button;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Label;
import com.ssc14.handy.server.ui.ServerUIManager;
import com.ssc14.handy.window.access.Source;
import net.mcreator.ssc.Ssc14Mod;
import net.ssc14.content.block.gravity.GravityGeneratorBlockEntity;
import net.mcreator.ssc.procedures.GravGenGUIETAtextProcedure;
import net.mcreator.ssc.procedures.GravGenGUIpercentTextProcedure;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

/** One slot-free Handy window; all mutations and displayed values originate on the server. */
public final class GravityGeneratorWindow {
    public static final int WIDTH = 250;
    public static final int HEIGHT = 174;
    private static final double MAX_DISTANCE_SQUARED = 64;
    private static final int PADDING = 10;
    private static final int GAP = 6;
    private static final int BUTTON_HEIGHT = 20;
    private static final int FULL_CHARGE = 2000;

    private GravityGeneratorWindow() { }

    public static boolean canAccess(GravityGeneratorBlockEntity generator, Player player, GlobalPos source) {
        if (source == null || !player.isAlive() || player.isSpectator() || generator.isRemoved()
                || !player.level().dimension().equals(source.dimension())
                || !generator.getBlockPos().equals(source.pos()) || generator.getLevel() != player.level()
                || !player.level().hasChunkAt(source.pos())) return false;
        return player.level().getBlockEntity(source.pos()) == generator
                && player.distanceToSqr(source.pos().getCenter()) <= MAX_DISTANCE_SQUARED
                && (!(player instanceof ServerPlayer) || player.level().mayInteract(player, source.pos()));
    }

    public static ModularUI create(GravityGeneratorBlockEntity generator, GlobalPos source, Player player) {
        var root = new UIElement();
        root.layout(layout -> layout.widthPercent(100).heightPercent(100).paddingAll(PADDING).gapAll(GAP));
        root.lss("background", "#202832");
        // Handy can request a UI directly, without a preceding block interaction.
        if (!canAccess(generator, player, source)) {
            Ssc14Mod.LOGGER.debug("Rejected gravity window request at {}", source);
            return ModularUI.of(UI.of(root), player);
        }

        var title = new Label();
        title.setText(Component.translatable("item.ssc_14.gravity_generator"));
        root.addChild(title);
        addValue(root, () -> Component.translatable("gui.ssc_14.handy.gravity.power",
                Component.translatable(generator.getPersistentData().getBooleanOr("active", false)
                        ? "gui.ssc_14.handy.gravity.on" : "gui.ssc_14.handy.gravity.off")));
        addValue(root, () -> Component.translatable("gui.ssc_14.handy.gravity.status", status(generator)));
        addValue(root, () -> Component.translatable("gui.ssc_14.handy.gravity.charge",
                GravGenGUIpercentTextProcedure.execute(generator.getLevel(), source.pos().getX(), source.pos().getY(), source.pos().getZ())));
        addValue(root, () -> Component.translatable("gui.ssc_14.handy.gravity.eta",
                GravGenGUIETAtextProcedure.execute(generator.getLevel(), source.pos().getX(), source.pos().getY(), source.pos().getZ())));
        addButton(root, "gui.ssc_14.handy.gravity.enable", () -> setEnabled(generator, source, player, true));
        addButton(root, "gui.ssc_14.handy.gravity.disable", () -> setEnabled(generator, source, player, false));
        addButton(root, "gui.ssc_14.handy.gravity.close", () -> {
            if (player instanceof ServerPlayer serverPlayer) ServerUIManager.closeWindow(serverPlayer, Source.blockEntity(source));
        });
        return ModularUI.of(UI.of(root), player);
    }

    private static void addValue(UIElement root, Supplier<Component> value) {
        var label = new Label();
        label.bind(DataBindingBuilder.componentS2C(value).build());
        root.addChild(label);
    }

    private static void addButton(UIElement root, String translation, Runnable action) {
        var button = new Button().setText(Component.translatable(translation));
        button.layout(layout -> layout.height(BUTTON_HEIGHT).widthPercent(100));
        button.setOnServerClick(event -> action.run());
        root.addChild(button);
    }

    private static Component status(GravityGeneratorBlockEntity generator) {
        double charge = generator.getPersistentData().getDoubleOr("b_energy", 0);
        boolean enabled = generator.getPersistentData().getBooleanOr("active", false);
        String key = enabled ? (charge >= FULL_CHARGE ? "charged" : "charging") : (charge > 0 ? "discharging" : "off");
        return Component.translatable("gui.ssc_14.handy.gravity." + key);
    }

    private static void setEnabled(GravityGeneratorBlockEntity generator, GlobalPos source, Player player, boolean enabled) {
        if (!(player instanceof ServerPlayer) || !canAccess(generator, player, source)) {
            Ssc14Mod.LOGGER.debug("Rejected gravity window action at {}", source);
            return;
        }
        generator.getPersistentData().putBoolean("active", enabled);
        generator.setChanged();
        var level = player.level();
        var state = generator.getBlockState();
        level.sendBlockUpdated(source.pos(), state, state, 3);
    }
}
