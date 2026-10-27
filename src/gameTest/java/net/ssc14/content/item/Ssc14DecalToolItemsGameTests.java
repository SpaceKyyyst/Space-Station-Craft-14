package net.ssc14.content.item;

import java.util.List;
import net.mcreator.ssc.DecalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.decal.DecalToolItem;
import net.ssc14.content.item.decal.DecalToolKind;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14DecalToolItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_decal_tool_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.DECAL_1SPAWNER, "decal_1spawner",
                    DecalToolKind.SPAWNER_1, "decal_1"),
            item(Ssc14Items.DECAL_1DELETION, "decal_1deletion",
                    DecalToolKind.DELETION, null),
            item(Ssc14Items.DECAL_2SPAWNER, "decal_2spawner",
                    DecalToolKind.SPAWNER_2, "decal_2"),
            item(Ssc14Items.DECAL_3SPAWNER, "decal_3spawner",
                    DecalToolKind.SPAWNER_3, "decal_3"),
            item(Ssc14Items.DECAL_4SPAWNER, "decal_4spawner",
                    DecalToolKind.SPAWNER_4, "decal_4"),
            item(Ssc14Items.DECAL_5SPAWNER, "decal_5spawner",
                    DecalToolKind.SPAWNER_5, "decal_5"),
            item(Ssc14Items.DECAL_6SPAWNER, "decal_6spawner",
                    DecalToolKind.SPAWNER_6, "decal_6"));

    private Ssc14DecalToolItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14DecalToolItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_decal_tool_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        BlockPos absolutePos = helper.absolutePos(new BlockPos(1, 2, 1));
        var hit = new BlockHitResult(
                Vec3.atCenterOf(absolutePos), Direction.UP, absolutePos, false);

        for (ExpectedItem expected : ITEMS) {
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof DecalToolItem,
                    expected.id() + " canonical item type changed");
            DecalToolItem tool = (DecalToolItem) item;
            helper.assertTrue(tool.kind() == expected.kind(),
                    expected.id() + " decal role changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == DecalToolItem.MAX_STACK_SIZE,
                    expected.id() + " stack size changed");

            ItemStack stack = new ItemStack(item);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            if (expected.decalId() == null) {
                helper.assertTrue(item.useOn(new UseOnContext(
                                player, InteractionHand.MAIN_HAND, hit)).consumesAction(),
                        expected.id() + " use-on interaction changed");
                continue;
            }

            helper.assertTrue(stack.getCapability(
                            Capabilities.Item.ITEM, ItemAccess.forStack(stack)) != null,
                    expected.id() + " item capability is missing");
            helper.assertTrue(item.useOn(new UseOnContext(
                            player, InteractionHand.MAIN_HAND, hit)).consumesAction(),
                    expected.id() + " placement no longer consumes the interaction");
            var decals = helper.getLevel().getChunkAt(absolutePos)
                    .getData(DecalRegistry.DECAL_ATTACHMENT).decals();
            helper.assertTrue(decals.stream().anyMatch(decal ->
                            decal.pos().equals(absolutePos)
                                    && decal.face() == Direction.UP
                                    && decal.decalId().equals(expected.decalId())),
                    expected.id() + " placed the wrong decal type");
        }
        helper.succeed();
    }

    private static ExpectedItem item(
            DeferredItem<? extends Item> holder,
            String id,
            DecalToolKind kind,
            String decalId) {
        return new ExpectedItem(holder, id, kind, decalId);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(
            DeferredItem<? extends Item> holder,
            String id,
            DecalToolKind kind,
            String decalId) { }
}
