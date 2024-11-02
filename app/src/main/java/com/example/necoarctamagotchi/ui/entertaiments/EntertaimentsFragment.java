package com.example.necoarctamagotchi.ui.entertaiments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.necoarctamagotchi.R;

public class EntertaimentsFragment extends Fragment {

    private EntertaimentsViewModel mViewModel;

    public static EntertaimentsFragment newInstance() {
        return new EntertaimentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entertaiments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EntertaimentsViewModel.class);
        // TODO: Use the ViewModel
    }

}