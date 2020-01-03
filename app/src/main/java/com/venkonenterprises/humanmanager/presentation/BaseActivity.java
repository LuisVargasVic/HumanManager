package com.venkonenterprises.humanmanager.presentation;

import android.content.DialogInterface;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.venkonenterprises.humanmanager.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected AlertDialog simpleAlertDialog(String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }

    protected AlertDialog setUpProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        return builder.create();
    }
}
