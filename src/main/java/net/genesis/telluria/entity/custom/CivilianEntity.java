package net.genesis.telluria.entity.custom;

import net.genesis.telluria.entity.util.civilian.Emotion;
import net.genesis.telluria.entity.util.civilian.Emotions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CivilianEntity extends LivingEntity implements IAnimatable {

    //Villager Traits
    public float extrovert = 1f;                        //A higher extrovert value will require a civilian to be around more civilians to be happy.
    public float dexterity = 1f;                        //A civilian with a higher dexterity will get exhausted slower.

    //Emotions
    public float mentalState = 1;                       //mental happiness
    public float loneliness = 0;                        //social happiness
    public float exhaustion = 1;                        //energy happiness

    public float overallHappiness;                      //Average of all happiness values;


    public Emotion currentEmotion = Emotions.NEUTRAL;   //The current emotion



    // Check Frequencies, to improve performance.
    public int emotionCheckFrequency = 100;              //Ticks after an emotion check to check again, default is 5 seconds between each check
    public int lonelinessCheckFrequency = 200;           //Ticks after an emotion check to check again, default is 10 seconds between each check.

    public float lonelinessDeterioration = 0.03f;        //How much loneliness a civilian gains per check when social requirements are not met. 
    
    ///////////////////////////////////////////////////////////////////////////

    public CivilianEntity(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier setAttributes() {
        return LivingEntity.createLivingAttributes()
            .add(Attributes.MAX_HEALTH, 20.00)
            .add(Attributes.ATTACK_DAMAGE, 2.00f)
            .add(Attributes.ATTACK_SPEED, 2.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {

        return null;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

        
    }

    @Override
    public HumanoidArm getMainArm() {

        return null;
    }



    ////Animations
    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.civilian.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.civilian.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    ////


    int lastEmotionCheck;
    int lastLonelyCheck;

    @Override
    public void tick() {
        //Emotion Check
        if(tickCount - lastEmotionCheck > emotionCheckFrequency) {
            currentEmotion = Emotions.GetEmotionalState(mentalState);        
            lastEmotionCheck = tickCount;
        }

        //Loniliness Check
        if(tickCount - lastLonelyCheck > lonelinessCheckFrequency) {
            int neighbors = level.getEntities(this, new AABB(position().add(new Vec3(-6,-3,-6)), position().add(new Vec3(6,3,6)))).size();
            
            if (neighbors <= 1) {
                loneliness = Mth.clamp(loneliness - (extrovert/3), 0, 2);
            } else {
                // If is near more than one person
                loneliness = Mth.clamp(loneliness + (extrovert * (neighbors / 8)), 0, 2);
            }
            lastLonelyCheck = tickCount;
        }   

        super.tick();
    }
}
