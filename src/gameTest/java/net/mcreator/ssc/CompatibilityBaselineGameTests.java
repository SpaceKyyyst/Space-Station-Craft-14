package net.mcreator.ssc;

import net.ssc14.integration.neoforge.network.CompatibilityPayloadRegistrar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/** Runtime compatibility gates for identifiers that must survive the migration. */
@EventBusSubscriber(modid = Ssc14Mod.MODID)
public final class CompatibilityBaselineGameTests {
    private static final String REGISTRY_TEST_NAME = "compatibility_registry_ids";
    private static final String NETWORK_TEST_NAME = "compatibility_network_contracts";
    private static final String REGISTRY_MANIFEST = "/compatibility/registry-ids.txt";
    private static final String NETWORK_MANIFEST = "/compatibility/network-contracts.txt";

    private CompatibilityBaselineGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> {
            helper.register(id(REGISTRY_TEST_NAME), CompatibilityBaselineGameTests::registryIdsMatchBaseline);
            helper.register(id(NETWORK_TEST_NAME), CompatibilityBaselineGameTests::networkContractsMatchBaseline);
        });
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("compatibility_environment"));
        var data = new TestData<>(environment, id("audit_empty"), 20, 1, true);
        for (String testName : new String[] {REGISTRY_TEST_NAME, NETWORK_TEST_NAME}) {
            event.registerTest(id(testName), new FunctionGameTestInstance(
                    ResourceKey.create(Registries.TEST_FUNCTION, id(testName)), data));
        }
    }

    static void registryIdsMatchBaseline(GameTestHelper helper) {
        try {
            Map<String, Set<Identifier>> expected = readRegistryManifest(REGISTRY_MANIFEST);
            Map<String, Registry<?>> registries = registries();
            Set<String> differences = new TreeSet<>();
            for (Map.Entry<String, Registry<?>> entry : registries.entrySet()) {
                Set<Identifier> actualIds = new TreeSet<>(entry.getValue().keySet().stream()
                        .filter(identifier -> Ssc14Mod.MODID.equals(identifier.getNamespace()))
                        .toList());
                Set<Identifier> expectedIds = expected.getOrDefault(entry.getKey(), Set.of());
                difference(differences, entry.getKey(), "missing", expectedIds, actualIds);
                difference(differences, entry.getKey(), "unexpected", actualIds, expectedIds);
            }
            if (!differences.isEmpty()) {
                helper.fail("Registry compatibility drift:\n" + String.join("\n", differences));
                return;
            }
            helper.succeed();
        } catch (IOException | IllegalArgumentException exception) {
            helper.fail("Invalid registry compatibility manifest: " + exception.getMessage());
        }
    }

    static void networkContractsMatchBaseline(GameTestHelper helper) {
        try {
            Set<String> expected = readNetworkManifest(NETWORK_MANIFEST);
            Set<String> actual = CompatibilityPayloadRegistrar.registeredContracts();
            Set<String> differences = new TreeSet<>();
            stringDifference(differences, "missing", expected, actual);
            stringDifference(differences, "unexpected", actual, expected);
            if (!differences.isEmpty()) {
                helper.fail("Network compatibility drift:\n" + String.join("\n", differences));
                return;
            }
            helper.succeed();
        } catch (IOException | IllegalArgumentException exception) {
            helper.fail("Invalid network compatibility manifest: " + exception.getMessage());
        }
    }

    private static Map<String, Registry<?>> registries() {
        Map<String, Registry<?>> registries = new LinkedHashMap<>();
        registries.put("attribute", BuiltInRegistries.ATTRIBUTE);
        registries.put("block", BuiltInRegistries.BLOCK);
        registries.put("block_entity_type", BuiltInRegistries.BLOCK_ENTITY_TYPE);
        registries.put("creative_mode_tab", BuiltInRegistries.CREATIVE_MODE_TAB);
        registries.put("entity_type", BuiltInRegistries.ENTITY_TYPE);
        registries.put("fluid", BuiltInRegistries.FLUID);
        registries.put("item", BuiltInRegistries.ITEM);
        registries.put("menu", BuiltInRegistries.MENU);
        registries.put("particle_type", BuiltInRegistries.PARTICLE_TYPE);
        registries.put("sound_event", BuiltInRegistries.SOUND_EVENT);
        return Map.copyOf(registries);
    }

    private static Map<String, Set<Identifier>> readRegistryManifest(String resource) throws IOException {
        InputStream stream = CompatibilityBaselineGameTests.class.getResourceAsStream(resource);
        if (stream == null) throw new IllegalArgumentException("Missing resource " + resource);
        Map<String, Set<Identifier>> result = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            int lineNumber = 0;
            for (String line; (line = reader.readLine()) != null;) {
                lineNumber++;
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;
                String[] columns = trimmed.split("\\s+");
                if (columns.length != 2) {
                    throw new IllegalArgumentException("Line " + lineNumber + " must have exactly 2 columns");
                }
                if (!registries().containsKey(columns[0])) {
                    throw new IllegalArgumentException("Line " + lineNumber + " has unknown registry " + columns[0]);
                }
                Identifier identifier = Identifier.parse(columns[1]);
                if (!Ssc14Mod.MODID.equals(identifier.getNamespace())) {
                    throw new IllegalArgumentException("Line " + lineNumber + " has unsupported namespace " + identifier);
                }
                if (!result.computeIfAbsent(columns[0], ignored -> new TreeSet<>()).add(identifier)) {
                    throw new IllegalArgumentException("Line " + lineNumber + " duplicates " + trimmed);
                }
            }
        }
        return result;
    }

    private static Set<String> readNetworkManifest(String resource) throws IOException {
        InputStream stream = CompatibilityBaselineGameTests.class.getResourceAsStream(resource);
        if (stream == null) throw new IllegalArgumentException("Missing resource " + resource);
        Set<String> result = new TreeSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            int lineNumber = 0;
            for (String line; (line = reader.readLine()) != null;) {
                lineNumber++;
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;
                String[] columns = trimmed.split("\\s+");
                if (columns.length != 3) {
                    throw new IllegalArgumentException("Line " + lineNumber + " must have exactly 3 columns");
                }
                if (!Set.of("serverbound", "clientbound", "bidirectional").contains(columns[0])) {
                    throw new IllegalArgumentException("Line " + lineNumber + " has unknown direction " + columns[0]);
                }
                if (!columns[1].matches("[A-Za-z0-9._-]+")) {
                    throw new IllegalArgumentException("Line " + lineNumber + " has invalid version " + columns[1]);
                }
                Identifier identifier = Identifier.parse(columns[2]);
                if (!Ssc14Mod.MODID.equals(identifier.getNamespace())) {
                    throw new IllegalArgumentException("Line " + lineNumber + " has unsupported namespace " + identifier);
                }
                if (!result.add(String.join(" ", columns))) {
                    throw new IllegalArgumentException("Line " + lineNumber + " duplicates " + trimmed);
                }
            }
        }
        return result;
    }

    private static void difference(Set<String> output, String registry, String kind,
            Set<Identifier> left, Set<Identifier> right) {
        Set<Identifier> delta = new TreeSet<>(left);
        delta.removeAll(right);
        for (Identifier identifier : delta) output.add(registry + " " + kind + " " + identifier);
    }

    private static void stringDifference(Set<String> output, String kind, Set<String> left, Set<String> right) {
        Set<String> delta = new TreeSet<>(left);
        delta.removeAll(right);
        for (String contract : delta) output.add(kind + " " + contract);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, path);
    }
}
