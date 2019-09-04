package com.example.android.aroma.Ui.StepsDetails;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.android.aroma.FoodItem;
import com.example.android.aroma.FoodViewModel;
import com.example.android.aroma.R;
import com.example.android.aroma.databinding.FragmentStepDetailsBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment {


    private static final String TAG = StepDetailsFragment.class.getSimpleName();
    int id;
    FoodViewModel foodViewModel;
    int foodId;
    private FragmentStepDetailsBinding stepDetailsBinding;
    private String description;
    private String thumbnailUrl;
    private SimpleExoPlayer exoPlayer;
    private PlayerView playerView;
    private String videoUrl;
    private boolean playeWhenReady = true;
    private long playBackPosition = -1;
    private int currentWindow;
    private boolean isPlaying = false;

    public StepDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        stepDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_details, container, false);
        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        return stepDetailsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StepsDetailsActivity stepsDetailsActivity = (StepsDetailsActivity) getActivity();
        if (stepsDetailsActivity != null) {
            id = stepsDetailsActivity.getId();
            foodId = (stepsDetailsActivity.getFoodId() - 1);
            Log.e(TAG, "step id is: " + id);
            Log.e(TAG,"Food id is: " + foodId);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        stepDetailsBinding.shortDes.setText(description);
        if (thumbnailUrl != null) {
            stepDetailsBinding.thumbnailView.setVisibility(View.VISIBLE);
            Glide.with(this).load(thumbnailUrl).into(stepDetailsBinding.thumbnailView);
        }

        if (Util.SDK_INT <= 23 || exoPlayer == null) {
            if (!videoUrl.isEmpty()) {
                stepDetailsBinding.exoPlayer.setVisibility(View.VISIBLE);
                intializeExoPlayer(videoUrl);
            }
        }
        onNextButtonClicked();
        onPreviousButtonClicked();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            if (!videoUrl.isEmpty()) {

                stepDetailsBinding.exoPlayer.setVisibility(View.VISIBLE);
                intializeExoPlayer(videoUrl);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releaseExoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releaseExoPlayer();
        }
    }

    //Initialize exoPlayer
    private void intializeExoPlayer(String videoUrl) {
        if (exoPlayer == null && videoUrl != null) {

            isPlaying = true;
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),
                    new DefaultRenderersFactory(getContext()),
                    trackSelector, loadControl);
            stepDetailsBinding.exoPlayer.setPlayer(exoPlayer);
            stepDetailsBinding.exoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            exoPlayer.setPlayWhenReady(playeWhenReady);
            exoPlayer.seekTo(currentWindow, playBackPosition);
        }
        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        exoPlayer.prepare(mediaSource, true, false);
    }

    //Build media source
    private MediaSource buildMediaSource(Uri uri) {


        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        DefaultHttpDataSourceFactory dataSourceFactory =
                new DefaultHttpDataSourceFactory("user-agent");

        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("Aroma",
                        null,
                        DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                        DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                        true)).createMediaSource(uri);
        return mediaSource;
    }

    //Release ExoPlayer
    private void releaseExoPlayer() {
        if (exoPlayer != null) {

            isPlaying = false;
            playBackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playeWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    //Setter method for description
    public void setDescription(String description) {
        if (!description.isEmpty()) {
            this.description = description;
        }
    }

    //Setter method for videoUrl
    public void setVideoUrl(String videoUrl) {
        if (videoUrl != null) {
            this.videoUrl = videoUrl;
        }
    }

    //Setter method for thumbnailUrl

    public void setThumbnailUrl(String thumbnailUrl) {
        if (!thumbnailUrl.isEmpty()) {
            this.thumbnailUrl = thumbnailUrl;
        }
    }

    //Set id of the Step
    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        }
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    private void onNextButtonClicked() {
        stepDetailsBinding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "button clicked");

                if (id >= 0) {
                    id = id +1;
                    Log.e(TAG, "step id is: " + id);
                    Log.e(TAG,"Food id is: " + foodId);
                    FoodItem item = foodViewModel.getStepItem(foodId, id);
                    if (item != null) {
                        description = item.getDescription();
                        videoUrl = item.getVideoUrl();
                        thumbnailUrl = item.getThumbnailUrl();
                        releaseExoPlayer();
                        intializeExoPlayer(videoUrl);
                        stepDetailsBinding.shortDes.setText(description);
                    } else {
                        Log.e(TAG, "empty item");
                    }
                }
            }
        });
    }

    private void onPreviousButtonClicked() {
        stepDetailsBinding.prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "Previous button clicked");
                if (id > 0) {
                    id = id - 1;
                    Log.e(TAG, "step id is: " + id);
                    Log.e(TAG,"Food id is: " + foodId);
                    FoodItem item = foodViewModel.getStepItem(foodId, id);
                    if (item != null) {
                        description = item.getDescription();
                        videoUrl = item.getVideoUrl();
                        thumbnailUrl = item.getThumbnailUrl();
                        releaseExoPlayer();
                        intializeExoPlayer(videoUrl);
                        stepDetailsBinding.shortDes.setText(description);
                    } else {
                        Log.e(TAG, "empty item");
                    }
                }
            }
        });
    }
}
