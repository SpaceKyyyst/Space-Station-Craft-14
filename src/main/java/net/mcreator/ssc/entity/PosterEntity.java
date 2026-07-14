
package net.mcreator.ssc.entity;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class PosterEntity extends Monster {
    private static final EntityDataAccessor<String> POSTER_TYPE = SynchedEntityData.defineId(PosterEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> FACING = SynchedEntityData.defineId(PosterEntity.class, EntityDataSerializers.INT);

    public PosterEntity(EntityType<PosterEntity> type, Level world) {
        super(type, world);
        this.xpReward = 0;
        this.setNoAi(true); 
        this.setPersistenceRequired(); 
        this.noPhysics = true; 
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(POSTER_TYPE, "poster_random_anything");
        builder.define(FACING, Direction.NORTH.get3DDataValue());
    }

    public String getPosterType() {
        return this.entityData.get(POSTER_TYPE);
    }

    public void setPosterType(String type) {
        this.entityData.set(POSTER_TYPE, type);
    }

    public Direction getFacingDirection() {
        return Direction.from3DDataValue(this.entityData.get(FACING));
    }

    public void setFacingDirection(Direction direction) {
        this.entityData.set(FACING, direction.get3DDataValue());
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(Vec3.ZERO); 
    }

    @Override
    public boolean isPickable() {
        return this.isAlive(); 
    }

    // ИСПРАВЛЕННАЯ ЛОГИКА ПРОЧНОСТИ ДЛЯ 1.21.8
    @Override
    public boolean hurtServer(ServerLevel serverLevel, net.minecraft.world.damagesource.DamageSource source, float amount) {
        if (source.getEntity() instanceof Player player) {
            // Креатив (Маппер): Ваншот без дропа
            if (player.getAbilities().instabuild) {
                this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
                this.discard();
                return true;
            }
            
            // Выживание: Снимаем здоровье постера
            this.playSound(SoundEvents.WOOL_HIT, 0.8F, 1.5F); // Звук шлепка по бумаге/ткани при ударе
            
            // Передаем урон базовому классу, чтобы он уменьшил здоровье (HP) сущности
            boolean hurtResult = super.hurtServer(serverLevel, source, amount);
            
            // Если здоровье кончилось — постер окончательно рвётся
            if (!this.isAlive()) {
                this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
                this.discard();
            }
            return hurtResult;
        }
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) { return false; }
    @Override
    public boolean isPushable() { return false; }
    @Override
    protected void doPush(Entity entityIn) {}
    @Override
    protected void pushEntities() {}

    public static void init(RegisterSpawnPlacementsEvent event) {}

    // НАСТРОЙКА ХП: Выставляем максимальное здоровье постера
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.0);
        builder = builder.add(Attributes.MAX_HEALTH, 5.0); // 5 единиц здоровья (кулак наносит 1 единицу урона)
        return builder;
    }
}
