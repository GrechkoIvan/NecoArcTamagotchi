package com.example.necoarctamagotchi.data.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.necoarctamagotchi.data.preferences.SharedPreferencesManager;

import javax.inject.Inject;
import javax.inject.Singleton;

// Репозиторий для управления деньгами

@Singleton
public class MoneyModel {
    private final SharedPreferencesManager sharedPreferencesManager;
    private final MutableLiveData<Integer> moneyCount = new MutableLiveData<>();

    @Inject
    public MoneyModel (SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        moneyCount.setValue(sharedPreferencesManager.getMoneyCount());
    }

    public LiveData<Integer> getMoneyCountLiveData() {
        return moneyCount;
    }

    public void increaseMoneyCount(int money) {
        int newMoney = moneyCount.getValue() + money;
        moneyCount.setValue(newMoney);
        sharedPreferencesManager.saveMoneyCount(newMoney);
    }

    public void decreaseMoneyCount(int money) {
        int newMoney = moneyCount.getValue() - money;
        moneyCount.setValue(newMoney);
        sharedPreferencesManager.saveMoneyCount(newMoney);
    }
}
