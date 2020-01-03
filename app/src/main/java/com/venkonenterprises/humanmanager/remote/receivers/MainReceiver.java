package com.venkonenterprises.humanmanager.remote.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.venkonenterprises.humanmanager.presentation.main.MainActivity.setMainConnection;

public class MainReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        Boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        setMainConnection(isConnected);
    }
}
