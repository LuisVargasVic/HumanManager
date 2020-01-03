package com.venkonenterprises.humanmanager.presentation.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.presentation.main.fragments.bonus.BonusFragment;
import com.venkonenterprises.humanmanager.presentation.main.fragments.employees.EmployeesFragment;

class PagerAdapter extends FragmentStatePagerAdapter {
    
    private final Context context;

    PagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new EmployeesFragment();
        } else {
            fragment = new BonusFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.employees);
        } else {
            return context.getString(R.string.bonus);
        }
    }
}
