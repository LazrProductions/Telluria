package net.genesis.telluria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.genesis.telluria.block.TelluriaBlocks;
import net.genesis.telluria.item.TelluriaItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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

		GeckoLib.initialize();

		
		MinecraftForge.EVENT_BUS.register(this);		
	}
	
	public void setup(final FMLCommonSetupEvent event) {
	}
}
