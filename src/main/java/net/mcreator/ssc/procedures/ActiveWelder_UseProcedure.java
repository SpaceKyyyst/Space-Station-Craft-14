
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;

import net.mcreator.ssc.init.Ssc14ModParticleTypes;
import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class ActiveWelder_UseProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
        // === БАЗОВЫЕ ПРОВЕРКИ ===
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;
        
        ItemStack mainHand = livingEntity.getMainHandItem();
        if (!mainHand.is(Ssc14ModItems.ACTIVE_WELDER.get())) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        double posHash = entity.getX() + entity.getY() + entity.getZ();
        Block targetBlock = blockstate.getBlock();
        IntegerProperty stateProp = findIntegerProperty(blockstate, "blockstate");
        IntegerProperty arlProp = findIntegerProperty(blockstate, "arl_variat");
        int currentState = stateProp != null ? blockstate.getValue(stateProp) : -1;
        int currentArl = arlProp != null ? blockstate.getValue(arlProp) : -1;

        // === КОНФИГУРАЦИЯ (Java 21 record) ===
        record WeldConfig(int delay, Block resultBlock, net.minecraft.world.item.Item dropItem, 
                         int requiredState, int targetState, IntegerProperty prop, boolean updateNeighbors) {}
        
        WeldConfig config = null;

        // === ОПРЕДЕЛЕНИЕ НАСТРОЕК ПО БЛОКУ ===
        if (targetBlock == Ssc14ModBlocks.STEEL_WALL.get()) {
            config = new WeldConfig(20, Ssc14ModBlocks.WALL_CARCASE.get(), Ssc14ModItems.STEEL.get(), -1, -1, null, true);
        }
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get()) {
            if (currentState == 2) config = new WeldConfig(13, null, null, 2, 3, stateProp, true);
            else if (currentState == 5) config = new WeldConfig(27, null, null, 5, 6, stateProp, true);
        }
        else if (targetBlock == Ssc14ModBlocks.ARMORED_WINDOW.get()) {
            if (currentState == 0) config = new WeldConfig(20, null, null, 0, 1, stateProp, true);
            else if (currentState == 3) config = new WeldConfig(27, null, null, 3, 4, stateProp, true);
        }
        // 🔧 НОВАЯ ЛОГИКА: Сварка шлюзов (по тегу ssc14:airlocks)
        else if (blockstate.is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks")))) {
            // 🔹 Проверяем, что это действительно шлюз с нужными свойствами
            if (arlProp != null) {
                BlockEntity be = world.getBlockEntity(pos);
                if (be != null) {
                    CompoundTag nbt = be.getPersistentData();
                    boolean isWelded = nbt.getBoolean("welded").orElse(false);
                    
                    // 🔹 Защита: нельзя варить, если шлюз открыт или в анимации
                    if (currentArl == 11 || (currentArl >= 12 && currentArl <= 15)) {
                        // Звук отказа (опционально)
                        playLevelSound(world, pos, ResourceLocation.parse("ssc_14:airlock_no_access"), 0.5F, 1.0F);
                        return;
                    }
                    
                    // 🔹 Определяем целевое состояние arl_variat при переключении сварки
                    int newArl = currentArl;
                    if (!isWelded) {
                        // Не заварен → завариваем
                        switch (currentArl) {
                            case 0 -> newArl = 16;  // закрытый → заваренный
                            case 2 -> newArl = 3;   // болты → болты+сварка
                            case 4 -> newArl = 5;   // аварийка → аварийка+сварка
                            case 9 -> newArl = 10;  // нет питания → нет питания+сварка
                        }
                    } else {
                        // Заварен → развариваем
                        switch (currentArl) {
                            case 16 -> newArl = 0;  // заваренный → закрытый
                            case 3 -> newArl = 2;   // болты+сварка → болты
                            case 5 -> newArl = 4;   // аварийка+сварка → аварийка
                            case 10 -> newArl = 9;  // нет питания+сварка → нет питания
                        }
                    }
                    
                    // 🔧 Создаём конфиг для сварки шлюза
                    config = new WeldConfig(25, null, null, currentArl, newArl, arlProp, true);
                }
            }
        }

        // Если блок не подходит — выходим
        if (config == null) return;

        // === ЛОКАЛЬНЫЙ КЛАСС ПРОЦЕССА ===
        class WeldProcess {
            private final LivingEntity entity;
            private final LevelAccessor world;
            private final BlockPos pos;
            private final double posHash;
            private final Block targetBlock;
            private final WeldConfig config;
            private final boolean isAirlock;

            WeldProcess(LivingEntity entity, LevelAccessor world, BlockPos pos, double posHash, Block targetBlock, WeldConfig config) {
                this.entity = entity; this.world = world; this.pos = pos; this.posHash = posHash;
                this.targetBlock = targetBlock; this.config = config;
                this.isAirlock = blockstate.is(BlockTags.create(ResourceLocation.parse("ssc14:airlocks")));
            }

            void run(int step) {
                // === ПРОВЕРКИ ОТМЕНЫ НА КАЖДОМ ШАГЕ ===
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.ACTIVE_WELDER.get())) { reset(); return; }
                
                BlockState currentBs = world.getBlockState(pos);
                if (currentBs.getBlock() != targetBlock) { reset(); return; }
                
                // Защита от повторов для шлюзов
                if (isAirlock && config.prop() != null) {
                    int currentVal = currentBs.getValue(config.prop());
                    if (currentVal != config.requiredState()) { reset(); return; }
                }

                // === ВИЗУАЛЬНЫЕ ЭФФЕКТЫ ===
                if (world instanceof ServerLevel sl) {
                    sl.sendParticles(Ssc14ModParticleTypes.SPARK.get(), 
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        20, 0.2, 0.2, 0.2, 0.2);
                }
                
                ResourceLocation sfx = ResourceLocation.parse("ssc_14:welding_work");
                float pitch = 0.8F + (world instanceof Level l ? l.random : RandomSource.create()).nextFloat() * 0.4F;
                playLevelSound(world, pos, sfx, 0.2F, pitch);

                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < 6) {
                    Ssc14Mod.queueServerWork(config.delay(), () -> run(step + 1));
                } else {
                    Ssc14Mod.queueServerWork(config.delay(), this::executeFinal);
                }
            }

            private void executeFinal() {
                // Финальные проверки
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.ACTIVE_WELDER.get())) { reset(); return; }
                
                BlockState currentBs = world.getBlockState(pos);
                if (currentBs.getBlock() != targetBlock) { reset(); return; }
                if (isAirlock && config.prop() != null) {
                    int currentVal = currentBs.getValue(config.prop());
                    if (currentVal != config.requiredState()) { reset(); return; }
                }

                // === ФИНАЛЬНОЕ ДЕЙСТВИЕ ===
                ItemStack stack = livingEntity.getMainHandItem();
                double fuel = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                    .copyTag().getDoubleOr("fuel", 0);

                if (fuel > 450) {
                    playLevelSound(world, pos, ResourceLocation.parse("ssc_14:welder_use"), 1.0F, 1.0F);
                
                    BlockState oldState = world.getBlockState(pos);
                    BlockState newState;
                    
                    if (config.resultBlock() != null) {
                        newState = config.resultBlock().defaultBlockState();
                    } else if (config.prop() != null) {
                        IntegerProperty p = findIntegerProperty(oldState, isAirlock ? "arl_variat" : "blockstate");
                        newState = (p != null) ? oldState.setValue(p, config.targetState()) : oldState;
                    } else {
                        newState = oldState;
                    }
                    
                    world.setBlock(pos, newState, 3);
                
                    // 🔧 ДЛЯ ШЛЮЗОВ: переключаем NBT welded + визуальная синхронизация
                    if (isAirlock) {
                        BlockEntity be = world.getBlockEntity(pos);
                        if (be != null) {
                            CompoundTag nbt = be.getPersistentData();
                            boolean wasWelded = nbt.getBoolean("welded").orElse(false);
                            nbt.putBoolean("welded", !wasWelded); // 🔁 Переключаем
                            
                            // 🔧 Принудительная синхронизация с клиентом (как в контроллере)
                            be.setChanged();
                            if (world instanceof Level lvl) {
                                lvl.setBlock(pos, newState, 11); // Флаг 11 = полная синхронизация
                                lvl.sendBlockUpdated(pos, oldState, newState, 11);
                                
                                // 🔧 Вызываем PutProcedure для обновления заглушки и полной синхронизации
                                BaseAirlockD1PutProcedure.execute(lvl, pos.getX(), pos.getY(), pos.getZ(), newState);
                            }
                        }
                    }
                
                    if (config.dropItem() != null && world instanceof ServerLevel sLevel) {
                        ItemEntity drop = new ItemEntity(sLevel, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 
                            new ItemStack(config.dropItem()));
                        drop.setPickUpDelay(1);
                        drop.setUnlimitedLifetime();
                        sLevel.addFreshEntity(drop);
                    }
                
                    CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("fuel", fuel - 350));
                
                    if (config.updateNeighbors() && world instanceof Level lvl && !lvl.isClientSide()) {
                        lvl.sendBlockUpdated(pos, oldState, newState, 3);
                        for (net.minecraft.core.Direction dir : net.minecraft.core.Direction.values()) {
                            BlockPos neighborPos = pos.relative(dir);
                            BlockState neighborState = lvl.getBlockState(neighborPos);
                            lvl.sendBlockUpdated(neighborPos, neighborState, neighborState, 3);
                            lvl.updateNeighborsAt(neighborPos, neighborState.getBlock());
                        }
                        lvl.updateNeighborsAt(pos, newState.getBlock());
                    }
                }
                reset();
            }

            void reset() {
                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
            }
        }

        new WeldProcess(livingEntity, world, pos, posHash, targetBlock, config).run(1);
    }

    // ============================================================================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    // ============================================================================

    private static IntegerProperty findIntegerProperty(BlockState state, String name) {
        for (Property<?> prop : state.getProperties()) {
            if (prop.getName().equals(name) && prop instanceof IntegerProperty ip) return ip;
        }
        return null;
    }

    private static void playLevelSound(LevelAccessor world, BlockPos pos, ResourceLocation soundLoc, float volume, float pitch) {
        if (world instanceof Level lvl) {
            net.minecraft.sounds.SoundEvent sound = BuiltInRegistries.SOUND_EVENT.getValue(soundLoc);
            if (sound != null) {
                if (!lvl.isClientSide()) 
                    lvl.playSound(null, pos, sound, SoundSource.NEUTRAL, volume, pitch);
                else 
                    lvl.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.NEUTRAL, volume, pitch, false);
            }
        }
    }
}
