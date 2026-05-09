
package net.mcreator.ssc.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import java.util.UUID;

public class CorpseEntity extends Monster {
    // 🔧 Данные для реанимации (хранятся в памяти сессии)
    private UUID originalPlayerUUID;
    private double corpseTotalDamage;

    public CorpseEntity(EntityType<CorpseEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(true);              // Отключаем ИИ
        setPersistenceRequired();   // Не исчезает сам по себе
        setInvulnerable(true);      // Нельзя убить обычным уроном
        setPose(Pose.SWIMMING);     // Поза лежания
        refreshDimensions();        // 🔹 Применяем размеры сразу
    }

    // 🔧 Установка данных при создании тела
    public void setOriginalPlayerData(UUID playerUUID, double totalDamage) {
        this.originalPlayerUUID = playerUUID;
        this.corpseTotalDamage = totalDamage;
    }

    public UUID getOriginalPlayerUUID() { return originalPlayerUUID; }
    public double getCorpseTotalDamage() { return corpseTotalDamage; }

    @Override
    public void aiStep() {
        super.aiStep();
        
        // 🔧 Фиксируем позу, чтобы ваниль не сбрасывала её
        if (this.getPose() != Pose.SWIMMING) {
            this.setPose(Pose.SWIMMING);
        }
        
        // 🔧 Минимальное трение, но оставляем возможность толкать
        if (this.onGround()) {
            this.setDeltaMovement(
                this.getDeltaMovement().x * 0.95, 
                this.getDeltaMovement().y, 
                this.getDeltaMovement().z * 0.95
            );
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false; // Не исчезает, даже если игрок далеко
    }

    @Override
    public boolean isPushable() {
        return true; // Труп можно толкать
    }

    @Override
    public boolean isPushedByFluid() {
        return true; // Вода/лава могут сносить тело
    }

    // 🔧 Заглушки для MCreator (не трогаем)
    public static void init(RegisterSpawnPlacementsEvent event) {}
    
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0); // Сам не двигается
    }
}
