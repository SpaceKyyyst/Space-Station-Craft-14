package net.mcreator.ssc;

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
    private static final String TEST_NAME = "compatibility_registry_ids";
    private static final String MANIFEST = "/compatibility/registry-ids.txt";

    private CompatibilityBaselineGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper ->
                helper.register(id(TEST_NAME), CompatibilityBaselineGameTests::registryIdsMatchBaseline));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("compatibility_environment"));
        var data = new TestData<>(environment, id("audit_empty"), 20, 1, true);
        event.registerTest(id(TEST_NAME), new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, id(TEST_NAME)), data));
    }

    static void registryIdsMatchBaseline(GameTestHelper helper) {
        try {
            Map<String, Set<Identifier>> expected = readRegistryManifest(MANIFEST);
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

    private static void difference(Set<String> output, String registry, String kind,
            Set<Identifier> left, Set<Identifier> right) {
        Set<Identifier> delta = new TreeSet<>(left);
        delta.removeAll(right);
        for (Identifier identifier : delta) output.add(registry + " " + kind + " " + identifier);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Mod.MODID, path);
    }
}
