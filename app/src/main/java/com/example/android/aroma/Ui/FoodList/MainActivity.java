package com.example.android.aroma.Ui.FoodList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android.aroma.FoodViewModel;
import com.example.android.aroma.R;
import com.example.android.aroma.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    private RecyclerView recyclerView;
    private FoodListAdapter foodListAdapter;
    FoodViewModel foodViewModel;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        recyclerView = mainBinding.foodRecycler;
        recyclerView.setHasFixedSize(true);
        foodListAdapter = new FoodListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodListAdapter);
        Log.e(TAG,"Welcome");

        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        foodViewModel.getFoodItems().observe(MainActivity.this, foodItems -> {
            if (!foodItems.isEmpty()){
                foodListAdapter.setFoodItemList(foodItems);
                Toast.makeText(getApplicationContext(),"Food List", Toast.LENGTH_LONG).show();
                Log.e(TAG,"Food list");
            }else {
                Toast.makeText(getApplicationContext(),"Food List empty", Toast.LENGTH_LONG).show();
                Log.e(TAG,"Empty Food list");

            }
        });
    }
}
