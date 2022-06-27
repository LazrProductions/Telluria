package net.genesis.telluria.world.feature;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.TelluriaBlocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureEntry;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;

public class TelluriaConfiguredFeatures {

    private static final Random RANDOM = new Random();

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> WILLOWTREE = ConfiguredFeatures
            .register("willow_tree", Feature.TREE, new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(TelluriaBlocks.WILLOWLOG) /* The Log to use */,
                    new LargeOakTrunkPlacer(8 /* Height */, 6 /* Branches */, 2 /* Branch Length */),
                    BlockStateProvider.of(TelluriaBlocks.WILLOWLEAVES)/* The Leaves to use */,
                    new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3)/* Leaf Radius */,
                            ConstantIntProvider.create(0) /* Leaf Offset */,
                            ConstantIntProvider.create(2) /* Leaft Height */, RANDOM.nextInt(50) + 30) /* Leaf Attempts */,
                    new TwoLayersFeatureSize(1, 1, 2))
                    .decorators(ImmutableList
                            .of(/* new BeehiveTreeDecorator(0.5f) Spawn Beehives */ new TrunkVineTreeDecorator() /* Spawn Vines */))
                    .forceDirt()
                    .build());

    public static final RegistryEntry<PlacedFeature> WILLOWCHECKED = 
        PlacedFeatures.register("willow_checked", WILLOWTREE, 
            PlacedFeatures.wouldSurvive(TelluriaBlocks.WILLOWSAPLING));

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> WILLOWSPAWN =
        ConfiguredFeatures.register("willow_spawn", Feature.RANDOM_SELECTOR, 
            new RandomFeatureConfig(List.of(new RandomFeatureEntry(WILLOWCHECKED, 0.5f)),
                WILLOWCHECKED));

    public static void registerConfiguredFeatures() {
        System.out.println("Registering Congifured Features for " + TelluriaMod.MOD_ID);
    }
}