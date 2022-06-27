package net.genesis.telluria.block.custom.flora;

import org.jetbrains.annotations.Nullable;

import net.genesis.telluria.block.enums.TripleBlockHalf;
import net.genesis.telluria.block.util.TripleTallPlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BulrushBlock extends TripleTallPlantBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED;
   protected static final VoxelShape SHAPE;

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return false;
   }

   public BulrushBlock(Properties settings) {
      super(settings);
      this.registerDefaultState(
            this.stateDefinition.any().setValue(HALF, TripleBlockHalf.LOWER).setValue(WATERLOGGED, false));
   }

   public VoxelShape getShape(BlockState state, BlockGetter gettter, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   @Override
   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {

      if (state.getValue(HALF) == TripleBlockHalf.LOWER) {
         if (level.getBlockState(pos.below()).is(Blocks.MUD) || level.getBlockState(pos.below()).is(BlockTags.DIRT)
               || level.getBlockState(pos.below()).is(BlockTags.SAND)) {
            return true;
         } else {
            return false;
         }
      } else {
         BlockState blockState = level.getBlockState(pos.below());
         return blockState.is(this)
               && (blockState.getValue(HALF) == TripleBlockHalf.LOWER || blockState.getValue(HALF) == TripleBlockHalf.CENTER);
      }

   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      super.getStateForPlacement(context);
      BlockPos blockpos = context.getClickedPos();
      BlockState blockstate = context.getLevel().getBlockState(blockpos);
      return blockstate;
   }


   public FluidState getFluidState(BlockState p_56397_) {
      return p_56397_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
   }

   public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
      return SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
   }

   public boolean canPlaceLiquid(BlockGetter getter, BlockPos pos, BlockState state, Fluid fluid) {
      return SimpleWaterloggedBlock.super.canPlaceLiquid(getter, pos, state, fluid);
   }

   public BlockState updateShape(BlockState p_56381_, Direction p_56382_, BlockState p_56383_, LevelAccessor p_56384_, BlockPos p_56385_, BlockPos p_56386_) {
      if (p_56381_.getValue(WATERLOGGED)) {
         p_56384_.scheduleTick(p_56385_, Fluids.WATER, Fluids.WATER.getTickDelay(p_56384_));
      }

      return super.updateShape(p_56381_, p_56382_, p_56383_, p_56384_, p_56385_, p_56386_);
   }

   public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType compution) {
      switch (compution) {
         case LAND:
            return false;
         case WATER:
            return getter.getFluidState(pos).is(FluidTags.WATER);
         case AIR:
            return false;
         default:
            return false;
      }
   }


   // Register Blockstates
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
      state.add(WATERLOGGED);
      super.createBlockStateDefinition(state);
   }

   public float getVerticalModelOffsetMultiplier() {
      return 0.1F;
   }

   static {
      WATERLOGGED = BlockStateProperties.WATERLOGGED;
      SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
   }
}
