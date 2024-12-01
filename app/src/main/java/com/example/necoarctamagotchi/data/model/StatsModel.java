package com.example.necoarctamagotchi.data.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.preferences.SharedPreferencesManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StatsModel {
    private final SharedPreferencesManager sharedPreferencesManager;
    private final MutableLiveData<StatsDto> statsLiveData = new MutableLiveData<>();

    @Inject
    public StatsModel(SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        loadStats();
    }

    private void loadStats() {
        float hunger = sharedPreferencesManager.getHunger();
        float happiness = sharedPreferencesManager.getHappiness();
        float energy = sharedPreferencesManager.getEnergy();
        float health = sharedPreferencesManager.getHealth();
        statsLiveData.setValue(new StatsDto(hunger, happiness, energy, health));
    }

    public LiveData<StatsDto> getStatsLiveData() {
        return statsLiveData;
    }

    public void updateStats(StatsDto newStats) {
        float hunger = newStats.getHunger();
        float happiness = newStats.getHappiness();
        float energy = newStats.getEnergy();
        float health = newStats.getHealth();

        if (hunger < 0) {
            hunger = 0;
        } else if (hunger > 100) {
            hunger = 100;
        }

        if (happiness < 0) {
            happiness = 0;
        } else if (happiness > 100) {
            happiness = 100;
        }

        if (energy < 0) {
            energy = 0;
        } else if (energy > 100) {
            energy = 100;
        }

        if (health < 0) {
            health = 0;
        } else if (health > 100) {
            health = 100;
        }

        StatsDto stats = new StatsDto(hunger, happiness, energy, health);
        statsLiveData.setValue(stats);
        sharedPreferencesManager.saveHunger(hunger);
        sharedPreferencesManager.saveHappiness(happiness);
        sharedPreferencesManager.saveEnergy(energy);
        sharedPreferencesManager.saveHealth(health);
    }
}
