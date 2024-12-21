package com.example.necoarctamagotchi.ui.viewmodels;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.model.SettingsModel;
import com.example.necoarctamagotchi.data.model.StatsModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final StatsModel statsModel;
    private final SettingsModel settingsModel;
    private final LiveData<StatsDto> statsLiveData;
    private final Handler statsUpdateHandler = new Handler(Looper.getMainLooper());
    private final Runnable statsUpdateRunnable;
    private int tickSpeed;
    private final Observer<Integer> tickSpeedObserver;

    @Inject
    public MainViewModel(StatsModel statsModel, SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
        this.statsModel = statsModel;

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
}
