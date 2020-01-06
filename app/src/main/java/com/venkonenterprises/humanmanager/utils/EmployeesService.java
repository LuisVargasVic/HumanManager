package com.venkonenterprises.humanmanager.utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;


public class EmployeesService extends IntentService {

    private static final String ACTION_UPDATE_WIDGETS = "com.venkonenterpises.humanmanager.action.widget";

    public EmployeesService() {
        super("EmployeesService");
    }

    /**
     * Starts this service to perform UpdateIngredientsWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, EmployeesService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }

    /**
     * @param intent intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                handleActionUpdateEmployeeWidgets();
            }
        }
    }

    /**
     * Handle action UpdateIngredientsWidgets in the provided background thread
     */
    private void handleActionUpdateEmployeeWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetProvider.class));
        WidgetProvider.updateWidget(EmployeesService.this, appWidgetManager, appWidgetIds);
    }
}
