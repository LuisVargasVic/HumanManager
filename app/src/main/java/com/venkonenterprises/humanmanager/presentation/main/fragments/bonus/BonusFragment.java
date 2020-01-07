package com.venkonenterprises.humanmanager.presentation.main.fragments.bonus;


import android.content.Intent;
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
import com.venkonenterprises.humanmanager.presentation.add.AddActivity;
import com.venkonenterprises.humanmanager.presentation.main.MainActivity;

import java.util.List;

import static com.venkonenterprises.humanmanager.presentation.add.AddActivity.EMPLOYEE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class BonusFragment extends Fragment implements BonusAdapter.OnClickListener {

    public BonusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bonus, container, false);

        final BonusAdapter adapter = new BonusAdapter(getContext(), this);

        final RecyclerView recyclerView = view.findViewById(R.id.rv_bonus);
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

    @Override
    public void onItemClicked(Employee employee) {
        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(EMPLOYEE_KEY, employee);
        startActivity(intent);
    }

}
