package net.genesis.telluria;

import static net.minecraftforge.client.gui.ForgeIngameGui.FOOD_LEVEL_ELEMENT;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.genesis.telluria.block.TelluriaBlocks;
import net.genesis.telluria.client.gui.overlay.ThirstOverlay;
import net.genesis.telluria.event.PacketHandler;
import net.genesis.telluria.event.TelluriaHandlers;
import net.genesis.telluria.item.TelluriaItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(TelluriaMod.MOD_ID)
public class TelluriaMod {
    public static final String MOD_ID = "telluria";
	public static final Logger LOGGER = LogManager.getLogger();

	public TelluriaMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register Blocks and Items
		TelluriaItems.register(eventBus);
        TelluriaBlocks.register(eventBus);
		
        eventBus.addListener(this::setup);	
        eventBus.addListener(this::clientSetup);
		
        TelluriaHandlers.init();

		GeckoLib.initialize();

		
		MinecraftForge.EVENT_BUS.register(this);		
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(TelluriaBlocks.BULRUSH.get(), RenderType.cutout());

		OverlayRegistry.registerOverlayAbove(FOOD_LEVEL_ELEMENT, "thirst_overlay", new ThirstOverlay());
		
		//Register Block Colors

	}

	public void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(PacketHandler::init);
	}


}
