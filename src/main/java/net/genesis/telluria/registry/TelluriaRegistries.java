package net.genesis.telluria.registry;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.TelluriaBlocks;

public class TelluriaRegistries {
    public static void registerTelluria() {
        registerFuels();
        registerStrippables();
        registerFlammableBlock();
    }

    public static void registerFuels(){
        TelluriaMod.LOGGER.info("Registering fuels for " + TelluriaMod.MOD_ID);
        FuelRegistry registry = FuelRegistry.INSTANCE;
        
        registry.add(TelluriaBlocks.WILLOWLOG.asItem(), 300);
        registry.add(TelluriaBlocks.STRIPPEDWILLOWLOG.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWWOOD.asItem(), 300);
        registry.add(TelluriaBlocks.STRIPPEDWILLOWWOOD.asItem(), 300);
        //registry.add(TelluriaBlocks.WILLOWSAPLING.asItem(), 100);

        registry.add(TelluriaBlocks.WILLOWPLANKS.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWPREASUREPLATE.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWBUTTON.asItem(), 100);
        registry.add(TelluriaBlocks.WILLOWFENCE.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWFENCEGATE.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWDOOR.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWTRAPDOOR.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWSLAB.asItem(), 150);
        registry.add(TelluriaBlocks.WILLOWSTAIRS.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWSIGN.asItem(), 300);
        registry.add(TelluriaBlocks.WILLOWWALLSIGN.asItem(), 300);
    }

    public static void registerStrippables() {
        TelluriaMod.LOGGER.info("Registering strippables for " + TelluriaMod.MOD_ID);

        StrippableBlockRegistry.register(TelluriaBlocks.WILLOWLOG, TelluriaBlocks.STRIPPEDWILLOWLOG);
        StrippableBlockRegistry.register(TelluriaBlocks.WILLOWWOOD, TelluriaBlocks.STRIPPEDWILLOWWOOD);
    }

    public static void registerFlammableBlock() {
        TelluriaMod.LOGGER.info("Registering flammable blocks for " + TelluriaMod.MOD_ID);
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(TelluriaBlocks.WILLOWLOG, 5, 5);
        registry.add(TelluriaBlocks.STRIPPEDWILLOWLOG, 5, 5);
        registry.add(TelluriaBlocks.WILLOWWOOD, 5, 5);
        registry.add(TelluriaBlocks.STRIPPEDWILLOWWOOD, 5, 5);
        registry.add(TelluriaBlocks.WILLOWLEAVES, 30, 60);

        registry.add(TelluriaBlocks.WILLOWPLANKS, 5, 20);
        registry.add(TelluriaBlocks.WILLOWPREASUREPLATE, 5, 20);
        registry.add(TelluriaBlocks.WILLOWBUTTON, 5, 20);
        registry.add(TelluriaBlocks.WILLOWFENCE, 5, 20);
        registry.add(TelluriaBlocks.WILLOWFENCEGATE, 5, 20);
        registry.add(TelluriaBlocks.WILLOWDOOR, 5, 20);
        registry.add(TelluriaBlocks.WILLOWTRAPDOOR, 5, 20);
        registry.add(TelluriaBlocks.WILLOWSLAB, 5, 20);
        registry.add(TelluriaBlocks.WILLOWSTAIRS, 5, 20);
        registry.add(TelluriaBlocks.WILLOWSIGN, 5, 20);
        registry.add(TelluriaBlocks.WILLOWWALLSIGN, 5, 20);

    }
}
