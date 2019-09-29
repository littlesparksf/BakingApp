package com.example.bakingapp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.bakingapp.Model.Recipe;
import com.example.bakingapp.Model.Step;
import com.example.bakingapp.R;
import com.example.bakingapp.Utils.ConstantsUtil;

public class RecipeDetailActivity extends AppCompatActivity {

    // For two-pane tablet layout
    private boolean isTablet;
    private Step mStep;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        displayRecipeFragment((Recipe) getIntent().getExtras().getParcelable("recipe"));

        // Check if device is a tablet
        if ((findViewById(R.id.fl_player_container) != null)) {
        // this is video layout, only there if it is a tablet
            isTablet = true;
        } else {
            isTablet = false;
        }

//        mStep = getIntent().getParcelableExtra("step");
//        mRecipe = getIntent().getParcelableExtra("recipe");
//
//        if (isTablet) {
//            playVideo(mStep);
//            displayRecipeFragment(mRecipe);
//        } else {
//            playVideo(mStep);
//        }
    }


//    public void playVideo(Step step) {
//        VideoFragment videoPlayerFragment = new VideoFragment();
//        Bundle stepsBundle = new Bundle();
//        stepsBundle.putParcelable(ConstantsUtil.STEP_SINGLE, step);
//        videoPlayerFragment.setArguments(stepsBundle);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .add(R.id.fl_player_container, videoPlayerFragment)
//                .addToBackStack(null)
//                .commit();
//    }


    public void displayRecipeFragment(Recipe recipe) {
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable(ConstantsUtil.RECIPE_SINGLE, recipe);
        recipeFragment.setArguments(recipeBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_fragment_container, recipeFragment)
                .addToBackStack(null)
                .commit();
    }
}

