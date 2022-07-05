package net.genesis.telluria.entity.util.civilian;

import java.util.ArrayList;

public class Emotions {
    
    // Saddest
    public static final Emotion SAD = register("sad", -1F, 0F, 0.6F, 0.6F, 0.6F);
    public static final Emotion NEUTRAL = register("neutral", 0F, 1F, 1F, 1F, 1F);
    public static final Emotion HAPPY = register("happy", 0F, 1F, 1F, 1F, 1F);
    //Happiest

    public static final ArrayList<Emotion> EMOTIONS = new ArrayList<>();
    
    //Registering
    public static Emotion register(String id, Float minInclusive, Float maxExclusive, Float speed, Float mining, Float resistance) {
        Emotion e = new Emotion(id, minInclusive, maxExclusive, speed, mining, resistance);
        EMOTIONS.add(e);
        return e;
    }

    /****** Utility Functinos ******/
    public static Emotion GetEmotionalState(Float state) {
        for (Emotion emotion : EMOTIONS) {
            if(state >= emotion.minMentalState && state < emotion.maxMentalState) {
                return emotion;
            }
        }

        return NEUTRAL; //Fallback to neutral emotion if no emotion is found.
    }
}
