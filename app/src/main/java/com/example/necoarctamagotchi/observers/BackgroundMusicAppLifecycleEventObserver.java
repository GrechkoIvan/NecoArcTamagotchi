package com.example.necoarctamagotchi.observers;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.necoarctamagotchi.utils.BackgroundMusicManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BackgroundMusicAppLifecycleEventObserver implements LifecycleEventObserver {
    private final BackgroundMusicManager backgroundMusicManager;

    @Inject
    public BackgroundMusicAppLifecycleEventObserver(BackgroundMusicManager backgroundMusicManager) {
        this.backgroundMusicManager = backgroundMusicManager;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_START:
                backgroundMusicManager.playMusic();
                break;
            case ON_STOP:
                backgroundMusicManager.pauseMusic();
                break;
        }
    }
}
