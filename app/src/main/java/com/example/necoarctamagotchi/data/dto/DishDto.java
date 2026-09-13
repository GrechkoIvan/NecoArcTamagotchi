package com.example.necoarctamagotchi.data.dto;

public class DishDto {
    private final int imageResId;
    private final StatsDto stats;
    private final int cost;

    public DishDto(int imageResId, StatsDto stats, int cost) {
        this.imageResId = imageResId;
        this.stats = stats;
        this.cost = cost;
    }

    public int getImageResId() {
        return imageResId;
    }

    public StatsDto getStats() {
        return stats;
    }

    public int getCost() {
        return cost;
    }
}
