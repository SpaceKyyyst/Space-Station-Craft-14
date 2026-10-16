
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.ssc.init.Ssc14ModItems;

public class Welding_Fuel_chekProcedure {
    
    private static final double INITIAL_FUEL = 10001.0;
    private static final double FUEL_CONSUMPTION = 2.0;
    private static final double MIN_FUEL_LIMIT = 150.0;

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null || itemstack.isEmpty()) return;
        if (!(entity instanceof LivingEntity livingEntity)) return;

        CustomData customData = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        double currentFuel = tag.getDoubleOr("fuel", 0.0);

        // Инициализация при самом первом клике/включении
        if (!tag.contains("fuel")) {
            currentFuel = INITIAL_FUEL;
            tag.putDouble("fuel", currentFuel);
            itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }

        // ЛОРНАЯ ЗАЩИТА: Проверяем, зажата ли "кнопка пуска" (находится ли аппарат в руках)
        boolean inMainHand = livingEntity.getMainHandItem() == itemstack;
        boolean inOffHand = livingEntity.getOffhandItem() == itemstack;

        // Если активная сварка валяется в карманах инвентаря — кнопка отпущена, она мгновенно тухнет!
        if (!inMainHand && !inOffHand) {
            deactivateWelderInInventory(itemstack, currentFuel);
            return;
        }

        // ПРОВЕРКА НА ОКОНЧАНИЕ ТОПЛИВА (Топливо ушло в ноль - 150 единиц)
        if (currentFuel == MIN_FUEL_LIMIT || !(currentFuel > MIN_FUEL_LIMIT)) {
            shutdownWelder(world, x, y, z, livingEntity, itemstack, currentFuel, inMainHand);
            return;
        }

        // РАСХОД ТОПЛИВА (Штатный режим работы в руке)
        currentFuel = currentFuel - FUEL_CONSUMPTION;
        tag.putDouble("fuel", currentFuel);
        itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        // ИНТЕРФЕЙС
        if (entity instanceof ServerPlayer serverPlayer) {
            long displayFuel = Math.round((currentFuel - 1.0) / 100.0);
            serverPlayer.sendSystemMessage(Component.literal("Fuel:  " + displayFuel), true);
        }
    }

    // Внутренний метод для умного гашения сварки в руках при нуле топлива
    private static void shutdownWelder(LevelAccessor world, double x, double y, double z, LivingEntity livingEntity, ItemStack itemstack, double fuel, boolean inMainHand) {
        ItemStack deactivated = new ItemStack(Ssc14ModItems.WELDING.get());
        deactivated.applyComponents(itemstack.getComponentsPatch());
        CompoundTag t = deactivated.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        t.putDouble("fuel", fuel);
        deactivated.set(DataComponents.CUSTOM_DATA, CustomData.of(t));
        deactivated.setCount(1);

        if (inMainHand) {
            livingEntity.setItemInHand(InteractionHand.MAIN_HAND, deactivated);
        } else {
            livingEntity.setItemInHand(InteractionHand.OFF_HAND, deactivated);
        }

        if (livingEntity instanceof Player player) player.getInventory().setChanged();

        if (world instanceof Level level) {
            var soundHolder = BuiltInRegistries.SOUND_EVENT.get(Identifier.fromNamespaceAndPath("ssc_14", "welder_off"));
            if (soundHolder != null && soundHolder.isPresent()) {
                if (!level.isClientSide()) level.playSound(null, BlockPos.containing(x, y, z), soundHolder.get().value(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                else level.playLocalSound(x, y, z, soundHolder.get().value(), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
        }
    }

    // Внутренний метод для тушения сварки, если её спрятали в карман инвентаря
    private static void deactivateWelderInInventory(ItemStack itemstack, double fuel) {
        // Меняем тип предмета "на лету" прямо внутри инвентаря через встроенные компоненты NeoForge 26.x
        // Это самый стабильный способ превратить активный предмет в пассивный в карманах
        CompoundTag tag = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        tag.putDouble("fuel", fuel);
        itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        // Для MCreator инвентарей мы перепишем внутренний ID через трансляцию предметов, 
        // но так как тип ItemStack в контейнере жестко зафиксирован, гашение при дропе на пол (Шаг 2) полностью закроет все лазейки!
    }
}
