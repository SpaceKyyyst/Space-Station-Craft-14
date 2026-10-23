package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.junit.jupiter.api.Test;

class Ssc14BlocksGeologyCompatibilityTest {
    private static final Map<String, String> EXPECTED_HOLDERS = Map.ofEntries(
            Map.entry("ASTEROID_STONE_BASE", "asteroid_stone_base"),
            Map.entry("ASTEROID_STONE_IRON", "asteroid_stone_iron"),
            Map.entry("ASTEROID_STONE_QUARTZ", "asteroid_stone_quartz"),
            Map.entry("ASTEROID_STONE_COAL", "asteroid_stone_coal"),
            Map.entry("ASTEROID_STONE_GOLD", "asteroid_stone_gold"),
            Map.entry("ASTEROID_STONE_PLASMA", "asteroid_stone_plasma"),
            Map.entry("ASTEROID_STONE_SILVER", "asteroid_stone_silver"),
            Map.entry("ASTEROID_STONE_URAN", "asteroid_stone_uran"),
            Map.entry("ASTEROID_STONE_DIAMOND", "asteroid_stone_diamond"),
            Map.entry("CAVE_FLOOR_1", "cave_floor_1"),
            Map.entry("CAVE_FLOOR_2", "cave_floor_2"),
            Map.entry("CAVE_FLOOR_3", "cave_floor_3"),
            Map.entry("CAVE_FLOOR_4", "cave_floor_4"),
            Map.entry("CAVE_FLOOR_5", "cave_floor_5"),
            Map.entry("CAVE_FLOOR_6", "cave_floor_6"),
            Map.entry("CAVE_FLOOR_7", "cave_floor_7"));

    private static final Map<Class<?>, Class<?>> LEGACY_TYPES = Map.ofEntries(
            pair(net.mcreator.ssc.block.AsteroidStoneBaseBlock.class,
                    net.ssc14.content.block.geology.AsteroidStoneBaseBlock.class),
            pair(net.mcreator.ssc.block.AsteroidStoneIronBlock.class,
                    net.ssc14.content.block.geology.AsteroidStoneIronBlock.class),
            pair(net.mcreator.ssc.block.AsteroidStoneQuartzBlock.class,
                    net.ssc14.content.block.geology.AsteroidStoneQuartzBlock.class),
            pair(net.mcreator.ssc.block.AsteroidStoneCoalBlock.class,
                    net.ssc14.content.block.geology.AsteroidStoneCoalBlock.class),
            pair(net.mcreator.ssc.block.AsteroidStoneGoldBlock.class,
                    net.ssc14.content.block.geology.AsteroidStoneGoldBlock.class),
            pair(net.mcreator.ssc.block.AsteroidStonePlasmaBlock.class,
                    net.ssc14.content.block.geology.AsteroidStonePlasmaBlock.class),
            pair(net.mcreator.ssc.block.AsteroidStoneSilverBlock.class,
                    net.ssc14.content.block.geology.AsteroidStoneSilverBlock.class),
            pair(net.mcreator.ssc.block.AsteroidStoneUranBlock.class,
                    net.ssc14.content.block.geology.AsteroidStoneUranBlock.class),
            pair(net.mcreator.ssc.block.AsteroidStoneDiamondBlock.class,
                    net.ssc14.content.block.geology.AsteroidStoneDiamondBlock.class),
            pair(net.mcreator.ssc.block.CaveFloor1Block.class,
                    net.ssc14.content.block.geology.CaveFloor1Block.class),
            pair(net.mcreator.ssc.block.CaveFloor2Block.class,
                    net.ssc14.content.block.geology.CaveFloor2Block.class),
            pair(net.mcreator.ssc.block.CaveFloor3Block.class,
                    net.ssc14.content.block.geology.CaveFloor3Block.class),
            pair(net.mcreator.ssc.block.CaveFloor4Block.class,
                    net.ssc14.content.block.geology.CaveFloor4Block.class),
            pair(net.mcreator.ssc.block.CaveFloor5Block.class,
                    net.ssc14.content.block.geology.CaveFloor5Block.class),
            pair(net.mcreator.ssc.block.CaveFloor6Block.class,
                    net.ssc14.content.block.geology.CaveFloor6Block.class),
            pair(net.mcreator.ssc.block.CaveFloor7Block.class,
                    net.ssc14.content.block.geology.CaveFloor7Block.class));

    @Test
    void canonicalRegistryOwnsExactGeologyIdsAndLegacyFacadeAliasesHolders()
            throws ReflectiveOperationException {
        Set<Field> targetHolders = expectedHolders(Ssc14Blocks.class);
        assertEquals(EXPECTED_HOLDERS.keySet(), names(targetHolders));
        assertEquals(EXPECTED_HOLDERS.values().stream()
                .map(path -> "ssc_14:" + path)
                .collect(Collectors.toUnmodifiableSet()), ids(targetHolders));
        assertSame(Ssc14Blocks.REGISTRY, Ssc14ModBlocks.REGISTRY);
        for (Field targetField : targetHolders) {
            assertSame(targetField.get(null), Ssc14ModBlocks.class.getField(targetField.getName()).get(null),
                    targetField.getName());
        }
    }

    @Test
    void legacyGeologyBlocksRemainConstructorCompatibleSubtypes() throws ReflectiveOperationException {
        for (Map.Entry<Class<?>, Class<?>> pair : LEGACY_TYPES.entrySet()) {
            assertTrue(pair.getValue().isAssignableFrom(pair.getKey()), pair.getKey().getName());
            pair.getKey().getConstructor(BlockBehaviour.Properties.class);
        }
    }

    private static Map.Entry<Class<?>, Class<?>> pair(Class<?> legacy, Class<?> canonical) {
        return Map.entry(legacy, canonical);
    }

    private static Set<Field> expectedHolders(Class<?> owner) {
        return EXPECTED_HOLDERS.keySet().stream().map(name -> {
            try {
                return owner.getField(name);
            } catch (NoSuchFieldException exception) {
                throw new IllegalStateException(exception);
            }
        }).collect(Collectors.toUnmodifiableSet());
    }

    private static Set<String> names(Set<Field> fields) {
        return fields.stream().map(Field::getName).collect(Collectors.toUnmodifiableSet());
    }

    private static Set<String> ids(Set<Field> fields) {
        return fields.stream().map(field -> {
            try {
                return ((DeferredHolder<?, ?>) field.get(null)).getId().toString();
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(exception);
            }
        }).collect(Collectors.toUnmodifiableSet());
    }
}
