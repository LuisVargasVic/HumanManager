package com.venkonenterprises.humanmanager.presentation.main.fragments.bonus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.domain.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BonusAdapter extends RecyclerView.Adapter<BonusAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    BonusAdapter(Context context) {
        mContext = context;
        mUsers = new ArrayList<>();
        mUsers.add(
                new User(
                        "Luis",
                        "Vargas",
                        "+52 1122334455",
                        "",
                        "",
                        "",
                        "01/01/2019",
                        "",
                        "",
                        "",
                        "",
                        "",
                        714.29f
                )
        );
        mUsers.add(
                new User(
                        "Eduardo",
                        "Victoria",
                        "+52 6677889900",
                        "",
                        "",
                        "",
                        "01/01/2015",
                        "",
                        "",
                        "",
                        "",
                        "",
                        714.29f
                )
        );
        mUsers.add(
                new User(
                        "Alfonso",
                        "Arreola",
                        "+52 6677889900",
                        "",
                        "",
                        "",
                        "01/01/2014",
                        "",
                        "",
                        "",
                        "",
                        "",
                        714.29f
                )
        );
        mUsers.add(
                new User(
                        "Rafael",
                        "Novoa",
                        "+52 6677889900",
                        "",
                        "",
                        "",
                        "07/01/2019",
                        "",
                        "",
                        "",
                        "",
                        "",
                        257.14f
                )
        );
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
        holder.bind(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        Context mContext;
        TextView tvName;
        TextView tvLastName;
        TextView tvDate;
        TextView tvWorkedDays;
        TextView tvWorkedYears;
        TextView tvBonus;
        TextView tvVacation;
        TextView tvHolidayBonus;
        TextView tvTotal;

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

        void bind(User user) {
            tvName.setText(user.getName());
            tvLastName.setText(user.getLastName());
            tvDate.setText(user.getDate());

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date date;
            try {
                date = formatter.parse(user.getDate());
                Calendar calender = Calendar.getInstance();
                calender.setTime(date);

                // Bonus

                double numOfDays = calender.getActualMaximum(Calendar.DAY_OF_YEAR);
                double dayAt = calender.get(Calendar.DAY_OF_YEAR);

                double workedDays = (numOfDays - dayAt + 1);
                double bonusDays = 14 / numOfDays * workedDays;

                double bonus = user.getDailySalary() * bonusDays;

                tvBonus.setText(String.format("%,.2f", bonus));

                // Holiday vacations and vacations

                int year = calender.get(Calendar.YEAR);
                int yearNow = Calendar.getInstance().get(Calendar.YEAR);
                double vacationDays;

                if (year < yearNow) {
                    vacationDays = 6;
                    int workedYears = yearNow - year;
                    tvWorkedDays.setText(mContext.getString(R.string.days,  Double.valueOf(workedYears * numOfDays - dayAt + 1).intValue()));
                    tvWorkedYears.setText(mContext.getString(R.string.years, workedYears));

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
                        tvWorkedDays.setText(mContext.getString(R.string.days, Double.valueOf(numOfDays).intValue()));
                    } else {
                        tvWorkedYears.setText(mContext.getString(R.string.less_year));
                        if (numOfDays >= 1) {
                            tvWorkedDays.setText(mContext.getString(R.string.days, Double.valueOf(workedDays).intValue()));
                        } else {
                            tvWorkedDays.setText(mContext.getString(R.string.day));
                        }
                    }

                }

                double vacation = user.getDailySalary() * vacationDays;
                double holidayVacation = user.getDailySalary() * vacationDays * 0.25;

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
