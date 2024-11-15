package com.example.necoarctamagotchi;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.necoarctamagotchi.ui.bedroom.BedroomFragment;
import com.example.necoarctamagotchi.ui.entertaiments.EntertaimentsFragment;
import com.example.necoarctamagotchi.ui.kitchen.KitchenFragment;
import com.example.necoarctamagotchi.ui.medicine.MedicineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        ProgressBar hungerBar = findViewById(R.id.hunger_progress);
        ProgressBar happinessBar = findViewById(R.id.happiness_progress);
        ProgressBar healthBar = findViewById(R.id.health_progress);
        ProgressBar energyBar = findViewById(R.id.energy_progress);

        ProgressColorManager progressColorManager = new ProgressColorManager();

        hungerBar.setProgress(15);
        progressColorManager.updateProgressColor(hungerBar, 15);
        happinessBar.setProgress(60);
        progressColorManager.updateProgressColor(happinessBar, 60);
        healthBar.setProgress(40);
        progressColorManager.updateProgressColor(healthBar, 40);
        energyBar.setProgress(80);
        progressColorManager.updateProgressColor(energyBar, 80);
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
}
