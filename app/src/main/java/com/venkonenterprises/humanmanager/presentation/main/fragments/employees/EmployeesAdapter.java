package com.venkonenterprises.humanmanager.presentation.main.fragments.employees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.databinding.ItemUserBinding;
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
        ItemUserBinding itemUserBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_user, parent, false);
        return new ViewHolder(itemUserBinding, mOnClickListener);
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

        ItemUserBinding mItemUserBinding;
        OnClickListener mOnClickListener;
        Employee mEmployee;

        ViewHolder(@NonNull ItemUserBinding itemUserBinding, OnClickListener onClickListener) {
            super(itemUserBinding.getRoot());
            mOnClickListener = onClickListener;
            mItemUserBinding = itemUserBinding;
        }

        void bind(Employee employee) {
            mEmployee = employee;
            mItemUserBinding.tvName.setText(employee.getName());
            mItemUserBinding.tvLastName.setText(employee.getLastName());
            mItemUserBinding.tvCellphone.setText(employee.getCellphone());
            mItemUserBinding.background.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onItemClicked(mEmployee);
        }
    }
}
