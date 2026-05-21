
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SSC14AtmosSetGasCommandProcedure {
    
    public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }
        
        try {
            // 1. Получаем параметры команды
            String gasName = commandParameterMessage(arguments, "gas_name").toLowerCase().trim();
            double gasAmount = DoubleArgumentType.getDouble(arguments, "gas_amount");
            
            // 2. Валидация
            if (gasAmount < 0) {
                sendError(arguments.getSource(), "Количество газа не может быть отрицательным!");
                return;
            }
            if (gasAmount > 1000) {
                sendError(arguments.getSource(), "Слишком большое количество газа (макс. 1000 моль)!");
                return;
            }
            
            // 3. Ищем газ по имени
            net.mcreator.ssc.GasType gasType = net.mcreator.ssc.GasType.byName(gasName);
            if (gasType == null) {
                StringBuilder available = new StringBuilder("§7Доступные газы: §r");
                for (int i = 0; i < net.mcreator.ssc.GasType.COUNT; i++) {
                    var g = net.mcreator.ssc.GasType.byIndex(i);
                    if (g != null) {
                        if (i > 0) available.append(", ");
                        available.append(g.getName());
                    }
                }
                sendError(arguments.getSource(), "Неизвестный газ: §c" + gasName + "§r\n" + available.toString());
                return;
            }
            
            // 4. Получаем игрока
            var source = arguments.getSource();
            var player = source.getPlayer();
            if (player == null) {
                sendError(source, "Эту команду можно выполнять только игроку!");
                return;
            }
            
            var pos = player.blockPosition();
            
            // 5. Получаем ячейку атмосферы
            var cell = net.mcreator.ssc.AtmosphereManager.get(serverLevel).getCellAt(pos);
            if (cell == null) {
                sendError(source, "Не удалось получить данные об атмосфере в этом блоке.\n§7Попробуй поставить/сломать блок рядом, чтобы активировать регион.");
                return;
            }
            
            // 6. Добавляем газ и активируем обновление
            cell.addMoles(gasType.ordinal(), (float) gasAmount);
            net.mcreator.ssc.AtmosphereManager.get(serverLevel).onBlockChanged(pos);
            
            // 7. Отправляем подтверждение
            float pressure = cell.getPressure();
            float tempC = cell.getTemperature() - 273.15f;
            
            source.sendSuccess(() -> Component.literal(
                "§a[SSC14] §rДобавлено §e" + String.format("%.2f", gasAmount) + " моль §r" + 
                gasType.getName() + "§r в " + pos.toShortString() + 
                "\n§7Давление: §f" + String.format("%.1f", pressure / 1000f) + " кПа" +
                " §7| §7Темп: §f" + String.format("%.1f", tempC) + "°C"
            ), false);
            
        } catch (Exception e) {
            arguments.getSource().sendFailure(Component.literal("§c[SSC14] Ошибка выполнения: " + e.getMessage()));
            e.printStackTrace();
        }
    }
    
    // Вспомогательный метод для получения строкового параметра
    private static String commandParameterMessage(CommandContext<CommandSourceStack> arguments, String parameter) {
        try {
            return MessageArgument.getMessage(arguments, parameter).getString();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    // ✅ ИСПРАВЛЕНО: правильный тип CommandSourceStack и корректный вызов sendFailure
    private static void sendError(CommandSourceStack source, String message) {
        source.sendFailure(Component.literal("§c[SSC14] " + message));
    }
}
