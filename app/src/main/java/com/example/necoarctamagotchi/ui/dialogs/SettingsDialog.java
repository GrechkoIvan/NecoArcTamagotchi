package com.example.necoarctamagotchi.ui.dialogs;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.model.SettingsModel;
import com.example.necoarctamagotchi.utils.BackgroundMusicManager;
import com.example.necoarctamagotchi.utils.SfxManager;
import com.google.android.material.switchmaterial.SwitchMaterial;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsDialog extends DialogFragment {

    @Inject
    SettingsModel settingsModel;
    @Inject
    BackgroundMusicManager backgroundMusicManager;
    @Inject
    SfxManager sfxManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        int width = (int) TypedValue.applyDimension(
                COMPLEX_UNIT_DIP,
                 300,
                getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(
                COMPLEX_UNIT_DIP,
                250,
                getResources().getDisplayMetrics());
        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_settings, container, false);

        SwitchMaterial musicSwitch = view.findViewById(R.id.music_switch);
        SwitchMaterial sfxSwitch = view.findViewById(R.id.sfx_switch);

        musicSwitch.setChecked(Boolean.TRUE.equals(settingsModel.getMusicEnabledStateLiveData().getValue()));
        sfxSwitch.setChecked(Boolean.TRUE.equals(settingsModel.getSfxEnabledStateLiveData().getValue()));

        musicSwitch.setOnCheckedChangeListener((v, isChecked) -> {
            settingsModel.updateMusicEnabledState(isChecked);
            sfxManager.playButtonClickSound();
            backgroundMusicManager.updateMusicState();
        });

        sfxSwitch.setOnCheckedChangeListener((v, isChecked) -> {
            settingsModel.updateSfxEnabledState(isChecked);
            sfxManager.playButtonClickSound();
        });

        ImageButton closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v-> {
            sfxManager.playButtonClickSound();
            dismiss();
        });

        return view;
    }
}
