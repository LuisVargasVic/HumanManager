package com.venkonenterprises.humanmanager.presentation.main.fragments.employees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.domain.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.ViewHolder> {

    private final Context mContext;
    private List<Employee> mEmployees;

    EmployeesAdapter(Context context) {
        mContext = context;
        mEmployees = new ArrayList<>();
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
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mEmployees.get(position));
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvName;
        final TextView tvLastName;
        final TextView tvCellphone;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLastName = itemView.findViewById(R.id.tv_last_name);
            tvCellphone = itemView.findViewById(R.id.tv_cellphone);
        }

        void bind(Employee employee) {
            tvName.setText(employee.getName());
            tvLastName.setText(employee.getLastName());
            tvCellphone.setText(employee.getCellphone());
        }
    }
}
