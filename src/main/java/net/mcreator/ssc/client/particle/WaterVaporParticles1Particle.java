package net.mcreator.ssc.client.particle;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;

public class WaterVaporParticles1Particle extends TextureSheetParticle {
	public static WaterVaporParticles1ParticleProvider provider(SpriteSet spriteSet) {
		return new WaterVaporParticles1ParticleProvider(spriteSet);
	}

	public static class WaterVaporParticles1ParticleProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public WaterVaporParticles1ParticleProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new WaterVaporParticles1Particle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}

	private final SpriteSet spriteSet;

	protected WaterVaporParticles1Particle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
		super(world, x, y, z);
		this.spriteSet = spriteSet;
		this.setSize(1.5f, 1.5f);
		this.quadSize *= 3f;
		this.lifetime = (int) Math.max(1, 120 + (this.random.nextInt(40) - 20));
		this.gravity = 0f;
		this.hasPhysics = true;
		this.xd = vx * 0.1;
		this.yd = vy * 0.1;
		this.zd = vz * 0.1;
		this.pickSprite(spriteSet);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();
	}
}