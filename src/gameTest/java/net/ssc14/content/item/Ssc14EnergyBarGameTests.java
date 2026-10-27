package net.ssc14.content.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.item.food.PackagedEnergyBarItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14EnergyBarGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_energy_bar_contract");

    private Ssc14EnergyBarGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14EnergyBarGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_energy_bar_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 160, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var item = Ssc14Items.ENERGY_BAR.get();
        helper.assertTrue(id("energy_bar").equals(BuiltInRegistries.ITEM.getKey(item)),
                "energy_bar item ID changed");
        helper.assertTrue(item instanceof PackagedEnergyBarItem,
                "energy_bar no longer uses PackagedEnergyBarItem");
        helper.assertTrue(item.getDefaultMaxStackSize() == PackagedEnergyBarItem.MAX_STACK_SIZE,
                "energy_bar stack size changed");

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        BlockPos dropPos = helper.absolutePos(new BlockPos(2, 2, 2));
        player.setPos(dropPos.getX(), dropPos.getY(), dropPos.getZ());
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(item));

        item.use(helper.getLevel(), player, InteractionHand.MAIN_HAND);

        helper.succeedWhen(() -> {
            helper.assertTrue(player.getMainHandItem().is(Ssc14Items.ENERGY_BAR_2.get()),
                    "energy_bar did not unwrap into energy_bar_2");
            helper.assertTrue(player.getMainHandItem().getCount() == 1,
                    "unwrapped energy bar count changed");
            boolean hasTrash = helper.getLevel()
                    .getEntitiesOfClass(ItemEntity.class, new AABB(dropPos).inflate(1.0))
                    .stream().anyMatch(entity -> entity.getItem().is(Ssc14Items.ENERGY_BAR_TRASH.get())
                            && entity.hasPickUpDelay());
            helper.assertTrue(hasTrash,
                    "energy_bar did not create compatible packaging waste");
        });
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
