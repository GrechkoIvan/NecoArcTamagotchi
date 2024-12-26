package com.example.necoarctamagotchi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.dto.MedicineDto;
import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.model.CharacterAppearanceModel;
import com.example.necoarctamagotchi.data.model.MoneyModel;
import com.example.necoarctamagotchi.data.model.StatsModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MedicineViewModel extends ViewModel {
    StatsModel statsModel;
    MoneyModel moneyModel;
    CharacterAppearanceModel characterAppearanceModel;
    private final MutableLiveData<List<MedicineDto>> medicines = new MutableLiveData<>();

    @Inject
    public MedicineViewModel(StatsModel statsModel, MoneyModel moneyModel, CharacterAppearanceModel characterAppearanceModel) {
        this.statsModel = statsModel;
        this.moneyModel = moneyModel;
        this.characterAppearanceModel = characterAppearanceModel;
        loadMedicines();
    }

    private void loadMedicines() {
        List<MedicineDto> medicines = new ArrayList<>();

        medicines.add(new MedicineDto(R.drawable.medicine_brilliant_green, 15, 10));
        medicines.add(new MedicineDto(R.drawable.medicine_first_aid_kit, 60, 30));
        medicines.add(new MedicineDto(R.drawable.medicine_bandage, 30, 18));

        this.medicines.setValue(medicines);
    }

    public LiveData<List<MedicineDto>> getMedicines() {
        return medicines;
    }

    public boolean takeMedicine(MedicineDto medicine) {
        if (moneyModel.getMoneyCountLiveData().getValue() >= medicine.getCost()) {
            float healthEffect = medicine.getHealthEffect();
            StatsDto currentStats = statsModel.getStatsLiveData().getValue();
            StatsDto newStats = new StatsDto(
                    currentStats.getHunger(),
                    currentStats.getHappiness(),
                    currentStats.getEnergy(),
                    currentStats.getHealth() + healthEffect
            );
            statsModel.updateStats(newStats);
            characterAppearanceModel.playJoyAnimation();
            statsModel.updateSleepingState(false);

            moneyModel.decreaseMoneyCount(medicine.getCost());
            return true;
        } else {
            return false;
        }
    }
}