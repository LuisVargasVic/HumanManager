package com.venkonenterprises.humanmanager.presentation.main.fragments.employees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.databinding.ItemEmployeeBinding;
import com.venkonenterprises.humanmanager.domain.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.ViewHolder> {

    private final Context mContext;
    private List<Employee> mEmployees;
    private OnClickListener mOnClickListener;

    EmployeesAdapter(Context context, OnClickListener onClickListener) {
        mContext = context;
        mOnClickListener = onClickListener;
        mEmployees = new ArrayList<>();
    }

    interface OnClickListener {
        void onItemClicked(Employee employee);
    }

    void setEmployees(List<Employee> employees) {
        mEmployees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        ItemEmployeeBinding itemEmployeeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_employee, parent, false);
        return new ViewHolder(itemEmployeeBinding, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mEmployees.get(position));
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemEmployeeBinding mItemEmployeeBinding;
        OnClickListener mOnClickListener;
        Employee mEmployee;

        ViewHolder(@NonNull ItemEmployeeBinding itemEmployeeBinding, OnClickListener onClickListener) {
            super(itemEmployeeBinding.getRoot());
            mOnClickListener = onClickListener;
            mItemEmployeeBinding = itemEmployeeBinding;
        }

        void bind(Employee employee) {
            mEmployee = employee;
            mItemEmployeeBinding.tvName.setText(employee.getName());
            mItemEmployeeBinding.tvLastName.setText(employee.getLastName());
            mItemEmployeeBinding.tvCellphone.setText(employee.getCellphone());
            mItemEmployeeBinding.background.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onItemClicked(mEmployee);
        }
    }
}
