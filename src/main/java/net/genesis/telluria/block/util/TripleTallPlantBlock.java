package net.genesis.telluria.block.util;

import net.genesis.telluria.block.enums.TripleBlockHalf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import org.jetbrains.annotations.Nullable;

public class TripleTallPlantBlock extends BushBlock {
   public static final EnumProperty<TripleBlockHalf> HALF = EnumProperty.create("half", TripleBlockHalf.class);

   public BlockState updateShape(BlockState state, Direction direction, BlockState neighbor_state, LevelAccessor level, BlockPos neighbor_pos, BlockPos pos3) {
      TripleBlockHalf doubleblockhalf = state.getValue(HALF);
      if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == TripleBlockHalf.LOWER != (direction == Direction.UP) || neighbor_state.is(this) && neighbor_state.getValue(HALF) != doubleblockhalf) {
         return doubleblockhalf == TripleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, neighbor_pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighbor_state, level, neighbor_pos, pos3);
      } else {
         return Blocks.AIR.defaultBlockState();
      }
   }

   public TripleTallPlantBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(this.stateDefinition.any().setValue(HALF, TripleBlockHalf.LOWER));
   }

   @Override
   public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighborPos) {
         //TripleBlockHalf doubleBlockHalf = state.getValue(HALF);
   
         // if ((doubleBlockHalf == TripleBlockHalf.CENTER || doubleBlockHalf == TripleBlockHalf.LOWER ) && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
         //    return Blocks.AIR.getDefaultState();
         // } else {
         //    if (doubleBlockHalf == TripleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
         //       return Blocks.AIR.getDefaultState();
         //    } else {
         //       return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
            
               
         //    }
         // }

      super.onNeighborChange(state, level, pos, neighborPos);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos blockpos = context.getClickedPos();
      Level level = context.getLevel();
      return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
   }


   public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placingEntity, ItemStack itemStack) {
      BlockPos blockpos = pos.above();
      level.setBlock(blockpos, copyWaterloggedFrom(level, blockpos, this.defaultBlockState().setValue(HALF, TripleBlockHalf.UPPER)), 3);
   }

   //Copy the water logged
   public static BlockState copyWaterloggedFrom(LevelReader level, BlockPos pos, BlockState state) {
      return state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(level.isWaterAt(pos))) : state;
   }

   public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide) {
         if (player.isCreative()) {
            preventCreativeDropFromBottomPart(level, pos, state, player);
         } else {
            dropResources(state, level, pos, (BlockEntity)null, player, player.getMainHandItem());
         }
      }

      super.playerWillDestroy(level, pos, state, player);
   }

   public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
      super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), blockEntity, itemStack);
   }

   //Prevent creative Players from dropping from the bottom part
   protected static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
      TripleBlockHalf doubleblockhalf = state.getValue(HALF);
      if (doubleblockhalf == TripleBlockHalf.UPPER) {
         BlockPos blockpos = pos.below();
         BlockState blockstate = level.getBlockState(blockpos);
         if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == TripleBlockHalf.LOWER) {
            BlockState blockstate1 = blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && blockstate.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(blockpos, blockstate1, 35);
            level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
         }
      }

   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
      state.add(HALF);
   }

   public long getSeed(BlockState state, BlockPos pos) {
      return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == TripleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
   }
}
