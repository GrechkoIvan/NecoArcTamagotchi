// Класс, назначающий цвет полосы ProgressBar
// в зависимости от значения прогресса

package com.example.necoarctamagotchi;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.widget.ProgressBar;

public class ProgressColorManager {
    public void updateProgressColor(ProgressBar progressBar, int value) {
        int color = getColorForProgress(value);

        if (progressBar.getProgressDrawable() instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) progressBar.getProgressDrawable();

            layerDrawable.findDrawableByLayerId(android.R.id.progress).setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    private int getColorForProgress(int value) {
        float ratio = value / 100f;

        int red, green, blue = 0;

        if (ratio < 0.5) {
            red = 255;
            green = (int) (255 * (ratio * 2));
        } else {
            green = 255;
            red = (int) (255 * (1 - ((ratio - 0.5) * 2)));
        }

        return Color.rgb(red, green, blue);
    }
}