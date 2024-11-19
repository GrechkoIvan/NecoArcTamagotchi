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

    public void increaseHunger(float amount) {
        Float hungerValue = Optional.ofNullable(hunger.getValue()).orElse(100f);
        float newHunger = Math.min(100, hungerValue + amount);
        hunger.setValue(newHunger);
        preferencesManager.saveHunger(newHunger);
    }

    public void decreaseHunger(float amount) {
        Float hungerValue = Optional.ofNullable(hunger.getValue()).orElse(100f);
        float newHunger = Math.max(0, hungerValue - amount);
        hunger.setValue(newHunger);
        preferencesManager.saveHunger(newHunger);
    }

    public LiveData<Float> getHappiness() {
        return happiness;
    }

    public void increaseHappiness(float amount) {
        Float happinessValue = Optional.ofNullable(happiness.getValue()).orElse(100f);
        float newHappiness = Math.min(100, happinessValue + amount);
        happiness.setValue(newHappiness);
        preferencesManager.saveHappiness(newHappiness);
    }

    public void decreaseHappiness(float amount) {
        Float happinessValue = Optional.ofNullable(happiness.getValue()).orElse(100f);
        float newHappiness = Math.max(0, happinessValue - amount);
        happiness.setValue(newHappiness);
        preferencesManager.saveHappiness(newHappiness);
    }

    public LiveData<Float> getEnergy() {
        return energy;
    }

    public void increaseEnergy(float amount) {
        Float energyValue = Optional.ofNullable(energy.getValue()).orElse(100f);
        float newEnergy = Math.min(100, energyValue + amount);
        energy.setValue(newEnergy);
        preferencesManager.saveEnergy(newEnergy);
    }

    public void decreaseEnergy(float amount) {
        Float energyValue = Optional.ofNullable(energy.getValue()).orElse(100f);
        float newEnergy = Math.max(0, energyValue - amount);
        energy.setValue(newEnergy);
        preferencesManager.saveEnergy(newEnergy);
    }

    public LiveData<Float> getHealth() {
        return health;
    }

    public void increaseHealth(float amount) {
        Float healthValue = Optional.ofNullable(health.getValue()).orElse(100f);
        float newHealth = Math.min(100, healthValue + amount);
        health.setValue(newHealth);
        preferencesManager.saveHealth(newHealth);
    }

    public void decreaseHealth(float amount) {
        Float healthValue = Optional.ofNullable(health.getValue()).orElse(100f);
        float newHealth = Math.max(0, healthValue - amount);
        health.setValue(newHealth);
        preferencesManager.saveHealth(newHealth);
    }
}
