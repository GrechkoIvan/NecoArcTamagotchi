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
import com.example.necoarctamagotchi.data.dto.MedicineDto;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    private List<MedicineDto> medicines;

    public MedicineAdapter(List<MedicineDto> medicines) {
        this.medicines = medicines;
    }

    @NonNull
    @Override
    public MedicineAdapter.MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.MedicineViewHolder holder, int position) {
        MedicineDto medicine = medicines.get(position);
        holder.bind(medicine);
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void updateMedicines(List<MedicineDto> newMedicines) {
        this.medicines = newMedicines;
        notifyDataSetChanged();
    }

    static class MedicineViewHolder extends RecyclerView.ViewHolder {
        private final ImageView medicineImage;
        private final TextView costText;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            medicineImage = itemView.findViewById(R.id.medicine_image);
            costText = itemView.findViewById(R.id.medicine_cost_text);
        }

        public void bind(MedicineDto medicine) {
            medicineImage.setImageResource(medicine.getImageResId());
            costText.setText(String.valueOf(medicine.getCost()));
        }
    }
}
