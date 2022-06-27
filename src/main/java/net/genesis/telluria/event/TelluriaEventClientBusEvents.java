package net.genesis.telluria.event;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.client.gui.overlay.ThirstOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TelluriaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TelluriaEventClientBusEvents {

	@SubscribeEvent
	public static void clientSetup(final FMLClientSetupEvent event) {
		TelluriaMod.LOGGER.info("Client Setup is running for " + TelluriaMod.MOD_ID);
		OverlayRegistry.registerOverlayTop("thirst_overlay", new ThirstOverlay());
	}
	
}
