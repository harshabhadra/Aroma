package com.example.android.aroma.Ui.FoodRecepie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android.aroma.FoodItem;
import com.example.android.aroma.R;

public class RecepieActivity extends AppCompatActivity {

    private static final String TAG = RecepieActivity.class.getSimpleName();
    FoodItem foodItem;
    int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie);

        foodItem = getIntent().getParcelableExtra("id");
        if (foodItem != null) {
            id = foodItem.getItemId();
            Log.e(TAG,"Food is is: " + id);
        }
    }

    public int getId(){
        return id;
    }
}
