package com.venkonenterprises.humanmanager.presentation.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.venkonenterprises.humanmanager.data.EmployeesRepository;
import com.venkonenterprises.humanmanager.domain.Employee;

public class AddViewModel extends AndroidViewModel {

    private final EmployeesRepository repository;

    public AddViewModel(@NonNull Application application) {
        super(application);
        repository = new EmployeesRepository();
    }

    void addEmployee(Employee employee, OnCompleteListener<DocumentReference> documentReferenceOnCompleteListener) {
        repository.addEmployee(employee, documentReferenceOnCompleteListener);
    }

    void updateEmployee(Employee employee, OnCompleteListener<Void> voidOnCompleteListener) {
        repository.updateEmployee(employee, voidOnCompleteListener);
    }

    void deleteEmployee(String employeeUid, OnCompleteListener<Void> voidOnCompleteListener) {
        repository.deleteEmployee(employeeUid, voidOnCompleteListener);
    }

}
