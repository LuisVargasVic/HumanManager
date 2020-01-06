package com.venkonenterprises.humanmanager.presentation.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.databinding.ActivityMainBinding;
import com.venkonenterprises.humanmanager.presentation.add.AddActivity;
import com.venkonenterprises.humanmanager.presentation.login.SignInActivity;
import com.venkonenterprises.humanmanager.remote.listeners.ConnectionListener;
import com.venkonenterprises.humanmanager.remote.listeners.RemoteListener;
import com.venkonenterprises.humanmanager.remote.receivers.MainReceiver;
import com.venkonenterprises.humanmanager.utils.EmployeesService;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RemoteListener, ConnectionListener {

    private ActivityMainBinding activityMainBinding;
    public MainViewModel viewModel;
    private BroadcastReceiver mReceiver;
    private static ConnectionListener connectionListener;
    public Snackbar snackbar;

    @Override
    protected void onStart() {
        super.onStart();
        // Initialize connection receiver before making requests
        mReceiver = new MainReceiver();
        registerReceiver(mReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        connectionListener = this;

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityMainBinding.swipeRefreshLayout.setOnRefreshListener(this);
        final Context context = this;
        activityMainBinding.toolbar.inflateMenu(R.menu.menu_main);
        activityMainBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_main_sign_out) {
                    viewModel.signOut();
                    navigateToLoginActivity();
                    return true;
                }
                return false;
            }
        });

        activityMainBinding.pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), this));
        activityMainBinding.tabs.setupWithViewPager(activityMainBinding.pager);

        activityMainBinding.fabAddUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRefresh() {
        viewModel.refresh( this);
    }

    public static void setMainConnection(Boolean connection) {
        connectionListener.connection(connection);
    }

    @Override
    public void connection(Boolean connection) {
        viewModel.refresh( this);
        if (connection) snackbar = Snackbar.make(activityMainBinding.swipeRefreshLayout, getString(R.string.employees_empty), Snackbar.LENGTH_INDEFINITE);
        else snackbar = Snackbar.make(activityMainBinding.swipeRefreshLayout, getString(R.string.employees_connection), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void preExecute() {
        activityMainBinding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void postExecute(Boolean result) {
        activityMainBinding.swipeRefreshLayout.setRefreshing(result);
        EmployeesService.startActionUpdateWidgets(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }
}
