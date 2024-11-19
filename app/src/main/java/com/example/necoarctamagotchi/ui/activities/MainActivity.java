package com.example.necoarctamagotchi.ui.activities;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.necoarctamagotchi.ProgressColorManager;
import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.ui.fragments.BedroomFragment;
import com.example.necoarctamagotchi.ui.fragments.EntertaimentsFragment;
import com.example.necoarctamagotchi.ui.fragments.KitchenFragment;
import com.example.necoarctamagotchi.ui.fragments.MedicineFragment;
import com.example.necoarctamagotchi.ui.viewmodels.StatsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {
    private StatsViewModel statsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statsViewModel = new StatsViewModel(this);

        statsViewModel.getUpdateSignal().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                updateStatsProgressBars();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);
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
            selectedFragment = new EntertaimentsFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(currentFragment)
                        .commit();
            }
        }
        return true;
    };

    private void updateStatsProgressBars() {
        ProgressBar hungerBar = findViewById(R.id.hunger_progress);
        ProgressBar happinessBar = findViewById(R.id.happiness_progress);
        ProgressBar healthBar = findViewById(R.id.health_progress);
        ProgressBar energyBar = findViewById(R.id.energy_progress);

        ProgressColorManager progressColorManager = new ProgressColorManager();

        float hunger = Optional.ofNullable(statsViewModel.getHunger().getValue()).orElse(100f);
        float happiness = Optional.ofNullable(statsViewModel.getHappiness().getValue()).orElse(100f);
        float energy = Optional.ofNullable(statsViewModel.getEnergy().getValue()).orElse(100f);
        float health = Optional.ofNullable(statsViewModel.getHealth().getValue()).orElse(100f);

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
