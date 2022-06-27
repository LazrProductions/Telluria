package net.genesis.telluria.block.custom.flora;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WillowLeavesBlock extends LeavesBlock {

    public static final BooleanProperty BOTTOM;

    public WillowLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if(!world.getBlockState(pos.down()).isAir()) {
            world.setBlockState(pos, state.with(BOTTOM, false));
        } else {
            world.setBlockState(pos, state.with(BOTTOM, true));
        }
    }

    @SuppressWarnings( "deprecation" )
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos,
            boolean notify) {
        if(!world.getBlockState(pos.down()).isAir()) {
            world.setBlockState(pos, state.with(BOTTOM, false));
        } else {
            world.setBlockState(pos, state.with(BOTTOM, true));
        }

        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(new Property[] { BOTTOM });
        super.appendProperties(builder);
    }

    static {
        BOTTOM = BooleanProperty.of("bottom");
    }
    
}
