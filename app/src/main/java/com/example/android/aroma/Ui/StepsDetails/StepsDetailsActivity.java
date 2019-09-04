package com.example.android.aroma.Ui.StepsDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.example.android.aroma.FoodItem;
import com.example.android.aroma.R;

public class StepsDetailsActivity extends AppCompatActivity {

    //Fragment key to restore onSaveInstanceState
    StepDetailsFragment detailFragment;

    private static final String TAG = StepsDetailsActivity.class.getSimpleName();

    String description = " ";
    String videoUrl = " ";
    String thumbnailUrl = " ";
    int id;
    int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);

        FoodItem foodItem = getIntent().getParcelableExtra("step");

        id = getIntent().getIntExtra("stepId",-1);
        Log.e("StepId", "Step Id: " + id);
        foodId = getIntent().getIntExtra("foodId",-1);
        Log.e(TAG,"food Id is: " + foodId);
        if (foodItem != null) {
            description = foodItem.getDescription();
            videoUrl = foodItem.getVideoUrl();

            thumbnailUrl = foodItem.getThumbnailUrl();
        }

        if (savedInstanceState == null){

            detailFragment = new StepDetailsFragment();
            detailFragment.setDescription(description);
            detailFragment.setVideoUrl(videoUrl);
            detailFragment.setThumbnailUrl(thumbnailUrl);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_details_fragment, detailFragment)
                    .commit();
        }
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }
}
