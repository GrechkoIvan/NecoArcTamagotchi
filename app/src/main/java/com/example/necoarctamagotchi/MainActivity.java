package com.example.necoarctamagotchi;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
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
        // It will help to replace the
        // one fragment to other.
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