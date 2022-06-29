package net.genesis.telluria.block.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import org.jetbrains.annotations.Nullable;

public class TripleTallPlantBlock extends BushBlock {
   public static final IntegerProperty HALF = IntegerProperty.create("half", 0, 2); // 0 - lower | 1 - center | 2 - upper


   public TripleTallPlantBlock(BlockBehaviour.Properties properties) {
      super(properties);
      this.registerDefaultState(this.stateDefinition.any().setValue(HALF, 0));
   }

   /**
    * On Neighbor Update
    * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific direction passed in.
    */
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
      int thisHalf = pState.getValue(HALF);
      if (pFacing.getAxis() == Axis.Y && (thisHalf == 1 == (pFacing == Direction.UP) || thisHalf == 0 == (pFacing == Direction.UP)) && (!pFacingState.is(this) || pFacingState.getValue(HALF) == thisHalf)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         if (thisHalf == 0 && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
         } else {
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
         }
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
      BlockPos blockPos = pContext.getClickedPos();
      Level world = pContext.getLevel();

      return blockPos.getY() < world.getMaxBuildHeight() - 2 && world.getBlockState(blockPos.above()).canBeReplaced(pContext)
            ? super.getStateForPlacement(pContext)
            : null;
   }

   /**
    * Called by BlockItem after this block has been placed.
    */
   public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
      BlockPos blockPos = pPos.above();
      pLevel.setBlock(blockPos, copyWaterloggedFrom(pLevel, blockPos,
            (BlockState) this.defaultBlockState().setValue(HALF, 1)), 3);
      pLevel.setBlock(blockPos.above(), copyWaterloggedFrom(pLevel, blockPos.above(),
            (BlockState) this.defaultBlockState().setValue(HALF, 2)), 3);
   }

   public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
      if (pState.getValue(HALF) == 0) {
         return super.canSurvive(pState, pLevel, pPos);
      } else {
         BlockState blockState = pLevel.getBlockState(pPos.below());
         return blockState.is(this)
               && (blockState.getValue(HALF) == 0 || blockState.getValue(HALF) == 1);
      }

   }

   public static void placeAt(LevelAccessor pLevel, BlockState pState, BlockPos pPos, int pFlags) {
      BlockPos blockPos = pPos.above();
      BlockPos blockPosTop = pPos.above().above();
      pLevel.setBlock(pPos, 
         copyWaterloggedFrom(pLevel, pPos, (BlockState) pState.setValue(HALF, 0)), pFlags);
      pLevel.setBlock(blockPos,
         copyWaterloggedFrom(pLevel, blockPos, (BlockState) pState.setValue(HALF, 1)), pFlags);
      pLevel.setBlock(blockPosTop,
         copyWaterloggedFrom(pLevel, blockPosTop, (BlockState) pState.setValue(HALF, 2)), pFlags);
   }

   public static BlockState copyWaterloggedFrom(LevelReader level, BlockPos pos, BlockState state) {
      return state.hasProperty(BlockStateProperties.WATERLOGGED) 
            ? state.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(level.isWaterAt(pos))) 
            : state;
   }

   /**
    * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
    * this block
    */
   public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
      if (!pLevel.isClientSide) {
         if (pPlayer.isCreative()) {
            preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
         } else {
            dropResources(pState, pLevel, pPos, (BlockEntity)null, pPlayer, pPlayer.getMainHandItem());
         }
      }

      super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
   }

   /**
    * Called after a player has successfully harvested this block. This method will only be called if the player has
    * used the correct tool and drops should be spawned.
    */
   public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pTe, ItemStack pStack) {
      super.playerDestroy(pLevel, pPlayer, pPos, pState, pTe, pStack);
   }

   protected static void preventCreativeDropFromBottomPart(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
      int doubleBlockHalf = pState.getValue(HALF);
      if (doubleBlockHalf == 2) {
         BlockPos blockPosCenter = pPos.below();
         BlockPos blockPosLower = blockPosCenter.below();
         BlockState blockStateCenter = pLevel.getBlockState(blockPosCenter);
         BlockState blockStateLower = pLevel.getBlockState(blockPosLower);

         if (blockStateCenter.is(pState.getBlock()) && blockStateCenter.getValue(HALF) == 1 && blockStateLower.getValue(HALF) == 0) {
            BlockState newBlockStateCenter = blockStateCenter.hasProperty(BlockStateProperties.WATERLOGGED)
                  && (Boolean) blockStateCenter.getValue(BlockStateProperties.WATERLOGGED) == true ? Blocks.WATER.defaultBlockState()
                        : Blocks.AIR.defaultBlockState();
            BlockState newBlockStateLower = blockStateLower.hasProperty(BlockStateProperties.WATERLOGGED)
                  && (Boolean) blockStateLower.getValue(BlockStateProperties.WATERLOGGED) == true ? Blocks.WATER.defaultBlockState()
                        : Blocks.AIR.defaultBlockState();


            pLevel.setBlock(blockPosCenter, newBlockStateCenter, 35);
            pLevel.setBlock(blockPosLower, newBlockStateLower, 35);
            pLevel.levelEvent(pPlayer, 2001, blockPosCenter, Block.getId(newBlockStateCenter));
            pLevel.levelEvent(pPlayer, 2001, blockPosLower, Block.getId(newBlockStateLower));
         }
      } else if (doubleBlockHalf == 1) {
         BlockPos blockPosLower = pPos.below();
         BlockPos blockPosUpper = pPos.above();
         BlockState blockStateLower = pLevel.getBlockState(blockPosLower);
         BlockState blockStateUpper = pLevel.getBlockState(blockPosUpper);

         if (blockStateLower.is(pState.getBlock()) && blockStateLower.getValue(HALF) == 0
               && blockStateUpper.getValue(HALF) == 2) {
            BlockState newBlockStateLower = blockStateLower.hasProperty(BlockStateProperties.WATERLOGGED)
                  && (Boolean) blockStateLower.getValue(BlockStateProperties.WATERLOGGED) == true ? Blocks.WATER.defaultBlockState()
                        : Blocks.AIR.defaultBlockState();
            BlockState newBlockStateUpper = blockStateUpper.hasProperty(BlockStateProperties.WATERLOGGED)
                  && (Boolean) blockStateUpper.getValue(BlockStateProperties.WATERLOGGED) == true ? Blocks.WATER.defaultBlockState()
                        : Blocks.AIR.defaultBlockState();

            pLevel.setBlock(blockPosLower, newBlockStateLower, 35);
            pLevel.setBlock(blockPosUpper, newBlockStateUpper, 35);
            pLevel.levelEvent(pPlayer, 2001, blockPosLower, Block.getId(newBlockStateLower));
            pLevel.levelEvent(pPlayer, 2001, blockPosUpper, Block.getId(newBlockStateUpper));
         }
      }

   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
      pBuilder.add(HALF);
   }

   /**
    * Return a random long to be passed to {@link net.minecraft.client.resources.model.BakedModel#getQuads}, used for
    * random model rotations
    */
   public long getSeed(BlockState pState, BlockPos pPos) {
      return Mth.getSeed(pPos.getX(), pPos.below(pState.getValue(HALF) == 0 ? 0 : 1).getY(), pPos.getZ());
   }
}
