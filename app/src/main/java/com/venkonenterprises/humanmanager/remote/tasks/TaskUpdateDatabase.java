package com.venkonenterprises.humanmanager.remote.tasks;

import android.os.AsyncTask;

import com.google.firebase.firestore.DocumentChange;
import com.venkonenterprises.humanmanager.database.DatabaseEmployee;
import com.venkonenterprises.humanmanager.database.EmployeesDatabase;
import com.venkonenterprises.humanmanager.remote.listeners.RemoteListener;

class TaskUpdateDatabase extends AsyncTask<Void, Void, Boolean> {

    private EmployeesDatabase mEmployeesDatabase;
    private RemoteListener mRemoteListener = null;
    private DatabaseEmployee mEmployee = null;
    private DocumentChange.Type mType = null;
    private boolean mLast = false;

    TaskUpdateDatabase(EmployeesDatabase employeesDatabase, RemoteListener remoteListener, DatabaseEmployee employee, DocumentChange.Type type, Boolean last) {
        mEmployeesDatabase = employeesDatabase;
        mRemoteListener = remoteListener;
        mEmployee = employee;
        mType = type;
        mLast = last;
    }

    TaskUpdateDatabase(EmployeesDatabase employeesDatabase) {
        mEmployeesDatabase = employeesDatabase;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mRemoteListener != null) mRemoteListener.preExecute();
    }

    @Override
    public Boolean doInBackground(Void... voids) {
        if (mEmployee != null) {
            if (mType.equals(DocumentChange.Type.REMOVED)) mEmployeesDatabase.employeesDao().deleteEmployee(mEmployee);
            else mEmployeesDatabase.employeesDao().insertEmployee(mEmployee);
            return mLast;
        } else {
            mEmployeesDatabase.employeesDao().deleteAllEmployees();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (mRemoteListener != null) mRemoteListener.postExecute(result);
    }
}
