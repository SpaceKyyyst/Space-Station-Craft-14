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
public final class Ssc14AsteroidBlockItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_asteroid_blocks");
    private static final List<Expected> EXPECTED = List.of(
            expected("asteroid_stone_base", Ssc14Blocks.ASTEROID_STONE_BASE,
                    Ssc14Items.ASTEROID_STONE_BASE),
            expected("asteroid_stone_iron", Ssc14Blocks.ASTEROID_STONE_IRON,
                    Ssc14Items.ASTEROID_STONE_IRON),
            expected("asteroid_stone_quartz", Ssc14Blocks.ASTEROID_STONE_QUARTZ,
                    Ssc14Items.ASTEROID_STONE_QUARTZ),
            expected("asteroid_stone_coal", Ssc14Blocks.ASTEROID_STONE_COAL,
                    Ssc14Items.ASTEROID_STONE_COAL),
            expected("asteroid_stone_gold", Ssc14Blocks.ASTEROID_STONE_GOLD,
                    Ssc14Items.ASTEROID_STONE_GOLD),
            expected("asteroid_stone_plasma", Ssc14Blocks.ASTEROID_STONE_PLASMA,
                    Ssc14Items.ASTEROID_STONE_PLASMA),
            expected("asteroid_stone_silver", Ssc14Blocks.ASTEROID_STONE_SILVER,
                    Ssc14Items.ASTEROID_STONE_SILVER),
            expected("asteroid_stone_uran", Ssc14Blocks.ASTEROID_STONE_URAN,
                    Ssc14Items.ASTEROID_STONE_URAN),
            expected("asteroid_stone_diamond", Ssc14Blocks.ASTEROID_STONE_DIAMOND,
                    Ssc14Items.ASTEROID_STONE_DIAMOND));

    private Ssc14AsteroidBlockItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14AsteroidBlockItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_asteroid_blocks_environment"));
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
            helper.assertTrue(stack.has(DataComponents.DAMAGE_RESISTANT),
                    expected.path() + " fire resistance changed");
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
