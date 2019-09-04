package com.example.android.aroma.Ui.FoodRecepie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.aroma.FoodItem;
import com.example.android.aroma.R;
import com.example.android.aroma.Ui.StepsDetails.StepsDetailsActivity;
import com.example.android.aroma.databinding.RecepieListBinding;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    LayoutInflater inflater;
    List<FoodItem> foodItemList = new ArrayList<>();
    int foodId = -1;

    public StepsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecepieListBinding recepieListBinding = DataBindingUtil.inflate(inflater, R.layout.recepie_list, parent, false);
        return new StepsViewHolder(recepieListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {

        FoodItem foodItem = foodItemList.get(position);
        holder.bindTo(foodItem, position);
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public void setStepList(List<FoodItem> foodItems) {
        if (foodItems != null) {
            foodItemList = foodItems;
            notifyDataSetChanged();
        }
    }

    public void setFoodId(int id){
        foodId = id;
        Log.e("StepAdapter","food is is: " + foodId);
        notifyDataSetChanged();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {

        RecepieListBinding recepieListBinding;

        public StepsViewHolder(@NonNull RecepieListBinding recepieListBinding) {
            super(recepieListBinding.getRoot());
            this.recepieListBinding = recepieListBinding;
        }

        public void bindTo(FoodItem foodItem, int position) {

            recepieListBinding.setSteps(foodItem);
            recepieListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent stepIntent = new Intent(view.getContext(), StepsDetailsActivity.class);
                    stepIntent.putExtra("step",foodItem);
                    stepIntent.putExtra("stepId", position);
                    stepIntent.putExtra("foodId", foodId);
                    view.getContext().startActivity(stepIntent);
                }
            });
            recepieListBinding.executePendingBindings();
        }
    }
}
