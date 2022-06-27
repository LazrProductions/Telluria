package net.genesis.telluria.world.gen;

import net.genesis.telluria.TelluriaMod;

public class TelluriaWorldGen {
    public static void generateTelluriaWorldGen() {
        TelluriaMod.LOGGER.info("Registering Telluria world generation.");

        TelluriaTreeGeneration.generateTrees();
    }
}
