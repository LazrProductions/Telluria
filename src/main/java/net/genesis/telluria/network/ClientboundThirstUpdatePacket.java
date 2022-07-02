package net.genesis.telluria.network;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import net.genesis.telluria.capabilities.TelluriaCapabilities;
import net.genesis.telluria.capabilities.thirst.IThirst;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundThirstUpdatePacket {

	public final int thirst;
	public final float hydration;
	public final float exhaustion;
	
	public ClientboundThirstUpdatePacket(int thirst, float hydration, float exhaustion) {
		this.thirst = thirst;
		this.hydration = hydration;
		this.exhaustion = exhaustion;
	}

	public ClientboundThirstUpdatePacket(FriendlyByteBuf buffer) {
		this(buffer.readInt(), buffer.readFloat(), buffer.readFloat());
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(thirst);
		buffer.writeFloat(hydration);
		buffer.writeFloat(exhaustion);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context ctx = supplier.get();
		ctx.enqueueWork(() -> {
			ClientAccess.updateThirst(thirst, hydration, exhaustion);
		});
		return true;
	}
}
