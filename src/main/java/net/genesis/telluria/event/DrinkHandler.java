package net.genesis.telluria.event;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.capabilities.TelluriaCapabilities;
import net.genesis.telluria.capabilities.thirst.IThirst;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DrinkHandler {

	@SubscribeEvent
	public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
		if(event.getEntityLiving() instanceof Player) {
			Player player = (Player)event.getEntityLiving();
			ItemStack heldItem = event.getItem();
			IThirst thirstHandler = player.getCapability(TelluriaCapabilities.THIRST).orElse(null);
			
			if(thirstHandler.isThirsty()) {
				if(heldItem.getItem().equals(Items.POTION)) {
					if(PotionUtils.getPotion(heldItem).getEffects().isEmpty()) {
						thirstHandler.addStats(7, 2f);
					}else {
						thirstHandler.addStats(4, 1f);
					}
				}else if (!(heldItem.getItem() instanceof PotionItem)) {
					
					if(heldItem.getItem().equals(Items.TROPICAL_FISH)) {
						thirstHandler.addStats(1, 0.5f);
					}
					if(heldItem.getItem().equals(Items.COD) || heldItem.getItem().equals(Items.SALMON)) {
						thirstHandler.addStats(3, 0.75f);
					}
					if(heldItem.getItem().equals(Items.PUFFERFISH)) {
						thirstHandler.addStats(2, 0.5f);
					}
					//Insert Config Non-Water cases.
				}
			}
		}
	}
	
}
