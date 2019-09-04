package com.example.android.aroma;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FoodRepository {

    private static final String TAG = FoodRepository.class.getSimpleName();

    //Store list of Food items
    private MutableLiveData<List<FoodItem>> foodItemListLiveData;

    //Store list of ingredients
    private MutableLiveData<List<FoodItem>> ingredientListLiveData;

    //Store list of steps
    private MutableLiveData<List<FoodItem>> stepsListLiveData;

    //Store Step object
    private FoodItem foodItem;

    public static FoodRepository getInstance() {
        return new FoodRepository();
    }

    //Method to get a step item
    public FoodItem getStepItem(int foodId, int id) {

        loadFoodItem(foodId, id);
        return foodItem;
    }

    //This Method will get list of Food items return in response from API
    public MutableLiveData<List<FoodItem>> getFoodItemListLiveData() {

        if (foodItemListLiveData == null) {
            foodItemListLiveData = new MutableLiveData<>();
            loadFoodItemList();
        }
        return foodItemListLiveData;
    }

    //This method will get list of Ingredients
    public MutableLiveData<List<FoodItem>> getIngredientListLiveData(int id) {
        if (ingredientListLiveData == null) {
            ingredientListLiveData = new MutableLiveData<>();
            loadIngredientsList(id);
        }

        return ingredientListLiveData;
    }

    //Method to ger list of Steps
    public MutableLiveData<List<FoodItem>> getStepsListLiveData(int id) {
        if (stepsListLiveData == null) {
            stepsListLiveData = new MutableLiveData<>();
            loadStepsLiveData(id);
        }
        return stepsListLiveData;
    }

    private void loadFoodItem(int foodId, int id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Jsonurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<String> call = api.getFoodItem();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.e(TAG, "response step item successful");
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        JSONObject jsonObject = jsonArray.getJSONObject(foodId);
                        JSONArray stepsArray = jsonObject.getJSONArray("steps");

                        JSONObject stepObj = stepsArray.getJSONObject(id);
                        String id = stepObj.getString("id");
                        String shortDes = stepObj.getString("shortDescription");
                        String description = stepObj.getString("description");
                        String videoUrl = stepObj.getString("videoURL");
                        String thumbUrl = stepObj.getString("thumbnailURL");
                        Log.e(TAG, "des: " + description);
                        Log.e(TAG, "video: " + videoUrl);
                        foodItem = new FoodItem(id, shortDes, description, videoUrl, thumbUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "no response");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    //Method to get steps from API
    private void loadStepsLiveData(int id) {
        List<FoodItem> stepsList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Jsonurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<String> call = api.getFoodItem();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    JSONArray jsonArray = new JSONArray(response.body());
                    JSONObject jsonObject = jsonArray.getJSONObject(id - 1);
                    JSONArray stepsArray = jsonObject.getJSONArray("steps");
                    for (int i = 0; i < stepsArray.length(); i++) {
                        JSONObject stepObj = stepsArray.getJSONObject(i);

                        String id = stepObj.getString("id");
                        String shortDes = stepObj.getString("shortDescription");
                        String description = stepObj.getString("description");
                        String videoUrl = stepObj.getString("videoURL");
                        String thumbUrl = stepObj.getString("thumbnailURL");
                        FoodItem foodItem = new FoodItem(id, shortDes, description, videoUrl, thumbUrl);
                        stepsList.add(foodItem);
                        stepsListLiveData.setValue(stepsList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    //Method to get ingredients list from API
    private void loadIngredientsList(int id) {

        List<FoodItem> ingredientsList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Jsonurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String> call = api.getFoodItem();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {

                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        JSONObject object = jsonArray.getJSONObject((id - 1));
                        JSONArray ingredientsArray = object.getJSONArray("ingredients");
                        for (int i = 0; i < ingredientsArray.length(); i++) {

                            JSONObject ingredientsObj = ingredientsArray.getJSONObject(i);
                            int quantity = ingredientsObj.getInt("quantity");
                            String ingreQty = String.valueOf(quantity);
                            String measure = ingredientsObj.getString("measure");
                            String ingreName = ingredientsObj.getString("ingredient");

                            FoodItem foodItem = new FoodItem(ingreQty, measure, ingreName);

                            ingredientsList.add(foodItem);
                            ingredientListLiveData.setValue(ingredientsList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    //Method to request to API for food items
    private void loadFoodItemList() {

        List<FoodItem> foodItems = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.Jsonurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String> call = api.getFoodItem();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body() != null) {

                    try {
                        JSONArray foodArray = new JSONArray(response.body());
                        for (int i = 0; i < foodArray.length(); i++) {
                            JSONObject jsonObject = foodArray.getJSONObject(i);

                            int id = jsonObject.getInt("id");
                            int servings = jsonObject.getInt("servings");
                            String name = jsonObject.getString("name");

                            Log.v(TAG, "id : " + id);
                            Log.v(TAG, "servings : " + servings);
                            Log.v(TAG, "name : " + name);
                            FoodItem foodItem = new FoodItem(name, id, servings);
                            foodItems.add(foodItem);
                            foodItemListLiveData.setValue(foodItems);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e(TAG, "Food list is empty response" + response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
