package com.example.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bakingapp.Activities.RecipeDetailActivity;
import com.example.bakingapp.R;
import com.example.bakingapp.Utils.ConstantsUtil;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateBakingAppWidget(Context context, String jsonRecipeIngredients, int imgResId, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        // Create an Intent to launch MainActivity
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(ConstantsUtil.WIDGET_EXTRA, "FROM_WIDGET");

        // Create a pending intent to wrap intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if(jsonRecipeIngredients.equals("")){
            jsonRecipeIngredients = "No ingredients yet!";
        }

        views.setTextViewText(R.id.ingredients_title, widgetText);
        views.setTextViewText(R.id.widget_ingredients, jsonRecipeIngredients);
        views.setImageViewResource(R.id.ivWidgetRecipeIcon, imgResId);

        // OnClick intent for textview
        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startActionOpenRecipe(context);
    }

    public static void updateBakingAppWidgetRecipe (Context context, String jsonRecipe, int imgResId, AppWidgetManager appWidgetManager,
                                                    int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateBakingAppWidget(context, jsonRecipe, imgResId, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

