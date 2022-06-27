package net.genesis.telluria.capabilities.thirst;

import javax.annotation.Nullable;

import org.antlr.v4.runtime.misc.NotNull;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.capabilities.TelluriaCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class ThirstAttacher{
	
	private static class ThirstProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        public static final ResourceLocation IDENTIFIER = new ResourceLocation(TelluriaMod.MOD_ID, "thirst");

        private final IThirst backend = new ThirstHandler();
        private final LazyOptional<IThirst> optionalData = LazyOptional.of(() -> backend);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return TelluriaCapabilities.THIRST.orEmpty(cap, this.optionalData);
        }

        void invalidate() {
            this.optionalData.invalidate();
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.backend.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.backend.deserializeNBT(nbt);
        }
    }

    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        final ThirstProvider provider = new ThirstProvider();

        event.addCapability(ThirstProvider.IDENTIFIER, provider);
    }

    private ThirstAttacher() {
    }
}
