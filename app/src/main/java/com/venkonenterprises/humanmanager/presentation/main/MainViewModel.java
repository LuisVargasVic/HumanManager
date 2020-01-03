package com.venkonenterprises.humanmanager.presentation.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.venkonenterprises.humanmanager.data.EmployeesRepository;
import com.venkonenterprises.humanmanager.database.EmployeesDatabase;
import com.venkonenterprises.humanmanager.domain.Employee;
import com.venkonenterprises.humanmanager.remote.listeners.RemoteListener;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private final EmployeesRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        EmployeesDatabase database = EmployeesDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the employees from the DataBase");
        repository = new EmployeesRepository(database);
    }

    void refresh(RemoteListener remoteListener) {
        repository.refresh(remoteListener);
    }

    public LiveData<List<Employee>> getEmployees() {
        return repository.getEmployees();
    }

}
