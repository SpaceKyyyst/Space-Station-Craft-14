
package net.mcreator.ssc.client.particle;

import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import net.mcreator.ssc.procedures.ExtinguisherSprayVizualnyiMasshtabChastitsyProcedure;

public class ExtinguisherSprayParticle extends SingleQuadParticle {
	public static Vec3 lastVelocity = Vec3.ZERO;
	private final SpriteSet spriteSet;

	public static ExtinguisherSprayParticleProvider provider(SpriteSet spriteSet) {
		return new ExtinguisherSprayParticleProvider(spriteSet);
	}

	public static class ExtinguisherSprayParticleProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public ExtinguisherSprayParticleProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		// ИСПРАВЛЕНО: Добавлен обязательный параметр RandomSource
		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return new ExtinguisherSprayParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet, random);
		}
	}

	// ИСПРАВЛЕНО: Передаем random из фабрики в конструктор
	protected ExtinguisherSprayParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet, RandomSource random) {
		super(world, Math.round(x), Math.round(y), Math.round(z), spriteSet.first());
		this.spriteSet = spriteSet;
		this.setSize(0.5f, 0.5f);
		
		// ИСПРАВЛЕНО: Используем переданный random вместо удаленного поля класса
		this.lifetime = (int) Math.max(1, 30 + (random.nextInt(20) - 10));
		this.gravity = 0f;
		this.hasPhysics = true;
		
		// Логика смещения и физики огнетушителя
		this.xd = (x - Math.round(x)) / 0.01;
		this.yd = (y - Math.round(y)) / 0.01;
		this.zd = (z - Math.round(z)) / 0.01;
		
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public int getLightCoords(float partialTick) {
		return 15728880;
	}

	// ИСПРАВЛЕНО: Вместо getRenderType используем getLayer
	@Override
	public SingleQuadParticle.Layer getLayer() {
		return SingleQuadParticle.Layer.TRANSLUCENT;
	}

	// ИСПРАВЛЕНО: Метод изменения масштаба под маппинги 1.21.4
	@Override
	public float getQuadSize(float partialTick) {
		return super.getQuadSize(partialTick) * (float) ExtinguisherSprayVizualnyiMasshtabChastitsyProcedure.execute(this.age);
	}

	@Override
	public void tick() {
		super.tick();
		this.xd *= 0.93; 
		this.yd *= 0.93; 
		this.zd *= 0.93;
		
		if (!this.removed) {
			int frame = (this.age * 4) / this.lifetime;
			this.setSprite(this.spriteSet.get(Math.min(frame, 3), 4));
		}
	}
}
