
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SSC14AtmosGasTemperatureCommandProcedure {
    
    public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }
        
        try {
            // 1. Получаем температуру из аргументов команды
            double gasTemperature = DoubleArgumentType.getDouble(arguments, "gas_temperature");
            
            // 2. Валидация диапазона [1; 1000000] K
            if (gasTemperature < 1.0) {
                sendError(arguments.getSource(), "Температура не может быть ниже 1 Кельвина (абсолютный ноль недопустим)!");
                return;
            }
            if (gasTemperature > 1000000) {
                sendError(arguments.getSource(), "Слишком высокая температура (макс. 1,000,000 К)!");
                return;
            }
            
            // 3. Получаем игрока
            var source = arguments.getSource();
            var player = source.getPlayer();
            if (player == null) {
                sendError(source, "Эту команду можно выполнять только игроку!");
                return;
            }
            
            var pos = player.blockPosition();
            
            // 4. Получаем ячейку атмосферы
            var cell = net.mcreator.ssc.AtmosphereManager.get(serverLevel).getCellAt(pos);
            if (cell == null) {
                sendError(source, "Не удалось получить данные об атмосфере в этом блоке.\n§7Попробуй поставить/сломать блок рядом, чтобы активировать регион.");
                return;
            }
            
            // 5. Устанавливаем температуру и активируем обновление региона
            float oldTemp = cell.getTemperature();
            cell.setTemperature((float) gasTemperature);
            net.mcreator.ssc.AtmosphereManager.get(serverLevel).onBlockChanged(pos);
            
            // 6. Отправляем подтверждение
            float pressure = cell.getPressure();
            float tempC = (float) gasTemperature - 273.15f;
            float oldTempC = oldTemp - 273.15f;
            
            source.sendSuccess(() -> Component.literal(
                "§a[SSC14] §rТемпература установлена: §e" + String.format("%.1f", gasTemperature) + " K §r(" + 
                String.format("%.1f", tempC) + "°C)§r в " + pos.toShortString() + 
                "\n§7Давление: §f" + String.format("%.1f", pressure / 1000f) + " кПа" +
                " §7| §7Было: §7" + String.format("%.1f", oldTempC) + "°C"
            ), false);
            
        } catch (Exception e) {
            arguments.getSource().sendFailure(Component.literal("§c[SSC14] Ошибка выполнения: " + e.getMessage()));
            e.printStackTrace();
        }
    }
    
    // Вспомогательный метод для отправки ошибок
    private static void sendError(CommandSourceStack source, String message) {
        source.sendFailure(Component.literal("§c[SSC14] " + message));
    }
}
