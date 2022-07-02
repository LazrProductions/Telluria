package net.genesis.telluria.world.feature;

import java.util.List;
import java.util.Random;


import com.google.common.collect.ImmutableList;

import net.genesis.telluria.block.TelluriaBlocks;
import net.genesis.telluria.world.feature.util.TelluriaFeatureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.Fluids;



public class TelluriaConfiguredFeatures {
        private static final Random RANDOM = new Random();


        /************ Tree Features ************/
        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> WILLOW_TREE = FeatureUtils.register("ebony",
                        Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                                        BlockStateProvider.simple(TelluriaBlocks.WILLOW_LOG.get()) /* The Log to use */,
                                        new FancyTrunkPlacer(8 /* Height */, 6 /* Branches */, 2 /* Branch Length */),
                                        BlockStateProvider.simple(TelluriaBlocks.WILLOW_LEAVES.get())/* The Leaves to use */,
                                        new RandomSpreadFoliagePlacer(ConstantInt.of(3)/* Leaf Radius */,
                                                        ConstantInt.of(0) /* Leaf Offset */,
                                                        ConstantInt.of(2) /* Leaft Height */,
                                                        RANDOM.nextInt(50) + 30) /* Leaf Attempts */,
                                        new TwoLayersFeatureSize(1, 1, 2))
                                        .decorators(ImmutableList
                                                        .of(new TrunkVineDecorator() /* Spawn Vines */))
                                        .forceDirt()
                                        .build());


        public static final Holder<PlacedFeature> WILLOW_CHECKED = PlacementUtils.register("willow_checked", WILLOW_TREE, 
                        PlacementUtils.filteredByBlockSurvival(TelluriaBlocks.WILLOW_SAPLING.get()));
        
        public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> WILLOW_SPAWN = 
                        FeatureUtils.register("willow_spawn", Feature.RANDOM_SELECTOR,
                                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WILLOW_CHECKED, 
                                        0.2f)), WILLOW_CHECKED)); 

        /************ Vegitation Features ************/
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BULRUSH = FeatureUtils.register("patch_bulrush", 
                        Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(
                                        TelluriaFeatureTypes.COMPLEX_BLOCK, 
                                        new SimpleBlockConfiguration(BlockStateProvider.simple(TelluriaBlocks.BULRUSH.get()))));

        public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> CATTAILS_INDIVIDUAL = FeatureUtils
                        .register("cattails_individual", TelluriaFeatureTypes.CATTAILS_FEATURE, new ProbabilityFeatureConfiguration(1F));


        public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> BULRUSH_INDIVIDUAL = FeatureUtils
                        .register("bulrush_individual", TelluriaFeatureTypes.BULRUSH_FEATURE, new ProbabilityFeatureConfiguration(1F));

        public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> BULRUSH_INDIVIDUAL_SECONDARY = FeatureUtils
                        .register("bulrush_individual_secondary", TelluriaFeatureTypes.BULRUSH_FEATURE, new ProbabilityFeatureConfiguration(1F));

        
        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_REEDS = FeatureUtils
                        .register("patch_reeds", Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 4, 0,
                                        PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN,
                                                        BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 6),
                                                                        BlockStateProvider.simple(TelluriaBlocks.REEDS.get())),
                                                        BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                                                                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                                                                        BlockPredicate.wouldSurvive(TelluriaBlocks.REEDS.get().defaultBlockState(),BlockPos.ZERO),
                                                                        BlockPredicate.anyOf(BlockPredicate
                                                                                        .matchesFluids(new BlockPos(1,
                                                                                                        -1, 0),
                                                                                                        Fluids.WATER,
                                                                                                        Fluids.FLOWING_WATER),
                                                                                        BlockPredicate.matchesFluids(
                                                                                                        new BlockPos(-1, -1,
                                                                                                                        0),
                                                                                                        Fluids.WATER,
                                                                                                        Fluids.FLOWING_WATER),
                                                                                        BlockPredicate.matchesFluids(
                                                                                                        new BlockPos(0, -1,
                                                                                                                        1),
                                                                                                        Fluids.WATER,
                                                                                                        Fluids.FLOWING_WATER),
                                                                                        BlockPredicate.matchesFluids(
                                                                                                        new BlockPos(0, -1,
                                                                                                                        -1),
                                                                                                        Fluids.WATER,
                                                                                                        Fluids.FLOWING_WATER)))))));
}
