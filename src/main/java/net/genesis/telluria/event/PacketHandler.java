package net.genesis.telluria.event;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.network.ClientboundThirstUpdatePacket;
import net.genesis.telluria.network.ServerboundThirstUpdatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {
	final static String PROTOCOL_VERSION = "1";

	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(TelluriaMod.MOD_ID, "simple"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);

	private PacketHandler() {

	}

	public static void init() {
		int index = 0;
		INSTANCE.messageBuilder(ServerboundThirstUpdatePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
				.encoder(ServerboundThirstUpdatePacket::encode).decoder(ServerboundThirstUpdatePacket::new)
				.consumer(ServerboundThirstUpdatePacket::handle).add();
		
		INSTANCE.messageBuilder(ClientboundThirstUpdatePacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
		.encoder(ClientboundThirstUpdatePacket::encode).decoder(ClientboundThirstUpdatePacket::new)
		.consumer(ClientboundThirstUpdatePacket::handle).add();
	}
}