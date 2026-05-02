
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

@EventBusSubscriber
public class ClosetEntityTICcheckProcedure {

    private static final String LOCKED_TAG = "ssc14:lockedInCloset";
    // Кэшируем TagKey один раз при загрузке класса (не в тике!)
    private static final TagKey<Block> CLOSET_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.parse("ssc14:closets"));

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        
        // 🛑 Работаем только на сервере
        if (entity.level().isClientSide()) return;

        // ✅ Проверяем наличие тега "запертости"
        if (!entity.getPersistentData().contains(LOCKED_TAG)) {
            return;
        }

        BlockPos entityPos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ());
        BlockState blockState = entity.level().getBlockState(entityPos);

        // Проверяем: стоит ли сущность внутри ЗАКРЫТОГО шкафа?
        boolean stillLocked = blockState.is(CLOSET_TAG) && isClosetClosed(blockState);

        if (!stillLocked) {
            // Сущность покинула шкаф или шкаф открылся — снимаем эффекты
            entity.getPersistentData().remove(LOCKED_TAG);
            entity.setInvisible(false);

            // Сброс физики заморозки
            entity.setDeltaMovement(Vec3.ZERO);
            entity.fallDistance = 0;
            entity.setOnGround(true);
        }
    }

    // Безопасная проверка свойства "open"
    private static boolean isClosetClosed(BlockState state) {
        var prop = state.getBlock().getStateDefinition().getProperty("open");
        return prop instanceof BooleanProperty openProp && !state.getValue(openProp);
    }
}
