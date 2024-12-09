package com.example.necoarctamagotchi.data.dto;

import com.example.necoarctamagotchi.ui.viewmodels.MedicineViewModel;

public class MedicineDto {
    private final int imageResId;
    private final float healthEffect;

    public MedicineDto(int imageResId, float healthEffect) {
        this.healthEffect = healthEffect;
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public float getHealthEffect() {
        return healthEffect;
    }
}
