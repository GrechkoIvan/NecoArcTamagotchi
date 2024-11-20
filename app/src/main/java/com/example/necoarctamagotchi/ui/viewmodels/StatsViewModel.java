package com.example.necoarctamagotchi.ui.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.data.preferences.SharedPreferencesManager;

import java.util.Optional;

public class StatsViewModel extends ViewModel {
    private final SharedPreferencesManager preferencesManager;
    private final MutableLiveData<Float> hunger = new MutableLiveData<>();
    private final MutableLiveData<Float> happiness = new MutableLiveData<>();
    private final MutableLiveData<Float> energy = new MutableLiveData<>();
    private final MutableLiveData<Float> health = new MutableLiveData<>();
    private final MediatorLiveData<Void> updateSignal = new MediatorLiveData<>();

    public StatsViewModel(Context context) {
        this.preferencesManager = new SharedPreferencesManager(context);
        loadStatsFromPreferences();

        updateSignal.addSource(hunger, value -> updateSignal.setValue(null));
        updateSignal.addSource(happiness, value -> updateSignal.setValue(null));
        updateSignal.addSource(energy, value -> updateSignal.setValue(null));
        updateSignal.addSource(health, value -> updateSignal.setValue(null));
    }

    private void loadStatsFromPreferences() {
        hunger.setValue(preferencesManager.getHunger());
        happiness.setValue(preferencesManager.getHappiness());
        energy.setValue(preferencesManager.getEnergy());
        health.setValue(preferencesManager.getHealth());
    }

    public LiveData<Void> getUpdateSignal() {
        return updateSignal;
    }

    public LiveData<Float> getHunger() {
        return hunger;
    }

    public void updateHunger(float amount) {
        Float hungerValue = Optional.ofNullable(hunger.getValue()).orElse(100f);
        float newHunger = hungerValue + amount;
        if (newHunger < 0) {
            newHunger = 0;
        } else if (newHunger > 100) {
            newHunger = 100;
        }

        hunger.setValue(newHunger);
        preferencesManager.saveHunger(newHunger);
    }

    public LiveData<Float> getHappiness() {
        return happiness;
    }

    public void updateHappiness(float amount) {
        Float happinessValue = Optional.ofNullable(happiness.getValue()).orElse(100f);
        float newHappiness = happinessValue + amount;
        if (newHappiness < 0) {
            newHappiness = 0;
        } else if (newHappiness > 100) {
            newHappiness = 100;
        }

        happiness.setValue(newHappiness);
        preferencesManager.saveHappiness(newHappiness);
    }

    public LiveData<Float> getEnergy() {
        return energy;
    }

    public void updateEnergy(float amount) {
        Float energyValue = Optional.ofNullable(energy.getValue()).orElse(100f);
        float newEnergy = energyValue + amount;
        if (newEnergy < 0) {
            newEnergy = 0;
        } else if (newEnergy > 100) {
            newEnergy = 100;
        }

        energy.setValue(newEnergy);
        preferencesManager.saveEnergy(newEnergy);
    }

    public LiveData<Float> getHealth() {
        return health;
    }

    public void updateHealth(float amount) {
        Float healthValue = Optional.ofNullable(health.getValue()).orElse(100f);
        float newHealth = healthValue + amount;
        if (newHealth < 0) {
            newHealth = 0;
        } else if (newHealth > 100) {
            newHealth = 100;
        }

        health.setValue(newHealth);
        preferencesManager.saveHealth(newHealth);
    }
}
