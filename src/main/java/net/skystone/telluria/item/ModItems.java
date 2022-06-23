package net.skystone.telluria.item;

import net.skystone.telluria.TelluriaMod;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    
    ///// Register Items Here

    

    /////


    ///// Registering Items
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TelluriaMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TelluriaMod.LOGGER.debug("Registering mod items for " + TelluriaMod.MOD_ID);
    }
}
