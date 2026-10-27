package net.ssc14.content.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.weapon.FoamDartLauncherItem;
import net.ssc14.content.item.weapon.FoamDartLauncherService;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14AstroAceFoamPowerItemGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_item_astro_ace_foam_power_contract");

    private Ssc14AstroAceFoamPowerItemGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14AstroAceFoamPowerItemGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_astro_ace_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var item = Ssc14Items.ASTRO_ACE_FOAM_POWER.get();
        helper.assertTrue(id("astro_ace_foam_power")
                        .equals(BuiltInRegistries.ITEM.getKey(item)),
                "Astro-Ace Foam Power item ID changed");
        helper.assertTrue(item instanceof FoamDartLauncherItem,
                "Astro-Ace no longer uses canonical FoamDartLauncherItem");
        helper.assertTrue(item.getDefaultMaxStackSize() == 1,
                "Astro-Ace stack limit changed");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack launcher = item.getDefaultInstance();
        player.setItemInHand(InteractionHand.MAIN_HAND, launcher);
        player.setItemInHand(InteractionHand.OFF_HAND,
                Ssc14Items.FOAM_DART.get().getDefaultInstance());

        FoamDartLauncherService.toggleDart(helper.getLevel(), player, launcher);
        helper.assertTrue(FoamDartLauncherService.dartCount(launcher) == 1,
                "loading a foam dart no longer increments stored ammunition");
        helper.assertTrue(player.getOffhandItem().isEmpty(),
                "loading a foam dart no longer consumes the offhand dart");

        FoamDartLauncherService.toggleDart(helper.getLevel(), player, launcher);
        helper.assertTrue(FoamDartLauncherService.dartCount(launcher) == 0,
                "unloading a foam dart no longer decrements stored ammunition");
        helper.assertTrue(player.getOffhandItem().is(Ssc14Items.FOAM_DART.get()),
                "unloading into an empty offhand no longer returns the dart");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
