package net.genesis.telluria.block.custom.flora;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.state.StateManager.Builder;

public class ReedsBlock extends Block implements Waterloggable, Fertilizable {
   private static final IntProperty AGE;
   public static final IntProperty STAGE;

   private static final BooleanProperty WATERLOGGED;
   private static final IntProperty END;

   protected static final VoxelShape SHAPE;

   public ReedsBlock(Settings settings) {
      super(settings);
      this.setDefaultState(
            (BlockState) ((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(AGE, 0))
                  .with(WATERLOGGED, false)).with(END, 0));

   }

   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      if (world.getBlockState(pos.down()).isOf(Blocks.MUD) || world.getBlockState(pos.down()).isIn(BlockTags.DIRT)
            || world.getBlockState(pos.down()).isIn(BlockTags.SAND) || world.getBlockState(pos.down()).isOf(this)) {
         if (!world.getBlockState(pos.down()).isOf(this)) {
            BlockPos blockPos = pos.down();
            Iterator<Direction> var6 = Type.HORIZONTAL.iterator();

            while (var6.hasNext()) {
               Direction direction = (Direction) var6.next();
               BlockState blockState2 = world.getBlockState(blockPos.offset(direction));
               FluidState fluidState = world.getFluidState(blockPos.offset(direction));
               if (fluidState.isIn(FluidTags.WATER) || blockState2.isOf(Blocks.FROSTED_ICE)) {
                  return true;
               }
            }

            if (world.isWater(pos)) {
               return true;
            }
         } else {
            return true;
         }
      }
      
      return false;
      
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos,
         boolean notify) {
      if (world.getBlockState(pos.up()).isOf(this) && !world.getBlockState(pos.down()).isAir()
            && !world.getBlockState(pos.down()).isOf(this)) {
         // Block beneath is not reeds and block above is reeds
         // So it is a stem
         world.setBlockState(pos, state.with(END, 0));
      }
      if (world.getBlockState(pos.up()).isOf(this) && world.getBlockState(pos.down()).isOf(this)) {
         // Block above is reeds and block below is reeds
         // So it is a filler
         world.setBlockState(pos, state.with(END, 1));

      }
      if (world.getBlockState(pos.down()).isOf(this) && world.getBlockState(pos.up()).isAir()) {
         // Block above is air and block below is reeds
         // So it is a tip
         world.setBlockState(pos, state.with(END, 2));
      }

      DebugInfoSender.sendNeighborUpdate(world, pos);
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (world.getBlockState(pos.up()).isOf(this) && !world.getBlockState(pos.down()).isAir()
            && !world.getBlockState(pos.down()).isOf(this)) {
         // Block beneath is not reeds and block above is reeds
         // So it is a stem
         world.setBlockState(pos, state.with(END, 0));
      }
      if (world.getBlockState(pos.up()).isOf(this) && world.getBlockState(pos.down()).isOf(this)) {
         // Block above is reeds and block below is reeds
         // So it is a filler
         world.setBlockState(pos, state.with(END, 1));

      }
      if (world.getBlockState(pos.down()).isOf(this) && !world.getBlockState(pos.up()).isOf(this)) {
         // Block above is air and block below is reeds
         // So it is a tip
         world.setBlockState(pos, state.with(END, 2));
      }
   }

   /// Growing
   @Override
   public boolean hasRandomTicks(BlockState state) {
      return state.get(STAGE) == 0;
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (state.get(STAGE) == 0) {
         if (world.isAir(pos.up())) {
            int i;
            for (i = 1; world.getBlockState(pos.down(i)).isOf(this); ++i) {
            }

            if (i < 6) {
               int j = (Integer) state.get(AGE);
               if (j == 15) {
                  world.setBlockState(pos.up(), this.getDefaultState());
                  world.setBlockState(pos, (BlockState) state.with(AGE, 0), 4);

               } else {
                  world.setBlockState(pos, (BlockState) state.with(AGE, j + 1), 4);
               }
            } else {
               world.setBlockState(pos, state.with(STAGE, 1));
            }
         }
      }
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!state.canPlaceAt(world, pos)) {
         world.breakBlock(pos, true);
      }
   }
   ///

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState blockState = super.getPlacementState(ctx);
      return blockState != null ? withWaterloggedState(ctx.getWorld(), ctx.getBlockPos(), blockState) : null;
   }

   public FluidState getFluidState(BlockState state) {
      return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
   }

   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
      WorldAccess world, BlockPos pos, BlockPos neighborPos) {
      if ((Boolean) state.get(WATERLOGGED)) {
         world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      if (!state.canPlaceAt(world, pos)) {
         world.createAndScheduleBlockTick(pos, this, 1);
      }

      return state;
   }

   protected void appendProperties(Builder<Block, BlockState> builder) {
      builder.add(new Property[] { WATERLOGGED, END, AGE, STAGE });
      super.appendProperties(builder);
   }

   public float getVerticalModelOffsetMultiplier() {
      return 0.1F;
   }

   public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
      return state.contains(Properties.WATERLOGGED)
            ? (BlockState) state.with(Properties.WATERLOGGED, world.isWater(pos))
            : state;
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      world.setBlockState(pos.up(), this.getDefaultState());
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      return world.getBlockState(pos.up()).isAir();
   }

   static {
      AGE = Properties.AGE_15;
      STAGE = Properties.STAGE;

      WATERLOGGED = Properties.WATERLOGGED;
      END = IntProperty.of("end", 0, 2);

      SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
   }
}
