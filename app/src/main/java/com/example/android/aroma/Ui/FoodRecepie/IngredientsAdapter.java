package com.example.android.aroma.Ui.FoodRecepie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.aroma.FoodItem;
import com.example.android.aroma.R;
import com.example.android.aroma.databinding.IngredientsListBinding;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private static final String TAG = IngredientsAdapter.class.getSimpleName();
    LayoutInflater inflater;
    List<FoodItem>ingredientsList = new ArrayList<>();

    public IngredientsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(DataBindingUtil.inflate(inflater,R.layout.ingredients_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {

        FoodItem ingredientItem = ingredientsList.get(position);
        holder.bindTo(ingredientItem);
    }

    @Override
    public int getItemCount() {
        if (!ingredientsList.isEmpty()){
            return ingredientsList.size();
        }else {
            return 0;
        }
    }

    public void setIngredientsList(List<FoodItem>ingredientsList){
        if (!ingredientsList.isEmpty()){
            this.ingredientsList = ingredientsList;
            notifyDataSetChanged();
        }
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder{

        IngredientsListBinding listBinding;

        public IngredientsViewHolder(@NonNull IngredientsListBinding listBinding) {
            super(listBinding.getRoot());

            this.listBinding = listBinding;
        }

        public void bindTo(FoodItem item){
            listBinding.setIngredients(item);
            listBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Ingredients clicked", Toast.LENGTH_LONG).show();
                }
            });
            listBinding.executePendingBindings();
        }
    }
}
