package com.example.necoarctamagotchi.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREFS_NAME = "GamePrefs";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
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
