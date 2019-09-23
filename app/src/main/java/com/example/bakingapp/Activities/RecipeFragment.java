package com.example.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.Adapters.StepAdapter;
import com.example.bakingapp.Model.Ingredient;
import com.example.bakingapp.Model.Step;
import com.example.bakingapp.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();
    private FragmentActivity mFrgAct;
    private Intent mIntent;

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

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.recipe_fragment, container, false);
        //  Get a reference to the RecyclerView
        mStepsRecyclerView = root.findViewById(R.id.steps_recycler_view);
        Log.v(LOG_TAG, "Reviews recycler view found.");
        // Set layout manager
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));

        // Create an adapter
        mStepsAdapter = new StepAdapter(this, new ArrayList<Step>());
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        Log.v(LOG_TAG, "Adapter set on recycler view.");

        // Set item animator to DefaultAnimator
        mStepsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        ButterKnife.bind(this, root);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Set up Ingredients TextView
        recipeIngredientsView = view.findViewById(R.id.recipe_ingredients_tv);

        mStepsEmptyView = view.findViewById(R.id.steps_empty_view);

        recipeTitleView = view.findViewById(R.id.recipe_detail_name);
        Log.v(LOG_TAG, "name: " + recipeName);
        recipeTitleView.setText(recipeName);

        // Get a reference to recipe image view
        recipeImageView = view.findViewById(R.id.recipe_detail_image_glide);

        if (recipeImageUrl == null) {
            Glide.with(this)
                    .load(R.drawable.food)
                    .into(recipeImageView);
        } else if (recipeImageUrl.length() == 0) {
            Glide.with(this)
                    .load(R.drawable.food)
                    .into(recipeImageView);
        } else {
            Glide.with(this)
                    .load(recipeImageUrl)
                    .into(recipeImageView);
        }

        for (int i = 0; i < ingredientArrayList.size(); i++) {
            recipeIngredientsView.append(ingredientArrayList.get(i).getIngredient());
            recipeIngredientsView.append("\n");
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFrgAct = getActivity();
        mIntent = mFrgAct.getIntent();
        // Get a reference to image url
        recipeImageUrl = mFrgAct.getIntent().getStringExtra("image");

        /* Once all of our views are setup, we can load the steps data. */
        ingredientArrayList = mFrgAct.getIntent().getParcelableArrayListExtra("ingredients");
        recipeName = mFrgAct.getIntent().getStringExtra("name");
        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_STEP_RECYCLER_LAYOUT);
            if (savedRecyclerLayoutState != null) {
                mStepsLinearLayoutManager.onRestoreInstanceState(savedRecyclerLayoutState);
            }
        }

        showStepsDataView();
        Log.v(LOG_TAG, "showStepsDataView called.");

        // Get step list from intent
        stepArrayList = mFrgAct.getIntent().getParcelableArrayListExtra("steps");
        Log.v(LOG_TAG, "getParcelableArrayListExtra called.");
        mStepsAdapter.setSteps(stepArrayList);

        Log.v(LOG_TAG, "showStepsDataView called.");

        // This is not working
        stepArrayList = mFrgAct.getIntent().getParcelableArrayListExtra("steps");
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
    public void onSaveInstanceState(Bundle outState) {
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


