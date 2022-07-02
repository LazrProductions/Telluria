package net.genesis.telluria.world.feature.util;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.world.feature.util.custom.BulrushFeature;
import net.genesis.telluria.world.feature.util.custom.CattailsFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class TelluriaFeatureTypes {
    public static final Feature<SimpleBlockConfiguration> COMPLEX_BLOCK = new ComplexBlockFeature(SimpleBlockConfiguration.CODEC);
    public static final CattailsFeature CATTAILS_FEATURE = new CattailsFeature(ProbabilityFeatureConfiguration.CODEC);
    public static final BulrushFeature BULRUSH_FEATURE = new BulrushFeature(ProbabilityFeatureConfiguration.CODEC);

      @SubscribeEvent
      public static void registerBiomes(RegisterEvent event) {
          event.register(ForgeRegistries.Keys.FEATURES, 
              helper -> {
                  helper.register(new ResourceLocation(TelluriaMod.MOD_ID, "complex_block"), COMPLEX_BLOCK);
                  helper.register(new ResourceLocation(TelluriaMod.MOD_ID, "cattails_feature"), CATTAILS_FEATURE);
                  helper.register(new ResourceLocation(TelluriaMod.MOD_ID, "bulrush_feature"), BULRUSH_FEATURE);
                  TelluriaMod.LOGGER.info("Registering Feature Types for Telluria");
              }
          );
      }
   
  }

