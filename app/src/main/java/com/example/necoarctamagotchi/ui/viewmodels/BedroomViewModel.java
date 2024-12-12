package com.example.necoarctamagotchi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.data.model.StatsModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BedroomViewModel extends ViewModel {
    StatsModel statsModel;

    @Inject
    public BedroomViewModel(StatsModel statsModel) {
        this.statsModel = statsModel;
    }

    public LiveData<Boolean> getSleepingStateLiveData() {
        return statsModel.getSleepingStateLiveData();
    }

    public void updateSleepingState(boolean isSleeping) {
        statsModel.updateSleepingState(isSleeping);
    }
}