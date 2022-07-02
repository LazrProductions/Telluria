package net.genesis.telluria.client;

import net.genesis.telluria.util.TelluriaColorManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ColorHandlerEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TelluriaClientEventsHandler {
    @SubscribeEvent
    public static void telluria_onColorHandlerEvent$Block(ColorHandlerEvent.Block event) {
        TelluriaColorManager.onBlockColorsInit(event.getBlockColors());
    }

    @SubscribeEvent
    public static void telluria_onColorHandlerEvent$Item(ColorHandlerEvent.Item event) {
        TelluriaColorManager.onItemColorsInit(event.getBlockColors(), event.getItemColors());
    }

}
