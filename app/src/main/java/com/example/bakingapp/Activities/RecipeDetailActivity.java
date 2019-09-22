package com.example.bakingapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Adapters.StepAdapter;
import com.example.bakingapp.Model.Ingredient;
import com.example.bakingapp.Model.Recipe;
import com.example.bakingapp.Model.Step;
import com.example.bakingapp.R;
import com.example.bakingapp.Utils.ConstantsUtil;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    // Saved instance state variables
    private static final String RECIPE_IMAGE_URL = "recipe_image_url";
    public static final String STEP_LIST_STATE_KEY = "step_list_key";
    public static final String BUNDLE_STEP_RECYCLER_LAYOUT = "step_recycler_layout";
    public static final String INGREDIENT_STATE_KEY = "ingredient_list_key";

    // Steps recycler view variables
    ArrayList<Step> stepArrayList;
    private RecyclerView mStepsRecyclerView;
    private StepAdapter mStepsAdapter;
    private TextView mStepsEmptyView;

    private Context mContext;
    Parcelable savedRecyclerLayoutState;
    LinearLayoutManager mStepsLinearLayoutManager;

    // ArrayList TextView variables
    ArrayList<Ingredient> ingredientArrayList;
    TextView recipeIngredientsView;

    // Other recipe variables
    String recipeImageUrl;
    ImageView recipeImageView;
    TextView recipeTitleView;
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(LOG_TAG, "onSaveInstanceState called.");

        //Current Recipe saved state
        String savedImageUrl = recipeImageUrl;
        outState.putString(RECIPE_IMAGE_URL, savedImageUrl);

        // Step List saved state
        ArrayList stepListSavedState = stepArrayList;
        outState.putParcelableArrayList(STEP_LIST_STATE_KEY, stepListSavedState);
        outState.putParcelable(BUNDLE_STEP_RECYCLER_LAYOUT, mStepsRecyclerView.getLayoutManager().onSaveInstanceState());

        // Ingredient List saved state
        ArrayList ingredientListSavedState = ingredientArrayList;
        outState.putParcelableArrayList(INGREDIENT_STATE_KEY, ingredientListSavedState);
    }
}
