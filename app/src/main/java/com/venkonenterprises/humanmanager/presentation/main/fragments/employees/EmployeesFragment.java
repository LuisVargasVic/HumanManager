package com.venkonenterprises.humanmanager.presentation.main.fragments.employees;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.domain.Employee;
import com.venkonenterprises.humanmanager.presentation.main.MainActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesFragment extends Fragment {

    public EmployeesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employees, container, false);

        final EmployeesAdapter adapter = new EmployeesAdapter(getContext());

        final RecyclerView recyclerView = view.findViewById(R.id.rv_users);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.viewModel != null) {
            mainActivity.viewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
                @Override
                public void onChanged(@Nullable List<Employee> employees) {
                    adapter.setEmployees(employees);
                    if (mainActivity.snackbar != null) mainActivity.snackbar.dismiss();
                }
            });
        }

        return view;
    }

}
