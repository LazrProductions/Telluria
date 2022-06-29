package net.genesis.telluria.block.custom.flora;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class ReedsBlock extends Block implements SimpleWaterloggedBlock, BonemealableBlock {

    public static final IntegerProperty END = IntegerProperty.create("end", 0, 2);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    public static final int MAX_HEIGHT = 8;
    public static final int MIN_HEIGHT_TO_STOP = 4;

    public ReedsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(STAGE, Integer.valueOf(0)).setValue(WATERLOGGED, false));
   
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE, STAGE, WATERLOGGED, END);
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    /*********** Normal Block Functions ***********/
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockstate = super.getStateForPlacement(ctx);
        return blockstate != null ? copyWaterloggedFrom(ctx.getLevel(), ctx.getClickedPos(), blockstate)
                : null;
    }

    //Ticking
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
           level.destroyBlock(pos, true);
        }
     }

    /**
    * @return whether this block needs random ticking.
    */
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(STAGE) == 0;
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(STAGE) == 0) {
           if (level.isEmptyBlock(pos.above()) && level.getRawBrightness(pos.above(), 0) >= 9) {
              int curHeight = this.getHeightBelowUpToMax(level, pos) + 1;
              if (curHeight < 16 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(3) == 0)) {
                 this.growBamboo(state, level, pos, random, curHeight);
                 net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
              }

           }
  
        }
     }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).is(BlockTags.DIRT)||pLevel.getBlockState(pPos.below()).is(Blocks.MUD)||pLevel.getBlockState(pPos.below()).is(this);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
           pLevel.scheduleTick(pCurrentPos, this, 1);
        }
  
        //Schedule fluid tick if is waterlogged
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        if (pFacing == Direction.UP && pFacingState.is(Blocks.BAMBOO) && pFacingState.getValue(AGE) > pState.getValue(AGE)) {
           pLevel.setBlock(pCurrentPos, pState.cycle(AGE), 2);
        }

        BlockState stateAtThisPoint = pLevel.getBlockState(pCurrentPos);

        if (pLevel.getBlockState(pCurrentPos.above()).is(this) && !pLevel.getBlockState(pCurrentPos.below()).isAir()
                && !pLevel.getBlockState(pCurrentPos.below()).is(this)) {
            // Block beneath is not reeds and block above is reeds
            // So it is a stem
            pLevel.setBlock(pCurrentPos, stateAtThisPoint.setValue(END, 0),3);
        }
        if (pLevel.getBlockState(pCurrentPos.above()).is(this) && pLevel.getBlockState(pCurrentPos.below()).is(this)) {
            // Block above is reeds and block below is reeds
            // So it is a filler
            pLevel.setBlock(pCurrentPos, stateAtThisPoint.setValue(END, 1),3);

        }
        if (pLevel.getBlockState(pCurrentPos.below()).is(this) && pLevel.getBlockState(pCurrentPos.above()).isAir()) {
            // Block above is air and block below is reeds
            // So it is a tip
            pLevel.setBlock(pCurrentPos, stateAtThisPoint.setValue(END, 2),3);
        }
  
        return pState;
     }

    /**********************************************/

    /*************** REEDS FUNCTIONS **************/
    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pLevel.getBlockState(pPos.above()).is(this) && !pLevel.getBlockState(pPos.below()).isAir()
                && !pLevel.getBlockState(pPos.below()).is(this)) {
            // Block beneath is not reeds and block above is reeds
            // So it is a stem
            pLevel.setBlock(pPos, pState.setValue(END, 0), 3);
        }
        if (pLevel.getBlockState(pPos.above()).is(this) && pLevel.getBlockState(pPos.below()).is(this)) {
            // Block above is reeds and block below is reeds
            // So it is a filler
            pLevel.setBlock(pPos, pState.setValue(END, 1), 3);

        }
        if (pLevel.getBlockState(pPos.below()).is(this) && !pLevel.getBlockState(pPos.above()).is(this)) {
            // Block above is air and block below is reeds
            // So it is a tip
            pLevel.setBlock(pPos, pState.setValue(END, 2), 3);
        }
    }
    
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity || pEntity instanceof Boat) {
           pEntity.makeStuckInBlock(pState, new Vec3((double)0.8F, 0.75D, (double)0.8F));
           if (!pLevel.isClientSide && pState.getValue(AGE) > 0 && (pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) {
              double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
              double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
              if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                pLevel.playSound(null, pPos, SoundEvents.AZALEA_LEAVES_STEP, SoundSource.BLOCKS, 0.6f, 1);
              }
           }
  
        }
     }  
    /**********************************************/

    /*************** BONE MEAL ABLE ***************/
    @Override
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        int i = this.getHeightAboveUpToMax(pLevel, pPos);
        int j = this.getHeightBelowUpToMax(pLevel, pPos);
        return i + j + 1 < MAX_HEIGHT && pLevel.getBlockState(pPos.above(i)).getValue(STAGE) != 1;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int i = this.getHeightAboveUpToMax(level, pos);
        int j = this.getHeightBelowUpToMax(level, pos);
        int k = i + j + 1;
        int l = 1 + random.nextInt(2);

        for (int i1 = 0; i1 < l; ++i1) {
            BlockPos blockpos = pos.above(i);
            BlockState blockstate = level.getBlockState(blockpos);
            if (k >= MAX_HEIGHT || blockstate.getValue(STAGE) == 1 || !level.isEmptyBlock(blockpos.above())) {
                return;
            }

            this.growBamboo(blockstate, level, blockpos, random, k);
            ++i;
            ++k;
        }

    }
    /**********************************************/


    /********** Water logging Functions ***********/
    
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
    /**********************************************/


    /************* Utility Functions **************/
    protected void growBamboo(BlockState state, Level level, BlockPos pos, RandomSource random, int height) {
        BlockPos blockpos = pos.below(2);
        BlockState blockstate1 = level.getBlockState(blockpos);
  
        int i = state.getValue(AGE) != 1 && !blockstate1.is(this) ? 0 : 1;
        int newStage = (height < MIN_HEIGHT_TO_STOP || !(random.nextFloat() < 0.25F)) && height != MAX_HEIGHT ? 0 : 1;
        level.setBlock(pos.above(), this.defaultBlockState().setValue(AGE, Integer.valueOf(i)).setValue(STAGE, Integer.valueOf(newStage)), 3);
    }

    /**
    * @return the number of continuous bamboo blocks above the position passed in, up to MAX_HEIGHT.*/
    protected int getHeightAboveUpToMax(BlockGetter pLevel, BlockPos pPos) {
         int i;
         for (i = 0; i < MAX_HEIGHT && pLevel.getBlockState(pPos.above(i + 1)).is(this); ++i) {
         }

         return i;
     }

    /**
    * @return the number of continuous bamboo blocks below the position passed in, up to MAX_HEIGHT.*/
    protected int getHeightBelowUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for (i = 0; i < MAX_HEIGHT && pLevel.getBlockState(pPos.below(i + 1)).is(this); ++i) {
        }

        return i;
    }

    //Return Waterlogged block if was waterlogged
    public static BlockState copyWaterloggedFrom(LevelReader level, BlockPos pos, BlockState state) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) 
              ? state.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(level.isWaterAt(pos))) 
              : state;
    }
    /**********************************************/

}