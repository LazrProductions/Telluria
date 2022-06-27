package net.genesis.telluria.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.util.SignType;

@Mixin(SignType.class)
public interface SignTypeAccessor {
    @Invoker("<init>")
    public static SignType newSignType(String name) { 
      throw new AssertionError(); 
    }

    @Invoker("register")
    public static SignType registerNew(SignType type) { 
      throw new AssertionError(); 
    }

  }
