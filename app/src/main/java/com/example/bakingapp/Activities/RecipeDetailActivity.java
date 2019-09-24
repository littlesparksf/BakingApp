package com.example.bakingapp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.bakingapp.Model.Recipe;
import com.example.bakingapp.R;
import com.example.bakingapp.Utils.ConstantsUtil;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        displayRecipeFragment((Recipe) getIntent().getExtras().getParcelable("recipe"));
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
}

