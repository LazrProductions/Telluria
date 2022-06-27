package net.genesis.telluria.world.feature.tree;

import org.jetbrains.annotations.Nullable;

import net.genesis.telluria.world.feature.TelluriaConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class WillowSaplingGenerator extends SaplingGenerator {

    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random arg0, boolean arg1) {
        return TelluriaConfiguredFeatures.WILLOWTREE;
    }
    
}
