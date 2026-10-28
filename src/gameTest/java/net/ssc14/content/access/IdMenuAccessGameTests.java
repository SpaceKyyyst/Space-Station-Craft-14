package net.ssc14.content.access;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.menu.access.IDcodeMenu;
import net.ssc14.content.item.Ssc14Items;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class IdMenuAccessGameTests {
    private static final Identifier TEST_ID = id("id_menu_access_state_contract");

    private IdMenuAccessGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, IdMenuAccessGameTests::verifyAccessStateTransport));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("id_menu_access_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyAccessStateTransport(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        IDcodeMenu menu = new IDcodeMenu(1, player.getInventory(), null);
        player.containerMenu = menu;

        ItemStack source = new ItemStack(Ssc14Items.ID_CARD_CAPITAN.get());
        CustomData.update(DataComponents.CUSTOM_DATA, source, tag -> {
            tag.putBoolean("Security", true);
            tag.putBoolean("Atmos", true);
        });
        ItemStack target = new ItemStack(Ssc14Items.ID_CARD_PASSANGER.get());
        menu.getSlots().get(0).set(source);
        menu.getSlots().get(1).set(target);

        IdJobAssignmentService.setAllAllowed(player, true);

        helper.assertTrue(menu.getMenuState(1, "Security", false),
                "Security access state was not transported");
        helper.assertTrue(menu.getMenuState(1, "Atmos", false),
                "Atmos access state was not transported");
        helper.assertTrue(!menu.getMenuState(1, "Medical", false),
                "Unlisted access state was transported");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
