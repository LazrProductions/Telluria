package net.genesis.telluria;

import net.fabricmc.api.ModInitializer;
import net.genesis.telluria.block.TelluriaBlocks;
import net.genesis.telluria.config.TelluriaConfigs;
import net.genesis.telluria.item.TelluriaItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelluriaMod implements ModInitializer {
    public static final String MOD_ID = "telluria";
	public static final Logger LOGGER = LoggerFactory.getLogger("telluria");

	@Override
	public void onInitialize() {
		
		// Register the config files and pull the values
		TelluriaConfigs.registerConfigs();

		// Register Blocks and Items
		TelluriaItems.registerModItems();
		TelluriaBlocks.registerModBlocks();
	}
}
