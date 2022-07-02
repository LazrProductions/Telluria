package net.genesis.telluria.client.particle;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.client.particle.custom.FliesParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TelluriaParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TelluriaMod.MOD_ID);

    //public static final RegistryObject<SimpleParticleType> FLIES_PARTICLES = register("flies_particles", new SimpleParticleType(true));

    private static <T extends SimpleParticleType> RegistryObject<T> register(String name, T particleType) {
        return PARTICLE_TYPES.register(name, () -> particleType);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerParticleTypes(ParticleFactoryRegisterEvent event) {
        //Minecraft.getInstance().particleEngine.register(TelluriaParticles.FLIES_PARTICLES.get(), FliesParticles.Factory::new);
    }
}
