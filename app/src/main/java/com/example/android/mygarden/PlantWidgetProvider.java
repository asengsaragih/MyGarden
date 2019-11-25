package com.example.android.mygarden;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.mygarden.ui.MainActivity;

import static com.example.android.mygarden.PlantWateringService.ACTION_WATER_PLANTS;

public class PlantWidgetProvider extends AppWidgetProvider {

    static void updateWidget(Context context, AppWidgetManager manager, int imgRes, int id) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.plant_widget);
        remoteViews.setImageViewResource(R.id.widget_plant_image, imgRes);
        remoteViews.setOnClickPendingIntent(R.id.widget_plant_image, pendingIntent);

        Intent i = new Intent(context, PlantWateringService.class);
        i.setAction(ACTION_WATER_PLANTS);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_water_button, pi);
        manager.updateAppWidget(id, remoteViews);
    }

    public static void updatePlantWidget(Context context, AppWidgetManager manager, int imgRes, int[] ids) {
        for (int id : ids) {
            updateWidget(context, manager, imgRes, id);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        PlantWateringService.startActionUpdatePlants(context);
    }
}
