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
    private final MutableLiveData<Integer> hunger = new MutableLiveData<>();
    private final MutableLiveData<Integer> happiness = new MutableLiveData<>();
    private final MutableLiveData<Integer> energy = new MutableLiveData<>();
    private final MutableLiveData<Integer> health = new MutableLiveData<>();
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

    public LiveData<Integer> getHunger() {
        return hunger;
    }

    public void increaseHunger(int amount) {
        Integer hungerValue = Optional.ofNullable(hunger.getValue()).orElse(100);
        int newHunger = Math.min(100, hungerValue + amount);
        hunger.setValue(newHunger);
        preferencesManager.saveHunger(newHunger);
    }

    public void decreaseHunger(int amount) {
        Integer hungerValue = Optional.ofNullable(hunger.getValue()).orElse(100);
        int newHunger = Math.max(0, hungerValue - amount);
        hunger.setValue(newHunger);
        preferencesManager.saveHunger(newHunger);
    }

    public LiveData<Integer> getHappiness() {
        return happiness;
    }

    public void increaseHappiness(int amount) {
        Integer happinessValue = Optional.ofNullable(happiness.getValue()).orElse(100);
        int newHappiness = Math.min(100, happinessValue + amount);
        happiness.setValue(newHappiness);
        preferencesManager.saveHappiness(newHappiness);
    }

    public void decreaseHappiness(int amount) {
        Integer happinessValue = Optional.ofNullable(happiness.getValue()).orElse(100);
        int newHappiness = Math.max(0, happinessValue - amount);
        happiness.setValue(newHappiness);
        preferencesManager.saveHappiness(newHappiness);
    }

    public LiveData<Integer> getEnergy() {
        return energy;
    }

    public void increaseEnergy(int amount) {
        Integer energyValue = Optional.ofNullable(energy.getValue()).orElse(100);
        int newEnergy = Math.min(100, energyValue + amount);
        energy.setValue(newEnergy);
        preferencesManager.saveEnergy(newEnergy);
    }

    public void decreaseEnergy(int amount) {
        Integer energyValue = Optional.ofNullable(energy.getValue()).orElse(100);
        int newEnergy = Math.max(0, energyValue - amount);
        energy.setValue(newEnergy);
        preferencesManager.saveEnergy(newEnergy);
    }

    public LiveData<Integer> getHealth() {
        return health;
    }

    public void increaseHealth(int amount) {
        Integer healthValue = Optional.ofNullable(health.getValue()).orElse(100);
        int newHealth = Math.min(100, healthValue + amount);
        health.setValue(newHealth);
        preferencesManager.saveHealth(newHealth);
    }

    public void decreaseHealth(int amount) {
        Integer healthValue = Optional.ofNullable(health.getValue()).orElse(100);
        int newHealth = Math.max(0, healthValue - amount);
        health.setValue(newHealth);
        preferencesManager.saveHealth(newHealth);
    }
}
