package net.ssc14.content.item;

import java.util.List;
import java.util.function.Supplier;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.entity.Ssc14EntityTypes;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14SpawnEggItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_spawn_egg_contract");
    private static final List<EggContract> CONTRACTS = List.of(
            new EggContract("wall_carcase_entit_spawn_egg", Ssc14Items.WALL_CARCASE_ENTIT_SPAWN_EGG,
                    Ssc14EntityTypes.WALL_CARCASE_ENTIT),
            new EggContract("id_console_entity_spawn_egg", Ssc14Items.ID_CONSOLE_ENTITY_SPAWN_EGG,
                    Ssc14EntityTypes.ID_CONSOLE_ENTITY),
            new EggContract("plassteel_wall_carcase_entit_spawn_egg",
                    Ssc14Items.PLASSTEEL_WALL_CARCASE_ENTIT_SPAWN_EGG,
                    Ssc14EntityTypes.PLASSTEEL_WALL_CARCASE_ENTIT),
            new EggContract("mouse_brown_spawn_egg", Ssc14Items.MOUSE_BROWN_SPAWN_EGG,
                    Ssc14EntityTypes.MOUSE_BROWN),
            new EggContract("mouse_gray_spawn_egg", Ssc14Items.MOUSE_GRAY_SPAWN_EGG,
                    Ssc14EntityTypes.MOUSE_GRAY),
            new EggContract("mouse_white_spawn_egg", Ssc14Items.MOUSE_WHITE_SPAWN_EGG,
                    Ssc14EntityTypes.MOUSE_WHITE),
            new EggContract("carp_spawn_egg", Ssc14Items.CARP_SPAWN_EGG,
                    Ssc14EntityTypes.CARP));

    private Ssc14SpawnEggItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14SpawnEggItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_spawn_egg_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 120, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int index = 0; index < CONTRACTS.size(); index++) {
            EggContract contract = CONTRACTS.get(index);
            Item item = contract.item().get();
            helper.assertTrue(id(contract.itemId()).equals(BuiltInRegistries.ITEM.getKey(item)),
                    contract.itemId() + " item ID changed");
            helper.assertTrue(item instanceof SpawnEggItem,
                    contract.itemId() + " is no longer a spawn egg");

            BlockPos floorRelative = new BlockPos(index + 1, 1, 1);
            BlockPos floorAbsolute = helper.absolutePos(floorRelative);
            helper.setBlock(floorRelative, Blocks.STONE);
            ItemStack stack = new ItemStack(item);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            var hit = new BlockHitResult(Vec3.atCenterOf(floorAbsolute).add(0.0D, 0.5D, 0.0D),
                    Direction.UP, floorAbsolute, false);

            helper.assertTrue(item.useOn(new UseOnContext(player, InteractionHand.MAIN_HAND, hit)).consumesAction(),
                    contract.itemId() + " no longer consumes the spawn interaction");
            AABB spawnArea = new AABB(floorAbsolute.above()).inflate(0.75D);
            long spawned = helper.getLevel().getEntitiesOfClass(Entity.class, spawnArea,
                    entity -> entity.getType() == contract.entityType().get()).size();
            helper.assertTrue(spawned == 1L,
                    contract.itemId() + " did not spawn exactly one expected entity");
            helper.assertTrue(stack.isEmpty(),
                    contract.itemId() + " survival use did not consume the egg");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record EggContract(
            String itemId,
            DeferredItem<? extends Item> item,
            Supplier<? extends EntityType<?>> entityType) { }
}
