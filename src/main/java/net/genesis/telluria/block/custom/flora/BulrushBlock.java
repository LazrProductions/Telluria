package net.genesis.telluria.block.custom.flora;

import net.genesis.telluria.block.base.TripleTallPlantBlock;
import net.genesis.telluria.block.enums.TripleBlockHalf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import org.jetbrains.annotations.Nullable;

public class BulrushBlock extends TripleTallPlantBlock implements Waterloggable {
    private static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape SHAPE;
 
    public BulrushBlock(Settings settings) {
       super(settings);
       this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HALF, TripleBlockHalf.LOWER)).with(WATERLOGGED, false)));
    }
 
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
       return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == TripleBlockHalf.LOWER) {
           if (world.getBlockState(pos.down()).isOf(Blocks.MUD) || world.getBlockState(pos.down()).isIn(BlockTags.DIRT) || world.getBlockState(pos.down()).isIn(BlockTags.SAND)) {
              return true;
           } else {
            return false;
            }
        } else {
           BlockState blockState = world.getBlockState(pos.down());
           return blockState.isOf(this)
                 && (blockState.get(HALF) == TripleBlockHalf.LOWER || blockState.get(HALF) == TripleBlockHalf.CENTER);
        }
     }
  

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
       BlockState blockState = super.getPlacementState(ctx);
       return blockState != null ? withWaterloggedState(ctx.getWorld(), ctx.getBlockPos(), blockState) : null;
    }
 
    public FluidState getFluidState(BlockState state) {
       return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
    }
 
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
       if ((Boolean)state.get(WATERLOGGED)) {
          world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
       }
 
       return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
 
    protected void appendProperties(Builder<Block, BlockState> builder) {
       builder.add(new Property[]{HALF, WATERLOGGED });
    }
 
    public float getVerticalModelOffsetMultiplier() {
       return 0.1F;
    }
 
    static {
       WATERLOGGED = Properties.WATERLOGGED;
       SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    }
 }
