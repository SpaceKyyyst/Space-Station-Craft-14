package net.ssc14.content.attribute;

import java.util.List;
import net.mcreator.ssc.init.Ssc14ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14AttributesGameTests {
    private static final String TEST_PATH = "compatibility_attribute_contract";
    private static final Identifier TEST_ID = id(TEST_PATH);
    private static final List<ExpectedAttribute> EXPECTED_ATTRIBUTES = List.of(
            expected(Ssc14Attributes.PROGRESS_BAR_ATRB, "progress_bar_atrb", 0.0, 0.0, 7.0),
            expected(Ssc14Attributes.XY_ZPLAYER, "xy_zplayer", 0.0, -30_000_000.0, 30_000_000.0),
            expected(Ssc14Attributes.ENT_PULL, "ent_pull", 0.0, 0.0, 1.0),
            expected(Ssc14Attributes.GAS_AN_GU_IATRIB, "gas_an_gu_iatrib", 0.0, 0.0, 1.0),
            expected(Ssc14Attributes.OPEN_WORLD_CHECK_MENU, "open_world_check_menu", 0.0, 0.0, 1.0),
            expected(Ssc14Attributes.NUTRIENTS, "nutrients", 125.0, 0.0, 200.0),
            expected(Ssc14Attributes.DIGESTIVE_PROCESSES, "digestive_processes", 0.0, 0.0, 10.0),
            expected(Ssc14Attributes.HEALTH_U_IATTRIBUTE, "health_u_iattribute", 0.0, 0.0, 6.0),
            expected(Ssc14Attributes.LIQUID, "liquid", 125.0, 0.0, 200.0),
            expected(Ssc14Attributes.FLUID_ABSORPTION, "fluid_absorption", 0.0, 0.0, 10.0),
            expected(Ssc14Attributes.RACE, "race", 0.0, 0.0, 2.0));

    private Ssc14AttributesGameTests() {
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION,
                registry -> registry.register(TEST_ID, Ssc14AttributesGameTests::verifyAttributeContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("attribute_compatibility_environment"));
        var data = new TestData<>(environment, id("audit_empty"), 20, 1, true);
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID), data));
    }

    private static void verifyAttributeContract(GameTestHelper helper) {
        for (ExpectedAttribute expected : EXPECTED_ATTRIBUTES) {
            Attribute attribute = expected.holder().get();
            helper.assertTrue(attribute instanceof RangedAttribute,
                    expected.path() + " must remain a RangedAttribute");
            RangedAttribute ranged = (RangedAttribute) attribute;
            helper.assertTrue(id(expected.path()).equals(BuiltInRegistries.ATTRIBUTE.getKey(attribute)),
                    expected.path() + " registry ID changed");
            helper.assertTrue(("attribute." + Ssc14Identity.MOD_ID + "." + expected.path())
                            .equals(attribute.getDescriptionId()),
                    expected.path() + " description key changed");
            assertDouble(helper, expected.defaultValue(), attribute.getDefaultValue(), expected.path() + " default");
            assertDouble(helper, expected.minimumValue(), ranged.getMinValue(), expected.path() + " minimum");
            assertDouble(helper, expected.maximumValue(), ranged.getMaxValue(), expected.path() + " maximum");
            helper.assertTrue(attribute.isClientSyncable(), expected.path() + " must remain client-syncable");
            helper.assertTrue(attribute.getStyle(false) == Attribute.Sentiment.NEUTRAL.getStyle(false)
                            && attribute.getStyle(true) == Attribute.Sentiment.NEUTRAL.getStyle(true),
                    expected.path() + " sentiment changed");
        }

        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (ExpectedAttribute expected : EXPECTED_ATTRIBUTES) {
            helper.assertTrue(player.getAttributes().hasAttribute(expected.holder()),
                    "Player is missing " + expected.path());
        }

        var wallCarcase = helper.spawn(Ssc14ModEntities.WALL_CARCASE_ENTIT.get(), new BlockPos(1, 2, 1));
        var idConsole = helper.spawn(Ssc14ModEntities.ID_CONSOLE_ENTITY.get(), new BlockPos(2, 2, 1));
        assertLegacyEntityBindings(helper, wallCarcase, "wall_carcase_entit");
        assertLegacyEntityBindings(helper, idConsole, "id_console_entity");
        helper.succeed();
    }

    private static void assertLegacyEntityBindings(GameTestHelper helper, LivingEntity entity, String entityName) {
        for (ExpectedAttribute expected : EXPECTED_ATTRIBUTES) {
            boolean shouldBeBound = expected.holder() == Ssc14Attributes.ENT_PULL;
            helper.assertTrue(entity.getAttributes().hasAttribute(expected.holder()) == shouldBeBound,
                    entityName + " binding changed for " + expected.path());
        }
    }

    private static void assertDouble(GameTestHelper helper, double expected, double actual, String label) {
        helper.assertTrue(Double.compare(expected, actual) == 0,
                label + " changed: expected " + expected + ", got " + actual);
    }

    private static ExpectedAttribute expected(
            DeferredHolder<Attribute, Attribute> holder,
            String path,
            double defaultValue,
            double minimumValue,
            double maximumValue) {
        return new ExpectedAttribute(holder, path, defaultValue, minimumValue, maximumValue);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }

    private record ExpectedAttribute(
            DeferredHolder<Attribute, Attribute> holder,
            String path,
            double defaultValue,
            double minimumValue,
            double maximumValue) {
    }
}
