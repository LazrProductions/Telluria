package net.genesis.telluria.event;

import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class HUDOverlayEvent
{

	/**
	 * If cancelled, will stop all rendering of the saturation overlay.
	 */
	public static class Thirst extends HUDOverlayEvent
	{
		public Thirst(float thirstLevel, int x, int y, MatrixStack matrixStack)
		{
			super(x, y, matrixStack);
			this.thirstLevel = thirstLevel;
		}

		public final float thirstLevel;

		public static Event<EventHandler<Thirst>> EVENT = EventHandler.createArrayBacked();
	}

	private HUDOverlayEvent(int x, int y, MatrixStack matrixStack)
	{
		this.x = x;
		this.y = y;
		this.matrixStack = matrixStack;
	}

	public int x;
	public int y;
	public MatrixStack matrixStack;
	public boolean isCanceled = false;
}