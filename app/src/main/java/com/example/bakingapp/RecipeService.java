package com.example.bakingapp;

import com.example.bakingapp.Model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
        // Get list of recipes
        @GET("baking.json")
        Call<ArrayList<Recipe>> getRecipes();
}
