package net.genesis.telluria.block.custom.flora;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CattailsBlock extends DoublePlantBlock implements SimpleWaterloggedBlock {

    private static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 2);
    
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public CattailsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED,
                Boolean.valueOf(false)));
    }

    public VoxelShape getShape(BlockState p_154610_, BlockGetter p_154611_, BlockPos p_154612_,
            CollisionContext p_154613_) {
        return SHAPE;
    }

    protected boolean mayPlaceOn(BlockState p_154636_, BlockGetter p_154637_, BlockPos p_154638_) {
        return p_154636_.is(BlockTags.DIRT) || p_154637_.getFluidState(p_154638_.above()).isSourceOfType(Fluids.WATER)
                && super.mayPlaceOn(p_154636_, p_154637_, p_154638_);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = super.getStateForPlacement(pContext);
        return blockstate != null ? copyWaterloggedFrom(pContext.getLevel(), pContext.getClickedPos(), blockstate)
                : null;
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = DoublePlantBlock.copyWaterloggedFrom(pLevel, blockpos,
                    this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
            pLevel.setBlock(blockpos, blockstate, 3);
        }

    }

    public FluidState getFluidState(BlockState p_154634_) {
        return p_154634_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    public boolean canSurvive(BlockState p_154615_, LevelReader p_154616_, BlockPos p_154617_) {
        if (p_154615_.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return super.canSurvive(p_154615_, p_154616_, p_154617_);
        } else {
            BlockPos blockpos = p_154617_.below();
            BlockState blockstate = p_154616_.getBlockState(blockpos);
            return this.mayPlaceOn(blockstate, p_154616_, blockpos);
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {

        int v = 0;
        if (pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            Random r = new Random();
            v = Mth.floor(r.nextDouble()*2.9d);
        } else {
            v = pLevel.getBlockState(pPos.below()).is(this) ? pLevel.getBlockState(pPos.below()).getValue(VARIANT) : 0;
        }
        pLevel.setBlock(pPos, pState.setValue(VARIANT, v), 3);
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor
     * state, returning a new state.
     * For example, fences make their connections to the passed in state if
     * possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction
     * passed in.
     */
    public BlockState updateShape(BlockState p_154625_, Direction p_154626_, BlockState p_154627_,
            LevelAccessor p_154628_, BlockPos p_154629_, BlockPos p_154630_) {
        if (p_154625_.getValue(WATERLOGGED)) {
            p_154628_.scheduleTick(p_154629_, Fluids.WATER, Fluids.WATER.getTickDelay(p_154628_));
        }

        return super.updateShape(p_154625_, p_154626_, p_154627_, p_154628_, p_154629_, p_154630_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_154632_) {
        p_154632_.add(HALF, WATERLOGGED, VARIANT);
    }

    public float getMaxVerticalOffset() {
        return 0.1F;
    }
}