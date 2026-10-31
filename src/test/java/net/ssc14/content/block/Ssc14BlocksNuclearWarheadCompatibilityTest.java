package net.ssc14.content.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.ssc14.content.block.explosives.NuclearWarheadBlock;
import net.ssc14.content.block.explosives.NuclearWarheadBlockEntity;
import net.ssc14.content.block.explosives.Ssc14ExplosiveBlockEntities;
import org.junit.jupiter.api.Test;

class Ssc14BlocksNuclearWarheadCompatibilityTest {
    @Test
    void canonicalRegistriesOwnWarheadIdsAndPreservesCanonicalIds() {
        assertEquals("ssc_14:nuclear_warhead",
                Ssc14Blocks.NUCLEAR_WARHEAD.getId().toString());
        assertEquals("ssc_14:nuclear_warhead",
                Ssc14ExplosiveBlockEntities.NUCLEAR_WARHEAD.getId().toString());
        assertSame(Ssc14ExplosiveBlockEntities.NUCLEAR_WARHEAD,
                Ssc14ExplosiveBlockEntities.NUCLEAR_WARHEAD);
    }

    @Test
    void legacyWarheadTypesRemainConstructorCompatibleWrappers() throws Exception {
        Class<?> legacyBlock = Class.forName("net.mcreator.ssc.block.NuclearWarheadBlock");
        assertTrue(NuclearWarheadBlock.class.isAssignableFrom(legacyBlock));
        legacyBlock.getConstructor(BlockBehaviour.Properties.class);
        Class<?> legacyEntity = Class.forName(
                "net.mcreator.ssc.block.entity.NuclearWarheadBlockEntity");
        assertTrue(NuclearWarheadBlockEntity.class.isAssignableFrom(legacyEntity));
    }

    @Test
    void canonicalWarheadKeepsLegacyPhysicalDefaults() {
        NuclearWarheadBlock block =
                (NuclearWarheadBlock) Ssc14Blocks.NUCLEAR_WARHEAD.get();
        assertEquals(-1.0F, block.defaultDestroyTime());
        assertEquals(3_600_000.0F, block.getExplosionResistance());
    }

}
