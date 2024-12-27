package com.example.necoarctamagotchi.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.ui.viewmodels.MineViewModel;
import com.example.necoarctamagotchi.utils.BackgroundMusicManager;
import com.example.necoarctamagotchi.utils.MoneyCountTextFormatter;
import com.example.necoarctamagotchi.utils.ProgressColorManager;
import com.example.necoarctamagotchi.utils.SfxManager;

import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MineActivity extends AppCompatActivity {
    private MineViewModel mineViewModel;
    private ImageButton oreButton;
    private ProgressBar miningProgressBar;
    TextView moneyCountText;
    private int progress;

    @Inject
    SfxManager sfxManager;
    @Inject
    BackgroundMusicManager backgroundMusicManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mine), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mineViewModel = new ViewModelProvider(this).get(MineViewModel.class);

        oreButton = findViewById(R.id.gold_ore);
        miningProgressBar = findViewById(R.id.mining_progress);
        updateMiningProgress(100);
        progress = 100;

        moneyCountText = findViewById(R.id.mine_money_count_text);

        mineViewModel.getMoneyCountLiveData().observe(this, moneyCount -> {
            moneyCountText.setText(MoneyCountTextFormatter.formatMoneyText(moneyCount));
        });

        oreButton.setOnClickListener(v -> {
            shakeOreButton();
            mineOre();
            sfxManager.playOreMiningSound();
        });

        ImageButton closeButton = findViewById(R.id.mine_close_button);
        closeButton.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            finish();
        });

        backgroundMusicManager.playMusic();
    }

    private void mineOre() {
        if (progress > 0) {
            progress -= 10;
            updateMiningProgress(progress);
        }

        if (progress == 0) {
            mineViewModel.increaseMoneyCount(1);
            progress = 100;
            updateMiningProgress(progress);
            showCoinEffect();
        }
    }

    private void updateMiningProgress(int progress) {
        miningProgressBar.setProgress(progress);
        ProgressColorManager.updateProgressColor(miningProgressBar, progress);
    }

    private void shakeOreButton() {
        Random random = new Random();
        int randomIndex = random.nextInt(2);
        switch (randomIndex) {
            case 0:
                oreButton.animate().rotationY(20).rotation(10).setDuration(100)
                        .withEndAction(() -> oreButton.animate().rotationY(-20).rotation(-10).setDuration(100)
                                .withEndAction(() -> oreButton.animate().rotationY(0).rotation(0).setDuration(100)));
                break;
            case 1:
                oreButton.animate().rotationY(-20).rotation(-10).setDuration(100)
                        .withEndAction(() -> oreButton.animate().rotationY(20).rotation(10).setDuration(100)
                                .withEndAction(() -> oreButton.animate().rotationY(0).rotation(0).setDuration(100)));
                break;
        }
    }

    private void showCoinEffect() {
        final TextView coinEffect = new TextView(this);
        coinEffect.setText("+1");
        coinEffect.setTextSize(30);
        coinEffect.setTextColor(Color.WHITE);

        int[] location = new int[2];
        moneyCountText.getLocationOnScreen(location);

        coinEffect.setX(location[0] + moneyCountText.getWidth() / 2 - 40);
        coinEffect.setY(location[1] - 70);
        ((ViewGroup) findViewById(R.id.mine)).addView(coinEffect);

        coinEffect.animate().translationY(-0.5f).alpha(0).setDuration(1000)
                .withEndAction(() -> ((ViewGroup) findViewById(R.id.mine)).removeView(coinEffect));
    }
}