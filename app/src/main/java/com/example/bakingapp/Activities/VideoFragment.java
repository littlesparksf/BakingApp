package com.example.bakingapp.Activities;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.Model.Step;
import com.example.bakingapp.R;
import com.example.bakingapp.Utils.ConstantsUtil;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {

    public static final String STEP_URI = "step_uri";
    public static final String STEP_VIDEO_POSITION = "step_video_position";
    public static final String STEP_PLAY_WHEN_READY = "step_play_video_when_ready";
    public static final String STEP_PLAY_WINDOW_INDEX = "step_play_window_index";
    public static final String STEP_SINGLE = "step_single";

    int mVideoNumber = 0;

    @BindView(R.id.tv_step_title)
    TextView mStepTitle;

    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.tv_step_description)
    TextView mStepDescription;

    @BindView(R.id.iv_video_placeholder)
    ImageView mImageViewPlaceholder;

    SimpleExoPlayer mSimpleExoPlayer;

    Step mStep;
    ArrayList<Step> mStepArrayList;
    Uri mVideoUri;
    String mVideoThumbnail;
    Bitmap mVideoThumbnailImage;
    boolean mShouldPlayWhenReady = true;
    long mPlayerPosition;
    int mWindowIndex;

//    PlayerView mPlayerView;
//    DefaultBandwidthMeter bandwidthMeter;
//    TrackSelection.Factory videoTrackSelectionFactory;
//    TrackSelector trackSelector;
//    DataSource.Factory dataSourceFactory;
//    MediaSource videoSource;
//
//
//    ArrayList<Step> mStepArrayList;
//    int mVideoNumber;
//
//    // Keys for saving state
//    public static final String STEP_LIST_STATE = "step_list_state";
//    public static final String STEP_NUMBER_STATE = "step_number_state";


    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.video_fragment, container, false);

        ButterKnife.bind(this, root);

        // Check if there is a saved state
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(STEP_SINGLE);
            mShouldPlayWhenReady = savedInstanceState.getBoolean(STEP_PLAY_WHEN_READY);
            mPlayerPosition = savedInstanceState.getLong(STEP_VIDEO_POSITION);
            mWindowIndex = savedInstanceState.getInt(STEP_PLAY_WINDOW_INDEX);
            mVideoUri = Uri.parse(savedInstanceState.getString(STEP_URI));
        } else {
            if (getArguments() != null) {
                mImageViewPlaceholder.setVisibility(View.GONE);
                mPlayerView.setVisibility(View.VISIBLE);

                mStep = getArguments().getParcelable(ConstantsUtil.STEP_SINGLE);
                // Just added this, trying to move click handling into fragment
                mStepArrayList = getArguments().getParcelableArrayList(ConstantsUtil.RECIPE_SINGLE);

                if (mStep.getVideoUrl().equals("")) {
                    // Check thumbnail
                    if (mStep.getThumbnailUrl().equals("")) {
                        mPlayerView.setUseArtwork(true);
                        mImageViewPlaceholder.setVisibility(View.VISIBLE);
                        mPlayerView.setUseController(false);
                    } else {
                        mImageViewPlaceholder.setVisibility(View.GONE);
                        mPlayerView.setVisibility(View.VISIBLE);
                        mVideoThumbnail = mStep.getThumbnailUrl();
                        mVideoThumbnailImage = ThumbnailUtils.createVideoThumbnail(mVideoThumbnail, MediaStore.Video.Thumbnails.MICRO_KIND);
                        mPlayerView.setUseArtwork(true);
                        mPlayerView.setDefaultArtwork(mVideoThumbnailImage);
                    }
                } else {
                    mVideoUri = Uri.parse(mStep.getVideoUrl());
                }
            }
        }
        return root;
    }

    public void initializeVideoPlayer(Uri videoUri) {

        mStepDescription.setText(mStep.getDescription());
        mStepTitle.setText(mStep.getShortDescription());

        if (mSimpleExoPlayer == null) {
            // 1. Create a default TrackSelector
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            // Bind the player to the view.
            mPlayerView.setPlayer(mSimpleExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            if (mPlayerPosition != C.TIME_UNSET) {
                mSimpleExoPlayer.seekTo(mPlayerPosition);
            }

            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(mShouldPlayWhenReady);
        }
    }

    // Release player
    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            updateStartPosition();
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeVideoPlayer(mVideoUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mSimpleExoPlayer == null) {
            initializeVideoPlayer(mVideoUri);
        }
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(mShouldPlayWhenReady);
            mSimpleExoPlayer.seekTo(mPlayerPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSimpleExoPlayer != null) {
            updateStartPosition();
            if (Util.SDK_INT <= 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSimpleExoPlayer != null) {
            updateStartPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        if (mSimpleExoPlayer != null) {
//            releasePlayer();
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        releasePlayer();
//    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateStartPosition();
        outState.putString(STEP_URI, mStep.getVideoUrl());
        outState.putParcelable(STEP_SINGLE, mStep);
        outState.putLong(STEP_VIDEO_POSITION, mPlayerPosition);
        outState.putBoolean(STEP_PLAY_WHEN_READY, mShouldPlayWhenReady);
    }

    private void updateStartPosition() {
        if (mSimpleExoPlayer != null) {
            mShouldPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mWindowIndex = mSimpleExoPlayer.getCurrentWindowIndex();
            mPlayerPosition = mSimpleExoPlayer.getCurrentPosition();
        }
    }
}
