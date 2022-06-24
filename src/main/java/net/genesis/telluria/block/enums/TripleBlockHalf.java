package net.genesis.telluria.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum TripleBlockHalf implements StringIdentifiable {
    UPPER,
    CENTER,
    LOWER,;
 
    public String toString() {
       return this.asString();
    }
 
    public String asString() {
       if(this == UPPER) return "upper";
       else if(this == CENTER) return "center";
       return "lower";
    }
}
