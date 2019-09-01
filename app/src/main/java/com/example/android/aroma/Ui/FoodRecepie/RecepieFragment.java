package com.example.android.aroma.Ui.FoodRecepie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.aroma.FoodItem;
import com.example.android.aroma.FoodViewModel;
import com.example.android.aroma.R;
import com.example.android.aroma.databinding.RecepieFragmentBinding;

import java.util.List;

public class RecepieFragment extends Fragment {

    private static final String TAG = RecepieFragment.class.getSimpleName();
    RecepieFragmentBinding recepieFragmentBinding;
    IngredientsAdapter ingredientsAdapter;
    int id;
    private RecyclerView ingredientsRecycler;
    private RecyclerView stepsRecyclerView;
    private FoodViewModel foodViewModel;
    private StepsAdapter stepsAdapter;

    public static RecepieFragment newInstance() {
        return new RecepieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        recepieFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.recepie_fragment, container, false);
        return recepieFragmentBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Setting up ingredients and steps recyclerView
        setIngredientsRecycler();
        setStepsRecyclerView();

        //Getting the value of the food Id from activity to fragment
        RecepieActivity recepieActivity = (RecepieActivity) getActivity();
        if (recepieActivity != null) {
            id = recepieActivity.getId();
        }

        //Initializing ViewModel
        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);

        //Getting list of ingredients
        foodViewModel.getIngredientsList(id).observe(this, foodItems -> {
            if (foodItems != null) {

                ingredientsAdapter.setIngredientsList(foodItems);
            }
        });

        //Getting list of steps
        foodViewModel.getStepsList(id).observe(this, foodItems -> {
            if (foodItems != null){
                Log.e(TAG," Step List is full");
                stepsAdapter.setStepList(foodItems);
            }else {
                Log.e(TAG, "Step List is empty");
            }
        });
    }

    //Set up Ingredients list to the recyclerView
    private void setIngredientsRecycler() {

        //Initializing layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        //bind the recycler view
        ingredientsRecycler = recepieFragmentBinding.ingredientsRecycler;
        ingredientsRecycler.setHasFixedSize(true);

        //set layout manager to the recyclerView
        ingredientsRecycler.setLayoutManager(linearLayoutManager);

        //adding divider to recyclerView
        DividerItemDecoration decoration = new DividerItemDecoration(ingredientsRecycler.getContext()
                , linearLayoutManager.getOrientation());
        ingredientsRecycler.addItemDecoration(decoration);

        //Setting adapter to the recyclerView
        ingredientsAdapter = new IngredientsAdapter(getActivity());
        ingredientsRecycler.setAdapter(ingredientsAdapter);
    }

    //Set Up steps in recyclerView
    private void setStepsRecyclerView(){

        //Initializing layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        //bind the recyclerView
        stepsRecyclerView = recepieFragmentBinding.stepsRecycler;
        stepsRecyclerView.setHasFixedSize(true);

        //Set layout manager to the recyclerView
        stepsRecyclerView.setLayoutManager(linearLayoutManager);

        //Setting adapter
        stepsAdapter = new StepsAdapter(getActivity());
        stepsRecyclerView.setAdapter(stepsAdapter);
        Log.e(TAG,"Recycler Attached with adapter");
    }

}
