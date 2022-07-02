package net.genesis.telluria.world.feature.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;


public record ComplexBlockConfiguration(BlockStateProvider toPlace) implements FeatureConfiguration {
    public static final Codec<ComplexBlockConfiguration> CODEC = RecordCodecBuilder.create((p_191331_) -> {
        return p_191331_.group(BlockStateProvider.CODEC.fieldOf("to_place").forGetter((p_161168_) -> {
            return p_161168_.toPlace;
        })).apply(p_191331_, ComplexBlockConfiguration::new);
    });
}

