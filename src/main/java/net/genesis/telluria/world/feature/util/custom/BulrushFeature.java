package net.genesis.telluria.world.feature.util.custom;

import com.mojang.serialization.Codec;

import net.genesis.telluria.block.TelluriaBlocks;
import net.genesis.telluria.block.util.TripleTallPlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class BulrushFeature extends Feature<ProbabilityFeatureConfiguration> {
   public BulrushFeature(Codec<ProbabilityFeatureConfiguration> codec) {
      super(codec);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param pContext A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
      boolean flag = false;
      RandomSource randomsource = context.random();
      WorldGenLevel worldgenlevel = context.level();
      BlockPos blockpos = context.origin();

      ProbabilityFeatureConfiguration probabilityfeatureconfiguration = context.config();

      int i = randomsource.nextInt(8) - randomsource.nextInt(8);
      int j = randomsource.nextInt(8) - randomsource.nextInt(8);
      int k = worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR, blockpos.getX() + i, blockpos.getZ() + j);

      BlockPos blockpos1 = new BlockPos(blockpos.getX() + i, k, blockpos.getZ() + j);

      //if block is water or air
      if (worldgenlevel.isEmptyBlock(blockpos1) || worldgenlevel.isWaterAt(blockpos1)) {        
         boolean flag1 = randomsource.nextDouble() < (double)probabilityfeatureconfiguration.probability;
         BlockState blockstate = flag1 ? TelluriaBlocks.BULRUSH.get().defaultBlockState() : TelluriaBlocks.BULRUSH.get().defaultBlockState();

         if (blockstate.canSurvive(worldgenlevel, blockpos1)) {
            if (flag1) {
               BlockState blockstate1 = blockstate.setValue(TripleTallPlantBlock.HALF, 1);
               BlockState blockstate2 = blockstate.setValue(TripleTallPlantBlock.HALF, 2);
               BlockPos blockpos2 = blockpos1.above();
               BlockPos blockpos3 = blockpos2.above();

               //block above and above that is air or water
               if((worldgenlevel.isEmptyBlock(blockpos2) || worldgenlevel.isWaterAt(blockpos2)) && (worldgenlevel.isEmptyBlock(blockpos3))) {
                  worldgenlevel.setBlock(blockpos1, blockstate.setValue(BlockStateProperties.WATERLOGGED, worldgenlevel.getBlockState(blockpos1).is(Blocks.WATER)), 2);
                  worldgenlevel.setBlock(blockpos2, blockstate1.setValue(BlockStateProperties.WATERLOGGED, worldgenlevel.getBlockState(blockpos2).is(Blocks.WATER)), 2);
                  worldgenlevel.setBlock(blockpos3, blockstate2.setValue(BlockStateProperties.WATERLOGGED, worldgenlevel.getBlockState(blockpos3).is(Blocks.WATER)), 2);
               }
            }

            flag = false;
         }
      }

      return flag;
   }
}