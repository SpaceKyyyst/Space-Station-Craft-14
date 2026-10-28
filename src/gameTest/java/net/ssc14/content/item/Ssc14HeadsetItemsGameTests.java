package net.ssc14.content.item;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.communication.HeadsetChannel;
import net.ssc14.content.item.communication.HeadsetInitializationService;
import net.ssc14.content.item.communication.HeadsetItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14HeadsetItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_headset_contract");
    private static final Set<HeadsetChannel> PASSANGER = channels();
    private static final Set<HeadsetChannel> ALL = channels(
            HeadsetChannel.COMMAND, HeadsetChannel.ENGINEERING, HeadsetChannel.MEDICAL,
            HeadsetChannel.RESEARCH, HeadsetChannel.SECURITY, HeadsetChannel.SERVICE,
            HeadsetChannel.CARGO);
    private static final List<ExpectedHeadset> HEADSETS = List.of(
            headset(Ssc14Items.HEADSET_BASE, "headset_base", PASSANGER),
            headset(Ssc14Items.HEADSET_BRIGMEDIC, "headset_brigmedic",
                    channels(HeadsetChannel.MEDICAL, HeadsetChannel.SECURITY)),
            headset(Ssc14Items.HEADSET_CARGO, "headset_cargo",
                    channels(HeadsetChannel.CARGO)),
            headset(Ssc14Items.HEADSET_CENTCOM, "headset_centcom", ALL),
            headset(Ssc14Items.HEADSET_COMMAND, "headset_command", ALL),
            headset(Ssc14Items.HEADSET_ENGINEERING, "headset_engineering",
                    channels(HeadsetChannel.ENGINEERING)),
            headset(Ssc14Items.HEADSET_MEDICAL, "headset_medical",
                    channels(HeadsetChannel.MEDICAL)),
            headset(Ssc14Items.HEADSET_MEDICAL_SCIENCE, "headset_medical_science",
                    channels(HeadsetChannel.MEDICAL, HeadsetChannel.RESEARCH)),
            headset(Ssc14Items.HEADSET_MINING, "headset_mining",
                    channels(HeadsetChannel.CARGO)),
            headset(Ssc14Items.HEADSET_ROBOTICS, "headset_robotics",
                    channels(HeadsetChannel.RESEARCH)),
            headset(Ssc14Items.HEADSET_SCIENCE, "headset_science",
                    channels(HeadsetChannel.RESEARCH)),
            headset(Ssc14Items.HEADSET_SECURITY, "headset_security",
                    channels(HeadsetChannel.SECURITY)),
            headset(Ssc14Items.HEADSET_SERVICE, "headset_service",
                    channels(HeadsetChannel.SERVICE)),
            headset(Ssc14Items.HEADSET_SYNDICATE, "headset_syndicate", PASSANGER));

    private Ssc14HeadsetItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14HeadsetItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_headset_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (ExpectedHeadset expected : HEADSETS) {
            verifyHeadset(helper, expected);
        }
        verifyOneShotInitialization(helper);
        verifyKeyInteraction(helper);
        helper.succeed();
    }

    private static void verifyHeadset(GameTestHelper helper, ExpectedHeadset expected) {
        Item item = expected.holder().get();
        helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                expected.id() + " item ID changed");
        helper.assertTrue(item instanceof HeadsetItem,
                expected.id() + " no longer uses HeadsetItem");
        helper.assertTrue(item.getDefaultMaxStackSize() == HeadsetItem.MAX_STACK_SIZE
                        && item.getDefaultInstance().has(DataComponents.DAMAGE_RESISTANT),
                expected.id() + " item properties changed");

        ItemStack headset = new ItemStack(item);
        HeadsetInitializationService.initialize(headset);
        helper.assertTrue(isGenerated(headset), expected.id() + " was not initialized");
        ResourceHandler<ItemResource> handler = handler(helper, headset, expected.id());
        for (HeadsetChannel channel : HeadsetChannel.values()) {
            ItemStack stored = ItemUtil.getStack(handler, channel.slot());
            if (expected.channels().contains(channel)) {
                helper.assertTrue(stored.is(channel.keyItem()),
                        expected.id() + " lost default " + channel + " key");
            } else {
                helper.assertTrue(stored.isEmpty(),
                        expected.id() + " gained unexpected " + channel + " key");
            }
        }
    }

    private static void verifyOneShotInitialization(GameTestHelper helper) {
        ItemStack headset = new ItemStack(Ssc14Items.HEADSET_BASE.get());
        HeadsetInitializationService.initialize(headset);
        ResourceHandler<ItemResource> handler = handler(helper, headset, "headset_base");
        clearSlot(handler, HeadsetChannel.PASSANGER.slot());
        HeadsetInitializationService.initialize(headset);
        helper.assertTrue(ItemUtil.getStack(handler, HeadsetChannel.PASSANGER.slot()).isEmpty(),
                "generated headset was initialized more than once");
    }

    private static void verifyKeyInteraction(GameTestHelper helper) {
        ItemStack headset = new ItemStack(Ssc14Items.HEADSET_BASE.get());
        HeadsetInitializationService.initialize(headset);
        ResourceHandler<ItemResource> handler = handler(helper, headset, "headset_base");
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, headset);

        player.setItemInHand(InteractionHand.OFF_HAND,
                new ItemStack(Ssc14Items.ENCRYPTION_KEY_SERVICE.get()));
        headset.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getOffhandItem().isEmpty(),
                "new encryption key was not consumed");
        helper.assertTrue(ItemUtil.getStack(handler, HeadsetChannel.SERVICE.slot())
                        .is(Ssc14Items.ENCRYPTION_KEY_SERVICE.get()),
                "new encryption key was not inserted");

        player.setItemInHand(InteractionHand.OFF_HAND,
                new ItemStack(Ssc14Items.ENCRYPTION_KEY_SERVICE.get()));
        headset.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getOffhandItem().getCount() == 1,
                "duplicate encryption key was consumed");

        player.setItemInHand(InteractionHand.OFF_HAND,
                new ItemStack(Ssc14ModItems.SCREWDRIVER.get()));
        headset.getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        for (int slot = 1; slot <= 15; slot++) {
            helper.assertTrue(ItemUtil.getStack(handler, slot).isEmpty(),
                    "screwdriver did not clear headset slot " + slot);
        }
    }

    private static ResourceHandler<ItemResource> handler(
            GameTestHelper helper, ItemStack headset, String id) {
        ResourceHandler<ItemResource> handler = headset.getCapability(
                Capabilities.Item.ITEM, ItemAccess.forStack(headset));
        helper.assertTrue(handler != null, id + " item capability is missing");
        return handler;
    }

    private static void clearSlot(ResourceHandler<ItemResource> handler, int slot) {
        try (var transaction = Transaction.openRoot()) {
            handler.extract(slot, handler.getResource(slot),
                    handler.getAmountAsInt(slot), transaction);
            transaction.commit();
        }
    }

    private static boolean isGenerated(ItemStack headset) {
        return headset.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getBooleanOr("generated", false);
    }

    private static Set<HeadsetChannel> channels(HeadsetChannel... channels) {
        EnumSet<HeadsetChannel> result = EnumSet.of(HeadsetChannel.PASSANGER);
        for (HeadsetChannel channel : channels) {
            result.add(channel);
        }
        return Set.copyOf(result);
    }

    private static ExpectedHeadset headset(
            DeferredItem<? extends Item> holder, String id, Set<HeadsetChannel> channels) {
        return new ExpectedHeadset(holder, id, channels);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedHeadset(
            DeferredItem<? extends Item> holder, String id, Set<HeadsetChannel> channels) { }
}
