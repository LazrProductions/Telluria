package net.skystone.telluria;

import net.fabricmc.api.ModInitializer;
import net.skystone.telluria.block.ModBlocks;
import net.skystone.telluria.config.ModConfigs;
import net.skystone.telluria.item.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
