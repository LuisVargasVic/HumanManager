package com.venkonenterprises.humanmanager.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.venkonenterprises.humanmanager.R;

public class PagerAdapter extends FragmentStatePagerAdapter {
    
    private Context context;

     PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        /*if (position == 0) {
            fragment = new UsersFragment();
        } else {
            fragment = new BonusFragment();
        }*/
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
