package com.example.necoarctamagotchi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.example.necoarctamagotchi.data.dto.AnimationDto;

public class AnimationManager {
    private final Context context;
    private AnimationDto animation;
    private final ImageView targetImage;
    private Bitmap[] frames;
    private final Handler handler;

    private int currentFrame;
    private int currentRepeat;

    public AnimationManager(Context context, AnimationDto animation, ImageView targetImage) {
        this.context = context;
        this.animation = animation;
        this.targetImage = targetImage;
        this.handler = new Handler();
        splitSpriteSheet();
    }

    private void splitSpriteSheet() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), animation.getSpriteSheetResId(), options);

        int frameWidth = animation.getFrameWidth();
        int frameHeight = animation.getFrameHeight();

        frames = new Bitmap[animation.getFrameCount()];
        for (int i = 0; i < animation.getFrameCount(); i++) {
            frames[i] = Bitmap.createBitmap(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);
        }
    }

    public void changeAnimation(AnimationDto animation) {
        this.animation = animation;
        splitSpriteSheet();
    }

    public void startAnimation() {
        currentFrame = 0;
        currentRepeat = 0;
        handler.post(animationRunnable);
    }

    public void stopAnimation() {
        handler.removeCallbacks(animationRunnable);
    }

    private final Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {
            targetImage.setImageBitmap(frames[currentFrame]);
            currentFrame = (currentFrame + 1) % animation.getFrameCount();

            if (currentFrame == 0) {
                currentRepeat++;
                if (currentRepeat >= animation.getRepeatCount() && animation.getRepeatCount() != AnimationDto.ENDLESS_DURATION) {
                    stopAnimation();
                    return;
                }
            }

            handler.postDelayed(this, animation.getFrameDuration());
        }
    };
}
