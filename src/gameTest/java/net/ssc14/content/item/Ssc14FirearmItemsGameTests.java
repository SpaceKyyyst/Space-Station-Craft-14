package net.ssc14.content.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.weapon.FirearmState;
import net.ssc14.content.item.weapon.Mk58Item;
import net.ssc14.content.item.weapon.Mk58State;
import net.ssc14.content.item.weapon.PistolMagazineItem;
import net.ssc14.content.item.weapon.PistolMagazineState;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14FirearmItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_firearm_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.MK_58, "mk_58", Mk58State.CLOSED_WITH_MAGAZINE),
            item(Ssc14Items.MK_58OPEN, "mk_58open", Mk58State.OPEN_WITH_MAGAZINE),
            item(Ssc14Items.MK_58NOMAGZ, "mk_58nomagz", Mk58State.CLOSED_NO_MAGAZINE),
            item(Ssc14Items.MK_58OPEN_NO_MAGZ, "mk_58open_no_magz",
                    Mk58State.OPEN_NO_MAGAZINE),
            item(Ssc14Items.MAGAZINE_PISTOL_35, "magazine_pistol_35",
                    PistolMagazineState.LOADED),
            item(Ssc14Items.MAGAZINE_PISTOL_35EMPTY, "magazine_pistol_35empty",
                    PistolMagazineState.EMPTY));

    private Ssc14FirearmItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14FirearmItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_firearm_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyDefinitions(helper);
        verifyInitialState(helper);
        verifyMk58StateCycle(helper);
        verifyMagazineReload(helper);
        verifyFiring(helper);
        helper.succeed();
    }

    private static void verifyDefinitions(GameTestHelper helper) {
        for (ExpectedItem expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 1,
                    expected.id() + " stack size changed");
            helper.assertTrue(item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                    expected.id() + " fire resistance changed");
            if (expected.state() instanceof Mk58State mk58State) {
                helper.assertTrue(item instanceof Mk58Item,
                        expected.id() + " canonical MK-58 type changed");
                Mk58Item mk58 = (Mk58Item) item;
                helper.assertTrue(mk58.state() == mk58State,
                        expected.id() + " MK-58 state changed");
                ItemStack stack = item.getDefaultInstance();
                helper.assertTrue(mk58.getUseAnimation(stack) == ItemUseAnimation.CROSSBOW,
                        expected.id() + " use animation changed");
                helper.assertTrue(mk58.getUseDuration(stack, null) == -10,
                        expected.id() + " use duration changed");
                helper.assertTrue(mk58.getDestroySpeed(
                                stack, Blocks.STONE.defaultBlockState()) == 0.0F,
                        expected.id() + " destroy speed changed");
            } else {
                helper.assertTrue(item instanceof PistolMagazineItem,
                        expected.id() + " canonical magazine type changed");
                helper.assertTrue(((PistolMagazineItem) item).state() == expected.state(),
                        expected.id() + " magazine state changed");
            }
        }
    }

    private static void verifyInitialState(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack mk58 = Ssc14Items.MK_58.get().getDefaultInstance();
        Ssc14Items.MK_58.get().inventoryTick(
                mk58, helper.getLevel(), player, EquipmentSlot.MAINHAND);
        assertTag(helper, mk58, "GenEraTE", true, "MK-58 generation marker");
        assertTag(helper, mk58, "MagazBullets", 10.0D, "MK-58 magazine count");
        assertTag(helper, mk58, "BulletChamber", 1.0D, "MK-58 chamber count");

        ItemStack magazine = Ssc14Items.MAGAZINE_PISTOL_35.get().getDefaultInstance();
        Ssc14Items.MAGAZINE_PISTOL_35.get().inventoryTick(
                magazine, helper.getLevel(), player, EquipmentSlot.MAINHAND);
        assertTag(helper, magazine, "GenEraTE", true, "magazine generation marker");
        assertTag(helper, magazine, "Bullets", 10.0D, "magazine bullet count");

        ItemStack empty = Ssc14Items.MAGAZINE_PISTOL_35EMPTY.get().getDefaultInstance();
        Ssc14Items.MAGAZINE_PISTOL_35EMPTY.get().inventoryTick(
                empty, helper.getLevel(), player, EquipmentSlot.MAINHAND);
        assertTag(helper, empty, "GenEraTE", false, "empty magazine generation marker");
        assertTag(helper, empty, "Bullets", 0.0D, "empty magazine bullet count");
    }

    private static void verifyMk58StateCycle(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(helper.absolutePos(BlockPos.ZERO).getCenter());
        player.setShiftKeyDown(false);
        ItemStack initial = Ssc14Items.MK_58.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.MAIN_HAND, initial);
        Ssc14Items.MK_58.get().inventoryTick(
                initial, helper.getLevel(), player, EquipmentSlot.MAINHAND);

        Ssc14Items.MK_58.get().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        ItemStack open = player.getMainHandItem();
        helper.assertTrue(open.is(Ssc14Items.MK_58OPEN.get()),
                "opening the MK-58 breech no longer selects the open state");
        assertTag(helper, open, "MagazBullets", 10.0D, "open MK-58 magazine count");

        Ssc14Items.MK_58OPEN.get().use(
                helper.getLevel(), player, InteractionHand.MAIN_HAND);
        ItemStack closed = player.getMainHandItem();
        helper.assertTrue(closed.is(Ssc14Items.MK_58.get()),
                "closing the MK-58 breech no longer selects the closed state");
        assertTag(helper, closed, "MagazBullets", 9.0D,
                "closed MK-58 magazine count after chambering");
        assertTag(helper, closed, "BulletChamber", 1.0D,
                "closed MK-58 chamber count after chambering");

        player.setShiftKeyDown(true);
        player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        Ssc14Items.MK_58.get().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        ItemStack withoutMagazine = player.getMainHandItem();
        ItemStack removedMagazine = player.getOffhandItem();
        helper.assertTrue(withoutMagazine.is(Ssc14Items.MK_58NOMAGZ.get()),
                "removing the magazine no longer selects the no-magazine state");
        helper.assertTrue(removedMagazine.is(Ssc14Items.MAGAZINE_PISTOL_35.get()),
                "removing the magazine no longer returns the loaded magazine item");
        assertTag(helper, removedMagazine, "Bullets", 9.0D,
                "removed magazine bullet count");

        Ssc14Items.MK_58NOMAGZ.get().use(
                helper.getLevel(), player, InteractionHand.MAIN_HAND);
        ItemStack reloaded = player.getMainHandItem();
        helper.assertTrue(reloaded.is(Ssc14Items.MK_58.get()),
                "inserting the magazine no longer restores the closed MK-58 state");
        helper.assertTrue(player.getOffhandItem().isEmpty(),
                "inserting the magazine no longer consumes the offhand magazine");
        assertTag(helper, reloaded, "MagazBullets", 9.0D,
                "reinserted MK-58 magazine count");
    }

    private static void verifyMagazineReload(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack emptyMagazine =
                Ssc14Items.MAGAZINE_PISTOL_35EMPTY.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.MAIN_HAND, emptyMagazine);
        player.setItemInHand(InteractionHand.OFF_HAND,
                Ssc14Items.BULLET_35ITEM.get().getDefaultInstance());
        Ssc14Items.MAGAZINE_PISTOL_35EMPTY.get().use(
                helper.getLevel(), player, InteractionHand.MAIN_HAND);
        ItemStack loadedMagazine = player.getMainHandItem();
        helper.assertTrue(loadedMagazine.is(Ssc14Items.MAGAZINE_PISTOL_35.get()),
                "loading an empty magazine no longer selects the loaded state");
        helper.assertTrue(player.getOffhandItem().isEmpty(),
                "loading a magazine no longer consumes one offhand bullet");
        assertTag(helper, loadedMagazine, "Bullets", 1.0D,
                "reloaded magazine bullet count");
    }

    private static void verifyFiring(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(helper.absolutePos(new BlockPos(1, 0, 1)).getCenter());
        ItemStack mk58 = Ssc14Items.MK_58.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.MAIN_HAND, mk58);
        Ssc14Items.MK_58.get().inventoryTick(
                mk58, helper.getLevel(), player, EquipmentSlot.MAINHAND);
        Ssc14Items.MK_58.get().onEntitySwing(mk58, player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getMainHandItem().is(Ssc14Items.MK_58.get()),
                "firing a loaded MK-58 changed its item state unexpectedly");
        assertTag(helper, player.getMainHandItem(), "MagazBullets", 9.0D,
                "MK-58 magazine count after firing");
        assertTag(helper, player.getMainHandItem(), "BulletChamber", 1.0D,
                "MK-58 chamber count after firing");
    }

    private static void assertTag(
            GameTestHelper helper,
            ItemStack stack,
            String name,
            double expected,
            String description) {
        double actual = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getDoubleOr(name, 0.0D);
        helper.assertTrue(actual == expected,
                description + " changed: expected " + expected + ", got " + actual);
    }

    private static void assertTag(
            GameTestHelper helper,
            ItemStack stack,
            String name,
            boolean expected,
            String description) {
        boolean actual = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getBooleanOr(name, false);
        helper.assertTrue(actual == expected,
                description + " changed: expected " + expected + ", got " + actual);
    }

    private static ExpectedItem item(
            DeferredItem<? extends Item> holder, String id, FirearmState state) {
        return new ExpectedItem(holder, id, state);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(
            DeferredItem<? extends Item> holder,
            String id,
            FirearmState state) { }
}
