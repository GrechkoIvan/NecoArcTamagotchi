package com.example.necoarctamagotchi.data.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.preferences.SharedPreferencesManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsModel {
    private final SharedPreferencesManager sharedPreferencesManager;
    private final MutableLiveData<Integer> tickSpeedLiveData = new MutableLiveData<>();
    private long lastClosedTimeMillis;

    @Inject
    public SettingsModel(SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        loadSettings();
    }

    private void loadSettings() {
        tickSpeedLiveData.setValue(sharedPreferencesManager.getTickSpeed());
        lastClosedTimeMillis = sharedPreferencesManager.getLastClosedTime();
    }

    public LiveData<Integer> getTickSpeedLiveData() {
        return tickSpeedLiveData;
    }

    public void updateTickSpeed(int tickSpeed) {
        tickSpeedLiveData.setValue(tickSpeed);
        sharedPreferencesManager.saveTickSpeed(tickSpeed);
    }

    public long getLastClosedTimeMillis() {
        return lastClosedTimeMillis;
    }

    public void updateLastClosedTimeMillis(long millis) {
        lastClosedTimeMillis = millis;
        sharedPreferencesManager.saveLastClosedTime(millis);
    }
}
