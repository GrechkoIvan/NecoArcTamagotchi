package com.example.necoarctamagotchi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.dto.MedicineDto;
import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.model.CharacterAppearanceModel;
import com.example.necoarctamagotchi.data.model.StatsModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MedicineViewModel extends ViewModel {
    StatsModel statsModel;
    CharacterAppearanceModel characterAppearanceModel;
    private final MutableLiveData<List<MedicineDto>> medicines = new MutableLiveData<>();

    @Inject
    public MedicineViewModel(StatsModel statsModel, CharacterAppearanceModel characterAppearanceModel) {
        this.statsModel = statsModel;
        this.characterAppearanceModel = characterAppearanceModel;
        loadMedicines();
    }

    private void loadMedicines() {
        List<MedicineDto> medicines = new ArrayList<>();

        medicines.add(new MedicineDto(R.drawable.medicine_brilliant_green, 20));
        medicines.add(new MedicineDto(R.drawable.medicine_first_aid_kit, 60));
        medicines.add(new MedicineDto(R.drawable.medicine_bandage, 40));

        this.medicines.setValue(medicines);
    }

    public LiveData<List<MedicineDto>> getMedicines() {
        return medicines;
    }

    public void takeMedicine(int position) {
        MedicineDto medicine = medicines.getValue().get(position);
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
    }
}