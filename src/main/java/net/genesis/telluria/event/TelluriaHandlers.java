package net.genesis.telluria.event;

import net.genesis.telluria.capabilities.TelluriaCapabilities;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

public class TelluriaHandlers {

	public static void init() {
		//Events For Thirst
        MinecraftForge.EVENT_BUS.addListener(TelluriaCapabilities::register);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, StatEventHandler::onAttachCapabilitiesPlayer);
        MinecraftForge.EVENT_BUS.addListener(StatEventHandler::onPlayerCloned);
        MinecraftForge.EVENT_BUS.addListener(StatEventHandler::onWorldTick);
		MinecraftForge.EVENT_BUS.register(ThirstStatHandler.class);
		MinecraftForge.EVENT_BUS.register(DrinkHandler.class);
	}
	
}
