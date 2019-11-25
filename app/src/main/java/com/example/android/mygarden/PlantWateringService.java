package com.example.android.mygarden;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import static com.example.android.mygarden.provider.PlantContract.BASE_CONTENT_URI;
import static com.example.android.mygarden.provider.PlantContract.PATH_PLANTS;
import static com.example.android.mygarden.provider.PlantContract.PlantEntry.COLUMN_LAST_WATERED_TIME;
import static com.example.android.mygarden.utils.PlantUtils.MAX_AGE_WITHOUT_WATER;

public class PlantWateringService extends IntentService {

    public static final String ACTION_WATER_PLANTS = "com.example.android.mygarden.action.water.plants";

    public PlantWateringService() {
        super("PlantWateringService");
    }

    public static void startActionWaterPlants(Context context) {
        Intent intent = new Intent(context, PlantWateringService.class);
        intent.setAction(ACTION_WATER_PLANTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_WATER_PLANTS.equals(action)) {
                handleActionWaterPlants();
            }
        }
    }

    private void handleActionWaterPlants() {
        Uri PLANTS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLANTS).build();
        ContentValues values = new ContentValues();
        long timeNow = System.currentTimeMillis();

        values.put(COLUMN_LAST_WATERED_TIME, timeNow);

        getContentResolver().update(
                PLANTS_URI,
                values,
                COLUMN_LAST_WATERED_TIME + ">?",
                new String[] {String.valueOf(timeNow - MAX_AGE_WITHOUT_WATER)}
        );
    }
}
