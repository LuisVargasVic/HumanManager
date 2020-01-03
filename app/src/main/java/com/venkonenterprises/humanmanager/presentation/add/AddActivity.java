package com.venkonenterprises.humanmanager.presentation.add;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.databinding.ActivityAddBinding;
import com.venkonenterprises.humanmanager.domain.Employee;
import com.venkonenterprises.humanmanager.presentation.BaseActivity;
import com.venkonenterprises.humanmanager.remote.listeners.RemoteListener;
import com.venkonenterprises.humanmanager.utils.DatePickerFragment;

import java.util.Objects;

public class AddActivity extends BaseActivity implements RemoteListener {

    private ActivityAddBinding activityAddBinding;
    private AddViewModel viewModel;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddBinding = DataBindingUtil.setContentView(this, R.layout.activity_add);

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

        final RemoteListener remoteListener = this;
        progressDialog = setUpProgressDialog();

        activityAddBinding.addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                String date = activityAddBinding.dateLayout.getEditText().getText().toString();
                String country = Objects.requireNonNull(activityAddBinding.countryLayout.getEditText()).getText().toString();
                String folio = Objects.requireNonNull(activityAddBinding.folioLayout.getEditText()).getText().toString();
                String ssn = Objects.requireNonNull(activityAddBinding.ssnLayout.getEditText()).getText().toString();
                String uprc = Objects.requireNonNull(activityAddBinding.uprcLayout.getEditText()).getText().toString();
                String ftr = Objects.requireNonNull(activityAddBinding.ftrLayout.getEditText()).getText().toString();


                if (TextUtils.isEmpty(name)) {
                    activityAddBinding.nameLayout.setError(getString(R.string.name_required));
                } else if (TextUtils.isEmpty(lastName)) {
                    activityAddBinding.lastNameLayout.setError(getString(R.string.last_name_required));
                } else if (TextUtils.isEmpty(cellphone)) {
                    activityAddBinding.cellphoneLayout.setError(getString(R.string.cellphone_required));
                } else if (TextUtils.isEmpty(address)) {
                    activityAddBinding.addressLayout.setError(getString(R.string.address_required));
                } else if (TextUtils.isEmpty(dailySalary)) {
                    activityAddBinding.salaryLayout.setError(getString(R.string.daily_salary_required));
                } else if (validateSalary(dailySalary) == null) {
                    activityAddBinding.salaryLayout.setError(getString(R.string.daily_salary_error));
                } else if (TextUtils.isEmpty(referenceName)) {
                    activityAddBinding.referenceNameLayout.setError(getString(R.string.reference_name_required));
                } else if (TextUtils.isEmpty(referenceCellphone)) {
                    activityAddBinding.referenceCellphoneLayout.setError(getString(R.string.reference_cellphone_required));
                } else if (TextUtils.isEmpty(date)) {
                    activityAddBinding.dateLayout.setError(getString(R.string.date_required));
                } else if (TextUtils.isEmpty(country)) {
                    activityAddBinding.countryLayout.setError(getString(R.string.country_required));
                } else if (TextUtils.isEmpty(folio)) {
                    activityAddBinding.folioLayout.setError(getString(R.string.folio_required));
                } else if (TextUtils.isEmpty(ssn)) {
                    activityAddBinding.ssnLayout.setError(getString(R.string.ssn_required));
                } else if (TextUtils.isEmpty(uprc)) {
                    activityAddBinding.uprcLayout.setError(getString(R.string.uprc_required));
                } else if (TextUtils.isEmpty(ftr)) {
                    activityAddBinding.ftrLayout.setError(getString(R.string.ftr_required));
                } else {
                    remoteListener.preExecute();
                    viewModel.addEmployees(
                            new Employee(name, lastName, cellphone, address, referenceName, referenceCellphone, date, country, folio, ssn, uprc, ftr, validateSalary(dailySalary)),
                            remoteListener);
                }
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

    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void preExecute() {
        progressDialog.show();
    }

    @Override
    public void postExecute(Boolean result) {
        progressDialog.dismiss();
        finish();
    }
}
