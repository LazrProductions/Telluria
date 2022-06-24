package net.genesis.telluria.block.base;

import net.genesis.telluria.block.enums.TripleBlockHalf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
//import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class TripleTallPlantBlock extends PlantBlock {
   public static final EnumProperty<TripleBlockHalf> HALF;



   public TripleTallPlantBlock(Settings settings) {
      super(settings);
      this.setDefaultState(
            (BlockState) ((BlockState) this.stateManager.getDefaultState()).with(HALF, TripleBlockHalf.LOWER));
   }

   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
      WorldAccess world, BlockPos pos, BlockPos neighborPos) {
      TripleBlockHalf doubleBlockHalf = (TripleBlockHalf) state.get(HALF);

      if (direction.getAxis() == Axis.Y && doubleBlockHalf == TripleBlockHalf.LOWER == (direction == Direction.UP)
            && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
         return Blocks.AIR.getDefaultState();
      } else {
         return doubleBlockHalf == TripleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
               ? Blocks.AIR.getDefaultState()
               : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
      }
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockPos blockPos = ctx.getBlockPos();
      World world = ctx.getWorld();

      return blockPos.getY() < world.getTopY() - 2 && world.getBlockState(blockPos.up()).canReplace(ctx)
            ? super.getPlacementState(ctx)
            : null;
   }

   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      BlockPos blockPos = pos.up();
      world.setBlockState(blockPos, withWaterloggedState(world, blockPos,
            (BlockState) this.getDefaultState().with(HALF, TripleBlockHalf.CENTER)), 3);
       world.setBlockState(blockPos.up(), withWaterloggedState(world, blockPos.up(),
            (BlockState) this.getDefaultState().with(HALF, TripleBlockHalf.UPPER)), 3);
   }

   

   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      if (state.get(HALF) == TripleBlockHalf.LOWER) {
         if (world.getBlockState(pos.down()).isOf(Blocks.MUD)) {
            return true;
         }

         return super.canPlaceAt(state, world, pos);
      } else {
         BlockState blockState = world.getBlockState(pos.down());
         return blockState.isOf(this)
               && (blockState.get(HALF) == TripleBlockHalf.LOWER || blockState.get(HALF) == TripleBlockHalf.CENTER);
      }
   }

   public static void placeAt(WorldAccess world, BlockState state, BlockPos pos, int flags) {
      BlockPos blockPos = pos.up();
      BlockPos blockPosTop = pos.up().up();
      world.setBlockState(pos, withWaterloggedState(world, pos, (BlockState) state.with(HALF, TripleBlockHalf.LOWER)),
            flags);
      world.setBlockState(blockPos,
            withWaterloggedState(world, blockPos, (BlockState) state.with(HALF, TripleBlockHalf.CENTER)), flags);
      world.setBlockState(blockPosTop,
            withWaterloggedState(world, blockPosTop, (BlockState) state.with(HALF, TripleBlockHalf.UPPER)), flags);
   }

   public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
      return state.contains(Properties.WATERLOGGED)
            ? (BlockState) state.with(Properties.WATERLOGGED, world.isWater(pos))
            : state;
   }

   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.isClient) {
         if (player.isCreative()) {
            onBreakInCreative(world, pos, state, player);
         } else {
            dropStacks(state, world, pos, (BlockEntity) null, player, player.getMainHandStack());
         }
      }

      super.onBreak(world, pos, state, player);
   }

   public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
         @Nullable BlockEntity blockEntity, ItemStack stack) {
      super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
   }

   protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      TripleBlockHalf doubleBlockHalf = (TripleBlockHalf) state.get(HALF);
      if (doubleBlockHalf == TripleBlockHalf.UPPER) {
         BlockPos blockPos = pos.down();
         BlockPos blockUpPos = pos.down().down();
         BlockState blockState = world.getBlockState(blockPos);
         BlockState blockStateUp = world.getBlockState(blockUpPos);

         if (blockState.isOf(state.getBlock()) && blockState.get(HALF) == TripleBlockHalf.CENTER && blockStateUp.get(HALF) == TripleBlockHalf.LOWER) {
            BlockState blockState2 = blockState.contains(Properties.WATERLOGGED)
                  && (Boolean) blockState.get(Properties.WATERLOGGED) ? Blocks.WATER.getDefaultState()
                        : Blocks.AIR.getDefaultState();
            BlockState blockStateUp2 = blockStateUp.contains(Properties.WATERLOGGED)
                  && (Boolean) blockStateUp.get(Properties.WATERLOGGED) ? Blocks.WATER.getDefaultState()
                        : Blocks.AIR.getDefaultState();


            world.setBlockState(blockPos, blockState2, 35);
            world.setBlockState(blockUpPos, blockStateUp2, 35);
            world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            world.syncWorldEvent(player, 2001, blockUpPos, Block.getRawIdFromState(blockStateUp2));
         }
      } else if (doubleBlockHalf == TripleBlockHalf.CENTER) {
         BlockPos blockPos = pos.down();
         BlockPos blockUpPos = pos.up();
         BlockState blockState = world.getBlockState(blockPos);
         BlockState blockStateUp = world.getBlockState(blockUpPos);

         if (blockState.isOf(state.getBlock()) && blockState.get(HALF) == TripleBlockHalf.LOWER
               && blockStateUp.get(HALF) == TripleBlockHalf.UPPER) {
            BlockState blockState2 = blockState.contains(Properties.WATERLOGGED)
                  && (Boolean) blockState.get(Properties.WATERLOGGED) ? Blocks.WATER.getDefaultState()
                        : Blocks.AIR.getDefaultState();
            BlockState blockUpState2 = blockStateUp.contains(Properties.WATERLOGGED)
                  && (Boolean) blockStateUp.get(Properties.WATERLOGGED) ? Blocks.WATER.getDefaultState()
                        : Blocks.AIR.getDefaultState();

            world.setBlockState(blockPos, blockState2, 35);
            world.setBlockState(blockUpPos, blockUpState2, 35);
            world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            world.syncWorldEvent(player, 2001, blockUpPos, Block.getRawIdFromState(blockStateUp));
         }
      }

   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      builder.add(new Property[] { HALF });
   }

   static {
      HALF = EnumProperty.of("half", TripleBlockHalf.class);
   }

}
