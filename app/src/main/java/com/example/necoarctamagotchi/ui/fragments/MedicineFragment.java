package com.example.necoarctamagotchi.ui.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.ui.adapters.ArcPageTransformer;
import com.example.necoarctamagotchi.ui.adapters.DishAdapter;
import com.example.necoarctamagotchi.ui.adapters.MedicineAdapter;
import com.example.necoarctamagotchi.ui.viewmodels.KitchenViewModel;
import com.example.necoarctamagotchi.ui.viewmodels.MedicineViewModel;
import com.example.necoarctamagotchi.utils.SfxManager;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MedicineFragment extends Fragment {

    @Inject
    SfxManager sfxManager;

    private MedicineViewModel mViewModel;

    MedicineAdapter medicineAdapter;
    ImageButton buttonNext;
    ImageButton buttonPrevious;

    public static MedicineFragment newInstance() {
        return new MedicineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine, container, false);

        buttonNext = view.findViewById(R.id.medicine_scroll_button_next);
        buttonPrevious = view.findViewById(R.id.medicine_scroll_button_previous);

        ViewPager2 viewPager = view.findViewById(R.id.medicine_slider);
        medicineAdapter = new MedicineAdapter(new ArrayList<>());

        MedicineViewModel medicineViewModel = new ViewModelProvider(this).get(MedicineViewModel.class);
        medicineAdapter.updateMedicines(medicineViewModel.getMedicines().getValue());

        medicineViewModel.getMedicines().observe(getViewLifecycleOwner(), medicines -> medicineAdapter.updateMedicines(medicines));

        viewPager.setAdapter(medicineAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setPageTransformer(new ArcPageTransformer());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateButtonVisibility(position);
            }
        });

        buttonPrevious.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            }
        });

        buttonNext.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < medicineAdapter.getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            }
        });

        Button takeMedicineButton = view.findViewById(R.id.take_medicine_button);
        takeMedicineButton.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            sfxManager.playJoyVoice();
            int currentItemPosition = viewPager.getCurrentItem();
            medicineViewModel.takeMedicine(currentItemPosition);
        });

        updateButtonVisibility(0);

        return view;
    }

    private void updateButtonVisibility(int position) {
        if (position == 0) {
            buttonPrevious.setVisibility(View.GONE);
        } else {
            buttonPrevious.setVisibility(View.VISIBLE);
            if (position == medicineAdapter.getItemCount() - 1) {
                buttonNext.setVisibility(View.GONE);
            } else {
                buttonNext.setVisibility(View.VISIBLE);

            }
        }
    }
}