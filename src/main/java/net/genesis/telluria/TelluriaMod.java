package net.genesis.telluria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

<<<<<<< HEAD
import net.genesis.telluria.block.TelluriaBlocks;
import net.genesis.telluria.item.TelluriaItems;
=======
import net.genesis.telluria.block.ModBlocks;
import net.genesis.telluria.capabilities.thirst.Thirst;
import net.genesis.telluria.item.ModItems;
>>>>>>> 9103d624e9fd0df747d6dc02b7cf4ff08377b200
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
<<<<<<< HEAD
		TelluriaItems.register(eventBus);
        TelluriaBlocks.register(eventBus);
		
=======
		ModItems.register(eventBus);
        ModBlocks.register(eventBus);

>>>>>>> 9103d624e9fd0df747d6dc02b7cf4ff08377b200
		eventBus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(Thirst.class);
		
        GeckoLib.initialize();

		MinecraftForge.EVENT_BUS.register(this);		
	}
	
	public void setup(final FMLCommonSetupEvent event) {
	}
}
