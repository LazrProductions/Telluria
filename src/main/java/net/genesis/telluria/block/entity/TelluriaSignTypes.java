package net.genesis.telluria.block.entity;

import net.genesis.telluria.mixin.SignTypeAccessor;
import net.minecraft.util.SignType;

public class TelluriaSignTypes {
    public static final SignType WILLOW = 
        SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("willow"));
}
