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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.ui.viewmodels.BedroomViewModel;
import com.example.necoarctamagotchi.ui.viewmodels.EntertainmentsViewModel;
import com.example.necoarctamagotchi.utils.SfxManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EntertainmentsFragment extends Fragment {

    @Inject
    SfxManager sfxManager;

    private EntertainmentsViewModel mViewModel;
    Handler handler = new Handler();

    public static EntertainmentsFragment newInstance() {
        return new EntertainmentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entertainments, container, false);

        ImageButton pepsiToyButton = view.findViewById(R.id.pepsi_toy_entertainment);
        ImageButton danceButton = view.findViewById(R.id.dance_entertainment_button);
        ImageButton flipButton = view.findViewById(R.id.flip_entertainment_button);

        EntertainmentsViewModel entertainmentsViewModel =
                new ViewModelProvider(this).get(EntertainmentsViewModel.class);


        pepsiToyButton.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            boolean action = entertainmentsViewModel.playWithPepsiToy();
            if (!action) {
                Toast toast = Toast.makeText(requireContext(), R.string.toast_lack_of_energy_text,Toast.LENGTH_SHORT);
                toast.show();
            } else {
                sfxManager.playPepsiToyVoice();

                pepsiToyButton.setEnabled(false);
                danceButton.setEnabled(false);
                flipButton.setEnabled(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pepsiToyButton.setEnabled(true);
                        danceButton.setEnabled(true);
                        flipButton.setEnabled(true);
                    }
                }, 3200);
            }
        });

        danceButton.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            boolean action = entertainmentsViewModel.dance();
            if (!action) {
                Toast toast = Toast.makeText(requireContext(), R.string.toast_lack_of_energy_text,Toast.LENGTH_SHORT);
                toast.show();
            } else {
                sfxManager.playDanceVoice();

                pepsiToyButton.setEnabled(false);
                danceButton.setEnabled(false);
                flipButton.setEnabled(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pepsiToyButton.setEnabled(true);
                        danceButton.setEnabled(true);
                        flipButton.setEnabled(true);
                    }
                }, 4500);
            }
        });

        flipButton.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            boolean action = entertainmentsViewModel.doFlip();
            if (!action) {
                Toast toast = Toast.makeText(requireContext(), R.string.toast_lack_of_energy_text,Toast.LENGTH_SHORT);
                toast.show();
            } else {
                sfxManager.playFlipVoice();

                pepsiToyButton.setEnabled(false);
                danceButton.setEnabled(false);
                flipButton.setEnabled(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pepsiToyButton.setEnabled(true);
                        danceButton.setEnabled(true);
                        flipButton.setEnabled(true);
                    }
                }, 900);
            }
        });
        return view;
    }

}