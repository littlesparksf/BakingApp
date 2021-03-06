package com.example.bakingapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.bakingapp.Model.Recipe;
import com.example.bakingapp.Model.Step;
import com.example.bakingapp.R;
import com.example.bakingapp.Utils.ConstantsUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();
    //private FragmentActivity mFrgAct;
   // private Intent mIntent;

    // Saved instance state variables
    private static final String RECIPE_IMAGE_URL = "recipe_image_url";
    public static final String STEP_LIST_STATE_KEY = "step_list_key";
    public static final String BUNDLE_STEP_RECYCLER_LAYOUT = "step_recycler_layout";
    public static final String INGREDIENT_STATE_KEY = "ingredient_list_key";

    // Steps recycler view variables
    ArrayList<Step> mStepArrayList;
    private RecyclerView mStepsRecyclerView;
    private StepAdapter mStepsAdapter;
    private TextView mStepsEmptyView;
    private Recipe mRecipe;

    private Context mContext;
    Parcelable savedRecyclerLayoutState;
    LinearLayoutManager mStepsLinearLayoutManager;

    // ArrayList TextView variables
    ArrayList<Ingredient> ingredientArrayList;
    TextView recipeIngredientsView;

    // Other recipe variables
    String recipeImageUrl;
    ImageView recipeImageView;
    String recipeName;
    TextView recipeTitleView;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipe = getArguments().getParcelable(ConstantsUtil.RECIPE_SINGLE);
        ingredientArrayList = mRecipe.getIngredients();
        mStepArrayList = mRecipe.getSteps();
        recipeImageUrl = mRecipe.getImage();
        recipeName = mRecipe.getName();

        Log.v(LOG_TAG, "onCreate called in RecipeFragment." + ingredientArrayList);
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
        mStepsAdapter = new StepAdapter(getActivity(), new ArrayList<Step>());
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

        for (int i = 0; i < ingredientArrayList.size(); i++) {
            recipeIngredientsView.append(ingredientArrayList.get(i).getIngredient());
            recipeIngredientsView.append("\n");
        }

        // Set up empty view
        mStepsEmptyView = view.findViewById(R.id.steps_empty_view);

        // Set up title view
        recipeTitleView = view.findViewById(R.id.recipe_detail_name);
        Log.v(LOG_TAG, "name: " + recipeName);
        recipeTitleView.setText(recipeName);

        // Set up Recipe Image View
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
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_STEP_RECYCLER_LAYOUT);
            if (savedRecyclerLayoutState != null) {
                mStepsLinearLayoutManager.onRestoreInstanceState(savedRecyclerLayoutState);
            }
        }

        // Load steps data
        showStepsDataView();
        Log.v(LOG_TAG, "showStepsDataView called.");


        // Give steps list to adapter
        mStepsAdapter.setSteps(mStepArrayList);
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
        ArrayList stepListSavedState = mStepArrayList;
        outState.putParcelableArrayList(STEP_LIST_STATE_KEY, stepListSavedState);
        outState.putParcelable(BUNDLE_STEP_RECYCLER_LAYOUT, mStepsRecyclerView.getLayoutManager().onSaveInstanceState());

        // Ingredient List saved state
        ArrayList ingredientListSavedState = ingredientArrayList;
        outState.putParcelableArrayList(INGREDIENT_STATE_KEY, ingredientListSavedState);
    }
}


/*
package com.example.bakingapp.Activities;

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

        import com.bumptech.glide.Glide;
        import com.example.bakingapp.Adapters.RecipeAdapter;
        import com.example.bakingapp.Adapters.StepAdapter;
        import com.example.bakingapp.Model.Ingredient;
        import com.example.bakingapp.Model.Step;
        import com.example.bakingapp.R;

        import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity {

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

        // Get a reference to recipe title view
        recipeImageView = findViewById(R.id.recipe_detail_image_glide);

        // Get a reference to image view
        recipeImageUrl = getIntent().getStringExtra("image");

        // Set up Ingredients TextView
        recipeIngredientsView = findViewById(R.id.recipe_ingredients_tv);

        // Set up Steps Recycler View

        //  Get a reference to the RecyclerView
        mStepsRecyclerView = findViewById(R.id.steps_recycler_view);

        Log.v(LOG_TAG, "Reviews recycler view found.");

        mStepsEmptyView = findViewById(R.id.steps_empty_view);
        //mStepsLoadingIndicator = findViewById(R.id.steps_loading_indicator);

        LinearLayoutManager mStepsLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mStepsRecyclerView.setLayoutManager(mStepsLinearLayoutManager);
        mStepsRecyclerView.setHasFixedSize(true);
        mStepsAdapter = new StepAdapter(RecipeDetails.this, new ArrayList<Step>());
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        Log.v(LOG_TAG, "Adapter set on recycler view.");

        //Once all of our views are setup, we can load the steps data.
        if (savedInstanceState == null) {
            ingredientArrayList = getIntent().getParcelableArrayListExtra("ingredients");
            recipeName = getIntent().getStringExtra("name");
            loadSteps();
            populateUI();
        } else if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_STEP_RECYCLER_LAYOUT);
            loadSteps();
            //populateUI();
            if (savedRecyclerLayoutState != null) {
                mStepsLinearLayoutManager.onRestoreInstanceState(savedRecyclerLayoutState);
            }
        }

        Log.v(LOG_TAG, "loadStepscalled.");
    }

    private void populateUI() {

        TextView recipeNameView = findViewById(R.id.recipe_detail_name);
        Log.v(LOG_TAG, "name: " + recipeName);
        recipeNameView.setText(recipeName);

        ImageView glideImageView = findViewById(R.id.recipe_detail_image_glide);

        if (recipeImageUrl == null){
            Glide.with(this)
                    .load(R.drawable.food)
                    .into(glideImageView);
        } else if (recipeImageUrl.length()==0) {
            Glide.with(this)
                    .load(R.drawable.food)
                    .into(glideImageView);
        } else {
            Glide.with(this)
                    .load(recipeImageUrl)
                    .into(glideImageView);
        }

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

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        recipeImageUrl = savedInstanceState.getString(RECIPE_IMAGE_URL);
        recipeImageView.setImageResource(R.drawable.food);

        ArrayList stepListSavedState = savedInstanceState.getParcelableArrayList(STEP_LIST_STATE_KEY);
        mStepsAdapter.setSteps(stepListSavedState);
        savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_STEP_RECYCLER_LAYOUT);
        mStepsRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        loadSteps();

        ArrayList ingredientListSavedState = savedInstanceState.getParcelableArrayList(INGREDIENT_STATE_KEY);
        TextView recipeIngredientsView = findViewById(R.id.recipe_ingredients_tv);
        //  ingredientArrayList = getIntent().getParcelableArrayListExtra("ingredients");

        for (int i=0; i<ingredientListSavedState.size();i++) {
            recipeIngredientsView.append(ingredientArrayList.get(i).getIngredient());
            recipeIngredientsView.append("\n");
        }
    }
}
*/
