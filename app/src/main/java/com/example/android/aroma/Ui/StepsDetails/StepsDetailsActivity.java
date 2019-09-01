package com.example.android.aroma.Ui.StepsDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.android.aroma.FoodItem;
import com.example.android.aroma.R;

public class StepsDetailsActivity extends AppCompatActivity {

    //Fragment key to restore onSaveInstanceState
    private static final String DETIAL_FRAGMENT_KEY = "detail_fragment";
    StepDetailsFragment detailFragment;

    String description = " ";
    String videoUrl = " ";
    String thumbnailUrl = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);

        FoodItem foodItem = getIntent().getParcelableExtra("step");

        if (foodItem != null) {
            description = foodItem.getDescription();
            videoUrl = foodItem.getVideoUrl();
            thumbnailUrl = foodItem.getThumbnailUrl();
        }

        if (savedInstanceState == null){

            detailFragment = new StepDetailsFragment();
            detailFragment.setDescription(description);
            detailFragment.setVideoUrl(videoUrl);

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
}
