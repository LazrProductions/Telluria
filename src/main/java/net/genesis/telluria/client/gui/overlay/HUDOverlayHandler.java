package net.genesis.telluria.client.gui.overlay;

import java.util.Random;
import java.util.Vector;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.event.HUDOverlayEvent;
import net.genesis.telluria.util.IntPoint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class HUDOverlayHandler
{
	public static HUDOverlayHandler INSTANCE;

	private float unclampedFlashAlpha = 0f;
	private float flashAlpha = 0f;
	private byte alphaDir = 1;
	private int foodIconsOffset;

	public int FOOD_BAR_HEIGHT = 39;

	public final Vector<IntPoint> healthBarOffsets = new Vector<>();
	public final Vector<IntPoint> foodBarOffsets = new Vector<>();

	private final Random random = new Random();
	private final Identifier modIcons = new Identifier(TelluriaMod.MOD_ID, "textures/gui/icons.png");

	public static void init()
	{
		INSTANCE = new HUDOverlayHandler();
		TelluriaMod.LOGGER.info("The HUD Overlay is Initialized!");;
	}

	public void onRender(MatrixStack matrixStack)
	{
		MinecraftClient mc = MinecraftClient.getInstance();
		PlayerEntity player = mc.player;
		assert player != null;
		HungerManager stats = player.getHungerManager();

		int top = mc.getWindow().getScaledHeight() - (foodIconsOffset * 2);
		int left = mc.getWindow().getScaledWidth() / 2 - 91; // left of health bar
		int right = mc.getWindow().getScaledWidth() / 2 + 91; // right of food bar

		// generate at the beginning to avoid ArrayIndexOutOfBoundsException
		generateBarOffsets(top, left, right, mc.inGameHud.getTicks(), player);

		// notify everyone that we should render saturation hud overlay
		HUDOverlayEvent.Thirst saturationRenderEvent = new HUDOverlayEvent.Thirst(stats.getFoodLevel(), right, top, matrixStack);

		// notify everyone that we should render saturation hud overlay
		if (!saturationRenderEvent.isCanceled)
			HUDOverlayEvent.Thirst.EVENT.invoker().interact(saturationRenderEvent);

		// draw saturation overlay
		if (!saturationRenderEvent.isCanceled)
			drawSaturationOverlay(saturationRenderEvent, mc, 0, 1F);
	}

	public void drawSaturationOverlay(MatrixStack matrixStack, float saturationGained, float saturationLevel, MinecraftClient mc, int right, int top, float alpha)
	{
		if (saturationLevel + saturationGained < 0)
			return;

		enableAlpha(alpha);
		RenderSystem.setShaderTexture(0, modIcons);

		float modifiedSaturation = Math.max(0, Math.min(saturationLevel + saturationGained, 20));

		int startSaturationBar = 0;
		int endSaturationBar = (int) Math.ceil(modifiedSaturation / 2.0F);

		// when require rendering the gained saturation, start should relocation to current saturation tail.
		if (saturationGained != 0)
			startSaturationBar = (int) Math.max(saturationLevel / 2.0F, 0);

		int iconSize = 9;

		for (int i = startSaturationBar; i < endSaturationBar; ++i)
		{
			// gets the offset that needs to be render of icon
			IntPoint offset = foodBarOffsets.get(i);
			if (offset == null)
				continue;

			int x = right + offset.x;
			int y = top + offset.y;

			int v = 0;
			int u = 0;

			float effectiveSaturationOfBar = (modifiedSaturation / 2.0F) - i;

			if (effectiveSaturationOfBar >= 1)
				u = 3 * iconSize;
			else if (effectiveSaturationOfBar > .5)
				u = 2 * iconSize;
			else if (effectiveSaturationOfBar > .25)
				u = 1 * iconSize;

			mc.inGameHud.drawTexture(matrixStack, x, y, u, v, iconSize, iconSize);
		}

		// rebind default icons
		RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_TEXTURE);
		disableAlpha(alpha);
	}


	private void drawSaturationOverlay(HUDOverlayEvent.Thirst event, MinecraftClient mc, float saturationGained, float alpha)
	{
		drawSaturationOverlay(event.matrixStack, saturationGained, event.thirstLevel, mc, event.x, event.y, alpha);
	}

	private void enableAlpha(float alpha)
	{
		RenderSystem.enableBlend();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
	}

	private void disableAlpha(float alpha)
	{
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void generateBarOffsets(int top, int left, int right, int ticks, PlayerEntity player)
	{
		final int preferHealthBars = 10;
		final int preferFoodBars = 10;

		final float maxHealth = player.getMaxHealth();
		final float absorptionHealth = (float) Math.ceil(player.getAbsorptionAmount());

		int healthBars = (int) Math.ceil((maxHealth + absorptionHealth) / 2.0F);
		int healthRows = (int) Math.ceil((float) healthBars / (float) preferHealthBars);

		int healthRowHeight = Math.max(10 - (healthRows - 2), 3);

		boolean shouldAnimatedHealth = false;
		boolean shouldAnimatedFood = false;

		// hard code in `InGameHUD`
		random.setSeed((long) (ticks * 312871));

		// Special case for infinite/NaN. Infinite absorption has been seen in the wild.
		// This will effectively disable rendering while health is infinite.
		if (!Float.isFinite(maxHealth + absorptionHealth))
			healthBars = 0;

		// adjust the size
		if (healthBarOffsets.size() != healthBars)
			healthBarOffsets.setSize(healthBars);

		if (foodBarOffsets.size() != preferFoodBars)
			foodBarOffsets.setSize(preferFoodBars);

		// left alignment, multiple rows, reverse
		for (int i = healthBars - 1; i >= 0; --i)
		{
			int row = (int) Math.ceil((float) (i + 1) / (float) preferHealthBars) - 1;
			int x = left + i % preferHealthBars * 8;
			int y = top - row * healthRowHeight;
			// apply the animated offset
			if (shouldAnimatedHealth)
				y += random.nextInt(2);

			// reuse the point object to reduce memory usage
			IntPoint point = healthBarOffsets.get(i);
			if (point == null)
			{
				point = new IntPoint();
				healthBarOffsets.set(i, point);
			}

			point.x = x - left;
			point.y = y - top;
		}

		// right alignment, single row
		for (int i = 0; i < preferFoodBars; ++i)
		{
			int x = right - i * 8 - 9;
			int y = top;

			// apply the animated offset
			if (shouldAnimatedFood)
				y += random.nextInt(3) - 1;

			// reuse the point object to reduce memory usage
			IntPoint point = foodBarOffsets.get(i);
			if (point == null)
			{
				point = new IntPoint();
				foodBarOffsets.set(i, point);
			}

			point.x = x - right;
			point.y = y - top;
		}
	}
}