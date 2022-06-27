package net.genesis.telluria.capabilities.thirst;

import net.genesis.telluria.capabilities.IPlayerStat;

public interface IThirst extends IPlayerStat{
	public void setThirst(int thirst);
    public void setHydration(float hydration);
    public void setExhaustion(float exhaustion);
    public void addStats(int thirst, float hydration);
    
    public int getThirst();
    public float getHydration();
    public float getExhaustion();

    public void setChangeTime(int ticks);
    public int getChangeTime();
}
