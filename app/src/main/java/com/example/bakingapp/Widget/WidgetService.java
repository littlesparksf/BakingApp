package com.example.bakingapp.Widget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.bakingapp.Model.Ingredient;
import com.example.bakingapp.Model.Recipe;
import com.example.bakingapp.Utils.ConstantsUtil;
import com.google.gson.Gson;

import java.util.List;

public class WidgetService extends IntentService {

    public static final String ACTION_OPEN_RECIPE = "com.example.bakingapp.Widget.baking_app_widget_service";

    public WidgetService(String name) {
        super(name);
    }

    public WidgetService(){super("WidgetService");}

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "baking_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "BakingApp service", NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_OPEN_RECIPE.equals(action)) {
                handleActionOpenRecipe();
            }
        }
    }

    private void handleActionOpenRecipe() {

        //Get data from shared pref
        SharedPreferences sharedpreferences = getSharedPreferences(ConstantsUtil.SHARED_PREFERENCES, MODE_PRIVATE);
        String jsonRecipe = sharedpreferences.getString(ConstantsUtil.JSON_RESULT_EXTRA, "flour");

        //
        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(jsonRecipe, Recipe.class);
        int id = recipe.getId();
        int imgResId = ConstantsUtil.recipeIcons[id - 1];
        List<Ingredient> ingredientList = recipe.getIngredients();
        for (Ingredient ingredient : ingredientList) {
            String quantity = String.valueOf(ingredient.getQuantity());
            String measure = ingredient.getMeasure();
            String ingredientName = ingredient.getIngredient();
            String line = quantity + " " + measure + " " + ingredientName;
            stringBuilder.append(line + "\n");
        }
        String ingredientsString = stringBuilder.toString();

        //Here we create AppWidgetManager to update AppWidget state
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds;
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetService.class));
        //Pass recipe info into the widget provider
        BakingAppWidget.updateBakingAppWidgetRecipe(this, ingredientsString, imgResId, appWidgetManager, appWidgetIds);
    }

    // Trigger the service to perform the action
    public static void startActionOpenRecipe(Context context) {
        Intent intent = new Intent(context, BakingAppWidget.class);
        intent.setAction(ACTION_OPEN_RECIPE);
        context.startService(intent);
    }

    // For Android O and above
    public static void startActionOpenRecipeO(Context context) {
        Intent intent = new Intent(context, BakingAppWidget.class);
        intent.setAction(ACTION_OPEN_RECIPE);
        ContextCompat.startForegroundService(context, intent);
    }
}