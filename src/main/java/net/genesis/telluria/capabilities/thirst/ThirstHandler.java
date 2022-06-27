package net.genesis.telluria.capabilities.thirst;

import net.genesis.telluria.capabilities.StatHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.Phase;

public class ThirstHandler extends StatHandler implements IThirst{

	private int thirstLevel;
    private int prevThirstLevel;
    private float thirstHydrationLevel;
    private float thirstExhaustionLevel;
    
    private int thirstDamageTimer;
	
	public ThirstHandler() {
		this.thirstLevel = 20;
		this.thirstHydrationLevel = 5.0f;
	}
	
	@Override
	public void update(Player player, Level world, Phase phase) {
		if(player.isCreative())
			return;

        Difficulty enumdifficulty = world.getDifficulty();

        if (this.thirstExhaustionLevel > 4.0F)
        {
            this.thirstExhaustionLevel -= 4.0F;

            if (this.thirstHydrationLevel > 0.0F)
            {
                this.thirstHydrationLevel = Math.max(this.thirstHydrationLevel - 1.0F, 0.0F);
            }
            else if (enumdifficulty != Difficulty.PEACEFUL)
            {
                this.thirstLevel = Math.max(this.thirstLevel - 1, 0);
            }
        }

        if (this.thirstLevel <= 0)
        {
            ++this.thirstDamageTimer;

            if (this.thirstDamageTimer >= 80)
            {
            	if ((enumdifficulty == Difficulty.EASY && player.getHealth() > 10.0F) || (enumdifficulty == Difficulty.NORMAL && player.getHealth() > 1.0F) || enumdifficulty == Difficulty.HARD)
            	{
                    player.hurt(DamageSource.STARVE, 1.0F);
                }

                this.thirstDamageTimer = 0;
            }
        }
        else
        {
            this.thirstDamageTimer = 0;
        }
        if (player.isSprinting() && thirstLevel <= 6)
        {
            player.setSprinting(false);
        }
	}

	@Override
	public boolean hasChanged() {
        return this.prevThirstLevel != this.thirstLevel;
	}

	@Override
	public void setThirst(int thirst) {
		this.thirstLevel = thirst;
	}

	@Override
	public void setHydration(float hydration) {
		this.thirstHydrationLevel = hydration;
	}

	@Override
	public void setExhaustion(float exhaustion) {
		this.thirstExhaustionLevel = exhaustion;
	}

	@Override
	public int getThirst() {
		return this.thirstLevel;
	}

	@Override
	public float getHydration() {
		return this.thirstHydrationLevel;
	}

	@Override
	public float getExhaustion() {
		return this.thirstExhaustionLevel;
	}

	@Override
	public void setChangeTime(int ticks) {
		this.thirstDamageTimer = ticks;
	}

	@Override
	public int getChangeTime() {
		return this.thirstDamageTimer;
	}
	
	@Override
	public void addStats(int thirst, float hydration) {
		this.thirstLevel = Math.min(thirst + this.thirstLevel, 20);
		this.thirstHydrationLevel = Math.min(this.thirstHydrationLevel + (float) thirst * hydration * 2.0f, (float)this.thirstLevel);
	}
	
	public void addExhaustion(float amount)
    {
		this.thirstExhaustionLevel = Math.min(this.thirstExhaustionLevel + amount, 40.0F);
    }
    
    public boolean isThirsty()
    {
        return this.thirstLevel < 20;
    }
    
    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putInt("thirstLevel", this.thirstLevel);
        tag.putFloat("thirstHydrationLevel", this.thirstHydrationLevel);
        tag.putFloat("thirstExhaustionLevel", this.thirstExhaustionLevel);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.thirstLevel = nbt.getInt("thirstLevel");
        this.thirstHydrationLevel = nbt.getFloat("thirstHydrationLevel");
        this.thirstExhaustionLevel = nbt.getFloat("thirstExhaustionLevel");
    }

}
