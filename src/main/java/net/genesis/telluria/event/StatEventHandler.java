package net.genesis.telluria.event;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.capabilities.TelluriaCapabilities;
import net.genesis.telluria.capabilities.thirst.IThirst;
import net.genesis.telluria.capabilities.thirst.ThirstProvider;
import net.genesis.telluria.network.ClientboundThirstUpdatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;

public class StatEventHandler {

	// Whenever a new object of some type is created the AttachCapabilitiesEvent
	// will fire. In our case we want to know
	// when a new player arrives so that we can attach our capability here
	public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player) {
			if (!event.getObject().getCapability(TelluriaCapabilities.THIRST).isPresent()) {
				// The player does not already have this capability so we need to add the
				// capability provider here
				event.addCapability(new ResourceLocation(TelluriaMod.MOD_ID, "thirst"), new ThirstProvider());
			}
		}
	}

	// When a player dies or teleports from the end capabilities are cleared. Using
	// the PlayerEvent.Clone event
	// we can detect this and copy our capability from the old player to the new one
	public static void onPlayerCloned(PlayerEvent.Clone event) {
		if (!event.isWasDeath()) {
			// We need to copyFrom the capabilities
			event.getOriginal().getCapability(TelluriaCapabilities.THIRST).ifPresent(oldStore -> {
				event.getPlayer().getCapability(TelluriaCapabilities.THIRST).ifPresent(newStore -> {
					newStore.setThirst(oldStore.getThirst());
					newStore.setHydration(oldStore.getHydration());
					newStore.setExhaustion(oldStore.getExhaustion());
				});
			});
		}
	}

	public static void onWorldTick(TickEvent.WorldTickEvent event) {
		// Don't do anything client side
		if (event.world.isClientSide) {
			return;
		}
		if (!event.world.isClientSide && event.phase == TickEvent.Phase.END) {
			event.world.players().forEach(player -> {
				if (player instanceof ServerPlayer serverPlayer) {
					IThirst stat = serverPlayer.getCapability(TelluriaCapabilities.THIRST).orElse(null);
					
					stat.update(serverPlayer, event.world, event.phase);
					
					int thirst = serverPlayer.getCapability(TelluriaCapabilities.THIRST).map(IThirst::getThirst)
							.orElse(-1);
					float hydration = serverPlayer.getCapability(TelluriaCapabilities.THIRST).map(IThirst::getHydration)
							.orElse(-1f);
					float exhaustion = serverPlayer.getCapability(TelluriaCapabilities.THIRST)
							.map(IThirst::getExhaustion).orElse(-1f);
					PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
							new ClientboundThirstUpdatePacket(thirst, hydration, exhaustion));
				}
			});
		}
	}

}
