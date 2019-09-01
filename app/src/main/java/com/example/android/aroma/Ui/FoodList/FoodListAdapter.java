package com.example.android.aroma.Ui.FoodList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.aroma.FoodItem;
import com.example.android.aroma.R;
import com.example.android.aroma.Ui.FoodRecepie.RecepieActivity;
import com.example.android.aroma.databinding.FoodListBinding;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    LayoutInflater layoutInflater;
    List<FoodItem> foodItemList = new ArrayList<>();

    public FoodListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodListBinding foodListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.food_list,parent,false);
        return new FoodViewHolder(foodListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        if (foodItemList != null) {
            FoodItem foodItem = foodItemList.get(position);
            holder.bindTo(foodItem,position);
        }
    }

    @Override
    public int getItemCount() {
        if (foodItemList != null) {
            return foodItemList.size();
        }
        return 0;
    }

    public void setFoodItemList(List<FoodItem> foodItems) {
        if (!foodItems.isEmpty()) {
            foodItemList = foodItems;
            notifyDataSetChanged();
        }
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{

        FoodListBinding foodListBinding;

        public FoodViewHolder(@NonNull FoodListBinding foodListBinding) {
            super(foodListBinding.getRoot());
            this.foodListBinding = foodListBinding;
        }

        public void bindTo(FoodItem foodItem, int position){
            foodListBinding.setFood(foodItem);
            foodListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RecepieActivity.class);
                    intent.putExtra("id",foodItem);
                    view.getContext().startActivity(intent);
                }
            });
            foodListBinding.executePendingBindings();
        }
    }
}
