package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Adapters.StepAdapter;
import com.example.bakingapp.Pojos.Recipe;
import com.example.bakingapp.Pojos.Step;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity {

    ArrayList<Recipe> mRecipeArrayList;
//    Recipe mRecipe = null;
    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    StepService mStepService;
    ArrayList<Step> stepsList;

    // Steps njn recycler view variables
    private RecyclerView mStepsRecyclerView;
    private StepAdapter mStepsAdapter;
    private TextView mStepsEmptyView;
    private ProgressBar mStepsLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Receiving intent from MainActivity
        Intent recipeIntent = getIntent();
        mRecipeArrayList = recipeIntent.getParcelableArrayListExtra("RECIPE_KEY");

        // Set up Steps Recycler View

        //  Get a reference to the RecyclerView
        mStepsRecyclerView = findViewById(R.id.steps_recycler_view);

        Log.v(LOG_TAG, "Reviews recycler view found.");

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mStepsEmptyView = (TextView) findViewById(R.id.steps_empty_view);

        // Loading indicator will be shown as data loads
        mStepsLoadingIndicator = (ProgressBar) findViewById(R.id.steps_loading_indicator);

        // Set a LinearLayoutManager
        LinearLayoutManager reviewsLinearLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set the Layout Manager to the RecyclerView
        mStepsRecyclerView.setLayoutManager(reviewsLinearLayoutManager);

        // Setting to improve performance if changes in content do not change in child layout size
        mStepsRecyclerView.setHasFixedSize(true);

        // Call the constructor of CustomAdapter to send the reference and data to the Adapter
        mStepsAdapter = new StepAdapter(RecipeDetails.this, new ArrayList<Step>());

        // Set the adapter on the {@link RecyclerView}
        // so the list can be populated in the user interface
        mStepsRecyclerView.setAdapter(mStepsAdapter);

        Log.v(LOG_TAG, "Adapter set on recycler view.");

        /* Once all of our views are setup, we can load the reviews data. */
        loadSteps();

        Log.v(LOG_TAG, "loadStepscalled.");


    }


    private void populateUI() {

//        TextView recipeIngredientsView = findViewById(R.id.recipe_name_tv);
//        List ingredients = mRecipe.getIngredients();
//        String ingredientsString = ingredients.toString();
//        recipeIngredientsView.setText(ingredientsString);

    }

    private void loadSteps() {
        showStepsDataView();
        Log.v(LOG_TAG, "showStepsDataView called.");

        if (stepsList != null) {
            mStepsAdapter.setSteps(stepsList);
        }
    }

    private void showStepsDataView() {
        mStepsEmptyView.setVisibility(View.INVISIBLE);
        mStepsRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showStepsErrorMessage() {
        mStepsRecyclerView.setVisibility(View.INVISIBLE);
        mStepsEmptyView.setVisibility(View.VISIBLE);
    }
}
