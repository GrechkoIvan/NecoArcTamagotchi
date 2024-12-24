package com.example.necoarctamagotchi.data.skins;

import com.example.necoarctamagotchi.R;

public class DefaultSkin implements Skin{
    private static final int idleAnimationResId = R.drawable.default_idle_animation;
    private static final int layDownAnimationResId = R.drawable.default_lay_down_animation;
    private static final int getUpAnimationResId = R.drawable.default_get_up_animation;
    private static final int sleepingAnimationResId = R.drawable.default_sleeping_animation;
    private static final int dance1AnimationResId = R.drawable.default_dance1_animation;
    private static final int dance2AnimationResId = R.drawable.default_dance2_animation;
    private static final int dance3AnimationResId = R.drawable.default_dance3_animation;
    private static final int flipAnimationResId = R.drawable.defautl_flip_animation;
    private static final int angerAnimationResId = R.drawable.default_anger_animation;
    private static final int joyAnimationResId = R.drawable.default_joy_animation;
    private static final int pepsiToyAnimationResId = R.drawable.default_pepsi_toy_animation;

    @Override
    public int getIdleAnimationResId() {
        return idleAnimationResId;
    }

    @Override
    public int getLayDownAnimationResId() {
        return layDownAnimationResId;
    }

    @Override
    public int getGetUpAnimationResId() {
        return getUpAnimationResId;
    }

    @Override
    public int getSleepingAnimationResId() {
        return sleepingAnimationResId;
    }

    @Override
    public int getDance1AnimationResId() {
        return dance1AnimationResId;
    }

    @Override
    public int getDance2AnimationResId() {
        return dance2AnimationResId;
    }

    @Override
    public int getDance3AnimationResId() {
        return dance3AnimationResId;
    }

    @Override
    public int getFlipAnimationResId() {
        return flipAnimationResId;
    }

    @Override
    public int getAngerAnimationResId() {
        return angerAnimationResId;
    }

    @Override
    public int getJoyAnimationResId() {
        return joyAnimationResId;
    }

    @Override
    public int getPepsiToyAnimationResId() {
        return pepsiToyAnimationResId;
    }
}
