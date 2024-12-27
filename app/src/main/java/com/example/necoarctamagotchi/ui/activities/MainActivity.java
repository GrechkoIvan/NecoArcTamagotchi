package com.example.necoarctamagotchi.ui.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.necoarctamagotchi.ui.dialogs.SettingsDialog;
import com.example.necoarctamagotchi.utils.AnimationManager;
import com.example.necoarctamagotchi.utils.BackgroundMusicManager;
import com.example.necoarctamagotchi.utils.MoneyCountTextFormatter;
import com.example.necoarctamagotchi.utils.ProgressColorManager;
import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.dto.StatsDto;
import com.example.necoarctamagotchi.ui.fragments.BedroomFragment;
import com.example.necoarctamagotchi.ui.fragments.EntertainmentsFragment;
import com.example.necoarctamagotchi.ui.fragments.KitchenFragment;
import com.example.necoarctamagotchi.ui.fragments.MedicineFragment;
import com.example.necoarctamagotchi.ui.viewmodels.MainViewModel;
import com.example.necoarctamagotchi.utils.SfxManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity{
    private MainViewModel mainViewModel;
    private AnimationManager animationManager;

    @Inject
    BackgroundMusicManager backgroundMusicManager;
    @Inject
    SfxManager sfxManager;

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

        ImageView necoArc = findViewById(R.id.neco_arc);
        View dimOverlay = findViewById(R.id.dim_overlay);
        mainViewModel.getSleepingStateLiveData().observe(this, isSleeping -> {
            if (isSleeping) {
                dimOverlay.setVisibility(View.VISIBLE);
            } else {
                dimOverlay.setVisibility(View.GONE);
            }
        });

        mainViewModel.getAnimationLiveData().observe(this, animation -> {
            if (animationManager == null) {
                animationManager = new AnimationManager(this, mainViewModel.getAnimationLiveData().getValue(), necoArc);
            }
            animationManager.stopAnimation();
            animationManager.changeAnimation(animation);
            animationManager.startAnimation();
        });

        TextView moneyCountText = findViewById(R.id.money_count_text);
        mainViewModel.getMoneyCountLiveData().observe(this, moneyCount -> {
            moneyCountText.setText(MoneyCountTextFormatter.formatMoneyText(moneyCount));
        });

        ImageButton settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            SettingsDialog settingsDialog = new SettingsDialog();
            settingsDialog.show(getSupportFragmentManager(), "SettingsDialog");
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        checkNotificationPermission();

        ImageButton mineButton = findViewById(R.id.mine_button);
        mineButton.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            Intent intent = new Intent(this, MineActivity.class);
            startActivity(intent);
        });

        mainViewModel.stopBackgroundStatsUpdating();
        mainViewModel.startRealTimeStatsUpdating();
        mainViewModel.cancelNotification(this);
        backgroundMusicManager.playMusic();
    }

    @Override
    protected void onStop() {
        mainViewModel.stopRealTimeStatsUpdating();
        mainViewModel.startBackgroundStatsUpdating();
        sfxManager.release();
        mainViewModel.scheduleNotification(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        mainViewModel.stopBackgroundStatsUpdating();
        mainViewModel.startRealTimeStatsUpdating();
        mainViewModel.cancelNotification(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        backgroundMusicManager.stopMusic();
        super.onDestroy();
    }

    private final BottomNavigationView.OnItemSelectedListener navListener = item -> {
        sfxManager.playButtonClickSound();
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

        float hunger = stats.getHunger();
        float happiness = stats.getHappiness();
        float energy = stats.getEnergy();
        float health = stats.getHealth();

        hungerBar.setProgress(Math.round(hunger));
        ProgressColorManager.updateProgressColor(hungerBar, Math.round(hunger));
        happinessBar.setProgress(Math.round(happiness));
        ProgressColorManager.updateProgressColor(happinessBar, Math.round(happiness));
        energyBar.setProgress(Math.round(energy));
        ProgressColorManager.updateProgressColor(energyBar, Math.round(energy));
        healthBar.setProgress(Math.round(health));
        ProgressColorManager.updateProgressColor(healthBar, Math.round(health));
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.permission_dialog_title))
                        .setMessage(getResources().getString(R.string.permission_dialog_message))
                        .setPositiveButton(getResources().getString(R.string.permission_dialog_positive_button_text), (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                            startActivity(intent);
                        })
                        .setNegativeButton(getResources().getString(R.string.permission_dialog_negative_button_text), null)
                        .show();
            }
        }
    }
}
