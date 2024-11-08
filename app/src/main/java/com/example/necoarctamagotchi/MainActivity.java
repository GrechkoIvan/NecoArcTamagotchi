package com.example.necoarctamagotchi;

import android.os.Bundle;

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