package com.example.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.Pojos.Ingredient;
import com.example.bakingapp.Pojos.Recipe;
import com.example.bakingapp.Pojos.Step;
import com.example.bakingapp.R;
import com.example.bakingapp.RecipeDetails;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {

    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList)
    {
        this.mContext = context;
        this.mRecipeList = recipeList;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Inflate the recipe_list_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_list_row, viewGroup, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder viewHolder, int position) {
        // Determine the values of the wanted data - not sure what I need in this list view
        Recipe recipe = mRecipeList.get(position);
        int recipeId = recipe.getId();
        String recipeName = recipe.getName();
        List<Ingredient> recipeIngredients = recipe.getIngredients();
        List<Step> recipeSteps = recipe.getSteps();
        int recipeServings = recipe.getServings();
        String recipeImage = recipe.getImage();

        // Set values
        viewHolder.recipeName.setText(recipeName);
        // this doesn't work - viewHolder.recipeImage.setImageDrawable(recipeImage);
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();
    }

    public List<Recipe> getRecipes() {
        return mRecipeList;
    }

    public void setRecipes(ArrayList<Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;
        TextView recipeText;
        ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeText = itemView.findViewById(R.id.recipe_text);
            recipeImage = itemView.findViewById(R.id.recipe_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int recipePosition = getAdapterPosition();
            Recipe recipe = mRecipeList.get(recipePosition);
            Intent intent = new Intent(mContext, RecipeDetails.class);
            ArrayList<Recipe> recipeArrayList = new ArrayList<>();
            recipeArrayList.add(recipe);
            intent.putParcelableArrayListExtra("RECIPE _KEY", recipeArrayList);
            mContext.startActivity(intent);
        }

//        @Override
//        public void onClick(View v) {
//            int recipePosition = getAdapterPosition();
//            Recipe recipe = mRecipeList.get(recipePosition);
//
//            Intent intent = new Intent(recipeName.getContext(), RecipeDetails.class);
//            intent.putExtra ("recipe", new Recipe(recipe.getId(), recipe.getName(), recipe.getIngredients(), recipe.getSteps(), recipe.getServings(), recipe.getImage()));
//            recipeName.getContext().startActivity(intent);
//        }
    }
}
