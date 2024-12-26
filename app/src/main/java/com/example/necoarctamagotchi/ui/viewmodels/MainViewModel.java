package com.example.necoarctamagotchi.ui.viewmodels;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.data.dto.AnimationDto;
import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.model.CharacterAppearanceModel;
import com.example.necoarctamagotchi.data.model.SettingsModel;
import com.example.necoarctamagotchi.data.model.StatsModel;
import com.example.necoarctamagotchi.utils.NotificationScheduler;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final StatsModel statsModel;
    private final SettingsModel settingsModel;
    private final CharacterAppearanceModel characterAppearanceModel;
    private final LiveData<StatsDto> statsLiveData;
    private final Handler statsUpdateHandler = new Handler(Looper.getMainLooper());
    private final Runnable statsUpdateRunnable;
    private int tickSpeed;
    private final Observer<Integer> tickSpeedObserver;

    @Inject
    public MainViewModel(
            StatsModel statsModel,
            SettingsModel settingsModel,
            CharacterAppearanceModel characterAppearanceModel) {

        this.settingsModel = settingsModel;
        this.statsModel = statsModel;
        this.characterAppearanceModel = characterAppearanceModel;

        tickSpeedObserver = tickSpeed -> {
            this.tickSpeed = tickSpeed;
        };

        settingsModel.getTickSpeedLiveData().observeForever(tickSpeedObserver);

        this.statsLiveData = statsModel.getStatsLiveData();
        statsUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                statsModel.decreaseStatsTick();
                statsUpdateHandler.postDelayed(this, tickSpeed);
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        settingsModel.getTickSpeedLiveData().removeObserver(tickSpeedObserver);
    }

    public LiveData<StatsDto> getStatsLiveData() {
        return statsLiveData;
    }

    public LiveData<Boolean> getSleepingStateLiveData() {
        return statsModel.getSleepingStateLiveData();
    }

    public LiveData<AnimationDto> getAnimationLiveData() {
        return characterAppearanceModel.getAnimationLiveData();
    }

    public void startRealTimeStatsUpdating() {
        statsUpdateHandler.post(statsUpdateRunnable);
    }

    public void startBackgroundStatsUpdating() {
        settingsModel.updateLastClosedTimeMillis(System.currentTimeMillis());
    }

    public void stopRealTimeStatsUpdating() {
        statsUpdateHandler.removeCallbacks(statsUpdateRunnable);
    }

    public void stopBackgroundStatsUpdating() {
        long currentTime = System.currentTimeMillis();
        long lastClosedTime = settingsModel.getLastClosedTimeMillis();
        if (lastClosedTime != 0) {
            long elapsedTime = currentTime - lastClosedTime;
            int ticks = Math.round((float) (elapsedTime / tickSpeed));
            for (int i = 0; i < ticks; i++){
                statsModel.decreaseStatsTick();
            }
        }
    }

    public void scheduleNotification(Context context) {
        StatsDto statsDto = statsModel.getStatsLiveData().getValue();
        long minDelay = (long) ((statsDto.getHunger() - 30) / (-1 * StatsModel.HUNGER_EFFECT));
        if (minDelay < 0) {
            minDelay = 60000;
        }

        long happinessDelay = (long) ((statsDto.getHappiness() - 30) / (-1 * StatsModel.HAPPINESS_EFFECT));
        if (happinessDelay < minDelay && happinessDelay > 0) {
            minDelay = happinessDelay;
        }

        if (!statsModel.getSleepingStateLiveData().getValue()) {
            long energyDelay = (long) ((statsDto.getEnergy() - 30) / (-1 * StatsModel.ENERGY_EFFECT_NOT_SLEEPING));
            if (energyDelay < minDelay && happinessDelay > 0) {
                minDelay = energyDelay;
            }
        }

        NotificationScheduler.scheduleNotification(context, minDelay);
    }

    public void cancelNotification(Context context) {
        NotificationScheduler.cancelNotification(context);
    }
}
