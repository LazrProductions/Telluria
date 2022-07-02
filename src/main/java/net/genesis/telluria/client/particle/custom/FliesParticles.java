package net.genesis.telluria.client.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class FliesParticles extends TextureSheetParticle {
    private final SpriteSet sprites;
    
    protected FliesParticles(ClientLevel pLevel, double pX, double pY, double pZ, float pXSeedMultiplier, float pYSpeedMultiplier, float pZSpeedMultiplier, double pXSpeed, double pYSpeed, double pZSpeed, float pQuadSizeMultiplier, SpriteSet pSprites, float pRColMultiplier, int pLifetime, float pGravity, boolean pHasPhysics) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);

        this.friction = 0.96F;
        this.gravity = pGravity;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = pSprites;
        this.xd *= (double)pXSeedMultiplier;
        this.yd *= (double)pYSpeedMultiplier;
        this.zd *= (double)pZSpeedMultiplier;
        this.xd += pXSpeed;
        this.yd += pYSpeed;
        this.zd += pZSpeed;
        float f = pLevel.random.nextFloat() * pRColMultiplier;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize *= 0.75F * pQuadSizeMultiplier;
        this.lifetime = (int)((double)pLifetime / ((double)pLevel.random.nextFloat() * 0.8D + 0.2D) * (double)pQuadSizeMultiplier);
        this.lifetime = Math.max(this.lifetime, 1);
        this.setSpriteFromAge(pSprites);
        this.hasPhysics = pHasPhysics;
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
     }
  
     public float getQuadSize(float pScaleFactor) {
        return this.quadSize * Mth.clamp(((float)this.age + pScaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
     }
  
     @Override
     public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
     }


    public static class Factory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double r, double g, double b) {
            FliesParticles fliesParticle = new FliesParticles(worldIn, b, b, b, 1, 1, 1, 1, 1, 1, 1, spriteSet, 1, 1, 0.4f, true);
            fliesParticle.setColor((float)r, (float)g, (float)b);
            fliesParticle.pickSprite(this.spriteSet);
            return fliesParticle;
        }
    }
}
