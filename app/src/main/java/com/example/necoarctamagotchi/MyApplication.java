package com.example.necoarctamagotchi;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.necoarctamagotchi.observers.BackgroundMusicAppLifecycleEventObserver;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {

    @Inject
    BackgroundMusicAppLifecycleEventObserver appLifecycleObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(appLifecycleObserver);
    }
}
