package net.genesis.telluria.client;

import net.fabricmc.api.ClientModInitializer;
import net.genesis.telluria.client.gui.ModUserInterfaceHander;

public class TelluriaClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
    	//Initialize UI Elements
    	ModUserInterfaceHander.init();
        
    }
    
}
