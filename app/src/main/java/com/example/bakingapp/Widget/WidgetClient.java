package com.example.bakingapp.Widget;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WidgetClient {

    private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    // Need to create step url ... baking/recipeArrayList/steps
    public final WidgetService mWidgetService;

    public WidgetClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mWidgetService = retrofit.create(WidgetService.class);
    }
}