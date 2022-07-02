package net.genesis.telluria.network;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import net.genesis.telluria.capabilities.TelluriaCapabilities;
import net.genesis.telluria.capabilities.thirst.IThirst;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class ServerboundThirstUpdatePacket {

	public final int thirst;
	public final float hydration;
	public final float exhaustion;
	
	public ServerboundThirstUpdatePacket(int thirst, float hydration, float exhaustion) {
		this.thirst = thirst;
		this.hydration = hydration;
		this.exhaustion = exhaustion;
	}

	public ServerboundThirstUpdatePacket(FriendlyByteBuf buffer) {
		this(buffer.readInt(), buffer.readFloat(), buffer.readFloat());
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(thirst);
		buffer.writeFloat(hydration);
		buffer.writeFloat(exhaustion);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		final var success = new AtomicBoolean(false);
		ctx.get().enqueueWork(() -> {
			final Player player = ctx.get().getSender();
			IThirst stat = player.getCapability(TelluriaCapabilities.THIRST).orElse(null);
			if(stat != null) {
				stat.setThirst(thirst);
				stat.setHydration(hydration);
				stat.setExhaustion(exhaustion);
				success.set(true);
			}
		});
		
		ctx.get().setPacketHandled(true);
		return success.get();
	}
}
