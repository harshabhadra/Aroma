package com.example.android.aroma.Ui.StepsDetails;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.android.aroma.R;
import com.example.android.aroma.databinding.FragmentStepDetailsBinding;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
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
    FragmentStepDetailsBinding stepDetailsBinding;
    String description;
    private SimpleExoPlayer exoPlayer;
    private PlayerView playerView;
    private String videoUrl;
    private boolean playeWhenReady = true;
    private long playBackPosition = -1;
    private int currentWindow;

    private final String SELECTED_POSITION = "selected_position";
    private final String PLAY_WHEN_READY = "play_when_ready";

    public StepDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        stepDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_details, container, false);

        if (savedInstanceState != null){
            playBackPosition = savedInstanceState.getLong(SELECTED_POSITION,C.TIME_UNSET);
            playeWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }
        return stepDetailsBinding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        stepDetailsBinding.shortDes.setText(description);
        if (Util.SDK_INT <= 23 || exoPlayer == null) {
            if (videoUrl != null) {
                intializeExoPlayer(videoUrl);
                Log.e(TAG, "exo player started");
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (videoUrl != null) {
                intializeExoPlayer(videoUrl);
                Log.e(TAG, "exo player started");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releaseExoPlayer();
            Log.e(TAG, "exo player released");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releaseExoPlayer();
            Log.e(TAG, "exo player released");
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION,playBackPosition);
        outState.putBoolean(PLAY_WHEN_READY,playeWhenReady);
    }

    //Initialize exoPlayer
    private void intializeExoPlayer(String videoUrl) {
        if (exoPlayer == null) {
            stepDetailsBinding.exoPlayer.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),
                    new DefaultRenderersFactory(getContext()),
                    trackSelector, loadControl);
            stepDetailsBinding.exoPlayer.setPlayer(exoPlayer);
            stepDetailsBinding.exoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            exoPlayer.setPlayWhenReady(playeWhenReady);
            exoPlayer.seekTo(currentWindow, playBackPosition);
            Log.e(TAG, "exoplayer created");
        }
        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        exoPlayer.prepare(mediaSource, true, false);
    }

    //Build media source
    private MediaSource buildMediaSource(Uri uri) {
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

            playBackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playeWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.stop();
            exoPlayer.release();
        }
    }
}
