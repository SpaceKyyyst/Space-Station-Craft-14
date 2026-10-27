package net.ssc14.content.item;

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
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.tool.NetworkConfiguratorItem;
import net.ssc14.content.item.tool.NetworkConfiguratorService;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14NetworkConfiguratorItemGameTests {
    private static final Identifier TEST_ID =
            id("compatibility_item_network_configurator_contract");

    private Ssc14NetworkConfiguratorItemGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14NetworkConfiguratorItemGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_network_configurator_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var item = Ssc14Items.NETWORK_CONFIGURATOR.get();
        helper.assertTrue(id("network_configurator")
                        .equals(BuiltInRegistries.ITEM.getKey(item)),
                "network configurator ID changed");
        helper.assertTrue(item instanceof NetworkConfiguratorItem,
                "network configurator no longer uses canonical implementation");
        var stack = item.getDefaultInstance();
        helper.assertTrue(stack.getMaxStackSize() == 1,
                "network configurator stack limit changed");
        helper.assertTrue(stack.has(DataComponents.DAMAGE_RESISTANT),
                "network configurator fire resistance changed");

        BlockPos pos = helper.absolutePos(new BlockPos(2, 2, 2));
        helper.getLevel().setBlock(pos, Ssc14Blocks.BUTTON.get().defaultBlockState(), 3);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        NetworkConfiguratorService.use(helper.getLevel(), pos, player, stack);
        helper.assertTrue(NetworkConfiguratorService.hasFirstTarget(stack),
                "first network target is no longer stored");
        NetworkConfiguratorService.reset(stack);
        helper.assertTrue(!NetworkConfiguratorService.hasFirstTarget(stack),
                "dropping/closing no longer clears network targets");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
