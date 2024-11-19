package com.example.necoarctamagotchi.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREFS_NAME = "GamePrefs";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveHunger(int hunger) {
        sharedPreferences.edit().putInt("hunger", hunger).apply();
    }

    public int getHunger() {
        return sharedPreferences.getInt("hunger", 100);
    }

    public void saveHappiness(int happiness) {
        sharedPreferences.edit().putInt("happiness", happiness).apply();
    }

    public int getHappiness() {
        return sharedPreferences.getInt("happiness", 100);
    }

    public void saveEnergy(int energy) {
        sharedPreferences.edit().putInt("energy", energy).apply();
    }

    public int getEnergy() {
        return sharedPreferences.getInt("energy", 100);
    }

    public void saveHealth(int health) {
        sharedPreferences.edit().putInt("health", health).apply();
    }

    public int getHealth() {
        return sharedPreferences.getInt("health", 100);
    }

    public void saveTickSpeed(int speed) {
        sharedPreferences.edit().putInt("tickSpeed", speed).apply();
    }

    public int getTickSpeed() {
        return sharedPreferences.getInt("tickSpeed", 5000); // Значение по умолчанию
    }
}
