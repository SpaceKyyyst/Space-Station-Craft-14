
package net.mcreator.ssc;

import net.minecraft.world.level.block.state.BlockState;

/**
 * Интерфейс для блоков, влияющих на атмосферу.
 * Реализуй его в своих блоках через MCreator (Custom Block Code).
 */
public interface IAtmosBlock {
    
    /**
     * Коэффициент проницаемости: 0.0 = герметично, 1.0 = полностью открыто
     */
    float getPermeability(BlockState state);
    
    /**
     * Блокирует ли блок распространение газов полностью (для оптимизации)
     */
    default boolean isGasBarrier(BlockState state) {
        return getPermeability(state) <= 0.001f;
    }
    
    /**
     * Стандартные значения для часто используемых блоков
     */
    interface Defaults {
        float SOLID = 0.0f;           // Стены, пол
        float DOOR_CLOSED = 0.0f;     // Закрытая дверь
        float DOOR_OPEN = 1.0f;       // Открытая дверь
        float GRILLE = 0.8f;          // Решётка
        float WINDOW = 0.9f;          // Окно
        float AIR = 1.0f;             // Воздух
        float VENT = 0.5f;            // Вентиляционная решётка
    }
}
