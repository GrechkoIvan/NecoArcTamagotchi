package com.example.necoarctamagotchi.data.dto;

public class DishDto {
    private final int imageResId;
    private final StatsDto stats;

    public DishDto(int imageResId, StatsDto stats) {
        this.imageResId = imageResId;
        this.stats = stats;
    }

    public int getImageResId() {
        return imageResId;
    }

    public StatsDto getStats() {
        return stats;
    }
}
