
package net.mcreator.ssc.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

public class Decal1Entity extends Monster {
    
    public BlockPos attachedPos = BlockPos.ZERO;

    public Decal1Entity(EntityType<Decal1Entity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(true);
        setPersistenceRequired();
        this.noPhysics = true;
    }

    // === NBT: ПРОСТОЙ МЕТОД, БЕЗ @Override, БЕЗ super ===
    // Это НЕ переопределение, а дополнительный метод, который MCreator вызывает сам
    public void addAdditionalSaveData(CompoundTag compound) {
        if (attachedPos != null) {
            compound.putLong("AttachedPos", attachedPos.asLong());
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("AttachedPos")) {
            attachedPos = BlockPos.of(compound.getLong("AttachedPos").orElse(0L));
        }
    }

    public BlockPos getAttachedPos() { return attachedPos; }
    public void setAttachedPos(BlockPos pos) { this.attachedPos = pos; }

    @Override public boolean removeWhenFarAway(double d) { return false; }
    @Override public boolean isPushable() { return false; }
    @Override public boolean isPushedByFluid() { return false; }
    @Override protected void doPush(Entity e) {}
    @Override protected void pushEntities() {}
	@Override public void tick() {
	    super.tick(); // Если у Monster есть логика в tick()
	    
	    // 🔑 Блокируем вращение: декаль всегда смотрит "вверх" и не крутится
	    this.setYRot(0.0f);
	    this.setXRot(0.0f);
	    this.yHeadRot = 0.0f;
	    this.yBodyRot = 0.0f;
    }

    public static void init(RegisterSpawnPlacementsEvent event) {}
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MOVEMENT_SPEED, 0)
            .add(Attributes.MAX_HEALTH, 1)
            .add(Attributes.ARMOR, 0)
            .add(Attributes.ATTACK_DAMAGE, 0);
    }
}
