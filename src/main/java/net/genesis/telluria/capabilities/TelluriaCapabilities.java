package net.genesis.telluria.capabilities;

import net.genesis.telluria.capabilities.thirst.IThirst;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TelluriaCapabilities {
	public static final Capability<IThirst> THIRST = CapabilityManager.get(new CapabilityToken<>() {});

	@SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IThirst.class);
    }

    private TelluriaCapabilities() {
    }
}
