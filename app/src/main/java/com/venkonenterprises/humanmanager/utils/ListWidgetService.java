package com.venkonenterprises.humanmanager.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.Observer;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.data.EmployeesRepository;
import com.venkonenterprises.humanmanager.database.EmployeesDatabase;
import com.venkonenterprises.humanmanager.domain.Employee;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    static class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
        private final Context mContext;
        private List<Employee> mEmployees;

        ListRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {
            final EmployeesRepository repository;

            EmployeesDatabase database = EmployeesDatabase.getInstance(mContext);
            Log.d(TAG, "Actively retrieving the widget with the ingredients from the DataBase");
            repository = new EmployeesRepository(database);
            repository.getEmployees().observeForever(new Observer<List<Employee>>() {
                    @Override
                    public void onChanged(List<Employee> employees) {
                        mEmployees = employees;
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, WidgetProvider.class));
                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
                        WidgetProvider.updateWidget(mContext, appWidgetManager, appWidgetIds);
                    }
            });
        }


        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mEmployees == null) return 0;
            return mEmployees.size();
        }

        /**
         * This method acts like the onBindViewHolder method in an Adapter
         *
         * @param position The current position of the item in the ListView to be displayed
         * @return The RemoteViews object to display for the provided position
         */
        @Override
        public RemoteViews getViewAt(int position) {
            if (mEmployees == null || mEmployees.isEmpty()) return null;
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_employee_widget);
            views.setTextViewText(R.id.tv_name, mEmployees.get(position).getName());
            views.setTextViewText(R.id.tv_last_name, String.valueOf(mEmployees.get(position).getLastName()));
            views.setTextViewText(R.id.tv_cellphone, mEmployees.get(position).getCellphone());
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1; // Treat all items in the ListView the same
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
