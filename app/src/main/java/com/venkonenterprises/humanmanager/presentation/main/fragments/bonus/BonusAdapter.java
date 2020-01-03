package com.venkonenterprises.humanmanager.presentation.main.fragments.bonus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;
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

    BonusAdapter(Context context) {
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
        View view = layoutInflater.inflate(R.layout.item_bonus, parent, false);
        return new ViewHolder(view, mContext);
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

        final Context mContext;
        final TextView tvName;
        final TextView tvLastName;
        final TextView tvDate;
        final TextView tvWorkedDays;
        final TextView tvWorkedYears;
        final TextView tvBonus;
        final TextView tvVacation;
        final TextView tvHolidayBonus;
        final TextView tvTotal;

        ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;
            tvName = itemView.findViewById(R.id.tv_name);
            tvLastName = itemView.findViewById(R.id.tv_last_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvWorkedDays = itemView.findViewById(R.id.tv_worked_days);
            tvWorkedYears = itemView.findViewById(R.id.tv_worked_years);
            tvBonus = itemView.findViewById(R.id.tv_bonus);
            tvVacation = itemView.findViewById(R.id.tv_vacation);
            tvHolidayBonus = itemView.findViewById(R.id.tv_holiday_bonus);
            tvTotal = itemView.findViewById(R.id.tv_total);
        }

        @SuppressLint("DefaultLocale")
        void bind(Employee employee) {
            tvName.setText(employee.getName());
            tvLastName.setText(employee.getLastName());
            tvDate.setText(employee.getDate());

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

                tvBonus.setText(String.format("%,.2f", bonus));

                // Holiday vacations and vacations

                int year = calender.get(Calendar.YEAR);
                int yearNow = Calendar.getInstance().get(Calendar.YEAR);
                double vacationDays;

                if (year < yearNow) {
                    vacationDays = 6;
                    int workedYears = yearNow - year;
                    tvWorkedDays.setText(mContext.getString(R.string.time,  Double.valueOf(workedYears * numOfDays - dayAt + 1).intValue(), mContext.getString(R.string.days)));
                    tvWorkedYears.setText(mContext.getString(R.string.time, workedYears, mContext.getString(R.string.years)));

                    if (workedYears < 5) {
                        vacationDays += workedYears * 2;
                    } else {
                        vacationDays += 6;
                        vacationDays += workedYears / 5 * 2;
                    }
                } else {
                    vacationDays = 6 / numOfDays * workedDays;
                    if (numOfDays == workedDays) {
                        tvWorkedYears.setText(mContext.getString(R.string.one_year));
                        tvWorkedDays.setText(mContext.getString(R.string.time, Double.valueOf(numOfDays).intValue(), mContext.getString(R.string.days)));
                    } else {
                        tvWorkedYears.setText(mContext.getString(R.string.less_year));
                        if (numOfDays >= 1) {
                            tvWorkedDays.setText(mContext.getString(R.string.time, Double.valueOf(workedDays).intValue(), mContext.getString(R.string.days)));
                        } else {
                            tvWorkedDays.setText(mContext.getString(R.string.day));
                        }
                    }

                }

                double vacation = employee.getDailySalary() * vacationDays;
                double holidayVacation = employee.getDailySalary() * vacationDays * 0.25;

                tvVacation.setText(String.format("%,.2f", vacation));
                tvHolidayBonus.setText(String.format("%,.2f", holidayVacation));
                double total = bonus + vacation + holidayVacation;
                tvTotal.setText(String.format("%,.2f", total));


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
