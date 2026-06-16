
package net.mcreator.ssc;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

/**
 * Интерфейс для блоков, влияющих на атмосферу.
 */
public interface IAtmosBlock {
    
    // ✅ ИСПРАВЛЕНО: точное совпадение с рабочим примером!
    TagKey<net.minecraft.world.level.block.Block> NON_HERMETIC_TAG = 
        TagKey.create(Registries.BLOCK, ResourceLocation.parse("ssc14:non-hermetic"));
    
    /**
     * Коэффициент проницаемости: 0.0 = герметично, 1.0 = полностью открыто
     */
    float getPermeability(BlockState state);
    
    /**
     * Блокирует ли блок распространение газов полностью
     */
    default boolean isGasBarrier(BlockState state) {
        return getPermeability(state) <= 0.001f;
    }
}
