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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.combat.GlassShardItem;
import net.ssc14.content.item.combat.GlassShardKind;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14GlassShardItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_glass_shard_contract");
    private static final List<ExpectedItem> ITEMS = List.of(
            item(Ssc14Items.GLASS_SHARD, "glass_shard", GlassShardKind.STANDARD, 2.5F),
            item(Ssc14Items.ARM_GLASS_SHARD, "arm_glass_shard",
                    GlassShardKind.ARMORED, 4.5F));

    private Ssc14GlassShardItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14GlassShardItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_glass_shard_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 20, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var attacker = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int index = 0; index < ITEMS.size(); index++) {
            ExpectedItem expected = ITEMS.get(index);
            Item item = expected.holder().get();
            helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expected.id() + " item ID changed");
            helper.assertTrue(item instanceof GlassShardItem,
                    expected.id() + " canonical item type changed");
            GlassShardItem shard = (GlassShardItem) item;
            helper.assertTrue(shard.kind() == expected.kind(),
                    expected.id() + " shard role changed");
            helper.assertTrue(shard.kind().bonusDamage() == expected.bonusDamage(),
                    expected.id() + " bonus damage changed");

            ItemStack stack = item.getDefaultInstance();
            helper.assertTrue(stack.getMaxStackSize() == 1,
                    expected.id() + " stack size changed");
            helper.assertTrue(stack.get(DataComponents.DAMAGE_RESISTANT) != null,
                    expected.id() + " fire resistance changed");
            ItemAttributeModifiers modifiers = stack.getOrDefault(
                    DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
            helper.assertTrue(modifiers.compute(
                            Attributes.ATTACK_DAMAGE, 0, EquipmentSlot.MAINHAND) == -0.9D,
                    expected.id() + " attack damage modifier changed");
            helper.assertTrue(modifiers.compute(
                            Attributes.ATTACK_SPEED, 0, EquipmentSlot.MAINHAND) == -3.0D,
                    expected.id() + " attack speed modifier changed");

            var target = helper.spawn(EntityType.COW, new BlockPos(index + 1, 2, 1));
            target.setHealth(target.getMaxHealth());
            shard.hurtEnemy(stack, target, attacker);
            float expectedHealth = target.getMaxHealth() - expected.bonusDamage();
            helper.assertTrue(target.getHealth() == expectedHealth,
                    expected.id() + " legacy slash damage changed: expected "
                            + expectedHealth + ", got " + target.getHealth());
        }
        helper.succeed();
    }

    private static ExpectedItem item(
            DeferredItem<? extends Item> holder,
            String id,
            GlassShardKind kind,
            float bonusDamage) {
        return new ExpectedItem(holder, id, kind, bonusDamage);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedItem(
            DeferredItem<? extends Item> holder,
            String id,
            GlassShardKind kind,
            float bonusDamage) { }
}
