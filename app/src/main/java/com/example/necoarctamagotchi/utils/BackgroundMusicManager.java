package com.example.necoarctamagotchi.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.model.SettingsModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class BackgroundMusicManager {
    private MediaPlayer mediaPlayer;
    private final Context context;
    private final SettingsModel settingsModel;

    @Inject
    public BackgroundMusicManager(@ApplicationContext Context context, SettingsModel settingsModel) {
        this.context = context;
        this.settingsModel = settingsModel;
    }

    public void playMusic() {
        if (settingsModel.getMusicEnabledStateLiveData().getValue() && mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.background_music);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.3f, 0.3f);
            mediaPlayer.start();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void updateMusicState() {
        if (settingsModel.getMusicEnabledStateLiveData().getValue()) {
            playMusic();
        } else {
            stopMusic();
        }
    }
}
