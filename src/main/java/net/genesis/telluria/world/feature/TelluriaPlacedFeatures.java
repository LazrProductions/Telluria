package net.genesis.telluria.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class TelluriaPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> WILLOWPLACED = PlacedFeatures.register("willow_placed",
        TelluriaConfiguredFeatures.WILLOWSPAWN, VegetationPlacedFeatures.modifiers(
            PlacedFeatures.createCountExtraModifier(1, 0.1f, 2)));
}
