package net.genesis.telluria.world.feature.util;

import com.mojang.serialization.Codec;

import net.genesis.telluria.block.util.TripleTallPlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class ComplexBlockFeature extends Feature<SimpleBlockConfiguration> {
    public ComplexBlockFeature(Codec<SimpleBlockConfiguration> p_66808_) {
        super(p_66808_);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks,
     * centered on the chunk being generated,
     * that they can safely generate into.
     * 
     * @param pContext A context object with a reference to the level and the
     *                 position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration simpleblockconfiguration = context.config();
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        BlockState blockstate = simpleblockconfiguration.toPlace().getState(context.random(), blockpos);
        if (blockstate.canSurvive(worldgenlevel, blockpos)) {
            if (!worldgenlevel.getBlockState(blockpos).is(Blocks.WATER)) {
                //In Air
                if (blockstate.getBlock() instanceof DoublePlantBlock) {
                    if (!worldgenlevel.isEmptyBlock(blockpos.above())) {
                        return false;
                    }

                    DoublePlantBlock.placeAt(worldgenlevel, blockstate, blockpos, 2);
                } else if(blockstate.getBlock() instanceof TripleTallPlantBlock) {
                    //Block above and above that is not air, so cannot replace
                    if (!worldgenlevel.isEmptyBlock(blockpos.above()) && !worldgenlevel.isEmptyBlock(blockpos.above().above())) {
                        return false;
                    }

                    TripleTallPlantBlock.placeAt(worldgenlevel, blockstate, blockpos, 2);
                } else {
                    worldgenlevel.setBlock(blockpos, blockstate, 2);
                }
            } else {
                //In Water, so waterlog it
                if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED)) {
                    if (blockstate.getBlock() instanceof DoublePlantBlock) {
                        //Block above is not air and is not water, so cannot replace
                        if (!worldgenlevel.isEmptyBlock(blockpos.above()) && !worldgenlevel.getBlockState(blockpos.above()).is(Blocks.WATER)) {
                            return false;
                        }
    
                        DoublePlantBlock.placeAt(worldgenlevel, blockstate.setValue(BlockStateProperties.WATERLOGGED, true), blockpos, 2);
                    } else if(blockstate.getBlock() instanceof TripleTallPlantBlock) {
                        //Block above and above that is not air and is not water, so cannot replace
                        if (!worldgenlevel.isEmptyBlock(blockpos.above()) && !worldgenlevel.isWaterAt(blockpos.above()) && !worldgenlevel.isEmptyBlock(blockpos.above().above()) && !worldgenlevel.isWaterAt(blockpos.above().above())) {
                            return false;
                        }
    
                        TripleTallPlantBlock.placeAt(worldgenlevel, blockstate.setValue(BlockStateProperties.WATERLOGGED, true), blockpos, 2);
                    } else {
                        worldgenlevel.setBlock(blockpos, blockstate, 2);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}