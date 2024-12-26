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
    private final MutableLiveData<Boolean> isSleepingLiveData = new MutableLiveData<>();

    public static final float HUNGER_EFFECT = -0.117f;
    public static final float HAPPINESS_EFFECT = -0.133f;
    public static final float HEALTH_EFFECT = -0.06f;
    public static final float ENERGY_EFFECT_SLEEPING = 0.188f;
    public static final float ENERGY_EFFECT_NOT_SLEEPING = -0.096f;


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
        isSleepingLiveData.setValue(sharedPreferencesManager.getSleepingState());
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

    public LiveData<Boolean> getSleepingStateLiveData() {
        return isSleepingLiveData;
    }

    public void updateSleepingState(boolean isSleeping) {
        isSleepingLiveData.setValue(isSleeping);
        sharedPreferencesManager.saveSleepingState(isSleeping);
    }

    public void decreaseStatsTick() {
        StatsDto currentStats = statsLiveData.getValue();

        float newEnergy = currentStats.getEnergy();
        if (Boolean.TRUE.equals(isSleepingLiveData.getValue())) {
            newEnergy += ENERGY_EFFECT_SLEEPING;
        } else {
            newEnergy += ENERGY_EFFECT_NOT_SLEEPING;
        }
        float newHunger = currentStats.getHunger() + HUNGER_EFFECT;
        float newHappiness = currentStats.getHappiness() + HAPPINESS_EFFECT;

        float newHealth = currentStats.getHealth();
        if (newHunger < 0) {
            newHealth += HEALTH_EFFECT;
        }

        StatsDto newStats = new StatsDto(
                newHunger,
                newHappiness,
                newEnergy,
                newHealth
        );
        updateStats(newStats);
    }
}
