
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.ssc.init.Ssc14ModItems;

public class FuelTank_USE_Procedure {
    
    private static final double MAX_WELDER_FUEL = 10000.0;

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;
        if (!(entity instanceof LivingEntity livingEntity)) return;

        // ОПТИМИЗАЦИЯ: Работаем напрямую с настоящим предметом в руке без создания багнутых копий .copy()
        ItemStack handItem = livingEntity.getMainHandItem();
        if (handItem.isEmpty()) return;

        BlockPos tankPos = BlockPos.containing(x, y, z);
        double currentTankFuel = getBlockNBTNumber(world, tankPos, "fuel");
        if (currentTankFuel == -1.0 || !(currentTankFuel > 0.0)) currentTankFuel = 0.0;

        CustomData customData = handItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag itemTag = customData.copyTag();
        double currentWelderFuel = itemTag.getDoubleOr("fuel", 0.0);

        // ===== ВАРИАНТ 1: В руке выключенная сварка (Заправка) =====
        if (handItem.is(Ssc14ModItems.WELDING.get())) {
            double neededFuel = MAX_WELDER_FUEL - currentWelderFuel;
            if (neededFuel == 0.0 || !(neededFuel > 0.0)) return; // Сварка уже полная

            double fuelToSubtractFromTank = 0.0;

            // Если в баке топлива БОЛЬШЕ или РОВНО столько, сколько нужно сварке до максимума
            if (currentTankFuel - neededFuel == 0.0 || (currentTankFuel - neededFuel > 0.0)) {
                fuelToSubtractFromTank = neededFuel;
                currentWelderFuel = MAX_WELDER_FUEL;
            } 
            // Если в баке МЕНЬШЕ топлива, чем нужно сварке (выкачиваем остатки бака под ноль)
            else {
                fuelToSubtractFromTank = currentTankFuel;
                currentWelderFuel = currentWelderFuel + currentTankFuel;
            }

            // Записываем обновлённое топливо в настоящую сварку в руке игрока
            itemTag.putDouble("fuel", currentWelderFuel);
            handItem.set(DataComponents.CUSTOM_DATA, CustomData.of(itemTag));

            // Обновляем топливо внутри NBT-данных блока топливного резервуара
            if (!world.isClientSide()) {
                BlockEntity be = world.getBlockEntity(tankPos);
                BlockState bs = world.getBlockState(tankPos);
                if (be != null) {
                    double newTankFuel = Math.max(0.0, currentTankFuel - fuelToSubtractFromTank);
                    be.getPersistentData().putDouble("fuel", newTankFuel);
                    be.setChanged();
                }
                if (world instanceof Level level) {
                    level.sendBlockUpdated(tankPos, bs, bs, 3);
                }
            }

            // Проигрываем звук успешной заправки со случайным питчем по твоей формуле
            if (world instanceof Level level) {
                var soundHolder = BuiltInRegistries.SOUND_EVENT.get(Identifier.fromNamespaceAndPath("ssc_14", "effects_refill"));
                if (soundHolder != null && soundHolder.isPresent()) {
                    float volume = (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1);
                    float pitch = (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1);
                    if (!level.isClientSide()) {
                        level.playSound(null, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), soundHolder.get().value(), SoundSource.BLOCKS, volume, pitch);
                    } else {
                        level.playLocalSound(x + 0.5, y + 0.5, z + 0.5, soundHolder.get().value(), SoundSource.BLOCKS, volume, pitch, false);
                    }
                }
            }
        } 
        // ===== ВАРИАНТ 2: В руке зажжённая сварка (БАХ!) =====
        else if (handItem.is(Ssc14ModItems.ACTIVE_WELDER.get())) {
            if (world instanceof Level level && !level.isClientSide()) {
                float explosionPower = (float) (currentTankFuel / 10000.0);
                if (explosionPower == 0.0f || !(explosionPower > 0.0f)) explosionPower = 0.1f; // Минимальный бах, если бак пуст
                
                level.explode(null, x + 0.5, y + 0.5, z + 0.5, explosionPower, Level.ExplosionInteraction.BLOCK);
            }
        }
    }

    private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            return blockEntity.getPersistentData().getDoubleOr(tag, 0.0);
        }
        return -1.0;
    }
}
