package net.genesis.telluria.block.enums;

import net.minecraft.util.StringRepresentable;

public enum TripleBlockHalf implements StringRepresentable {
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

   @Override
   public String getSerializedName() {
      return null;
   }
}
