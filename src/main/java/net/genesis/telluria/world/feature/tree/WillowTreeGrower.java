package net.genesis.telluria.world.feature.tree;

import javax.annotation.Nullable;

import net.genesis.telluria.world.feature.TelluriaConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WillowTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_222910_,
            boolean p_222911_) {
        return TelluriaConfiguredFeatures.WILLOW_TREE;
    }
    
}
