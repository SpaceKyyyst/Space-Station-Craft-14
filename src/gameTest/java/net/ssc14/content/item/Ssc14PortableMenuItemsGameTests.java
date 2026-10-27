package net.ssc14.content.item;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.List;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Consumer;
import net.mcreator.ssc.world.inventory.AccessConfigGUIMenu;
import net.mcreator.ssc.world.inventory.BackpackGUIMenu;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
                    PortableMenuItemKind.BACKPACK, false, 21));

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
        helper.assertTrue(player.containerMenu instanceof AccessConfigGUIMenu,
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
