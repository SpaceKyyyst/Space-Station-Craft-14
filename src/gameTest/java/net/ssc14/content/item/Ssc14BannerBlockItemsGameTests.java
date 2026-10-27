package net.ssc14.content.item;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BannerBlockItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_banner_blocks");
    private static final List<Expected> EXPECTED = List.of(
            expected("banner_nt", Ssc14Blocks.BANNER_NT, Ssc14Items.BANNER_NT),
            expected("banner_med", Ssc14Blocks.BANNER_MED, Ssc14Items.BANNER_MED),
            expected("banner_rn_d", Ssc14Blocks.BANNER_RN_D, Ssc14Items.BANNER_RN_D),
            expected("banner_sec", Ssc14Blocks.BANNER_SEC, Ssc14Items.BANNER_SEC),
            expected("banner_cargo", Ssc14Blocks.BANNER_CARGO, Ssc14Items.BANNER_CARGO),
            expected("banner_eng", Ssc14Blocks.BANNER_ENG, Ssc14Items.BANNER_ENG),
            expected("banner_synd", Ssc14Blocks.BANNER_SYND, Ssc14Items.BANNER_SYND));

    private Ssc14BannerBlockItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BannerBlockItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_banner_blocks_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 40, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (Expected expected : EXPECTED) {
            var item = expected.item().get();
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.path() + " item ID changed");
            helper.assertTrue(item instanceof BlockItem blockItem
                            && blockItem.getBlock() == expected.block().get(),
                    expected.path() + " no longer places its canonical block");
            var stack = item.getDefaultInstance();
            helper.assertTrue(stack.getMaxStackSize() == 1,
                    expected.path() + " stack limit changed");
            helper.assertTrue(!stack.has(DataComponents.DAMAGE_RESISTANT),
                    expected.path() + " unexpectedly became fire resistant");
        }
        helper.succeed();
    }

    private static Expected expected(String path, DeferredBlock<?> block,
            DeferredItem<?> item) {
        return new Expected(path, block, item);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record Expected(String path, DeferredBlock<?> block, DeferredItem<?> item) { }
}
