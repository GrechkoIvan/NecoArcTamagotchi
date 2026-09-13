package com.example.necoarctamagotchi.data.dto;

public class AnimationDto {
    private final int spriteSheetResId;
    private final int frameWidth;
    private final int frameHeight;
    private final int frameCount;
    private final int frameDuration;
    private final int repeatCount;

    public static int ENDLESS_DURATION = 0;

    public AnimationDto(int spriteSheetResId, int frameWidth, int frameHeight, int frameCount, int frameDuration, int repeatCount) {
        this.spriteSheetResId = spriteSheetResId;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frameDuration = frameDuration;
        this.repeatCount = repeatCount;
    }

    public int getSpriteSheetResId() {
        return spriteSheetResId;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getFrameDuration() {
        return frameDuration;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public int getTotalDuration() {
        if (repeatCount == ENDLESS_DURATION) {
            return -1;
        } else {
            return (frameCount * frameDuration) * repeatCount;
        }
    }
}
