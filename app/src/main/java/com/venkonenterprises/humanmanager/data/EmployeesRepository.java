package com.venkonenterprises.humanmanager.data;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.venkonenterprises.humanmanager.database.DatabaseEmployee;
import com.venkonenterprises.humanmanager.database.EmployeesDatabase;
import com.venkonenterprises.humanmanager.domain.Employee;
import com.venkonenterprises.humanmanager.remote.listeners.RemoteListener;
import com.venkonenterprises.humanmanager.remote.tasks.TaskEmployees;

import java.util.ArrayList;
import java.util.List;

public class EmployeesRepository {

    private EmployeesDatabase mEmployeesDatabase;

    public EmployeesRepository(EmployeesDatabase database) {
        mEmployeesDatabase = database;
    }

    public EmployeesRepository() {

    }

    public void refresh(RemoteListener remoteListener) {
        new TaskEmployees(mEmployeesDatabase, remoteListener).refresh();
    }

    public LiveData<List<Employee>> getEmployees() {
        return Transformations.map(mEmployeesDatabase.employeesDao().getEmployees(),
                new Function<List<DatabaseEmployee>, List<Employee>>() {
                    @Override
                    public List<Employee> apply(List<DatabaseEmployee> databaseEmployees) {
                        List<Employee> employees = new ArrayList<>();

                        for (int i = 0; i < databaseEmployees.size(); i++) {
                            DatabaseEmployee databaseEmployee = databaseEmployees.get(i);
                            employees.add(new Employee(
                                            databaseEmployee.getId(),
                                            databaseEmployee.getName(),
                                            databaseEmployee.getLastName(),
                                            databaseEmployee.getCellphone(),
                                            databaseEmployee.getAddress(),
                                            databaseEmployee.getReferenceName(),
                                            databaseEmployee.getReferenceCellphone(),
                                            databaseEmployee.getDate(),
                                            databaseEmployee.getCountry(),
                                            databaseEmployee.getFolio(),
                                            databaseEmployee.getSsn(),
                                            databaseEmployee.getUprc(),
                                            databaseEmployee.getFtr(),
                                            databaseEmployee.getDailySalary()
                                    )
                            );
                        }

                        return employees;
                    }
                });
    }

    public void addEmployee(Employee employee, OnCompleteListener<DocumentReference> documentReferenceOnCompleteListener) {
        new TaskEmployees().addEmployee(employee, documentReferenceOnCompleteListener);
    }

    public void updateEmployee(Employee employee, OnCompleteListener<Void> voidOnCompleteListener) {
        new TaskEmployees().updateEmployee(employee, voidOnCompleteListener);
    }

    public void deleteEmployee(String employeeUid, OnCompleteListener<Void> voidOnCompleteListener) {
        new TaskEmployees().deleteEmployee(employeeUid, voidOnCompleteListener);
    }

    public void deleteAllEmployees() {
        new TaskEmployees(mEmployeesDatabase).deleteAllEmployees();
    }
}
