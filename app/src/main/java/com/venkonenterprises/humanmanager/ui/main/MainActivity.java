package com.venkonenterprises.humanmanager.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.venkonenterprises.humanmanager.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        ViewPager pager = findViewById(R.id.pager);
        TabLayout tabs = findViewById(R.id.tabs);

        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), this));
        tabs.setupWithViewPager(pager);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_add_users);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, AddActivity.class);
                startActivity(intent);*/
            }
        });
    }
}
