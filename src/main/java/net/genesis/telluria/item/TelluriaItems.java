package net.genesis.telluria.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.TelluriaBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TelluriaItems {
    
    ///// Register Items Here
    
    //Wood
    public static final Item WILLOWSIGN = registerItem("willow_sign",
            new SignItem(new FabricItemSettings().group(TelluriaItemGroups.BLOCKS).maxCount(16), TelluriaBlocks.WILLOWSIGN, TelluriaBlocks.WILLOWWALLSIGN));

    /////


    ///// Registering Items
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TelluriaMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TelluriaMod.LOGGER.debug("Registering mod items for " + TelluriaMod.MOD_ID);
    }
}
