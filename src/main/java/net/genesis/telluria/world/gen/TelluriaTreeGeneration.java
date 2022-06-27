package net.genesis.telluria.world.gen;

// import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
// import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.genesis.telluria.TelluriaMod;
// import net.genesis.telluria.world.feature.TelluriaPlacedFeatures;
// import net.minecraft.world.gen.GenerationStep;

public class TelluriaTreeGeneration {
    public static void generateTrees() {
        TelluriaMod.LOGGER.info("Registering Telluria tree generation.");

        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(TelluriaBiomes.SALTMARSH), 
        //    GenerationStep.Feature.VEGETAL_DECORATION, TelluriaPlacedFeatures.WILLOWPLACED.getKey().get());
    }
}
