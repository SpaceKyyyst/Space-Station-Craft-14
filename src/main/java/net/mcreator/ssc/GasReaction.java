
package net.mcreator.ssc;

import java.util.Arrays;

/**
 * Описание химической реакции между газами.
 * Используется record (Java 14+) для иммутабельности.
 */
public record GasReaction(
    String id,
    boolean[] requiredGases,      // индексы газов, которые должны присутствовать
    float minTemperatureK,        // мин. температура для реакции
    float maxTemperatureK,        // макс. температура
    float[] consumptionPerTick,   // молей каждого газа тратится за тик
    float[] productionPerTick,    // молей каждого газа производится за тик
    float heatReleaseJoules,      // выделение (>0) или поглощение (<0) тепла
    float activationThreshold     // мин. сумма молей для попытки реакции
) {
    
    /**
     * Проверяет, могут ли начаться реакции в данной ячейке
     */
    public boolean canOccur(float[] moles, float temperatureK) {
        if (temperatureK < minTemperatureK || temperatureK > maxTemperatureK) {
            return false;
        }
        
        float totalMoles = 0f;
        for (int i = 0; i < GasType.COUNT; i++) {
            if (requiredGases[i] && moles[i] <= 0f) {
                return false; // требуемый газ отсутствует
            }
            if (requiredGases[i]) {
                totalMoles += moles[i];
            }
        }
        return totalMoles >= activationThreshold;
    }
    
    /**
     * Применяет реакцию к ячейке (изменяет массив moles и температуру)
     * @return было ли применено изменение
     */
    public boolean apply(float[] moles, float[] temperatureRef) {
        if (!canOccur(moles, temperatureRef[0])) {
            return false;
        }
        
        // Проверка: хватает ли газов для потребления
        for (int i = 0; i < GasType.COUNT; i++) {
            if (consumptionPerTick[i] > moles[i]) {
                return false; // не хватает реагента
            }
        }
        
        // Применяем изменения
        for (int i = 0; i < GasType.COUNT; i++) {
            moles[i] -= consumptionPerTick[i];
            moles[i] += productionPerTick[i];
            if (moles[i] < 0f) moles[i] = 0f; // защита от отрицательных значений
        }
        
        // Обновляем температуру (упрощённо: ΔT = Q / (n * Cv))
        float totalMoles = 0f, totalCv = 0f;
        for (int i = 0; i < GasType.COUNT; i++) {
            if (moles[i] > 0f) {
                GasType gas = GasType.byIndex(i);
                totalMoles += moles[i];
                totalCv += moles[i] * gas.getHeatCapacity();
            }
        }
		if (totalCv > 0f && totalMoles > 0f) {
		    temperatureRef[0] += heatReleaseJoules / totalCv;
		    if (temperatureRef[0] < 1f) temperatureRef[0] = 1f;
		}
		return true; // ✅ явно возвращаем true при успешном применении
    }
    
    /**
     * Фабрика для создания реакции с проверками
     */
    public static GasReaction create(String id, boolean[] required, 
                                     float minT, float maxT,
                                     float[] consume, float[] produce,
                                     float heat, float threshold) {
        if (required.length != GasType.COUNT || 
            consume.length != GasType.COUNT || 
            produce.length != GasType.COUNT) {
            throw new IllegalArgumentException("Array length must be " + GasType.COUNT);
        }
        return new GasReaction(id, required, minT, maxT, consume, produce, heat, threshold);
    }
}
