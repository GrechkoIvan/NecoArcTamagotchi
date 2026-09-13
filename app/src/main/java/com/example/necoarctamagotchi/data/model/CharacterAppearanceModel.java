package com.example.necoarctamagotchi.data.model;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.necoarctamagotchi.data.dto.AnimationDto;
import com.example.necoarctamagotchi.data.preferences.SharedPreferencesManager;
import com.example.necoarctamagotchi.data.skins.Skin;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

// Репозиторий для управления анимациями и скинами

@Singleton
public class CharacterAppearanceModel {
    private final SharedPreferencesManager sharedPreferencesManager;
    private final Skin currentSkin;
    private final MutableLiveData<AnimationDto> animationLiveData = new MutableLiveData<>();
    private final int frameWidth = 900;
    private final int frameHeight = 1005;
    private final Handler handler;
    Random random;

    @Inject
    public CharacterAppearanceModel(SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        currentSkin = sharedPreferencesManager.getSkin();
        handler = new Handler();
        random = new Random();
        if (sharedPreferencesManager.getSleepingState()) {
            playSleepingAnimation();
        } else {
            playIdleAnimation();
        }
    }

    public LiveData<AnimationDto> getAnimationLiveData() {
        return animationLiveData;
    }

    public void playIdleAnimation() {
        AnimationDto mainAnimation = new AnimationDto(
                currentSkin.getIdleAnimationResId(),
                frameWidth,
                frameHeight,
                9,
                100,
                AnimationDto.ENDLESS_DURATION
        );
        if (sharedPreferencesManager.getSleepingState()) {
            playGetUpAnimation();

            handler.postDelayed(() -> animationLiveData.setValue(mainAnimation), animationLiveData.getValue().getTotalDuration());
        } else {
            animationLiveData.setValue(mainAnimation);
        }
    }

    private void playGetUpAnimation() {
        animationLiveData.setValue(new AnimationDto(
                currentSkin.getGetUpAnimationResId(),
                frameWidth,
                frameHeight,
                4,
                100,
                1)
        );
    }

    private void playLayDownAnimation() {
        animationLiveData.setValue(new AnimationDto(
                currentSkin.getLayDownAnimationResId(),
                frameWidth,
                frameHeight,
                4,
                100,
                1)
        );
    }

    public void playSleepingAnimation() {
        AnimationDto mainAnimation = new AnimationDto(
                currentSkin.getSleepingAnimationResId(),
                frameWidth,
                frameHeight,
                12,
                100,
                AnimationDto.ENDLESS_DURATION
        );
        if (!sharedPreferencesManager.getSleepingState()) {
            playLayDownAnimation();
            handler.postDelayed(() -> animationLiveData.setValue(mainAnimation), animationLiveData.getValue().getTotalDuration());
        } else {
            animationLiveData.setValue(mainAnimation);
        }

    }

    public void playDanceAnimation() {
        AnimationDto mainAnimation;
        int randomIndex = random.nextInt(3);
        switch (randomIndex) {
            case 0:
                mainAnimation = new AnimationDto(
                        currentSkin.getDance1AnimationResId(),
                        frameWidth,
                        frameHeight,
                        10,
                        100,
                        4
                );
                break;
            case 1:
                mainAnimation = new AnimationDto(
                        currentSkin.getDance2AnimationResId(),
                        frameWidth,
                        frameHeight,
                        15,
                        100,
                        3
                );
                break;
            default:
                mainAnimation = new AnimationDto(
                        currentSkin.getDance3AnimationResId(),
                        frameWidth,
                        frameHeight,
                        22,
                        100,
                        2
                );
                break;
        }

        if (sharedPreferencesManager.getSleepingState()) {
            playGetUpAnimation();
            handler.postDelayed(() -> {
                animationLiveData.setValue(mainAnimation);
                handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
            }, animationLiveData.getValue().getTotalDuration());
        } else {
            animationLiveData.setValue(mainAnimation);
            handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
        }
    }

    public void playFlipAnimation() {
        AnimationDto mainAnimation = new AnimationDto(
                currentSkin.getFlipAnimationResId(),
                frameWidth,
                frameHeight,
                9,
                100,
                1
        );
        if (sharedPreferencesManager.getSleepingState()) {
            playGetUpAnimation();
            handler.postDelayed(() -> {
                animationLiveData.setValue(mainAnimation);
                handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
            }, animationLiveData.getValue().getTotalDuration());
        } else {
            animationLiveData.setValue(mainAnimation);
            handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
        }
    }

    public void playAngerAnimaation() {
        AnimationDto mainAnimation = new AnimationDto(
                currentSkin.getAngerAnimationResId(),
                frameWidth,
                frameHeight,
                10,
                100,
                1
        );
        if (sharedPreferencesManager.getSleepingState()) {
            playGetUpAnimation();
            handler.postDelayed(() -> {
                animationLiveData.setValue(mainAnimation);
                handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
            }, animationLiveData.getValue().getTotalDuration());
        } else {
            animationLiveData.setValue(mainAnimation);
            handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
        }
    }

    public void playJoyAnimation() {
        AnimationDto mainAnimation = new AnimationDto(
                currentSkin.getJoyAnimationResId(),
                frameWidth,
                frameHeight,
                6,
                100,
                1
        );
        if (sharedPreferencesManager.getSleepingState()) {
            playGetUpAnimation();
            handler.postDelayed(() -> {
                animationLiveData.setValue(mainAnimation);
                handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
            }, animationLiveData.getValue().getTotalDuration());
        } else {
            animationLiveData.setValue(mainAnimation);
            handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
        }
    }

    public void playPepsiToyAnimmation() {
        AnimationDto mainAnimation = new AnimationDto(
                currentSkin.getPepsiToyAnimationResId(),
                frameWidth,
                frameHeight,
                32,
                100,
                1
        );
        if (sharedPreferencesManager.getSleepingState()) {
            playGetUpAnimation();
            handler.postDelayed(() -> {
                animationLiveData.setValue(mainAnimation);
                handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
            }, animationLiveData.getValue().getTotalDuration());
        } else {
            animationLiveData.setValue(mainAnimation);
            handler.postDelayed(this::playIdleAnimation, mainAnimation.getTotalDuration());
        }
    }
}
