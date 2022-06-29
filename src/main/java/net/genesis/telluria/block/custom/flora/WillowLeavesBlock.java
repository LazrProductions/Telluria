package net.genesis.telluria.block.custom.flora;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class WillowLeavesBlock extends LeavesBlock {

    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

    public WillowLeavesBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if(!pLevel.getBlockState(pPos.below()).isAir()) {
            pLevel.setBlock(pPos, pState.setValue(BOTTOM, false), 3);
        } else {
            pLevel.setBlock(pPos, pState.setValue(BOTTOM, true), 3);
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pLevel.getBlockState(pCurrentPos.below()).isAir()) {
            pLevel.setBlock(pCurrentPos, pState.setValue(BOTTOM, false), 3);
        } else {
            pLevel.setBlock(pCurrentPos, pState.setValue(BOTTOM, true), 3);
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BOTTOM);
        super.createBlockStateDefinition(pBuilder);
    }

}
