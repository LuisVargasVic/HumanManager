package com.venkonenterprises.humanmanager.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.databinding.ActivityLoginBinding;
import com.venkonenterprises.humanmanager.presentation.BaseActivity;
import com.venkonenterprises.humanmanager.presentation.main.MainActivity;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements OnCompleteListener<AuthResult> {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoginViewModel viewModel;
    private ActivityLoginBinding activityLoginBinding;
    private AlertDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        progressDialog = setUpProgressDialog();
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() != null) {
            navigateToMainActivity();
        }

        activityLoginBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                activityLoginBinding.usernameLayout.setError(null);
                activityLoginBinding.passwordLayout.setError(null);

                String username = Objects.requireNonNull(activityLoginBinding.usernameLayout.getEditText()).getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    activityLoginBinding.usernameLayout.setError(getString(R.string.email_empty));
                    return;
                }

                String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

                if (!Pattern.matches(regex, username)) {
                    activityLoginBinding.usernameLayout.setError(getString(R.string.email_invalid));
                    return;
                }

                String password = Objects.requireNonNull(activityLoginBinding.passwordLayout.getEditText()).getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    activityLoginBinding.passwordLayout.setError(getString(R.string.password_empty));
                    return;
                }

                if (password.length() < 6) {
                    activityLoginBinding.passwordLayout.setError(getString(R.string.password_invalid));
                    return;
                }

                viewModel.createUserWithEmailAndPassword(LoginActivity.this,LoginActivity.this, username, password);
            }
        });
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (!task.isSuccessful()) {
            try {
                throw Objects.requireNonNull(task.getException());
            }
            catch (FirebaseAuthWeakPasswordException weakPassword) {
                Log.d(TAG, "onComplete: " + weakPassword.getMessage());
                activityLoginBinding.passwordLayout.setError(weakPassword.getReason());
                progressDialog.hide();
            }
            catch (FirebaseAuthUserCollisionException existEmail) {
                String username = Objects.requireNonNull(activityLoginBinding.usernameLayout.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(activityLoginBinding.passwordLayout.getEditText()).getText().toString().trim();
                viewModel.signInWithEmailAndPassword(this,this, username, password);
            }
            catch (Exception e) {
                progressDialog.hide();
                simpleAlertDialog(e.getMessage()).show();
            }
        } else {
            progressDialog.dismiss();
            navigateToMainActivity();
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
