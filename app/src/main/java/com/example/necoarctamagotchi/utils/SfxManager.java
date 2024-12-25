package com.example.necoarctamagotchi.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.model.SettingsModel;

import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class SfxManager {
    private final SettingsModel settingsModel;
    private SoundPool soundPool;
    Random random;

    private int buttonClickSoundId;
    private int anger1VoiceId;
    private int anger2VoiceId;
    private int anger3VoiceId;
    private int anger4VoiceId;
    private int general1VoiceId;
    private int general2VoiceId;
    private int general3VoiceId;
    private int danceVoiceId;
    private int entertainmentVoiceId;
    private int joy1VoiceId;
    private int joy2VoiceId;
    private int flipVoiceId;
    private int sleepVoiceId;

    @Inject
    public SfxManager(@ApplicationContext Context context, SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder().
                setMaxStreams(5).
                setAudioAttributes(audioAttributes).
                build();

        random = new Random();

        buttonClickSoundId = soundPool.load(context, R.raw.sfx_button_click, 1);
        anger1VoiceId = soundPool.load(context, R.raw.sfx_voice_anger1, 1);
        anger2VoiceId = soundPool.load(context, R.raw.sfx_voice_anger2, 1);
        anger3VoiceId = soundPool.load(context, R.raw.sfx_voice_anger3, 1);
        anger4VoiceId = soundPool.load(context, R.raw.sfx_voice_anger4, 1);
        general1VoiceId = soundPool.load(context, R.raw.sfx_voice_general1, 1);
        general2VoiceId = soundPool.load(context, R.raw.sfx_voice_general2, 1);
        general3VoiceId = soundPool.load(context, R.raw.sfx_voice_general3, 1);
        danceVoiceId = soundPool.load(context, R.raw.sfx_voice_dance, 1);
        entertainmentVoiceId = soundPool.load(context, R.raw.sfx_voice_entertainment, 1);
        joy1VoiceId = soundPool.load(context, R.raw.sfx_voice_joy1, 1);
        joy2VoiceId = soundPool.load(context, R.raw.sfx_voice_joy2, 1);
        flipVoiceId = soundPool.load(context, R.raw.sfx_voice_flip, 1);
        sleepVoiceId = soundPool.load(context, R.raw.sfx_voice_sleep, 1);
    }

    public void playButtonClickSound() {
        if (settingsModel.getSfxEnabledStateLiveData().getValue()) {
            soundPool.play(buttonClickSoundId,1,1,0,0,1);
        }
    }

    public void playAngerVoice() {
        if (settingsModel.getSfxEnabledStateLiveData().getValue()) {
            int randomIndex = random.nextInt(4);
            switch (randomIndex) {
                case 0:
                    soundPool.play(anger1VoiceId, 1, 1, 0, 0, 1);
                    break;
                case 1:
                    soundPool.play(anger2VoiceId, 1, 1, 0, 0, 1);
                    break;
                case 2:
                    soundPool.play(anger3VoiceId, 1, 1, 0, 0, 1);
                    break;
                case 3:
                    soundPool.play(anger4VoiceId, 1, 1, 0, 0, 1);
                    break;
            }
        }
    }

    private void playGeneralVoice() {
        if (settingsModel.getSfxEnabledStateLiveData().getValue()) {
            int randomIndex = random.nextInt(3);
            switch (randomIndex) {
                case 0:
                    soundPool.play(general1VoiceId, 1, 1, 0, 0, 1);
                    break;
                case 1:
                    soundPool.play(general2VoiceId, 1, 1, 0, 0, 1);
                    break;
                case 2:
                    soundPool.play(general3VoiceId, 1, 1, 0, 0, 1);
                    break;
            }
        }
    }

    public void playJoyVoice() {
        if (settingsModel.getSfxEnabledStateLiveData().getValue()) {
            int randomIndex = random.nextInt(3);
            switch (randomIndex) {
                case 0:
                    playGeneralVoice();
                    break;
                case 1:
                    soundPool.play(joy1VoiceId, 1, 1, 0, 0, 1);
                    break;
                case 2:
                    soundPool.play(joy2VoiceId, 1, 1, 0, 0, 1);
                    break;
            }
        }
    }

    public void playDanceVoice() {
        if (settingsModel.getSfxEnabledStateLiveData().getValue()) {
            int randomIndex = random.nextInt(3);
            switch (randomIndex) {
                case 0:
                    playGeneralVoice();
                    break;
                case 1:
                    soundPool.play(danceVoiceId, 1, 1, 0, 0, 1);
                    break;
                case 2:
                    soundPool.play(entertainmentVoiceId, 1, 1, 0, 0, 1);
                    break;
            }
        }
    }

    public void playFlipVoice() {
        int randomIndex = random.nextInt(3);
        switch (randomIndex) {
            case 0:
                playGeneralVoice();
                break;
            case 1:
                soundPool.play(flipVoiceId,1,1,0,0,1);
                break;
            case 2:
                soundPool.play(entertainmentVoiceId,1,1,0,0,1);
                break;
        }
    }

    public void playPepsiToyVoice() {
        if (settingsModel.getSfxEnabledStateLiveData().getValue()) {
            int randomIndex = random.nextInt(2);
            switch (randomIndex) {
                case 0:
                    playJoyVoice();
                    break;
                case 1:
                    soundPool.play(entertainmentVoiceId, 1, 1, 0, 0, 1);
                    break;
            }
        }
    }

    public void playSleepVoice() {
        if (settingsModel.getSfxEnabledStateLiveData().getValue()) {
            soundPool.play(sleepVoiceId, 1, 1, 0, 0, 1);
        }
    }

    public void release() {
        soundPool.release();
    }
}
