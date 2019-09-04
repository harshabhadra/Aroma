package com.example.android.aroma;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class FoodViewModel extends ViewModel {

    FoodRepository foodRepository;

    public FoodViewModel() {

        foodRepository = FoodRepository.getInstance();
    }

    //Return List of Food item
    public LiveData<List<FoodItem>> getFoodItems() {
        return foodRepository.getFoodItemListLiveData();
    }

    //Return list of ingredients
    public LiveData<List<FoodItem>> getIngredientsList(int id) {
        return foodRepository.getIngredientListLiveData(id);
    }

    //Return list of steps
    public LiveData<List<FoodItem>>getStepsList(int id){
        return foodRepository.getStepsListLiveData(id);
    }

    //Return step item;
    public FoodItem getStepItem(int foodId, int id){
        return foodRepository.getStepItem(foodId, id);
    }
}
