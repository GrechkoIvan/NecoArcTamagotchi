package com.example.necoarctamagotchi.ui.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.ui.viewmodels.BedroomViewModel;
import com.example.necoarctamagotchi.utils.SfxManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BedroomFragment extends Fragment {

    @Inject
    SfxManager sfxManager;

    private ImageView lamp;
    Handler handler = new Handler();

    public static BedroomFragment newInstance() {
        return new BedroomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bedroom, container, false);

        lamp = view.findViewById(R.id.lamp_image);
        BedroomViewModel bedroomViewModel = new ViewModelProvider(this).get(BedroomViewModel.class);

        bedroomViewModel.getSleepingStateLiveData().observe(getViewLifecycleOwner(), this::updateLampState);

        lamp.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            bedroomViewModel.updateSleepingState(Boolean.FALSE.equals(bedroomViewModel.getSleepingStateLiveData().getValue()));
            updateLampState(Boolean.TRUE.equals(bedroomViewModel.getSleepingStateLiveData().getValue()));

            lamp.setEnabled(false);
            handler.postDelayed(() -> lamp.setEnabled(true), 500);
        });
        return view;
    }

    private void updateLampState(boolean isSleeping) {
        if (!isSleeping) {
            lamp.setImageResource(R.drawable.ic_lamp_on);
        } else {
            lamp.setImageResource(R.drawable.ic_lamp_off);
            sfxManager.playSleepVoice();
        }
    }
}