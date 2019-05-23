package com.example.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Adapters.StepAdapter;
import com.example.bakingapp.Model.Ingredient;
import com.example.bakingapp.Model.Recipe;
import com.example.bakingapp.Model.Step;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity {

    public Recipe recipe;
    public ArrayList<Recipe> mRecipeArrayList;
    public ArrayList<Step> stepsList;

    private String RECIPE_KEY;
    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    // Steps recycler view variables
    private RecyclerView mStepsRecyclerView;
    private StepAdapter mStepsAdapter;
    private TextView mStepsEmptyView;
    //private ProgressBar mStepsLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Get recipe data from MainActivity
        //Intent recipeIntent = getIntent();
        //mRecipeArrayList = getIntent().getExtras().getParcelableArrayList("RECIPE_KEY");
        // Set up Steps Recycler View

        //  Get a reference to the RecyclerView
        mStepsRecyclerView = findViewById(R.id.steps_recycler_view);

        Log.v(LOG_TAG, "Reviews recycler view found.");

        mStepsEmptyView = findViewById(R.id.steps_empty_view);
        //mStepsLoadingIndicator = findViewById(R.id.steps_loading_indicator);

        LinearLayoutManager reviewsLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mStepsRecyclerView.setLayoutManager(reviewsLinearLayoutManager);
        mStepsRecyclerView.setHasFixedSize(true);
        mStepsAdapter = new StepAdapter(RecipeDetails.this, new ArrayList<Step>());
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        Log.v(LOG_TAG, "Adapter set on recycler view.");

        /* Once all of our views are setup, we can load the steps data. */
        loadSteps();
        populateUI();

        Log.v(LOG_TAG, "loadStepscalled.");
    }

    private void populateUI() {

        TextView recipeNameView = findViewById(R.id.recipe_detail_name);
        String name = getIntent().getStringExtra("name");
        recipeNameView.setText(name);

        TextView recipeIngredientsView = findViewById(R.id.recipe_ingredients_tv);
        ArrayList<Ingredient> ingredientArrayList = getIntent().getParcelableArrayListExtra("ingredients");

        for (int i=0; i<ingredientArrayList.size();i++) {
            recipeIngredientsView.append(ingredientArrayList.get(i).getIngredient());
            recipeIngredientsView.append("\n");
        }
    }

    private void loadSteps() {
        showStepsDataView();
        Log.v(LOG_TAG, "showStepsDataView called.");

        // This is not working
        ArrayList<Step> stepArrayList = getIntent().getParcelableArrayListExtra("steps");
        Log.v(LOG_TAG, "getParcelableArrayListExtra called.");
        mStepsAdapter.setSteps(stepArrayList);
        Log.v(LOG_TAG, "setSteps called.");
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
