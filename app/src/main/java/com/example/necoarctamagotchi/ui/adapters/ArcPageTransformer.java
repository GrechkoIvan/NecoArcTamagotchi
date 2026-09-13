package com.example.necoarctamagotchi.ui.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class ArcPageTransformer implements ViewPager2.PageTransformer {

    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position < -1 || position > 1) {
            view.setAlpha(1);
        } else {
            float scale = Math.max(0.3f, 0.8f - Math.abs(position));

            float translationY = -Math.abs(position) * view.getHeight();
            float translationX = Math.min(0, 0.25f - Math.abs(position)) * Math.signum(position) * view.getWidth() * 1.25f;

            view.setTranslationY(translationY);
            view.setTranslationX(translationX);
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setAlpha(1);
        }
    }
}
