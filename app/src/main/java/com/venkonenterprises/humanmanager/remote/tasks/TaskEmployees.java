package com.venkonenterprises.humanmanager.remote.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.venkonenterprises.humanmanager.database.DatabaseEmployee;
import com.venkonenterprises.humanmanager.database.EmployeesDatabase;
import com.venkonenterprises.humanmanager.domain.Employee;
import com.venkonenterprises.humanmanager.remote.listeners.RemoteListener;

import java.util.HashMap;
import java.util.Iterator;

public class TaskEmployees {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private EmployeesDatabase mEmployeesDatabase;
    private static String USERS_KEY = "users";
    private static String EMPLOYEES_KEY = "employees";
    private static String NAME_KEY = "name";
    private static String LAST_NAME_KEY = "last_name";
    private static String CELLPHONE_KEY = "cellphone";
    private static String ADDRESS_KEY = "address";
    private static String REFERENCE_NAME_KEY = "reference_name";
    private static String REFERENCE_CELLPHONE_KEY = "reference_cellphone";
    private static String DATE_KEY = "date";
    private static String COUNTRY_KEY = "country";
    private static String FOLIO_KEY = "folio";
    private static String SSN_KEY = "ssn";
    private static String UPRC_KEY = "uprc";
    private static String FTR_KEY = "ftr";
    private static String DAILY_SALARY_KEY = "daily_salary";
    private RemoteListener mRemoteListener;

    public TaskEmployees() {

    }

    public TaskEmployees(EmployeesDatabase employeesDatabase, RemoteListener remoteListener) {
        mEmployeesDatabase = employeesDatabase;
        mRemoteListener = remoteListener;
    }

    public void refresh() {
        final HashMap<DatabaseEmployee, DocumentChange.Type> databaseEmployees = new HashMap<>();
        db.collection(USERS_KEY).document(userId).collection(EMPLOYEES_KEY)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) return;
                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                                DatabaseEmployee databaseEmployee;
                                if (documentChange.getType() != DocumentChange.Type.REMOVED) {
                                    databaseEmployee = new DatabaseEmployee(
                                            documentChange.getDocument().getId(),
                                            documentChange.getDocument().get(NAME_KEY).toString(),
                                            documentChange.getDocument().get(LAST_NAME_KEY).toString(),
                                            documentChange.getDocument().get(CELLPHONE_KEY).toString(),
                                            documentChange.getDocument().get(ADDRESS_KEY).toString(),
                                            documentChange.getDocument().get(REFERENCE_NAME_KEY).toString(),
                                            documentChange.getDocument().get(REFERENCE_CELLPHONE_KEY).toString(),
                                            documentChange.getDocument().get(DATE_KEY).toString(),
                                            documentChange.getDocument().get(COUNTRY_KEY).toString(),
                                            documentChange.getDocument().get(FOLIO_KEY).toString(),
                                            documentChange.getDocument().get(SSN_KEY).toString(),
                                            documentChange.getDocument().get(UPRC_KEY).toString(),
                                            documentChange.getDocument().get(FTR_KEY).toString(),
                                            Float.valueOf(documentChange.getDocument().get(DAILY_SALARY_KEY).toString())
                                    );
                                } else {
                                    databaseEmployee = new DatabaseEmployee(
                                            documentChange.getDocument().getId(),
                                            documentChange.getDocument().get(NAME_KEY).toString(),
                                            documentChange.getDocument().get(LAST_NAME_KEY).toString(),
                                            documentChange.getDocument().get(CELLPHONE_KEY).toString(),
                                            documentChange.getDocument().get(ADDRESS_KEY).toString(),
                                            documentChange.getDocument().get(REFERENCE_NAME_KEY).toString(),
                                            documentChange.getDocument().get(REFERENCE_CELLPHONE_KEY).toString(),
                                            documentChange.getDocument().get(DATE_KEY).toString(),
                                            documentChange.getDocument().get(COUNTRY_KEY).toString(),
                                            documentChange.getDocument().get(FOLIO_KEY).toString(),
                                            documentChange.getDocument().get(SSN_KEY).toString(),
                                            documentChange.getDocument().get(UPRC_KEY).toString(),
                                            documentChange.getDocument().get(FTR_KEY).toString(),
                                            Float.valueOf(documentChange.getDocument().get(DAILY_SALARY_KEY).toString())
                                    );
                                }
                                databaseEmployees.put(databaseEmployee, documentChange.getType());
                            }
                            Iterator<HashMap.Entry<DatabaseEmployee, DocumentChange.Type>> itr = databaseEmployees.entrySet().iterator();

                            while(itr.hasNext()) {
                                HashMap.Entry<DatabaseEmployee, DocumentChange.Type> entry = itr.next();
                                new TaskUpdateDatabase(mEmployeesDatabase, mRemoteListener, entry.getKey(), entry.getValue(), itr.hasNext()).execute();
                            }
                        }
                    }
                });
    }

    public void addEmployee(Employee employeeObject, final RemoteListener remoteListener) {
        HashMap employee = new HashMap<String, Object>();
        employee.put(NAME_KEY, employeeObject.getName());
        employee.put(LAST_NAME_KEY, employeeObject.getLastName());
        employee.put(CELLPHONE_KEY, employeeObject.getCellphone());
        employee.put(ADDRESS_KEY, employeeObject.getAddress());
        employee.put(REFERENCE_NAME_KEY, employeeObject.getReferenceName());
        employee.put(REFERENCE_CELLPHONE_KEY, employeeObject.getReferenceCellphone());
        employee.put(DATE_KEY, employeeObject.getDate());
        employee.put(COUNTRY_KEY, employeeObject.getCountry());
        employee.put(FOLIO_KEY, employeeObject.getFolio());
        employee.put(SSN_KEY, employeeObject.getSsn());
        employee.put(UPRC_KEY, employeeObject.getUprc());
        employee.put(FTR_KEY, employeeObject.getFtr());
        employee.put(DAILY_SALARY_KEY, employeeObject.getDailySalary());

        db.collection(USERS_KEY).document(userId).collection(EMPLOYEES_KEY)
                .add(employee)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            remoteListener.postExecute(true);
                        } else {
                            remoteListener.postExecute(false);
                        }
                    }
                });
    }

}
