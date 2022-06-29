package net.genesis.telluria.block.util;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.registries.RegistryObject;

public class LogBlock extends RotatedPillarBlock {

    public static Block BEFORE_STRIP; 
    public static Block AFTER_STRIP; 

    public LogBlock(Properties p_55926_, RegistryObject<Block> afterStrip) {
        super(p_55926_);
        BEFORE_STRIP = this;
        AFTER_STRIP = afterStrip.get();
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction,
            boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            if (state.is(BEFORE_STRIP)) {
                return AFTER_STRIP.defaultBlockState().setValue(AXIS,
                        state.getValue(AXIS));
            }
            if (state.is(BEFORE_STRIP)) {
                return AFTER_STRIP.defaultBlockState().setValue(AXIS,
                        state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
