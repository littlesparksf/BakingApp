package com.example.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bakingapp.Adapters.StepNumberAdapter;
import com.example.bakingapp.Model.Recipe;
import com.example.bakingapp.Model.Step;
import com.example.bakingapp.R;
import com.example.bakingapp.Utils.ConstantsUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity implements View.OnClickListener, StepNumberAdapter.OnStepClick, VideoFragment.VideoFragmentSaveInstanceListener {

    // Global variables
    private Step mStep;
    private Recipe mRecipe;
    private int mStepPosition;
    private ArrayList<Step> mStepArrayList;
    boolean isFromWidget;
    private long mCurrentPosition;

    // Keys for saving state
    private static final String VIDEO_PLAY_POSITION = "video_play_position";
    public static final String STEP_LIST_STATE = "step_list_state";
    public static final String STEP_NUMBER_STATE = "step_number";
    public static final String STEP_LIST_JSON_STATE = "step_list_json_state";
    private static final String LOG_TAG = StepDetailActivity.class.getSimpleName();

    int mVideoNumber = 0;

    @BindView(R.id.fl_player_container)
    FrameLayout mFragmentContainer;

    @BindView(R.id.btn_next_step)
    ImageButton mButtonNextStep;

    @BindView(R.id.btn_previous_step)
    ImageButton mButtonPreviousStep;

    @Nullable
    // Check this bc there is no steps recycler view on this page
    @BindView(R.id.steps_recycler_view)
    RecyclerView mRecyclerViewSteps;
    String mJsonResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        // Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get step list and current step from intent
        Intent intent = getIntent();
        mStepArrayList = intent.getParcelableArrayListExtra("steps");
        mStep = intent.getParcelableExtra("step");

        playVideo(mStep);

        if (intent != null) {
//            if (intent.hasExtra(ConstantsUtil.STEP_INTENT_EXTRA)) {
//                mStepArrayList = getIntent().getParcelableArrayListExtra(ConstantsUtil.STEP_INTENT_EXTRA);
//            }
//            if (intent.hasExtra(ConstantsUtil.JSON_RESULT_EXTRA)) {
//                mJsonResult = getIntent().getStringExtra(ConstantsUtil.JSON_RESULT_EXTRA);
//            }
            if (intent.getStringExtra(ConstantsUtil.WIDGET_EXTRA) != null) {
                isFromWidget = true;
            } else {
                isFromWidget = false;
            }
        }

        ButterKnife.bind(this);

        handleUiForDevice();
    }

    public void playVideo(Step step) {
        VideoFragment videoPlayerFragment = new VideoFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantsUtil.STEP_SINGLE, step);
        videoPlayerFragment.setArguments(stepsBundle);
        videoPlayerFragment.setExoPlayerPosition(mCurrentPosition);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fl_player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    // Not sure I need this, maybe I can just use playVideo method again
    public void playVideoReplace(Step step) {
        VideoFragment videoPlayerFragment = new VideoFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantsUtil.STEP_SINGLE, step);
        videoPlayerFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // up/home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // If last step
        if (mVideoNumber == mStepArrayList.size() - 1) {
            Toast.makeText(this, "You're done!", Toast.LENGTH_SHORT).show();
        } else {
            if (v.getId() == mButtonPreviousStep.getId()) {
                mVideoNumber--;
                if (mVideoNumber < 0) {
                    Toast.makeText(this, "Look for the next step!", Toast.LENGTH_SHORT).show();
                } else
                    playVideoReplace(mStepArrayList.get(mVideoNumber));
            } else if (v.getId() == mButtonNextStep.getId()) {
                mVideoNumber++;
                playVideoReplace(mStepArrayList.get(mVideoNumber));
            }
        }
    }

    // I think this belongs in Recipe Detail Activity tablet logic
    @Override
    public void onStepClick(int position) {
        mVideoNumber = position;
        playVideoReplace(mStepArrayList.get(position));
    }

//    putting tablet check in RecipeDetailActivity and taking it out of here
    public void handleUiForDevice() {
        //if (!isTablet) {
            // Set button listeners
            mButtonNextStep.setOnClickListener(this);
            mButtonPreviousStep.setOnClickListener(this);
        //}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST_STATE, mStepArrayList);
        outState.putString(STEP_LIST_JSON_STATE, mJsonResult);
        outState.putInt(STEP_NUMBER_STATE, mVideoNumber);
        //Find current position in exoplayer - getter and setter methods
        outState.putLong(VIDEO_PLAY_POSITION, mCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mStepArrayList = savedInstanceState.getParcelableArrayList(STEP_LIST_STATE);
            mJsonResult = savedInstanceState.getString(STEP_LIST_JSON_STATE);
            mVideoNumber = savedInstanceState.getInt(STEP_NUMBER_STATE);
            mCurrentPosition = savedInstanceState.getLong(VIDEO_PLAY_POSITION);
        }
    }

    @Override
    public void onVideoFragmentSaveInstance(long playerPosition) {
        mCurrentPosition = playerPosition;
    }
}




