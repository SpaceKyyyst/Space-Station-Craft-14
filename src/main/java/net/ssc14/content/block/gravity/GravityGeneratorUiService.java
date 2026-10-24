package net.ssc14.content.block.gravity;

import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.ssc14.handy.window.WindowProperties;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;

/** UI integration boundary kept outside the canonical gravity content model. */
public final class GravityGeneratorUiService {
    public interface Adapter {
        boolean canAccess(GravityGeneratorBlockEntity generator, Player player, GlobalPos source);

        ModularUI create(GravityGeneratorBlockEntity generator, GlobalPos source, Player player);

        WindowProperties windowProperties();

        AbstractContainerMenu createMenu(
                GravityGeneratorBlockEntity generator, int id, Inventory inventory);
    }

    private static Adapter adapter;

    private GravityGeneratorUiService() { }

    public static void install(Adapter replacement) {
        adapter = Objects.requireNonNull(replacement, "replacement");
    }

    public static boolean canAccess(
            GravityGeneratorBlockEntity generator, Player player, GlobalPos source) {
        return requireAdapter().canAccess(generator, player, source);
    }

    public static ModularUI create(
            GravityGeneratorBlockEntity generator, GlobalPos source, Player player) {
        return requireAdapter().create(generator, source, player);
    }

    public static WindowProperties windowProperties() {
        return requireAdapter().windowProperties();
    }

    public static AbstractContainerMenu createMenu(
            GravityGeneratorBlockEntity generator, int id, Inventory inventory) {
        return requireAdapter().createMenu(generator, id, inventory);
    }

    public static void open(Level level, BlockPos pos, Player player) {
        if (level.isClientSide()
                && level.getBlockEntity(pos) instanceof GravityGeneratorBlockEntity generator
                && generator.hasAccess(player, new GlobalPos(level.dimension(), pos))) {
            generator.openUI(generator, player);
        }
    }

    private static Adapter requireAdapter() {
        return Objects.requireNonNull(adapter, "Gravity generator UI adapter is not installed");
    }
}
