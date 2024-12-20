package com.example.necoarctamagotchi.ui.activities;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.necoarctamagotchi.ProgressColorManager;
import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.ui.fragments.BedroomFragment;
import com.example.necoarctamagotchi.ui.fragments.EntertainmentsFragment;
import com.example.necoarctamagotchi.ui.fragments.KitchenFragment;
import com.example.necoarctamagotchi.ui.fragments.MedicineFragment;
import com.example.necoarctamagotchi.ui.viewmodels.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity{
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getStatsLiveData().observe(this, stats -> {
            if (stats != null) {
                updateStatsProgressBars(stats);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        mainViewModel.stopBackgroundStatsUpdating();
        mainViewModel.startRealTimeStatsUpdating();
    }

    @Override
    protected void onStop() {
        mainViewModel.stopRealTimeStatsUpdating();
        mainViewModel.startBackgroundStatsUpdating();
        super.onStop();
    }

    private final BottomNavigationView.OnItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.kitchen) {
            selectedFragment = new KitchenFragment();
        } else if (itemId == R.id.bedroom) {
            selectedFragment = new BedroomFragment();
        } else if (itemId == R.id.medicine) {
            selectedFragment = new MedicineFragment();
        } else if (itemId == R.id.entertainments) {
            selectedFragment = new EntertainmentsFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(currentFragment)
                        .commit();
            }
        }
        return true;
    };

    private void updateStatsProgressBars(StatsDto stats) {
        ProgressBar hungerBar = findViewById(R.id.hunger_progress);
        ProgressBar happinessBar = findViewById(R.id.happiness_progress);
        ProgressBar healthBar = findViewById(R.id.health_progress);
        ProgressBar energyBar = findViewById(R.id.energy_progress);

        ProgressColorManager progressColorManager = new ProgressColorManager();

        float hunger = stats.getHunger();
        float happiness = stats.getHappiness();
        float energy = stats.getEnergy();
        float health = stats.getHealth();

        hungerBar.setProgress(Math.round(hunger));
        progressColorManager.updateProgressColor(hungerBar, Math.round(hunger));
        happinessBar.setProgress(Math.round(happiness));
        progressColorManager.updateProgressColor(happinessBar, Math.round(happiness));
        energyBar.setProgress(Math.round(energy));
        progressColorManager.updateProgressColor(energyBar, Math.round(energy));
        healthBar.setProgress(Math.round(health));
        progressColorManager.updateProgressColor(healthBar, Math.round(health));
    }
}
