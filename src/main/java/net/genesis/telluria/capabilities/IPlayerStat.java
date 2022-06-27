package net.genesis.telluria.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.TickEvent.Phase;

public interface IPlayerStat extends INBTSerializable<CompoundTag>{
	public void update(Player player, Level world, Phase phase);

	public boolean hasChanged();

	public void onSendClientUpdate();
}
