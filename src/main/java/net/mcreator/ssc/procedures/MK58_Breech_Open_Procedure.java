
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;

import net.mcreator.ssc.init.Ssc14ModItems;

public class MK58_Breech_Open_Procedure {

    public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        if (!(entity instanceof LivingEntity livingEntity)) 
            return;

        // Предметы в руках
        ItemStack mainHandStack = livingEntity.getMainHandItem();
        ItemStack offHandStack = livingEntity.getOffhandItem();
        Item mainHandItem = mainHandStack.getItem();

        boolean isShift = livingEntity.isShiftKeyDown();

        // Кулдаун
        if (livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(mainHandStack, 10);
        }

        // БУФЕРНЫЕ ПЕРЕМЕННЫЕ: Достаем предметы из пистолета ДО его подмены
        ItemStack buferSlot0 = ItemStack.EMPTY;
        ItemStack buferSlot1 = ItemStack.EMPTY;

        IItemHandler oldInv = mainHandStack.getCapability(Capabilities.ItemHandler.ITEM, null);
        if (oldInv != null) {
            buferSlot0 = oldInv.getStackInSlot(0).copy(); // Патронник
            buferSlot1 = oldInv.getStackInSlot(1).copy(); // Магазин
        }

        Item targetWeaponItem = mainHandItem;
        boolean stateChanged = false;

        // --- ЛОГИКА 1 и 3: Закрытый затвор ---
        if (mainHandItem == Ssc14ModItems.MK_58.get() || mainHandItem == Ssc14ModItems.MK_58NOMAGZ.get()) {
            if (!isShift) {
                // ОТКРЫВАЕМ ЗАТВОР
                playSound(world, livingEntity, "ssc_14:guns_bolt_open");

                // Выбрасываем патрон из буфера патронника
                if (!buferSlot0.isEmpty()) {
                    spawnItemInWorld(world, livingEntity, buferSlot0);
                    buferSlot0 = ItemStack.EMPTY;
                }

                // Переносим патрон из буфера магазина в буфер патронника
                boolean hasMagazine = (buferSlot1.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35.get() || buferSlot1.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get());
                if (hasMagazine) {
                    CustomData customData = buferSlot1.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                    CompoundTag tag = customData.copyTag();
                    
                    int ammoInMag = 0;
                    if (tag != null) {
                        // Безопасное извлечение Optional-значений в 1.21.8 NeoForge через чистый Object
                        Object rawDouble = tag.getDouble("Bullets");
                        if (rawDouble instanceof java.util.Optional<?> opt && opt.isPresent()) {
                            ammoInMag = ((Double) opt.get()).intValue();
                        } else {
                            Object rawInt = tag.getInt("Bullets");
                            if (rawInt instanceof java.util.Optional<?> optInt && optInt.isPresent()) {
                                ammoInMag = (Integer) optInt.get();
                            }
                        }
                    }
                    
                    if (ammoInMag > 0) {
                        final int nextAmmo = ammoInMag - 1;
                        // Перезаписываем NBT в буфере магазина
                        CustomData.update(DataComponents.CUSTOM_DATA, buferSlot1, tagUpdate -> {
                            tagUpdate.putInt("Bullets", nextAmmo);
                            tagUpdate.putDouble("Bullets", nextAmmo);
                        });
                        // Спавним патрон в патронник (слот 0)
                        buferSlot0 = new ItemStack(Ssc14ModItems.BULLET_35ITEM.get());
                    }
                }

                targetWeaponItem = hasMagazine ? Ssc14ModItems.MK_58OPEN.get() : Ssc14ModItems.MK_58OPEN_NO_MAGZ.get();
                stateChanged = true;

            } else {
                // Взаимодействие с магазином (Shift + ПКМ)
                boolean isSlot1Mag = (buferSlot1.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35.get() || buferSlot1.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get());
                boolean isOffHandMag = (offHandStack.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35.get() || offHandStack.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get());

                if (isSlot1Mag) {
                    // Извлекаем магазин из буфера в руку/мир
                    playSound(world, livingEntity, "ssc_14:items_pistol_mag_out");
                    if (offHandStack.isEmpty()) {
                        livingEntity.setItemInHand(InteractionHand.OFF_HAND, buferSlot1.copy());
                    } else {
                        spawnItemInWorld(world, livingEntity, buferSlot1);
                    }
                    buferSlot1 = ItemStack.EMPTY;
                    targetWeaponItem = Ssc14ModItems.MK_58NOMAGZ.get();
                    stateChanged = true;
                } else if (buferSlot1.isEmpty() && isOffHandMag) {
                    // Вставляем магазин из руки в буфер
                    playSound(world, livingEntity, "ssc_14:items_pistol_mag_in");
                    buferSlot1 = offHandStack.copy();
                    buferSlot1.setCount(1);
                    livingEntity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                    targetWeaponItem = Ssc14ModItems.MK_58.get();
                    stateChanged = true;
                }
            }
        } 
        // --- ЛОГИКА 2 и 4: Открытый затвор ---
        else if (mainHandItem == Ssc14ModItems.MK_58OPEN.get() || mainHandItem == Ssc14ModItems.MK_58OPEN_NO_MAGZ.get()) {
            if (!isShift) {
                // ЗАКРЫВАЕМ ЗАТВОР
                playSound(world, livingEntity, "ssc_14:guns_bolt_closed");
                boolean hasMagazine = (buferSlot1.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35.get() || buferSlot1.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get());
                targetWeaponItem = hasMagazine ? Ssc14ModItems.MK_58.get() : Ssc14ModItems.MK_58NOMAGZ.get();
                stateChanged = true;
            } else {
                // Взаимодействие с магазином (Shift + ПКМ)
                boolean isSlot1Mag = (buferSlot1.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35.get() || buferSlot1.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get());
                boolean isOffHandMag = (offHandStack.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35.get() || offHandStack.getItem() == Ssc14ModItems.MAGAZINE_PISTOL_35EMPTY.get());

                if (isSlot1Mag) {
                    playSound(world, livingEntity, "ssc_14:items_pistol_mag_out");
                    if (offHandStack.isEmpty()) {
                        livingEntity.setItemInHand(InteractionHand.OFF_HAND, buferSlot1.copy());
                    } else {
                        spawnItemInWorld(world, livingEntity, buferSlot1);
                    }
                    buferSlot1 = ItemStack.EMPTY;
                    targetWeaponItem = Ssc14ModItems.MK_58OPEN_NO_MAGZ.get();
                    stateChanged = true;
                } else if (buferSlot1.isEmpty() && isOffHandMag) {
                    playSound(world, livingEntity, "ssc_14:items_pistol_mag_in");
                    buferSlot1 = offHandStack.copy();
                    buferSlot1.setCount(1);
                    livingEntity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                    targetWeaponItem = Ssc14ModItems.MK_58OPEN.get();
                    stateChanged = true;
                }
            }
        }

        // --- НАДЁЖНАЯ ФИНАЛИЗАЦИЯ ИЗ БУФЕРА ---
        if (stateChanged) {
            // Создаем абсолютно чистый новый предмет пушки
            ItemStack nextWeaponStack = new ItemStack(targetWeaponItem, 1);

            // Напрямую открываем новое капабилити и выгружаем данные из буфера
            IItemHandlerModifiable newInv = (IItemHandlerModifiable) nextWeaponStack.getCapability(Capabilities.ItemHandler.ITEM, null);
            if (newInv != null) {
                newInv.setStackInSlot(0, buferSlot0);
                newInv.setStackInSlot(1, buferSlot1);
            }

            // Заменяем пистолет в руке игрока
            livingEntity.setItemInHand(InteractionHand.MAIN_HAND, nextWeaponStack);

            if (livingEntity instanceof Player player) {
                player.getInventory().setChanged();
            }
        }
    }

    private static void spawnItemInWorld(LevelAccessor world, LivingEntity entity, ItemStack stack) {
        if (world instanceof ServerLevel serverLevel) {
            ItemEntity entityToSpawn = new ItemEntity(serverLevel, entity.getX(), entity.getY() + (entity.getBbHeight() / 3d), entity.getZ(), stack.copy());
            entityToSpawn.setPickUpDelay(10);
            entityToSpawn.setUnlimitedLifetime();
            serverLevel.addFreshEntity(entityToSpawn);
        }
    }

    private static void playSound(LevelAccessor world, Entity entity, String soundRegistryName) {
        if (!(world instanceof Level level)) return;
        
        ResourceLocation soundRL = ResourceLocation.parse(soundRegistryName);
        float pitch = (float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05);
        BlockPos pos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ());

        if (!level.isClientSide()) {
        	level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.getValue(soundRL), SoundSource.MASTER, 1, pitch);
        	} else {
        		level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.getValue(soundRL), SoundSource.MASTER, 1, pitch, false);
		}
	}
}
