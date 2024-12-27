package com.example.necoarctamagotchi.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.necoarctamagotchi.R;
import com.example.necoarctamagotchi.data.dto.DishDto;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder>{
    private List<DishDto> dishes;

    public DishAdapter(List<DishDto> dishes) {
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public DishAdapter.DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishAdapter.DishViewHolder holder, int position) {
        DishDto dish = dishes.get(position);
        holder.bind(dish);
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public void updateDishes(List<DishDto> newDishes) {
        this.dishes = newDishes;
        notifyDataSetChanged();
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {
        private final ImageView dishImage;
        private final TextView costText;

        public DishViewHolder(View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.dish_image);
            costText = itemView.findViewById(R.id.dish_cost_text);
        }

        public void bind(DishDto dish) {
            dishImage.setImageResource(dish.getImageResId());
            costText.setText(String.valueOf(dish.getCost()));
        }
    }
}
