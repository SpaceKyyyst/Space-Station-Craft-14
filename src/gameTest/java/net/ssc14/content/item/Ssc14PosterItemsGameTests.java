package net.ssc14.content.item;

import java.util.List;
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
import net.ssc14.content.entity.PosterEntity;
import net.ssc14.content.item.poster.PosterItem;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14PosterItemsGameTests {
    private static final Identifier TEST_ID = id("compatibility_item_poster_contract");
    private static final List<DeferredItem<? extends Item>> ITEMS = List.of(
            Ssc14Items.POSTER_RANDOM_ANYTHING, Ssc14Items.POSTER_ANOMALY,
            Ssc14Items.POSTER_ARMORY, Ssc14Items.POSTER_ATMOS, Ssc14Items.POSTER_BAR,
            Ssc14Items.POSTER_BRIDGE, Ssc14Items.POSTER_CARGO, Ssc14Items.POSTER_CHAPEL,
            Ssc14Items.POSTER_CHEM, Ssc14Items.POSTER_COMMANDER, Ssc14Items.POSTER_DEATHSPOSAL,
            Ssc14Items.POSTER_DETECTIVE, Ssc14Items.POSTER_DRAMA, Ssc14Items.POSTER_ENG,
            Ssc14Items.POSTER_EVA, Ssc14Items.POSTER_GRAVI, Ssc14Items.POSTER_HYDRO,
            Ssc14Items.POSTER_JANITOR, Ssc14Items.POSTER_KITCHEN, Ssc14Items.POSTER_LAW,
            Ssc14Items.POSTER_MAIL, Ssc14Items.POSTER_MEDBAY, Ssc14Items.POSTER_MEDIUM_SECURE,
            Ssc14Items.POSTER_PODS, Ssc14Items.POSTER_PRISON, Ssc14Items.POSTER_RND,
            Ssc14Items.POSTER_SALVAGE, Ssc14Items.POSTER_SCI, Ssc14Items.POSTER_SECURITY,
            Ssc14Items.POSTER_SPACE, Ssc14Items.POSTER_SURGERY, Ssc14Items.POSTER_TELECOMS,
            Ssc14Items.POSTER_VAULT, Ssc14Items.POSTER_VIROLOGY, Ssc14Items.POSTER_XENOARCH);
    private static final List<String> IDS = List.of(
            "poster_random_anything", "poster_anomaly", "poster_armory", "poster_atmos", "poster_bar",
            "poster_bridge", "poster_cargo", "poster_chapel", "poster_chem", "poster_commander",
            "poster_deathsposal", "poster_detective", "poster_drama", "poster_eng", "poster_eva",
            "poster_gravi", "poster_hydro", "poster_janitor", "poster_kitchen", "poster_law",
            "poster_mail", "poster_medbay", "poster_medium_secure", "poster_pods", "poster_prison",
            "poster_rnd", "poster_salvage", "poster_sci", "poster_security", "poster_space",
            "poster_surgery", "poster_telecoms", "poster_vault", "poster_virology", "poster_xenoarch");

    private Ssc14PosterItemsGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14PosterItemsGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("item_poster_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 80, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        for (int index = 0; index < ITEMS.size(); index++) {
            Item item = ITEMS.get(index).get();
            String expectedId = IDS.get(index);
            helper.assertTrue(id(expectedId).equals(BuiltInRegistries.ITEM.getKey(item)),
                    expectedId + " item ID changed");
            helper.assertTrue(item instanceof PosterItem poster && poster.posterType().equals(expectedId),
                    expectedId + " poster type changed");
            helper.assertTrue(item.getDefaultMaxStackSize() == 1,
                    expectedId + " stack size changed");
        }

        BlockPos wallRelative = new BlockPos(2, 2, 2);
        BlockPos wallAbsolute = helper.absolutePos(wallRelative);
        helper.setBlock(wallRelative, Blocks.STONE);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack stack = new ItemStack(Ssc14Items.POSTER_ANOMALY.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        Vec3 click = Vec3.atCenterOf(wallAbsolute).add(0.0D, 0.0D, -0.5D);
        var context = new UseOnContext(player, InteractionHand.MAIN_HAND,
                new BlockHitResult(click, Direction.NORTH, wallAbsolute, false));
        helper.assertTrue(Ssc14Items.POSTER_ANOMALY.get().useOn(context).consumesAction(),
                "poster placement no longer consumes the interaction");

        List<PosterEntity> posters = helper.getLevel().getEntitiesOfClass(
                PosterEntity.class, new AABB(wallAbsolute).inflate(2.0D));
        helper.assertTrue(posters.size() == 1, "poster placement did not create exactly one entity");
        PosterEntity poster = posters.getFirst();
        helper.assertTrue(poster.getPosterType().equals("poster_anomaly"),
                "placed poster type changed");
        helper.assertTrue(poster.getFacingDirection() == Direction.NORTH,
                "placed poster facing changed");
        helper.assertTrue(stack.isEmpty(), "survival poster placement no longer consumes one item");
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
