package net.ssc14.client.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/** Owns typed client screen bindings while screen implementations migrate independently. */
public final class Ssc14ScreenBinder {
    private static final List<Consumer<RegisterMenuScreensEvent>> BINDINGS =
            new ArrayList<>();

    private Ssc14ScreenBinder() { }

    public static <M extends AbstractContainerMenu,
            S extends Screen & MenuAccess<M>> void bind(
                    Supplier<? extends MenuType<? extends M>> menuType,
                    MenuScreens.ScreenConstructor<M, S> constructor) {
        BINDINGS.add(event -> event.register(menuType.get(), constructor));
    }

    public static void registerScreens(RegisterMenuScreensEvent event) {
        BINDINGS.forEach(binding -> binding.accept(event));
    }

    public static int bindingCount() {
        return BINDINGS.size();
    }
}
