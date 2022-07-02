package net.genesis.telluria.block.custom.flora;

import org.jetbrains.annotations.Nullable;

import net.genesis.telluria.block.util.TripleTallPlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BulrushBlock extends TripleTallPlantBlock implements SimpleWaterloggedBlock {
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public BulrushBlock(BlockBehaviour.Properties p_154583_) {
      super(p_154583_);
      this.registerDefaultState(this.stateDefinition.any().setValue(HALF, 0).setValue(WATERLOGGED, Boolean.valueOf(false)));
   }

   public VoxelShape getShape(BlockState p_154610_, BlockGetter p_154611_, BlockPos p_154612_, CollisionContext p_154613_) {
      return SHAPE;
   }

   //May Place On
   protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      if (pState.getValue(HALF) == 0) {
         if (pLevel.getBlockState(pPos.below()).is(Blocks.MUD) || pLevel.getBlockState(pPos.below()).is(BlockTags.DIRT) || pLevel.getBlockState(pPos.below()).is(BlockTags.SAND)) {
            return true;
         } else {
          return false;
          }
      } else {
         BlockState blockState = pLevel.getBlockState(pPos.below());
         return blockState.is(this)
               && (blockState.getValue(HALF) == 0 || blockState.getValue(HALF) == 1);
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
      BlockState blockstate = super.getStateForPlacement(pContext);
      return blockstate != null ? copyWaterloggedFrom(pContext.getLevel(), pContext.getClickedPos(), blockstate) : null;
   }
   

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return mayPlaceOn(state, level, pos);
   }

   /**
    * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific direction passed in.
    */
   public BlockState updateShape(BlockState state, Direction direction, BlockState pos, LevelAccessor level, BlockPos neighbor_pos, BlockPos p_154630_) {
      if (state.getValue(WATERLOGGED)==true) {
         level.scheduleTick(neighbor_pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return super.updateShape(state, direction, pos, level, neighbor_pos, p_154630_);
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> manager) {
      manager.add(HALF, WATERLOGGED);
   }
}
