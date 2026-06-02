package net.mcreator.ssc.client.particle;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;

public class FreonParticles4Particle extends TextureSheetParticle {
	public static FreonParticles4ParticleProvider provider(SpriteSet spriteSet) {
		return new FreonParticles4ParticleProvider(spriteSet);
	}

	public static class FreonParticles4ParticleProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public FreonParticles4ParticleProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new FreonParticles4Particle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}

	private final SpriteSet spriteSet;

	protected FreonParticles4Particle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
		super(world, x, y, z);
		this.spriteSet = spriteSet;
		this.setSize(1f, 1f);
		this.quadSize *= 4f;
		this.lifetime = (int) Math.max(1, 60 + (this.random.nextInt(20) - 10));
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