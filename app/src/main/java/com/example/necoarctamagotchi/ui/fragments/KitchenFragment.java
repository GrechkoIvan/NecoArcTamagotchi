package com.example.necoarctamagotchi.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.dto.DishDto;
import com.example.necoarctamagotchi.ui.adapters.ArcPageTransformer;
import com.example.necoarctamagotchi.ui.adapters.DishAdapter;
import com.example.necoarctamagotchi.ui.viewmodels.KitchenViewModel;
import com.example.necoarctamagotchi.ui.viewmodels.MainViewModel;

import java.util.ArrayList;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class KitchenFragment extends Fragment {

    DishAdapter dishAdapter;
    ImageButton buttonNext;
    ImageButton buttonPrevious;

    public static KitchenFragment newInstance() {
        return new KitchenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitchen, container, false);

        buttonNext = view.findViewById(R.id.scroll_button_next);
        buttonPrevious = view.findViewById(R.id.scroll_button_previous);

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
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            }
        });

        buttonNext.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < dishAdapter.getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            }
        });

        Button feedButton = view.findViewById(R.id.feed_button);
        feedButton.setOnClickListener(v -> {
            int currentItemPosition = viewPager.getCurrentItem();
            kitchenViewModel.feedDish(currentItemPosition);
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