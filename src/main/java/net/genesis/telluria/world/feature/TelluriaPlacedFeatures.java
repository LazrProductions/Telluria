package net.genesis.telluria.world.feature;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class TelluriaPlacedFeatures {
    //Willow placed feature
    public static final Holder<PlacedFeature> WILLOW_PLACED = PlacementUtils.register("willow_placed", 
            TelluriaConfiguredFeatures.WILLOW_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(0 /* base # of trees every chunk */, 0.1f /* chance that extra trees spawn */, 1 /* amount of extra trees to spawn */)));

    //Bulrush placed feature
    public static final Holder<PlacedFeature> BULRUSH_PATCH_PLACED = PlacementUtils.register("bulrush_patch_placed", 
            TelluriaConfiguredFeatures.PATCH_BULRUSH, BiomeFilter.biome(),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()); 

    public static final Holder<PlacedFeature> CATTAILS_PLACED = PlacementUtils.register("cattails_placed",
                    TelluriaConfiguredFeatures.CATTAILS_INDIVIDUAL, seagrassPlacement(100));

    public static final Holder<PlacedFeature> BULRUSH_PLACED = PlacementUtils.register("bulrush_placed",
                    TelluriaConfiguredFeatures.BULRUSH_INDIVIDUAL, seagrassPlacement(100));

    public static final Holder<PlacedFeature> BULRUSH_PLACED_SECONDARY = PlacementUtils.register("bulrush_placed_secondary",
                    TelluriaConfiguredFeatures.BULRUSH_INDIVIDUAL_SECONDARY, seagrassPlacement(100));

    public static final Holder<PlacedFeature> PATCH_REEDS_PLACED = PlacementUtils.register(
                    "reeds_placed", TelluriaConfiguredFeatures.PATCH_REEDS,
                    RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
                    BiomeFilter.biome());

    public static List<PlacementModifier> seagrassPlacement(int amount) {
            return List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(amount), BiomeFilter.biome());
    }

    
}
