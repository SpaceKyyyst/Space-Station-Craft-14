
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block; // ← ВАЖНО: добавлен импорт
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;
import net.mcreator.ssc.init.Ssc14ModEntities;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.init.Ssc14ModAttributes;
import net.mcreator.ssc.Ssc14Mod;

public class Spanner_UseProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
        // === БАЗОВЫЕ ПРОВЕРКИ ===
        if (entity == null || !(entity instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.getAttributes().hasAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB)) return;
        if (!livingEntity.getMainHandItem().is(Ssc14ModItems.SPANNER.get())) return;

        BlockPos pos = BlockPos.containing(x, y, z);
        double posHash = entity.getX() + entity.getY() + entity.getZ();
        Block targetBlock = blockstate.getBlock();
        IntegerProperty ip_blockstate = findIntProp(blockstate, "blockstate");
        IntegerProperty ip_window = findIntProp(blockstate, "window_disassembly");
        int bsVal = ip_blockstate != null ? blockstate.getValue(ip_blockstate) : -1;

        // === КОНФИГУРАЦИЯ (Java 21 record) ===
        record SpannerConfig(int[] delays, Block target, Runnable finalAction) {}
        final SpannerConfig config;

        // === ОПРЕДЕЛЕНИЕ НАСТРОЕК ПО БЛОКУ ===
        if (targetBlock == Ssc14ModBlocks.WALL_CARCASE.get()) {
            config = new SpannerConfig(new int[]{2,2,2,2,2,2}, targetBlock, 
                () -> spawnEntityAndBreak(world, pos, x, y, z, Ssc14ModEntities.WALL_CARCASE_ENTIT.get()));
        } 
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get()) {
            config = new SpannerConfig(new int[]{2,2,2,2,2,2}, targetBlock, 
                () -> spawnEntityAndBreak(world, pos, x, y, z, Ssc14ModEntities.PLASSTEEL_WALL_CARCASE_ENTIT.get()));
        } 
        else if (targetBlock == Ssc14ModBlocks.CONSOLE_OF_ID.get()) {
            config = new SpannerConfig(new int[]{2,2,2,2,2,2}, targetBlock, 
                () -> spawnEntityAndBreak(world, pos, x, y, z, Ssc14ModEntities.ID_CONSOLE_ENTITY.get()));
        } 
        else if (targetBlock == Ssc14ModBlocks.BASE_WINDOW.get() && ip_window != null && blockstate.getValue(ip_window) == 1) {
            config = new SpannerConfig(new int[]{10,10,10,10,10,10}, targetBlock, () -> {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                dropItem(world, x, y, z, Ssc14ModItems.GLASS.get(), 2);
            });
        } 
        else if (targetBlock == Ssc14ModBlocks.PLASTEEL_WALL.get() && bsVal == 4) {
            config = new SpannerConfig(new int[]{3,3,3,3,3,3}, targetBlock, () -> {
                BlockState bs = world.getBlockState(pos);
                if (bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty p) {
                    world.setBlock(pos, bs.setValue(p, 5), 3);
                }
            });
        } 
        else if (targetBlock == Ssc14ModBlocks.ARMORED_WINDOW.get() && bsVal == 5) {
            config = new SpannerConfig(new int[]{10,10,10,10,10,10}, targetBlock, () -> {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                dropItem(world, x, y, z, Ssc14ModItems.ARM_GLASS.get(), 2);
            });
        } 
        else { return; } // Блок не подходит

        // === ЛОКАЛЬНЫЙ КЛАСС ПРОЦЕССА (внутри execute, БЕЗ static!) ===
        class SpannerProcess {
            void run(int step) {
                // 1. Отмена при движении
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                // 2. Отмена при смене предмета
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.SPANNER.get())) { reset(); return; }
                // 3. Отмена при изменении/удалении блока
                if (world.getBlockState(pos).getBlock() != config.target()) { reset(); return; }

                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(step);

                if (step < 6) {
                    Ssc14Mod.queueServerWork(config.delays()[step - 1], () -> run(step + 1));
                } else {
                    Ssc14Mod.queueServerWork(config.delays()[5], this::executeFinal);
                }
            }

            private void executeFinal() {
                // Финальные проверки перед выполнением
                if (entity.getX() + entity.getY() + entity.getZ() != posHash) { reset(); return; }
                if (!livingEntity.getMainHandItem().is(Ssc14ModItems.SPANNER.get())) { reset(); return; }
                if (world.getBlockState(pos).getBlock() != config.target()) { reset(); return; }

                // Выполняем финальное действие
                config.finalAction().run();
                playSound(world, pos, "ssc_14:spanner_use", 1.0F, 1.0F);
                reset();
            }

            void reset() {
                livingEntity.getAttribute(Ssc14ModAttributes.PROGRESS_BAR_ATRB).setBaseValue(0);
            }
        }

        // Запуск процесса
        new SpannerProcess().run(1);
    }

    // ============================================================================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ (вне execute, но внутри класса)
    // ============================================================================

    /** Поиск целочисленного свойства блока */
    private static IntegerProperty findIntProp(BlockState state, String name) {
        for (Property<?> p : state.getProperties()) {
            if (p.getName().equals(name) && p instanceof IntegerProperty ip) return ip;
        } return null;
    }

    /** Безопасный спавн сущности и замена блока на воздух */
    private static void spawnEntityAndBreak(LevelAccessor world, BlockPos pos, double x, double y, double z, EntityType<?> type) {
        if (world instanceof ServerLevel sLevel) {
            Entity spawned = type.spawn(sLevel, BlockPos.containing(x + 0.5, y + 0.5, z + 0.5), EntitySpawnReason.MOB_SUMMONED);
            if (spawned != null) spawned.setDeltaMovement(0, 0, 0);
        }
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }

    /** Дроп предмета с задержкой подбора и бессмертием */
    private static void dropItem(LevelAccessor world, double x, double y, double z, net.minecraft.world.item.Item item, int pickDelay) {
        if (world instanceof ServerLevel sLevel) {
            ItemEntity drop = new ItemEntity(sLevel, x + 0.5, y + 0.5, z + 0.5, new ItemStack(item));
            drop.setPickUpDelay(pickDelay);
            drop.setUnlimitedLifetime();
            sLevel.addFreshEntity(drop);
        }
    }

    /** Универсальное проигрывание звука (сервер/клиент) */
    private static void playSound(LevelAccessor world, BlockPos pos, String soundId, float volume, float pitch) {
        if (world instanceof Level lvl) {
            var sound = BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse(soundId));
            if (sound != null) {
                if (!lvl.isClientSide()) lvl.playSound(null, pos, sound, SoundSource.NEUTRAL, volume, pitch);
                else lvl.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.NEUTRAL, volume, pitch, false);
            }
        }
    }
}
