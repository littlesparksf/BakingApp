package com.example.bakingapp;

        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class StepClient {

    private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    // Need to create step url ... baking/recipe/steps
    public final StepService mStepService;

    public StepClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStepService = retrofit.create(StepService.class);
    }
}