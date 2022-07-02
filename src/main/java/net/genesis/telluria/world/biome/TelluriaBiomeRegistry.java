package net.genesis.telluria.world.biome;

import net.genesis.telluria.TelluriaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TelluriaBiomeRegistry {
    @SubscribeEvent
    public static void registerBiomes(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BIOMES, 
            helper -> {
                ResourceLocation location = new ResourceLocation(TelluriaMod.MOD_ID, "salt_marsh");
                helper.register(location, TelluriaOverworldBiomes.saltmarsh());
                TelluriaMod.LOGGER.info("Register Biomes for: " + location.toString());
            }
        );
    }
}
