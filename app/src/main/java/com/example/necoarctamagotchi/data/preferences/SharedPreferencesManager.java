package com.example.necoarctamagotchi.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesManager {
    private static final String PREFS_NAME = "GamePrefs";
    private final SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveHunger(float hunger) {
        sharedPreferences.edit().putFloat("hunger", hunger).apply();
    }

    public float getHunger() {
        return sharedPreferences.getFloat("hunger", 100f);
    }

    public void saveHappiness(float happiness) {
        sharedPreferences.edit().putFloat("happiness", happiness).apply();
    }

    public float getHappiness() {
        return sharedPreferences.getFloat("happiness", 100f);
    }

    public void saveEnergy(float energy) {
        sharedPreferences.edit().putFloat("energy", energy).apply();
    }

    public float getEnergy() {
        return sharedPreferences.getFloat("energy", 100f);
    }

    public void saveHealth(float health) {
        sharedPreferences.edit().putFloat("health", health).apply();
    }

    public float getHealth() {
        return sharedPreferences.getFloat("health", 100f);
    }

    public void saveTickSpeed(int speed) {
        sharedPreferences.edit().putInt("tickSpeed", speed).apply();
    }

    public int getTickSpeed() {
        return sharedPreferences.getInt("tickSpeed", 5000);
    }
}
