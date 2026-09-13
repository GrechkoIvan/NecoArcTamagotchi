package com.example.necoarctamagotchi.data.dto;

public class MedicineDto {
    private final int imageResId;
    private final float healthEffect;
    private final int cost;

    public MedicineDto(int imageResId, float healthEffect, int cost) {
        this.healthEffect = healthEffect;
        this.imageResId = imageResId;
        this.cost = cost;
    }

    public int getImageResId() {
        return imageResId;
    }

    public float getHealthEffect() {
        return healthEffect;
    }

    public int getCost() {
        return cost;
    }
}
