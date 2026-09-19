
package net.mcreator.ssc.client.particle;

import org.joml.Quaternionf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.Camera;
import net.mcreator.ssc.procedures.DeconstructPZORIENTProcedure;

public class DeconstructPZParticle extends SingleQuadParticle {
	public static DeconstructPZParticleProvider provider(SpriteSet spriteSet) {
		return new DeconstructPZParticleProvider(spriteSet);
	}

	public static class DeconstructPZParticleProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;
		public DeconstructPZParticleProvider(SpriteSet spriteSet) { this.spriteSet = spriteSet; }

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return new DeconstructPZParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}

	private final SpriteSet spriteSet;

	protected DeconstructPZParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
		super(world, x, y, z, spriteSet.first());
		this.spriteSet = spriteSet;
		this.setSize(1f, 1f);
		this.quadSize = 0.505f; // Восстановлен оригинальный размер
		this.lifetime = 160;
		this.gravity = 0f;
		this.hasPhysics = false;
		this.xd = 0; this.yd = 0; this.zd = 0;
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public int getLightCoords(float partialTick) { return 15728880; }

	@Override
	public SingleQuadParticle.Layer getLayer() { return SingleQuadParticle.Layer.TRANSLUCENT; }

	// ИСПРАВЛЕНО: Новый синтаксис 1.21.4 из твоей пустышки!
	@Override
	public void extract(QuadParticleRenderState particleTypeRenderState, Camera camera, float partialTicks) {
		Vec3 vec = DeconstructPZORIENTProcedure.execute(); // ИСПРАВЛЕНО: убраны аргументы
		Quaternionf tilt = new Quaternionf().rotationXYZ((float) vec.x(), (float) vec.y(), (float) vec.z());
		this.extractRotatedQuad(particleTypeRenderState, camera, tilt, partialTicks);
		
		Quaternionf flippedTilt = new Quaternionf(tilt).mul(new Quaternionf().rotateY((float) Math.PI));
		this.extractRotatedQuad(particleTypeRenderState, camera, flippedTilt, partialTicks);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.removed) {
			this.setSprite(this.spriteSet.get((this.age / 3) % 52 + 1, 52));
		}
	}
}
