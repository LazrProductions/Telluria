package net.genesis.telluria.block.util;

import net.genesis.telluria.block.entity.custom.TelluriaSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TelluriaWallSignBlock extends WallSignBlock {

    public TelluriaWallSignBlock(Properties p_58068_, WoodType p_58069_) {
        super(p_58068_, p_58069_);
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TelluriaSignBlockEntity(pPos, pState);
    }
}
