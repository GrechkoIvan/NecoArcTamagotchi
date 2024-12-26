package com.example.necoarctamagotchi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.data.model.MoneyModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MineViewModel extends ViewModel {

    private final MoneyModel moneyModel;

    @Inject
    MineViewModel(MoneyModel moneyModel) {
        this.moneyModel = moneyModel;
    }

    public LiveData<Integer> getMoneyCountLiveData() {
        return moneyModel.getMoneyCountLiveData();
    }

    public void increaseMoneyCount(int moneyCount) {
        moneyModel.increaseMoneyCount(moneyCount);
    }
}
