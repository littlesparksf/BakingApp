package com.example.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Adapters.StepAdapter;
import com.example.bakingapp.Model.Ingredient;
import com.example.bakingapp.Model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    // Saved instance state variables
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

    // ArrayList TextView variables
    ArrayList<Ingredient> ingredientArrayList;
    TextView recipeIngredientsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Set up Ingredients TextView
        recipeIngredientsView = findViewById(R.id.recipe_ingredients_tv);

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
          if (savedInstanceState == null) {
              loadSteps();
              populateUI();
          }

        Log.v(LOG_TAG, "loadStepscalled.");
    }

    private void populateUI() {

        TextView recipeNameView = findViewById(R.id.recipe_detail_name);
        String name = getIntent().getStringExtra("name");
        Log.v(LOG_TAG, "name: " + name);
        recipeNameView.setText(name);

        ImageView recipeImageView = findViewById(R.id.recipe_detail_image);
        String recipeImageUrl = "https://images.app.goo.gl/2rbE73RHMuKsiWh27";
        // getIntent().getStringExtra("image")
        Log.v(LOG_TAG, "imageurl: " + recipeImageUrl);

        if (recipeImageUrl.isEmpty()){
            recipeImageView.setImageResource(R.drawable.cat);
        } else {
            Picasso.with(this)
                    .load(recipeImageUrl)
                    .placeholder(R.drawable.cat)
                    .into(recipeImageView);
        }
        ingredientArrayList = getIntent().getParcelableArrayListExtra("ingredients");

        for (int i=0; i<ingredientArrayList.size(); i++) {
            recipeIngredientsView.append(ingredientArrayList.get(i).getIngredient());
            recipeIngredientsView.append("\n");
        }
    }

    private void loadSteps() {
        showStepsDataView();
        Log.v(LOG_TAG, "showStepsDataView called.");

        // This is not working
        stepArrayList = getIntent().getParcelableArrayListExtra("steps");
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(LOG_TAG, "onSaveInstanceState called.");

        // Step List saved state
        ArrayList stepListSavedState = stepArrayList;
        outState.putParcelableArrayList(STEP_LIST_STATE_KEY, stepListSavedState);
        outState.putParcelable(BUNDLE_STEP_RECYCLER_LAYOUT, mStepsRecyclerView.getLayoutManager().onSaveInstanceState());

        // Ingredient List saved state
        ArrayList ingredientListSavedState = ingredientArrayList;
        outState.putParcelableArrayList(INGREDIENT_STATE_KEY, ingredientListSavedState);
    }

//    @Override
//    protected void onRestoreInstanceState (Bundle savedInstanceState) {
//        ArrayList stepListSavedState = savedInstanceState.getParcelableArrayList(STEP_LIST_STATE_KEY);
//        mStepsAdapter.setSteps(stepListSavedState);
//        savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_STEP_RECYCLER_LAYOUT);
//        mStepsRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
//        showStepsDataView();
//
//        ArrayList ingredientListSavedState = savedInstanceState.getParcelableArrayList(INGREDIENT_STATE_KEY);
//        TextView recipeIngredientsView = findViewById(R.id.recipe_ingredients_tv);
//        ingredientArrayList = getIntent().getParcelableArrayListExtra("ingredients");
//
////        for (int i=0; i<ingredientListSavedState.size();i++) {
////            recipeIngredientsView.append(ingredientArrayList.get(i).getIngredient());
////            recipeIngredientsView.append("\n");
////        }
  //  }
}
