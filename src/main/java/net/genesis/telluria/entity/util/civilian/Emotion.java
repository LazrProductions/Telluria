package net.genesis.telluria.entity.util.civilian;

public class Emotion {
    public String identifier = "";
    public Float minMentalState = 0F;
    public Float maxMentalState = 1F;

    public Float speedMultiplier = 1F;
    public Float miningSpeedMultiplier = 1F;
    public Float resistanceMultiplier = 1F;

    public Emotion(String id, Float minInclusive, Float maxExclusive, Float speed, Float mining, Float resistance) {
        this.identifier = id;
        this.minMentalState = minInclusive;
        this.maxMentalState = maxExclusive;
        this.speedMultiplier = speed;
        this.miningSpeedMultiplier = mining;
        this.resistanceMultiplier = resistance;
    }
}
