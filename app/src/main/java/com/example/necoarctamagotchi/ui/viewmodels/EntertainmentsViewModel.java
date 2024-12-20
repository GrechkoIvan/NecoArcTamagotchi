package com.example.necoarctamagotchi.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.model.StatsModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EntertainmentsViewModel extends ViewModel {
    StatsModel statsModel;

    @Inject
    public EntertainmentsViewModel(StatsModel statsModel) {
        this.statsModel = statsModel;
    }


    public void playWithPepsiToy() {
        StatsDto currentStats = statsModel.getStatsLiveData().getValue();
        if (currentStats.getEnergy() != 0) {
            StatsDto newStats = new StatsDto(
                    currentStats.getHunger(),
                    currentStats.getHappiness() + 20,
                    currentStats.getEnergy() - 10,
                    currentStats.getHealth()
            );
            statsModel.updateStats(newStats);
            statsModel.updateSleepingState(false);
        }
    }

    public void dance() {
        StatsDto currentStats = statsModel.getStatsLiveData().getValue();
        if (currentStats.getEnergy() != 0) {
            StatsDto newStats = new StatsDto(
                    currentStats.getHunger(),
                    currentStats.getHappiness() + 30,
                    currentStats.getEnergy() - 15,
                    currentStats.getHealth()
            );
            statsModel.updateStats(newStats);
            statsModel.updateSleepingState(false);
        }
    }

    public void doFlip() {
        StatsDto currentStats = statsModel.getStatsLiveData().getValue();
        if (currentStats.getEnergy() != 0) {
            StatsDto newStats = new StatsDto(
                    currentStats.getHunger(),
                    currentStats.getHappiness() + 10,
                    currentStats.getEnergy() - 5,
                    currentStats.getHealth()
            );
            statsModel.updateStats(newStats);
            statsModel.updateSleepingState(false);
        }
    }
}