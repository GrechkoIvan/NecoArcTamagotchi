package com.example.necoarctamagotchi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.model.StatsModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private StatsModel statsModel;
    private LiveData<StatsDto> statsLiveData;

    @Inject
    public MainViewModel(StatsModel statsModel) {
        this.statsModel = statsModel;
        this.statsLiveData = statsModel.getStatsLiveData();
    }

    public LiveData<StatsDto> getStatsLiveData() {
        return statsLiveData;
    }
}
