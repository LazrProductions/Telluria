package net.genesis.telluria.client.gui.overlay;

import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.network.ClientAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class ThirstOverlay implements IIngameOverlay {

	public static final ResourceLocation ICONS_LOCATION = new ResourceLocation(TelluriaMod.MOD_ID,
			"textures/gui/icons.png");
	Minecraft minecraft = Minecraft.getInstance();
	Random random = new Random();

	@Override
	public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
		boolean isMounted = minecraft.player.getVehicle() instanceof LivingEntity;
		if (!isMounted && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
			minecraft.getProfiler().push("thirst");

			gui.setupOverlayRenderState(true, false, ICONS_LOCATION);
			RenderSystem.enableBlend();
			int left = width / 2 + 91;
			int top = height - gui.right_height;
			gui.right_height += 10;
			boolean unused = false;

			int level = ClientAccess.getThirst();
			float hydrationLevel = ClientAccess.getHydration();

			for (int i = 0; i < 10; ++i) {
				int idx = i * 2 + 1;
				int x = left - i * 8 - 9;
				int y = top;
				int icon = 0;
				byte background = 0;

				if (minecraft.player.hasEffect(MobEffects.HUNGER)) {
					icon += 36;
				}
				if (unused)
					background = 1;

				gui.blit(poseStack, x, y, background * 9, 16, 9, 9);

				if (idx < level)
					gui.blit(poseStack, x, y, icon + 9, 16, 9, 9);
				else if (idx == level)
					gui.blit(poseStack, x, y, icon + 18, 16, 9, 9);
				
				if (idx < hydrationLevel)
					gui.blit(poseStack, x, y, icon + 27, 16, 9, 9);
				else if (idx == hydrationLevel)
					gui.blit(poseStack, x, y, icon + 36, 16, 9, 9);
			}
			RenderSystem.disableBlend();
			minecraft.getProfiler().pop();
		}
	}

}
