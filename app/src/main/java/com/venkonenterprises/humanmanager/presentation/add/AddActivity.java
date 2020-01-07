package com.venkonenterprises.humanmanager.presentation.add;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.databinding.ActivityAddBinding;
import com.venkonenterprises.humanmanager.domain.Employee;
import com.venkonenterprises.humanmanager.presentation.BaseActivity;
import com.venkonenterprises.humanmanager.utils.DatePickerFragment;

import java.util.Objects;

public class AddActivity extends BaseActivity {

    private ActivityAddBinding activityAddBinding;
    private AddViewModel viewModel;
    public static final String EMPLOYEE_KEY = "employee";
    private Employee mEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_add);

        if (getIntent().hasExtra(EMPLOYEE_KEY)) {
            mEmployee = (Employee) getIntent().getSerializableExtra(EMPLOYEE_KEY);
            activityAddBinding.toolbar.setTitle(R.string.action_update_employee);
            Objects.requireNonNull(activityAddBinding.nameLayout.getEditText()).setText(mEmployee.getName());
            Objects.requireNonNull(activityAddBinding.lastNameLayout.getEditText()).setText(mEmployee.getLastName());
            Objects.requireNonNull(activityAddBinding.cellphoneLayout.getEditText()).setText(mEmployee.getCellphone());
            Objects.requireNonNull(activityAddBinding.addressLayout.getEditText()).setText(mEmployee.getAddress());
            Objects.requireNonNull(activityAddBinding.salaryLayout.getEditText()).setText(String.valueOf(mEmployee.getDailySalary()));
            Objects.requireNonNull(activityAddBinding.referenceNameLayout.getEditText()).setText(mEmployee.getReferenceName());
            Objects.requireNonNull(activityAddBinding.referenceCellphoneLayout.getEditText()).setText(mEmployee.getReferenceCellphone());
            Objects.requireNonNull(activityAddBinding.dateLayout.getEditText()).setText(mEmployee.getDate());
            Objects.requireNonNull(activityAddBinding.countryLayout.getEditText()).setText(mEmployee.getCountry());
            Objects.requireNonNull(activityAddBinding.folioLayout.getEditText()).setText(mEmployee.getFolio());
            Objects.requireNonNull(activityAddBinding.ssnLayout.getEditText()).setText(mEmployee.getSsn());
            Objects.requireNonNull(activityAddBinding.uprcLayout.getEditText()).setText(mEmployee.getUprc());
            Objects.requireNonNull(activityAddBinding.ftrLayout.getEditText()).setText(mEmployee.getFtr());
            activityAddBinding.addEmployee.setText(R.string.action_update_employee);
            activityAddBinding.deleteEmployee.setVisibility(View.VISIBLE);
        }

        viewModel = ViewModelProviders.of(this).get(AddViewModel.class);
        final Activity activity = this;

        Objects.requireNonNull(activityAddBinding.dateLayout.getEditText()).setTextIsSelectable(false);
        activityAddBinding.dateLayout.getEditText().setKeyListener(null);
        activityAddBinding.dateLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    activityAddBinding.dateLayout.getEditText().callOnClick();
                    hideSoftKeyboard(activity);
                }
            }
        });
        activityAddBinding.dateLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.date = activityAddBinding.dateLayout.getEditText();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        activityAddBinding.addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
                activityAddBinding.addEmployee.setEnabled(false);
                activityAddBinding.nameLayout.setError(null);
                activityAddBinding.lastNameLayout.setError(null);
                activityAddBinding.cellphoneLayout.setError(null);
                activityAddBinding.addressLayout.setError(null);
                activityAddBinding.salaryLayout.setError(null);
                activityAddBinding.referenceNameLayout.setError(null);
                activityAddBinding.referenceCellphoneLayout.setError(null);
                activityAddBinding.dateLayout.setError(null);
                activityAddBinding.countryLayout.setError(null);
                activityAddBinding.folioLayout.setError(null);
                activityAddBinding.ssnLayout.setError(null);
                activityAddBinding.uprcLayout.setError(null);
                activityAddBinding.ftrLayout.setError(null);

                String name = Objects.requireNonNull(activityAddBinding.nameLayout.getEditText()).getText().toString();
                String lastName = Objects.requireNonNull(activityAddBinding.lastNameLayout.getEditText()).getText().toString();
                String cellphone = Objects.requireNonNull(activityAddBinding.cellphoneLayout.getEditText()).getText().toString();
                String address = Objects.requireNonNull(activityAddBinding.addressLayout.getEditText()).getText().toString();
                String dailySalary = Objects.requireNonNull(activityAddBinding.salaryLayout.getEditText()).getText().toString();
                String referenceName = Objects.requireNonNull(activityAddBinding.referenceNameLayout.getEditText()).getText().toString();
                String referenceCellphone = Objects.requireNonNull(activityAddBinding.referenceCellphoneLayout.getEditText()).getText().toString();
                String date = Objects.requireNonNull(activityAddBinding.dateLayout.getEditText()).getText().toString();
                String country = Objects.requireNonNull(activityAddBinding.countryLayout.getEditText()).getText().toString();
                String folio = Objects.requireNonNull(activityAddBinding.folioLayout.getEditText()).getText().toString();
                String ssn = Objects.requireNonNull(activityAddBinding.ssnLayout.getEditText()).getText().toString();
                String uprc = Objects.requireNonNull(activityAddBinding.uprcLayout.getEditText()).getText().toString();
                String ftr = Objects.requireNonNull(activityAddBinding.ftrLayout.getEditText()).getText().toString();


                if (TextUtils.isEmpty(name)) {
                    activityAddBinding.nameLayout.setError(getString(R.string.name_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    activityAddBinding.lastNameLayout.setError(getString(R.string.last_name_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(cellphone)) {
                    activityAddBinding.cellphoneLayout.setError(getString(R.string.cellphone_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                }
                if (TextUtils.isEmpty(address)) {
                    activityAddBinding.addressLayout.setError(getString(R.string.address_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(dailySalary)) {
                    activityAddBinding.salaryLayout.setError(getString(R.string.daily_salary_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (validateSalary(dailySalary) == null) {
                    activityAddBinding.salaryLayout.setError(getString(R.string.daily_salary_error));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(referenceName)) {
                    activityAddBinding.referenceNameLayout.setError(getString(R.string.reference_name_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(referenceCellphone)) {
                    activityAddBinding.referenceCellphoneLayout.setError(getString(R.string.reference_cellphone_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    activityAddBinding.dateLayout.setError(getString(R.string.date_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(country)) {
                    activityAddBinding.countryLayout.setError(getString(R.string.country_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(folio)) {
                    activityAddBinding.folioLayout.setError(getString(R.string.folio_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(ssn)) {
                    activityAddBinding.ssnLayout.setError(getString(R.string.ssn_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(uprc)) {
                    activityAddBinding.uprcLayout.setError(getString(R.string.uprc_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(ftr)) {
                    activityAddBinding.ftrLayout.setError(getString(R.string.ftr_required));
                    activityAddBinding.addEmployee.setEnabled(true);
                    return;
                }

                onPreExecute();

                if (mEmployee != null) {
                    viewModel.updateEmployee(
                            new Employee(mEmployee.getId(), name, lastName, cellphone, address, referenceName, referenceCellphone, date, country, folio, ssn, uprc, ftr, validateSalary(dailySalary)),
                            new OnCompleteListenerGeneric<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    onPostExecute(task);

                                }
                            });
                } else {
                    viewModel.addEmployee(
                            new Employee("", name, lastName, cellphone, address, referenceName, referenceCellphone, date, country, folio, ssn, uprc, ftr, validateSalary(dailySalary)),
                            new OnCompleteListenerGeneric<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    onPostExecute(task);

                                }
                            });
                }
            }
        });
        activityAddBinding.deleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreExecute();
                hideSoftKeyboard(activity);
                viewModel.deleteEmployee(
                        mEmployee.getId(),
                        new OnCompleteListenerGeneric<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                onPostExecute(task);

                            }
                        });
            }
        });
    }

    private Float validateSalary(String dailySalary) {
        Float floatDailySalary = null;
        try {
            floatDailySalary = Float.valueOf(dailySalary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return floatDailySalary;
    }

    public void onPreExecute() {
        activityAddBinding.loading.setVisibility(View.VISIBLE);
        activityAddBinding.addEmployee.setEnabled(false);
        activityAddBinding.deleteEmployee.setEnabled(false);
    }

    public <T> void onPostExecute(Task<T> task) {
        if (task.isSuccessful()) {
            finish();
        } else {
            try {
                throw Objects.requireNonNull(task.getException());
            } catch (Exception e) {
                simpleAlertDialog(e.getMessage()).show();
            }
        }
        activityAddBinding.loading.setVisibility(View.INVISIBLE);
        activityAddBinding.addEmployee.setEnabled(true);
        activityAddBinding.deleteEmployee.setEnabled(true);
    }

    interface OnCompleteListenerGeneric<T> extends OnCompleteListener<T> {

    }
}
