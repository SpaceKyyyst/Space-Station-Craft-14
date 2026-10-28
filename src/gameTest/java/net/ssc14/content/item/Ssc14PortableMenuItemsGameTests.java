package net.ssc14.content.item;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.List;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Consumer;
import net.ssc14.content.menu.pda.PDApassangerGUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.ssc14.content.menu.access.AccessConfigItemMenu;
import net.ssc14.content.menu.food.EggCartonGUIMenu;
import net.ssc14.content.menu.storage.BackpackGUIMenu;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.container.PortableMenuItem;
import net.ssc14.content.item.container.PortableMenuItemKind;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14PortableMenuItemsGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_item_portable_menu_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.ACCESS_CONFIG, "access_config",
                    PortableMenuItemKind.ACCESS_CONFIG, true, 1),
            item(Ssc14Items.BACKPACK_2, "backpack_2",
                    PortableMenuItemKind.BACKPACK, false, 21),
            item(Ssc14Items.PD_APASSANGER, "pd_apassanger",
                    PortableMenuItemKind.PASSENGER_PDA, true, 2),
            item(Ssc14Items.EGG_CARTON, "egg_carton",
                    PortableMenuItemKind.EGG_CARTON, false, 11));

    private Ssc14PortableMenuItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14PortableMenuItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_portable_menu_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        verifyDefinitionsAndCapabilities(helper);
        verifyMenuBindings(helper);
        verifyInventoryInitialization(helper);
        helper.succeed();
    }

    private static void verifyDefinitionsAndCapabilities(GameTestHelper helper) {
        for (ExpectedItem expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof PortableMenuItem,
                    expected.id() + " canonical item type changed");
            PortableMenuItem portable = (PortableMenuItem) item;
            helper.assertTrue(portable.kind() == expected.kind(),
                    expected.id() + " portable menu role changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 1,
                    expected.id() + " stack size changed");
            boolean fireResistant = item.getDefaultInstance()
                    .has(DataComponents.DAMAGE_RESISTANT);
            helper.assertTrue(fireResistant == expected.fireResistant(),
                    expected.id() + " fire resistance changed");

            ItemStack stack = item.getDefaultInstance();
            ResourceHandler<ItemResource> capability = stack.getCapability(
                    Capabilities.Item.ITEM, ItemAccess.forStack(stack));
            helper.assertTrue(capability != null,
                    expected.id() + " item capability disappeared");
            helper.assertTrue(capability.size() == expected.inventorySlots(),
                    expected.id() + " inventory slot count changed");
            helper.assertTrue(!capability.isValid(0, ItemResource.of(stack)),
                    expected.id() + " can be nested inside itself");
        }
    }

    private static void verifyMenuBindings(GameTestHelper helper) {
        MenuOpeningFakePlayer player = new MenuOpeningFakePlayer(helper,
                new GameProfile(UUID.randomUUID(), "portable-menu-test"));
        player.setPos(helper.absolutePos(net.minecraft.core.BlockPos.ZERO).getCenter());

        ItemStack configurator = Ssc14Items.ACCESS_CONFIG.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.MAIN_HAND, configurator);
        Ssc14Items.ACCESS_CONFIG.get().use(
                helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.containerMenu instanceof AccessConfigItemMenu,
                "access configurator no longer opens its menu");
        player.assertPayload(helper, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.containerMenu.stillValid(player),
                "access configurator menu is not bound to the held item");
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        helper.assertTrue(!player.containerMenu.stillValid(player),
                "access configurator menu ignored replacement of the bound hand");
        player.closeContainer();

        ItemStack backpack = Ssc14Items.BACKPACK_2.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.OFF_HAND, backpack);
        Ssc14Items.BACKPACK_2.get().use(
                helper.getLevel(), player, InteractionHand.OFF_HAND);
        helper.assertTrue(player.containerMenu instanceof BackpackGUIMenu,
                "backpack no longer opens its menu");
        player.assertPayload(helper, InteractionHand.OFF_HAND);
        helper.assertTrue(player.containerMenu.stillValid(player),
                "backpack menu is not bound to the held item");
        player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        helper.assertTrue(!player.containerMenu.stillValid(player),
                "backpack menu ignored replacement of the bound hand");
        player.closeContainer();

        ItemStack offhandPda = Ssc14Items.PD_APASSANGER.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.OFF_HAND, offhandPda);
        Ssc14Items.PD_APASSANGER.get().use(
                helper.getLevel(), player, InteractionHand.OFF_HAND);
        helper.assertTrue(!(player.containerMenu instanceof PDApassangerGUIMenu),
                "passenger PDA unexpectedly opens from the off hand");
        player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);

        ItemStack pda = Ssc14Items.PD_APASSANGER.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.MAIN_HAND, pda);
        Ssc14Items.PD_APASSANGER.get().use(
                helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.containerMenu instanceof PDApassangerGUIMenu,
                "passenger PDA no longer opens its menu");
        player.assertPayload(helper, InteractionHand.MAIN_HAND);
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
		helper.assertTrue(!player.containerMenu.stillValid(player),
				"passenger PDA menu remains valid after removing the main-hand PDA");
        player.closeContainer();

        ItemStack eggCarton = Ssc14Items.EGG_CARTON.get().getDefaultInstance();
        player.setItemInHand(InteractionHand.OFF_HAND, eggCarton);
        Ssc14Items.EGG_CARTON.get().use(
                helper.getLevel(), player, InteractionHand.OFF_HAND);
        helper.assertTrue(player.containerMenu instanceof EggCartonGUIMenu,
                "egg carton no longer opens its menu");
        player.assertPayload(helper, InteractionHand.OFF_HAND);
        helper.assertTrue(player.containerMenu.stillValid(player),
                "egg carton menu is not bound to the held item");
        player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        helper.assertTrue(!player.containerMenu.stillValid(player),
                "egg carton menu ignored replacement of the bound hand");
        player.closeContainer();
    }

    private static void verifyInventoryInitialization(GameTestHelper helper) {
        MenuOpeningFakePlayer player = new MenuOpeningFakePlayer(helper,
                new GameProfile(UUID.randomUUID(), "portable-menu-inventory-test"));

        ItemStack pda = Ssc14Items.PD_APASSANGER.get().getDefaultInstance();
        Ssc14Items.PD_APASSANGER.get().inventoryTick(
                pda, helper.getLevel(), player, EquipmentSlot.MAINHAND);
        ResourceHandler<ItemResource> pdaInventory = pda.getCapability(
                Capabilities.Item.ITEM, ItemAccess.forStack(pda));
        helper.assertTrue(pdaInventory != null,
                "passenger PDA inventory capability disappeared during initialization");
        helper.assertTrue(pdaInventory.getResource(0).getItem()
                        == Ssc14Items.ID_CARD_PASSANGER.get(),
                "passenger PDA no longer initializes its ID card slot");
        helper.assertTrue(pdaInventory.getResource(1).getItem() == Ssc14Items.PEN.get(),
                "passenger PDA no longer initializes its pen slot");
        helper.assertTrue(pda.getOrDefault(DataComponents.CUSTOM_DATA,
                        net.minecraft.world.item.component.CustomData.EMPTY)
                        .copyTag().getBooleanOr("generate", false),
                "passenger PDA initialization marker disappeared");

        ItemStack eggCarton = Ssc14Items.EGG_CARTON.get().getDefaultInstance();
        Ssc14Items.EGG_CARTON.get().inventoryTick(
                eggCarton, helper.getLevel(), player, EquipmentSlot.MAINHAND);
        ResourceHandler<ItemResource> eggInventory = eggCarton.getCapability(
                Capabilities.Item.ITEM, ItemAccess.forStack(eggCarton));
        helper.assertTrue(eggInventory != null,
                "egg carton inventory capability disappeared during initialization");
        helper.assertTrue(eggInventory.getResource(0).isEmpty(),
                "egg carton slot zero initialization changed");
        for (int slot = 1; slot <= 10; slot++) {
            helper.assertTrue(eggInventory.getResource(slot).getItem() == Ssc14Items.EGG.get(),
                    "egg carton slot " + slot + " is no longer initialized with an egg");
        }
        helper.assertTrue(eggCarton.getOrDefault(DataComponents.CUSTOM_DATA,
                        net.minecraft.world.item.component.CustomData.EMPTY)
                        .copyTag().getBooleanOr("generated", false),
                "egg carton initialization marker disappeared");
    }

    private static final class MenuOpeningFakePlayer extends FakePlayer {
        private BlockPos payloadPosition;
        private byte payloadHand;
        private int payloadRemainder;

        private MenuOpeningFakePlayer(GameTestHelper helper, GameProfile profile) {
            super(helper.getLevel(), profile);
        }

        @Override
        public OptionalInt openMenu(
                MenuProvider provider, Consumer<RegistryFriendlyByteBuf> payloadWriter) {
            RegistryFriendlyByteBuf payload = new RegistryFriendlyByteBuf(
                    Unpooled.buffer(), registryAccess());
            try {
                payloadWriter.accept(payload);
                payloadPosition = payload.readBlockPos();
                payloadHand = payload.readByte();
                payloadRemainder = payload.readableBytes();
            } finally {
                payload.release();
            }
            int containerId = 1;
            var menu = provider.createMenu(containerId, getInventory(), this);
            if (menu == null) {
                return OptionalInt.empty();
            }
            containerMenu = menu;
            return OptionalInt.of(containerId);
        }

        private void assertPayload(GameTestHelper helper, InteractionHand hand) {
            helper.assertTrue(blockPosition().equals(payloadPosition),
                    "portable menu payload position changed");
            byte expectedHand = (byte) (hand == InteractionHand.MAIN_HAND ? 0 : 1);
            helper.assertTrue(payloadHand == expectedHand,
                    "portable menu payload hand changed");
            helper.assertTrue(payloadRemainder == 0,
                    "portable menu payload contains unexpected trailing data");
        }
    }

    private static ExpectedItem item(
            DeferredItem<? extends Item> holder,
            String id,
            PortableMenuItemKind kind,
            boolean fireResistant,
            int inventorySlots) {
        return new ExpectedItem(holder, id, kind, fireResistant, inventorySlots);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(
            DeferredItem<? extends Item> holder,
            String id,
            PortableMenuItemKind kind,
            boolean fireResistant,
            int inventorySlots) { }
}
