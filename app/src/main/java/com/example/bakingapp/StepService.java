package com.example.bakingapp;

import com.example.bakingapp.Model.Step;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StepService {
    // Get list of recipes
    @GET("baking.json")
    Call<List<Step>> getSteps();
}
