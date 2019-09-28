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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.bakingapp.Adapters.StepNumberAdapter;
import com.example.bakingapp.Model.Step;
import com.example.bakingapp.R;
import com.example.bakingapp.Utils.ConstantsUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity implements View.OnClickListener, StepNumberAdapter.OnStepClick {

    // Global variables
    private Step mStep;
    private int mStepPosition;
    private ArrayList<Step> mStepArrayList;
    boolean isFromWidget;

    // Keys for saving state
    public static final String STEP_LIST_STATE = "step_list_state";
    public static final String STEP_NUMBER_STATE = "step_number";
   // public static final String STEP_LIST_JSON_STATE = "step_list_json_state";
    private static final String LOG_TAG = StepDetailActivity.class.getSimpleName();

    // For two-pane tablet layout
    private boolean isTablet;
    int mVideoNumber = 0;

    @BindView(R.id.fl_player_container)
    FrameLayout mFragmentContainer;

    @BindView(R.id.recipe_fragment_container)
    FrameLayout mRecipeFragmentContainer;

    @BindView(R.id.btn_next_step)
    Button mButtonNextStep;

    @BindView(R.id.btn_previous_step)
    Button mButtonPreviousStep;

    @Nullable
    // Check this bc there is no steps recycler view on this page
    @BindView(R.id.steps_recycler_view)
    RecyclerView mRecyclerViewSteps;

//    ArrayList<Step> mStepArrayList = new ArrayList<>();
//    String mJsonResult;
//    boolean isFromWidget;
//    StepNumberAdapter mStepNumberAdapter;
//    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        // Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Check if device is a tablet
        if ((findViewById(R.id.activity_step_tablet) != null)) {
            isTablet = true;
        } else {
            isTablet = false;
        }

        Intent intent = getIntent();
        mStepArrayList = intent.getParcelableArrayListExtra("steps");
        mStep = intent.getParcelableExtra("step");
        // This is not working - mStepPosition = intent.getIntExtra("step_position");
        playVideo(mStep);


//        if (intent != null) {
//            if (intent.hasExtra(ConstantsUtil.STEP_INTENT_EXTRA)) {
//                mStepArrayList = getIntent().getParcelableArrayListExtra(ConstantsUtil.STEP_INTENT_EXTRA);
//            }
//            if (intent.hasExtra(ConstantsUtil.JSON_RESULT_EXTRA)) {
//                mJsonResult = getIntent().getStringExtra(ConstantsUtil.JSON_RESULT_EXTRA);
//            }
//            if (intent.getStringExtra(ConstantsUtil.WIDGET_EXTRA) != null) {
//                isFromWidget = true;
//            } else {
//                isFromWidget = false;
//            }
//        } else
//        // If no saved state, initiate fragment
//        //if (savedInstanceState != null) {
//        Log.v(LOG_TAG, "Getting video number");
//        playVideo(mStepArrayList.get(mVideoNumber));
//        //}

        ButterKnife.bind(this);

        handleUiForDevice();
    }

    public void playVideo(Step step) {
        VideoFragment videoPlayerFragment = new VideoFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantsUtil.STEP_SINGLE, step);
        videoPlayerFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fl_player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

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

    public void loadRecipeDetailFragment(Step step) {
        RecipeFragment recipeDetailFragment = new RecipeFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantsUtil.STEP_SINGLE, step);
        recipeDetailFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_fragment_container, recipeDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST_STATE, mStepArrayList);
        // outState.putString(STEP_LIST_JSON_STATE, mJsonResult);
        outState.putInt(STEP_NUMBER_STATE, mVideoNumber);
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

    @Override
    public void onStepClick(int position) {
        mVideoNumber = position;
        playVideoReplace(mStepArrayList.get(position));
    }

    public void handleUiForDevice() {
        if (!isTablet) {
            // Set button listeners
            mButtonNextStep.setOnClickListener(this);
            mButtonPreviousStep.setOnClickListener(this);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mStepArrayList = savedInstanceState.getParcelableArrayList(STEP_LIST_STATE);
            // mJsonResult = savedInstanceState.getString(STEP_LIST_JSON_STATE);
            mVideoNumber = savedInstanceState.getInt(STEP_NUMBER_STATE);
        }
    }
}

//    mStepNumber = getIntent().getIntExtra("step_position", 0);
//    mStepId = getIntent().getIntExtra("step_id", 0);
//    mVideoShortDescription = getIntent().getStringExtra("short_desc");
//    mVideoUri = getIntent().getStringExtra("step_video_url");
//    mVideoDescription = getIntent().getStringExtra("long_desc");
//    mVideoThumbnail = getIntent().getStringExtra("step_thumbnail_url");
//
//    mStep = new Step(mStepId, mVideoShortDescription, mVideoDescription, mVideoUri, mVideoShortDescription);





