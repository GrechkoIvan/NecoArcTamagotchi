package com.example.necoarctamagotchi.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.dto.DishDto;
import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.data.model.StatsModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class KitchenViewModel extends ViewModel {
    StatsModel statsModel;
    private final MutableLiveData<List<DishDto>> dishes = new MutableLiveData<>();

    @Inject
    public KitchenViewModel(StatsModel statsModel) {
        this.statsModel = statsModel;
        loadDishes();
    }

    private void loadDishes() {
        List<DishDto> dishList = new ArrayList<>();
        dishList.add(new DishDto(R.drawable.dish_burger, new StatsDto(20, 10, 0, 0)));
        dishList.add(new DishDto(R.drawable.dish_salad, new StatsDto(7, -5, 0, 2)));
        dishList.add(new DishDto(R.drawable.dish_pepsi, new StatsDto(0, 15, 3, 0)));
        dishList.add(new DishDto(R.drawable.dish_cookies, new StatsDto(5, 5, 0, 0)));
        dishList.add(new DishDto(R.drawable.dish_fish_bones, new StatsDto(5, -10, 0, 0)));

        dishes.setValue(dishList);
    }

    public LiveData<List<DishDto>> getDishes() {
        return dishes;
    }

    public void feedDish(int position) {
        DishDto dish = dishes.getValue().get(position);
        StatsDto dishStatsEffect = dish.getStats();
        StatsDto currentStats = statsModel.getStatsLiveData().getValue();
        StatsDto newStats = new StatsDto(
                currentStats.getHunger() + dishStatsEffect.getHunger(),
                currentStats.getHappiness() + dishStatsEffect.getHappiness(),
                currentStats.getEnergy() + dishStatsEffect.getEnergy(),
                currentStats.getHealth() + dishStatsEffect.getHealth()
        );
        statsModel.updateStats(newStats);
        statsModel.updateSleepingState(false);
    }
}