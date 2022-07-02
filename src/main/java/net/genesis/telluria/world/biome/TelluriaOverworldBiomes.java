package net.genesis.telluria.world.biome;

import net.genesis.telluria.sound.TelluriaSounds;
import net.genesis.telluria.world.feature.TelluriaConfiguredFeatures;
import net.genesis.telluria.world.feature.TelluriaPlacedFeatures;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class TelluriaOverworldBiomes
{
    @Nullable
    private static final Music NORMAL_MUSIC = null;

    protected static int calculateSkyColor(float color)
    {
        float $$1 = color / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

    private static Biome biome(Biome.Precipitation precipitation, float temperature, float downfall, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music)
    {
        return biome(precipitation, temperature, downfall, 4159204, 329011, spawnBuilder, biomeBuilder, music);
    }

    private static Biome biome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music)
    {
        return (new Biome.BiomeBuilder()).precipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();
    }

    private static Biome biome(Biome.Precipitation precipitation, float temperature, float downfall, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, BiomeSpecialEffects.Builder biomeEffects)
    {
        return (new Biome.BiomeBuilder()).precipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects(biomeEffects.build()).mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder)
    {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome saltmarsh()
    {
        //Biome Settings
        Biome.Precipitation precipitation = Biome.Precipitation.RAIN;
        float temperature = 0.7F;
        float downfall = 0.57F; 
        Music music = NORMAL_MUSIC;

        //Mob Spawns Builder
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        //Special Effects Builder
        BiomeSpecialEffects.Builder effectsBuilder = new BiomeSpecialEffects.Builder();
        effectsBuilder.ambientLoopSound(TelluriaSounds.SALT_MARSH_AMBIENCE_1.get());
        effectsBuilder.waterColor(4159204);
        effectsBuilder.waterFogColor(329011);
        effectsBuilder.fogColor(12638463);
        effectsBuilder.skyColor(calculateSkyColor(temperature));
        effectsBuilder.ambientParticle(new AmbientParticleSettings(ParticleTypes.ASH, 0.001f));

        //Biome Generation Builder
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        
        BiomeDefaultFeatures.addFossilDecoration(biomeBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        BiomeDefaultFeatures.addDefaultFlowers(biomeBuilder);
        BiomeDefaultFeatures.addDefaultGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addSwampExtraVegetation(biomeBuilder);

        //RAW GENERATION
        

        //LAKES


        //LOCAL MODIFICATIONS

    
        //UNDERGROUND STRUCTURES
        

        //SURFACE STRUCTURES


        //STRONGHOLDS


        //UNDERGROUND ORES
        

        //UNDERGROUND DECORATION


        //FLUID SPRINGS


        //VEGETAL DECORATION

        biomeBuilder.addFeature(Decoration.VEGETAL_DECORATION, TelluriaPlacedFeatures.WILLOW_PLACED);
        biomeBuilder.addFeature(Decoration.VEGETAL_DECORATION, TelluriaPlacedFeatures.CATTAILS_PLACED);
        biomeBuilder.addFeature(Decoration.VEGETAL_DECORATION, TelluriaPlacedFeatures.BULRUSH_PLACED);
        biomeBuilder.addFeature(Decoration.VEGETAL_DECORATION, TelluriaPlacedFeatures.BULRUSH_PLACED_SECONDARY);
        biomeBuilder.addFeature(Decoration.VEGETAL_DECORATION, TelluriaPlacedFeatures.PATCH_REEDS_PLACED);
        //TOP LEVL MODIFIACTION

        return biome(
            precipitation, 
            temperature, 
            downfall, 
            spawnBuilder, 
            biomeBuilder, 
            effectsBuilder
            );
    }
}