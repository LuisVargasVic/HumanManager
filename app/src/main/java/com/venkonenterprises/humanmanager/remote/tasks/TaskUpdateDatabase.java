package com.venkonenterprises.humanmanager.remote.tasks;

import android.os.AsyncTask;

import com.google.firebase.firestore.DocumentChange;
import com.venkonenterprises.humanmanager.database.DatabaseEmployee;
import com.venkonenterprises.humanmanager.database.EmployeesDatabase;
import com.venkonenterprises.humanmanager.remote.listeners.RemoteListener;

public class TaskUpdateDatabase extends AsyncTask<Void, Void, Boolean> {

    private EmployeesDatabase mEmployeesDatabase;
    private RemoteListener mRemoteListener;
    private DatabaseEmployee mEmployee;
    private DocumentChange.Type mType;
    private boolean mLast;

    TaskUpdateDatabase(EmployeesDatabase employeesDatabase, RemoteListener remoteListener, DatabaseEmployee employee, DocumentChange.Type type, boolean last) {
        mEmployeesDatabase = employeesDatabase;
        mRemoteListener = remoteListener;
        mEmployee = employee;
        mType = type;
        mLast = last;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mRemoteListener.preExecute();
    }

    @Override
    public Boolean doInBackground(Void... voids) {
        if (!mType.equals(DocumentChange.Type.REMOVED)) mEmployeesDatabase.employeesDao().insertEmployee(mEmployee);
        else mEmployeesDatabase.employeesDao().deleteEmployee(mEmployee);

        return mLast;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            mRemoteListener.postExecute(result);
        }
    }
}
