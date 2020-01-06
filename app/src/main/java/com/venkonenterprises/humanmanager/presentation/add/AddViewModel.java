package com.venkonenterprises.humanmanager.presentation.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.venkonenterprises.humanmanager.data.EmployeesRepository;
import com.venkonenterprises.humanmanager.domain.Employee;
import com.venkonenterprises.humanmanager.remote.listeners.RemoteListener;

public class AddViewModel extends AndroidViewModel {

    private final EmployeesRepository repository;

    public AddViewModel(@NonNull Application application) {
        super(application);
        repository = new EmployeesRepository();
    }

    void addEmployee(Employee employee, RemoteListener remoteListener) {
        repository.addEmployee(employee, remoteListener);
    }

    void updateEmployee(Employee employee, RemoteListener remoteListener) {
        repository.updateEmployee(employee, remoteListener);
    }

    void deleteEmployee(String employeeUid, RemoteListener remoteListener) {
        repository.deleteEmployee(employeeUid, remoteListener);
    }

}