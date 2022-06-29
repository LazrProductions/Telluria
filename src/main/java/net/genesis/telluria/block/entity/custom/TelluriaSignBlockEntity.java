package net.genesis.telluria.block.entity.custom;

import net.genesis.telluria.block.entity.TelluriaBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TelluriaSignBlockEntity extends SignBlockEntity {
    public TelluriaSignBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return TelluriaBlockEntities.SIGN_BLOCK_ENTITIES.get();
    }
}
