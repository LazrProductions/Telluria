package net.genesis.telluria.event;

import net.genesis.telluria.capabilities.TelluriaCapabilities;
import net.genesis.telluria.capabilities.thirst.ThirstHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ThirstStatHandler {

	@SubscribeEvent
	public static void onPlayerJump(LivingJumpEvent event) {
		Level world = event.getEntity().level;

		if (!world.isClientSide() && event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (!player.isCreative()) {
				ThirstHandler thirstStats = (ThirstHandler) player.getCapability(TelluriaCapabilities.THIRST, null)
						.orElseThrow(IllegalStateException::new);

				if (player.isSprinting()) {
					thirstStats.addExhaustion(0.8F);
				} else {
					thirstStats.addExhaustion(0.2F);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerHurt(LivingHurtEvent event) {
		Level world = event.getEntity().level;

		if (!world.isClientSide() && event.getAmount() != 0.0F) {
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();

				if (!player.isCreative()) {
					ThirstHandler thirstStats = (ThirstHandler) player.getCapability(TelluriaCapabilities.THIRST, null)
							.orElseThrow(IllegalStateException::new);

					thirstStats.addExhaustion(event.getSource().getFoodExhaustion());
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerAttackEntity(AttackEntityEvent event) {
		Level world = event.getEntity().level;
		Entity target = event.getTarget();

		if (!world.isClientSide()) {
			Player player = event.getPlayer();

			if (target.isAttackable() && !player.isCreative()) {
				float attackDamage = (float) player.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
				float weaponAttackDamage = 0.0F;

				if (target instanceof LivingEntity) {
					weaponAttackDamage = EnchantmentHelper.getDamageBonus(player.getMainHandItem(),
							((LivingEntity) target).getMobType());
				} else {
					weaponAttackDamage = EnchantmentHelper.getDamageBonus(player.getMainHandItem(),
							MobType.UNDEFINED);
				}

				if (attackDamage > 0.0F || weaponAttackDamage > 0.0F) {
					boolean canAttack = target.hurt(DamageSource.playerAttack(player), 0.0F);

					if (canAttack) {
						ThirstHandler thirstStats = (ThirstHandler) player.getCapability(TelluriaCapabilities.THIRST, null)
								.orElseThrow(IllegalStateException::new);

						thirstStats.addExhaustion(0.3F);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		LevelAccessor world = event.getWorld();
		Player player = event.getPlayer();
		BlockPos pos = event.getPos();
		BlockState state = event.getState();

		if (!world.isClientSide() && !player.isCreative()) {
			boolean canHarvestBlock = state.getBlock().canHarvestBlock(state, world, pos, player);

			if (canHarvestBlock) {
				ThirstHandler thirstStats = (ThirstHandler) player.getCapability(TelluriaCapabilities.THIRST, null)
						.orElseThrow(IllegalStateException::new);

				thirstStats.addExhaustion(0.025F);
			}
		}
	}

}
