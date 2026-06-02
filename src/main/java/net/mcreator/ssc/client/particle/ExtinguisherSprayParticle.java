
package net.mcreator.ssc.client.particle;

import net.minecraft.world.level.Level;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;

import net.mcreator.ssc.procedures.ExtinguisherSprayVizualnyiMasshtabChastitsyProcedure;

public class ExtinguisherSprayParticle extends TextureSheetParticle {
	// В начале класса, перед методами:
	public static net.minecraft.world.phys.Vec3 lastVelocity = net.minecraft.world.phys.Vec3.ZERO;
	
	public static ExtinguisherSprayParticleProvider provider(SpriteSet spriteSet) {
		return new ExtinguisherSprayParticleProvider(spriteSet);
	}

	public static class ExtinguisherSprayParticleProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public ExtinguisherSprayParticleProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new ExtinguisherSprayParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}

	private final SpriteSet spriteSet;

	protected ExtinguisherSprayParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
	    super(world, x, y, z);
	    this.spriteSet = spriteSet;
	    this.setSize(0.5f, 0.5f);
	    this.lifetime = (int) Math.max(1, 30 + (this.random.nextInt(20) - 10));
	    this.gravity = 0f;
	    this.hasPhysics = true;
	    
	    // 🛠 КОДЕК: Декодируем скорость из дробной части
	    // 0.007 / 0.01 = 0.7 ← точная скорость
	    this.xd = (x - Math.round(x)) / 0.01;
	    this.yd = (y - Math.round(y)) / 0.01;
	    this.zd = (z - Math.round(z)) / 0.01;
	    
	    // Возвращаем позицию к целой точке спавна
	    this.setPos(Math.round(x), Math.round(y), Math.round(z));
	    
	    this.setSpriteFromAge(spriteSet);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public float getQuadSize(float scale) {
		Level world = this.level;
		return super.getQuadSize(scale) * (float) ExtinguisherSprayVizualnyiMasshtabChastitsyProcedure.execute(age);
	}

	@Override
	public void tick() {
	    super.tick();
	    
	    // Плавное затухание (остановка ~на 3 блоках)
	    this.xd *= 0.93;
	    this.yd *= 0.93;
	    this.zd *= 0.93;
	    
	    if (!this.removed) {
	        // Анимация: 4 кадра, ровно 1 раз за lifetime
	        int frame = (this.age * 4) / this.lifetime;
	        this.setSprite(this.spriteSet.get(Math.min(frame, 3), 4));
	    }
	}
}
