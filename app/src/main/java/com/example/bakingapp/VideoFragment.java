package com.example.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.bakingapp.Model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    ArrayList<Step> mStepArrayList;
    int mVideoNumber;

    // Keys for saving state
    public static final String STEP_LIST_STATE = "step_list_state";
    public static final String STEP_NUMBER_STATE = "step_number_state";


    // Global variables
    SimpleExoPlayer mSimpleExoPlayer;
    PlayerView mPlayerView;
    DefaultBandwidthMeter bandwidthMeter;
    TrackSelection.Factory videoTrackSelectionFactory;
    TrackSelector trackSelector;
    DataSource.Factory dataSourceFactory;
    MediaSource videoSource;

    public void initializeVideoPlayer(Uri videoUri){
        if(mSimpleExoPlayer == null){
            // 1. Create a default TrackSelector
            bandwidthMeter = new DefaultBandwidthMeter();
            videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            // 2. Create the player
            mPlayerView = mPlayerView.findViewById(R.id.player_view);
            mSimpleExoPlayer =
                    ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            // Bind the player to the view.
            mPlayerView.setPlayer(mSimpleExoPlayer);

            // Produces DataSource instances through which media data is loaded.
            dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "YOUR_APP_NAME"), bandwidthMeter);

            // This is the MediaSource representing the media to be played.
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoUri);
            // Prepare the player with the source.
            mSimpleExoPlayer.prepare(videoSource);
        }
    }

    // Release player
    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
            dataSourceFactory = null;
            videoSource = null;
            trackSelector = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mSimpleExoPlayer!=null) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST_STATE,mStepArrayList);
        outState.putInt(STEP_NUMBER_STATE, mVideoNumber);
    }
}
