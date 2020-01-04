package com.venkonenterprises.humanmanager.presentation.main.fragments.bonus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.databinding.ItemBonusBinding;
import com.venkonenterprises.humanmanager.domain.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BonusAdapter extends RecyclerView.Adapter<BonusAdapter.ViewHolder> {

    private final Context mContext;
    private List<Employee> mEmployees;
    private OnClickListener mOnClickListener;

    BonusAdapter(Context context, OnClickListener onClickListener) {
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
        ItemBonusBinding itemBonusBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_bonus, parent, false);
        return new ViewHolder(itemBonusBinding, mContext, mOnClickListener);
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

        final Context mContext;
        private final ItemBonusBinding mItemBonusBinding;
        private final OnClickListener mOnClickListener;
        private Employee mEmployee;

        ViewHolder(@NonNull ItemBonusBinding itemBonusBinding, Context context, OnClickListener onClickListener) {
            super(itemBonusBinding.getRoot());
            mItemBonusBinding = itemBonusBinding;
            mContext = context;
            mOnClickListener = onClickListener;
        }

        @SuppressLint("DefaultLocale")
        void bind(Employee employee) {
            mEmployee = employee;
            mItemBonusBinding.tvName.setText(employee.getName());
            mItemBonusBinding.tvLastName.setText(employee.getLastName());
            mItemBonusBinding.tvDate.setText(employee.getDate());
            mItemBonusBinding.background.setOnClickListener(this);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date date;
            try {
                date = formatter.parse(employee.getDate());
                Calendar calender = Calendar.getInstance();
                assert date != null;
                calender.setTime(date);

                // Bonus

                double numOfDays = calender.getActualMaximum(Calendar.DAY_OF_YEAR);
                double dayAt = calender.get(Calendar.DAY_OF_YEAR);

                double workedDays = (numOfDays - dayAt + 1);
                double bonusDays = 14 / numOfDays * workedDays;

                double bonus = employee.getDailySalary() * bonusDays;

                mItemBonusBinding.tvBonus.setText(String.format("%,.2f", bonus));

                // Holiday vacations and vacations

                int year = calender.get(Calendar.YEAR);
                int yearNow = Calendar.getInstance().get(Calendar.YEAR);
                double vacationDays;

                if (year < yearNow) {
                    vacationDays = 6;
                    int workedYears = yearNow - year;
                    mItemBonusBinding.tvWorkedDays.setText(mContext.getString(R.string.time,  Double.valueOf(workedYears * numOfDays - dayAt + 1).intValue(), mContext.getString(R.string.days)));
                    mItemBonusBinding.tvWorkedYears.setText(mContext.getString(R.string.time, workedYears, mContext.getString(R.string.years)));

                    if (workedYears < 5) {
                        vacationDays += workedYears * 2;
                    } else {
                        vacationDays += 6;
                        vacationDays += workedYears / 5 * 2;
                    }
                } else {
                    vacationDays = 6 / numOfDays * workedDays;
                    if (numOfDays == workedDays) {
                        mItemBonusBinding.tvWorkedYears.setText(mContext.getString(R.string.one_year));
                        mItemBonusBinding.tvWorkedDays.setText(mContext.getString(R.string.time, Double.valueOf(numOfDays).intValue(), mContext.getString(R.string.days)));
                    } else {
                        mItemBonusBinding.tvWorkedYears.setText(mContext.getString(R.string.less_year));
                        if (numOfDays >= 1) {
                            mItemBonusBinding.tvWorkedDays.setText(mContext.getString(R.string.time, Double.valueOf(workedDays).intValue(), mContext.getString(R.string.days)));
                        } else {
                            mItemBonusBinding.tvWorkedDays.setText(mContext.getString(R.string.day));
                        }
                    }

                }

                double vacation = employee.getDailySalary() * vacationDays;
                double holidayVacation = employee.getDailySalary() * vacationDays * 0.25;

                mItemBonusBinding.tvVacation.setText(String.format("%,.2f", vacation));
                mItemBonusBinding.tvHolidayBonus.setText(String.format("%,.2f", holidayVacation));
                double total = bonus + vacation + holidayVacation;
                mItemBonusBinding.tvTotal.setText(String.format("%,.2f", total));


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onItemClicked(mEmployee);
        }
    }
}
