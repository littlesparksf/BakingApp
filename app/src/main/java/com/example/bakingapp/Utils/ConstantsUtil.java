package com.example.bakingapp.Utils;

import com.example.bakingapp.R;

public class ConstantsUtil {
    public static final String JSON_RESULT_EXTRA = "json_result_extra";
    public static final String WIDGET_EXTRA = "widget_extra";
    public static final String RECIPE_INTENT_EXTRA = "recipe_intent_extra";
    public static final String STEP_SINGLE = "step_single";
    public static final String STEP_INTENT_EXTRA = "step_intent_extra";
    public static final String SHARED_PREFERENCES = "MyPrefs";

    public static String [] units = {"CUP","TBLSP","TSP","G","K","OZ","UNIT"};
    public static String [] unitName = {"Cup","Tablespoon","Teaspoon","Gram","Kilogram","Ounce","Unit"};
    public static int [] unitIcons = {
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food
    };
    public static int [] recipeIcons = {
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food
    };
}
