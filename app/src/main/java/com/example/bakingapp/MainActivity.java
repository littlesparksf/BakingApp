package com.example.bakingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Pojos.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public static final String RECIPE_LIST_STATE_KEY = "movies";
    public static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";

    RecipeService mRecipeService;

    // Reviews recycler view variables
    private RecyclerView mRecipesRecyclerView;
    private RecipeAdapter mRecipesAdapter;
    private TextView mRecipesEmptyView;
    private ProgressBar mRecipesLoadingIndicator;
    Parcelable savedRecyclerLayoutState;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeService = new RecipeClient().mRecipeService;
        new FetchRecipesAsyncTask().execute();

        // Set up Reviews Recycler View

        //  Get a reference to the RecyclerView
        mRecipesRecyclerView = findViewById(R.id.recipes_recycler_view);

        Log.v(LOG_TAG, "Reviews recycler view found.");

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mRecipesEmptyView = (TextView) findViewById(R.id.recipes_empty_view);

        // Loading indicator will be shown as data loads
        mRecipesLoadingIndicator = (ProgressBar) findViewById(R.id.recipes_loading_indicator);

        // Set a LinearLayoutManager
        LinearLayoutManager reviewsLinearLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Set the Layout Manager to the RecyclerView
        mRecipesRecyclerView.setLayoutManager(reviewsLinearLayoutManager);

        // Setting to improve performance if changes in content do not change in child layout size
        mRecipesRecyclerView.setHasFixedSize(true);

        // Call the constructor of CustomAdapter to send the reference and data to the Adapter
        mRecipesAdapter = new RecipeAdapter(MainActivity.this, new ArrayList<Recipe>());

        // Set the adapter on the {@link RecyclerView}
        // so the list can be populated in the user interface
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);

        Log.v(LOG_TAG, "Adapter set on recycler view.");

        /* Once all of our views are setup, we can load the reviews data. */
        loadRecipes();

        Log.v(LOG_TAG, "loadRecipescalled.");
    }

    /* Fetching review data */

    private void loadRecipes() {
        showRecipesDataView();
        Log.v(LOG_TAG, "showRecipesDataView called.");

        new FetchRecipesAsyncTask().execute();

        Log.v(LOG_TAG, "FetchRecipesAsyncTask called.");
    }

    private void showRecipesDataView() {
        mRecipesEmptyView.setVisibility(View.INVISIBLE);
        /* Then, make sure the movie data is visible */
        mRecipesRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the movie
     * View.
     */
    private void showRecipesErrorMessage() {
        /* First, hide the currently visible data */
        mRecipesRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mRecipesEmptyView.setVisibility(View.VISIBLE);
    }

    private class FetchRecipesAsyncTask extends AsyncTask<Void,Void,ArrayList<Recipe>> {
        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            fetchRecipes();
            return null;
        }
    }

    // Fetch recipes
    private void fetchRecipes(){
        Call<ArrayList<Recipe>> call = mRecipeService.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call,
                                   Response<ArrayList<Recipe>> response) {

                // Test if response is successful
                ArrayList<Recipe> recipe = response.body();
                Log.d("LOG_TAG", recipe.get(0).getName());

                mRecipesLoadingIndicator.setVisibility(View.INVISIBLE);
                if (recipe != null) {
                    showRecipesDataView();
                    mRecipesAdapter.setRecipes(recipe);
                } else {
                    showRecipesErrorMessage();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
            Log.d("FAILURE", t.toString());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList recipeListSavedState = (ArrayList) mRecipesAdapter.getRecipes();
        outState.putParcelableArrayList(RECIPE_LIST_STATE_KEY, recipeListSavedState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecipesRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        ArrayList recipeListSavedState = savedInstanceState.getParcelableArrayList(RECIPE_LIST_STATE_KEY);
        mRecipesAdapter.setRecipes(recipeListSavedState);
        savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        mRecipesRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        showRecipesDataView();
    }
}

