package net.mcreator.ssc.procedures;

/**
 * Глобальный страж для защиты процедур шлюзов от взаимной рекурсии.
 * Если одна процедура шлюза выполняется — вторая сразу вернётся.
 */
public class AirlockGuard {
    
    // 🔧 ЕДИНЫЙ флаг на ВСЕ процедуры шлюзов
    private static volatile boolean isProcessing = false;
    
    /**
     * Попытаться войти в критическую секцию.
     * @return true если успешно, false если уже выполняется другая процедура
     */
    public static boolean tryEnter() {
        if (isProcessing) return false;
        isProcessing = true;
        return true;
    }
    
    /**
     * Выйти из критической секции.
     */
    public static void exit() {
        isProcessing = false;
    }
}