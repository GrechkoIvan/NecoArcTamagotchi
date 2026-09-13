package com.example.necoarctamagotchi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.data.model.CharacterAppearanceModel;
import com.example.necoarctamagotchi.data.model.StatsModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BedroomViewModel extends ViewModel {
    StatsModel statsModel;
    CharacterAppearanceModel characterAppearanceModel;

    @Inject
    public BedroomViewModel(StatsModel statsModel, CharacterAppearanceModel characterAppearanceModel) {
        this.statsModel = statsModel;
        this.characterAppearanceModel = characterAppearanceModel;
    }

    public LiveData<Boolean> getSleepingStateLiveData() {
        return statsModel.getSleepingStateLiveData();
    }

    public void updateSleepingState(boolean isSleeping) {
        if (isSleeping) {
            characterAppearanceModel.playSleepingAnimation();
        } else {
            characterAppearanceModel.playIdleAnimation();
        }
        statsModel.updateSleepingState(isSleeping);
    }
}