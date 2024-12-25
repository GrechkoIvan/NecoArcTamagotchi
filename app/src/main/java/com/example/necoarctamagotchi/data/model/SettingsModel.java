package com.example.necoarctamagotchi.data.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.necoarctamagotchi.data.preferences.SharedPreferencesManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsModel {
    private final SharedPreferencesManager sharedPreferencesManager;
    private final MutableLiveData<Integer> tickSpeedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> musicEnabledStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> sfxEnabledStateLiveData = new MutableLiveData<>();
    private long lastClosedTimeMillis;

    @Inject
    public SettingsModel(SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        loadSettings();
    }

    private void loadSettings() {
        tickSpeedLiveData.setValue(sharedPreferencesManager.getTickSpeed());
        musicEnabledStateLiveData.setValue(sharedPreferencesManager.getMusicEnabledState());
        sfxEnabledStateLiveData.setValue(sharedPreferencesManager.getSfxEnabledState());
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

    public LiveData<Boolean> getMusicEnabledStateLiveData() {
        return musicEnabledStateLiveData;
    }

    public void updateMusicEnabledState(boolean isMusicEnabled) {
        musicEnabledStateLiveData.setValue(isMusicEnabled);
        sharedPreferencesManager.saveMusicEnabledState(isMusicEnabled);
    }

    public LiveData<Boolean> getSfxEnabledStateLiveData() {
        return sfxEnabledStateLiveData;
    }

    public void updateSfxEnabledState(boolean isSfxEnabled) {
        sfxEnabledStateLiveData.setValue(isSfxEnabled);
        sharedPreferencesManager.saveSfxEnabledState(isSfxEnabled);
    }
}
