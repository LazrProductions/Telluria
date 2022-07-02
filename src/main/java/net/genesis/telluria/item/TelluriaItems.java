package net.genesis.telluria.item;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.TelluriaBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TelluriaItems {
    
	public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TelluriaMod.MOD_ID);

	///// Register Blocks here
    //Wood
	public static final RegistryObject<Item> WILLOW_SIGN = ITEMS.register("willow_sign", () -> new SignItem(new Item.Properties().tab(TelluriaItemGroups.BLOCKS),TelluriaBlocks.WILLOW_SIGN.get(),TelluriaBlocks.WILLOW_WALL_SIGN.get()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
