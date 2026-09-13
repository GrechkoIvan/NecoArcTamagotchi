package com.example.necoarctamagotchi.data.dto;

public class StatsDto {
    private final float hunger;
    private final float happiness;
    private final float energy;
    private final float health;

    public StatsDto(float hunger, float happiness, float energy, float health) {
        this.hunger = hunger;
        this.happiness = happiness;
        this.energy = energy;
        this.health = health;
    }

    public float getHunger() {
        return hunger;
    }

    public float getHappiness() {
        return happiness;
    }

    public float getEnergy() {
        return energy;
    }

    public float getHealth() {
        return health;
    }
}
