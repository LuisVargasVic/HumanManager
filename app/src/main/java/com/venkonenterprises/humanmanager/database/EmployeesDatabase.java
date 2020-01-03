package com.venkonenterprises.humanmanager.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        DatabaseEmployee.class
}, version = 1, exportSchema = false)
public abstract class EmployeesDatabase extends RoomDatabase {

    private static final String LOG_TAG = EmployeesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movies";
    private static EmployeesDatabase sInstance;

    public static EmployeesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        EmployeesDatabase.class, EmployeesDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract EmployeesDao employeesDao();

}
