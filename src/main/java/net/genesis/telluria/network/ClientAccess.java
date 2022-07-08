package net.genesis.telluria.network;

public class ClientAccess {

	private static int thirst;
    private static float hydration;
    private static float exhaustion;
	
	public static void updateThirst(int thirst, float hydration, float exhaustion) {
			ClientAccess.thirst = thirst;
			ClientAccess.hydration = hydration;
			ClientAccess.exhaustion = exhaustion;
	}

	public static int getThirst() {
		return thirst;
	}

	public static float getHydration() {
		return hydration;
	}

	public static float getExhaustion() {
		return exhaustion;
	}
}
