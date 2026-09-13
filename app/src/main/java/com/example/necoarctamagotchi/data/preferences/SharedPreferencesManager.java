package com.example.necoarctamagotchi.data.preferences;

import android.content.SharedPreferences;

import com.example.necoarctamagotchi.data.skins.DefaultSkin;
import com.example.necoarctamagotchi.data.skins.Skin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesManager {
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
        return sharedPreferences.getInt("tickSpeed", 60000);
    }

    public void saveLastClosedTime(long millis) {
        sharedPreferences.edit().putLong("lastClosedTime", millis).apply();
    }

    public long getLastClosedTime() {
        return sharedPreferences.getLong("lastClosedTime", System.currentTimeMillis());
    }

    public void saveSleepingState(boolean isSleeping) {
        sharedPreferences.edit().putBoolean("isSleeping", isSleeping).apply();
    }

    public boolean getSleepingState() {
        return sharedPreferences.getBoolean("isSleeping", false);
    }

    public void saveSkin(Skin skin) {
        sharedPreferences.edit().putString("skin", skin.getClass().getSimpleName()).apply();
    }

    public Skin getSkin() {
        String skinClassName = sharedPreferences.getString("skin", DefaultSkin.class.getSimpleName());
        try {
            return (Skin) Class.forName(skinClassName).newInstance();
        } catch (Exception e) {
            return new DefaultSkin();
        }
    }

    public void saveMusicEnabledState(boolean isMusicEnabled) {
        sharedPreferences.edit().putBoolean("isMusicEnabled", isMusicEnabled).apply();
    }

    public boolean getMusicEnabledState() {
        return sharedPreferences.getBoolean("isMusicEnabled", true);
    }

    public void saveSfxEnabledState(boolean isMusicEnabled) {
        sharedPreferences.edit().putBoolean("isSfxEnabled", isMusicEnabled).apply();
    }

    public boolean getSfxEnabledState() {
        return sharedPreferences.getBoolean("isSfxEnabled", true);
    }

    public int getMoneyCount() {
        return sharedPreferences.getInt("money", 0);
    }

    public void saveMoneyCount(int money) {
        sharedPreferences.edit().putInt("money", money).apply();
    }
}
