package net.genesis.telluria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.genesis.telluria.block.ModBlocks;
import net.genesis.telluria.config.ModConfigs;
import net.genesis.telluria.item.ModItems;

public class TelluriaMod implements ModInitializer {
    public static final String MOD_ID = "telluria";
	public static final Logger LOGGER = LoggerFactory.getLogger("telluria");

	@Override
	public void onInitialize() {
		
		// Register the config files and pull the values
		ModConfigs.registerConfigs();

		// Register Blocks and Items
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}
