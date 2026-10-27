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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.Ssc14Blocks;
import net.ssc14.content.item.tool.AsteroidPickItem;
import net.ssc14.content.item.tool.MechanicalHandToolItem;
import net.ssc14.content.item.tool.MechanicalToolProfile;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14MechanicalToolItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_mechanical_tool_contract");
    private static final List<ExpectedTool> TOOLS = List.of(
            tool(Ssc14Items.CROWBAR, "crowbar", MechanicalToolProfile.CROWBAR,
                    -0.9D, -3.0D, 8.0F),
            tool(Ssc14Items.NIPPERS, "nippers", MechanicalToolProfile.NIPPERS,
                    -0.9D, -2.0D, 2.0F),
            tool(Ssc14Items.FIRE_AXE, "fire_axe", MechanicalToolProfile.FIRE_AXE,
                    -0.9D, -3.25D, 10.0F),
            tool(Ssc14Items.SCREWDRIVER, "screwdriver",
                    MechanicalToolProfile.SCREWDRIVER, 0.0D, -2.0D, 6.0F),
            tool(Ssc14Items.SPANNER, "spanner", MechanicalToolProfile.SPANNER,
                    -0.9D, -2.5D, 4.5F),
            tool(Ssc14Items.PICK, "pick", MechanicalToolProfile.PICK,
                    -0.9D, -3.3D, 5.0F));

    private Ssc14MechanicalToolItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14MechanicalToolItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_mechanical_tool_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 30, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var attacker = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int index = 0; index < TOOLS.size(); index++) {
            verifyDefinition(helper, TOOLS.get(index));
            verifyDamage(helper, attacker, TOOLS.get(index), index);
        }
        verifyMiningContracts(helper);
        helper.succeed();
    }

    private static void verifyDefinition(GameTestHelper helper, ExpectedTool expected) {
        Item item = expected.holder().get();
        helper.assertTrue(id(expected.id()).equals(BuiltInRegistries.ITEM.getKey(item)),
                expected.id() + " item ID changed");
        helper.assertTrue(item.getDefaultMaxStackSize() == 1,
                expected.id() + " stack size changed");
        ItemStack stack = item.getDefaultInstance();
        helper.assertTrue(stack.has(DataComponents.DAMAGE_RESISTANT),
                expected.id() + " fire resistance changed");
        helper.assertTrue(!stack.has(DataComponents.MAX_DAMAGE),
                expected.id() + " unexpectedly regained durability");

        if (expected.profile() == MechanicalToolProfile.PICK) {
            helper.assertTrue(item instanceof AsteroidPickItem,
                    expected.id() + " canonical pick type changed");
        } else {
            helper.assertTrue(item instanceof MechanicalHandToolItem,
                    expected.id() + " canonical hand-tool type changed");
            helper.assertTrue(((MechanicalHandToolItem) item).profile() == expected.profile(),
                    expected.id() + " tool profile changed");
        }

        ItemAttributeModifiers modifiers = stack.getOrDefault(
                DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        helper.assertTrue(modifiers.compute(
                        Attributes.ATTACK_DAMAGE, 0, EquipmentSlot.MAINHAND)
                        == expected.attackDamage(),
                expected.id() + " attack damage modifier changed");
        helper.assertTrue(modifiers.compute(
                        Attributes.ATTACK_SPEED, 0, EquipmentSlot.MAINHAND)
                        == expected.attackSpeed(),
                expected.id() + " attack speed modifier changed");
    }

    private static void verifyDamage(
            GameTestHelper helper,
            net.minecraft.world.entity.player.Player attacker,
            ExpectedTool expected,
            int index) {
        var target = helper.spawn(EntityType.COW, new BlockPos(index + 1, 2, 1));
        target.setHealth(target.getMaxHealth());
        expected.holder().get().hurtEnemy(
                expected.holder().get().getDefaultInstance(), target, attacker);
        float expectedHealth = target.getMaxHealth() - expected.observableDamage();
        helper.assertTrue(target.getHealth() == expectedHealth,
                expected.id() + " custom damage changed: expected "
                        + expectedHealth + ", got " + target.getHealth());
    }

    private static void verifyMiningContracts(GameTestHelper helper) {
        MechanicalHandToolItem crowbar =
                (MechanicalHandToolItem) Ssc14Items.CROWBAR.get();
        ItemStack crowbarStack = crowbar.getDefaultInstance();
        helper.assertTrue(crowbar.getDestroySpeed(
                        crowbarStack, Blocks.STONE.defaultBlockState()) == 4.0F,
                "mechanical hand-tool mining speed changed");
        helper.assertTrue(crowbar.isCorrectToolForDrops(
                        crowbarStack, Blocks.DIRT.defaultBlockState()),
                "mechanical hand tool stopped accepting low-tier blocks");
        helper.assertTrue(!crowbar.isCorrectToolForDrops(
                        crowbarStack, Blocks.DIAMOND_ORE.defaultBlockState()),
                "mechanical hand tool now accepts high-tier blocks");
        helper.assertTrue(crowbar.canPerformAction(
                        crowbarStack, ItemAbilities.AXE_STRIP),
                "mechanical hand-tool abilities changed");

        AsteroidPickItem pick = (AsteroidPickItem) Ssc14Items.PICK.get();
        ItemStack pickStack = pick.getDefaultInstance();
        for (Block asteroidStone : List.of(
                Ssc14Blocks.ASTEROID_STONE_BASE.get(),
                Ssc14Blocks.ASTEROID_STONE_IRON.get(),
                Ssc14Blocks.ASTEROID_STONE_QUARTZ.get(),
                Ssc14Blocks.ASTEROID_STONE_COAL.get(),
                Ssc14Blocks.ASTEROID_STONE_GOLD.get(),
                Ssc14Blocks.ASTEROID_STONE_PLASMA.get(),
                Ssc14Blocks.ASTEROID_STONE_SILVER.get(),
                Ssc14Blocks.ASTEROID_STONE_URAN.get(),
                Ssc14Blocks.ASTEROID_STONE_DIAMOND.get())) {
            helper.assertTrue(pick.getDestroySpeed(
                            pickStack, asteroidStone.defaultBlockState()) == 40.0F,
                    "asteroid pick speed changed for "
                            + BuiltInRegistries.BLOCK.getKey(asteroidStone));
        }
        helper.assertTrue(pick.getDestroySpeed(
                        pickStack, Blocks.STONE.defaultBlockState()) == 1.0F,
                "asteroid pick fallback speed changed");
        helper.assertTrue(!pick.canPerformAction(pickStack, ItemAbilities.AXE_STRIP),
                "asteroid pick unexpectedly gained hand-tool abilities");
    }

    private static ExpectedTool tool(
            DeferredItem<? extends Item> holder,
            String id,
            MechanicalToolProfile profile,
            double attackDamage,
            double attackSpeed,
            float observableDamage) {
        return new ExpectedTool(
                holder, id, profile, attackDamage, attackSpeed, observableDamage);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedTool(
            DeferredItem<? extends Item> holder,
            String id,
            MechanicalToolProfile profile,
            double attackDamage,
            double attackSpeed,
            float observableDamage) { }
}
