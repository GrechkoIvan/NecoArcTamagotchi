package com.example.necoarctamagotchi.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.dto.DishDto;
import com.example.necoarctamagotchi.ui.adapters.ArcPageTransformer;
import com.example.necoarctamagotchi.ui.adapters.DishAdapter;
import com.example.necoarctamagotchi.ui.viewmodels.KitchenViewModel;
import com.example.necoarctamagotchi.utils.SfxManager;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class KitchenFragment extends Fragment {

    @Inject
    SfxManager sfxManager;

    DishAdapter dishAdapter;
    ImageButton buttonNext;
    ImageButton buttonPrevious;
    Handler handler = new Handler();

    public static KitchenFragment newInstance() {
        return new KitchenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitchen, container, false);

        buttonNext = view.findViewById(R.id.feed_scroll_button_next);
        buttonPrevious = view.findViewById(R.id.feed_scroll_button_previous);

        ViewPager2 viewPager = view.findViewById(R.id.dish_slider);
        dishAdapter = new DishAdapter(new ArrayList<>());

        KitchenViewModel kitchenViewModel = new ViewModelProvider(this).get(KitchenViewModel.class);
        dishAdapter.updateDishes(kitchenViewModel.getDishes().getValue());

        kitchenViewModel.getDishes().observe(getViewLifecycleOwner(), dishes -> dishAdapter.updateDishes(dishes));

        viewPager.setAdapter(dishAdapter);
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
            if (currentItem < dishAdapter.getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            }
        });

        Button feedButton = view.findViewById(R.id.feed_button);
        feedButton.setOnClickListener(v -> {
            sfxManager.playButtonClickSound();
            int currentItemPosition = viewPager.getCurrentItem();
            DishDto dish = kitchenViewModel.getDishes().getValue().get(currentItemPosition);

            boolean action = kitchenViewModel.feedDish(dish);
            if (action) {
                if (dish.getStats().getHappiness() < 0) {
                    sfxManager.playAngerVoice();
                } else {
                    sfxManager.playJoyVoice();
                }
                feedButton.setEnabled(false);
                handler.postDelayed(() -> feedButton.setEnabled(true), 1000);
            } else {
                Toast toast = Toast.makeText(requireContext(), R.string.toast_lack_of_money_text,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        updateButtonVisibility(0);

        return view;
    }

    private void updateButtonVisibility(int position) {
        if (position == 0) {
            buttonPrevious.setVisibility(View.GONE);
        } else {
            buttonPrevious.setVisibility(View.VISIBLE);
            if (position == dishAdapter.getItemCount() - 1) {
                buttonNext.setVisibility(View.GONE);
            } else {
                buttonNext.setVisibility(View.VISIBLE);

            }
        }
    }
}