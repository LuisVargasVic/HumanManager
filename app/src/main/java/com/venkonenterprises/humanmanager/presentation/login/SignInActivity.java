package com.venkonenterprises.humanmanager.presentation.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.databinding.ActivitySignInBinding;
import com.venkonenterprises.humanmanager.presentation.BaseActivity;
import com.venkonenterprises.humanmanager.presentation.main.MainActivity;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignInActivity extends BaseActivity implements OnCompleteListener<AuthResult> {

    private static final String TAG = SignInActivity.class.getSimpleName();
    private SignInViewModel viewModel;
    private ActivitySignInBinding activitySignInBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        viewModel = ViewModelProviders.of(this).get(SignInViewModel.class);

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() != null) {
            navigateToMainActivity();
        }
        final Activity activity = this;

        activitySignInBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
                activitySignInBinding.signIn.setEnabled(false);
                activitySignInBinding.usernameLayout.setError(null);
                activitySignInBinding.passwordLayout.setError(null);

                String username = Objects.requireNonNull(activitySignInBinding.usernameLayout.getEditText()).getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    activitySignInBinding.usernameLayout.setError(getString(R.string.email_empty));
                    activitySignInBinding.signIn.setEnabled(true);
                    return;
                }

                String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

                if (!Pattern.matches(regex, username)) {
                    activitySignInBinding.usernameLayout.setError(getString(R.string.email_invalid));
                    activitySignInBinding.signIn.setEnabled(true);
                    return;
                }

                String password = Objects.requireNonNull(activitySignInBinding.passwordLayout.getEditText()).getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    activitySignInBinding.passwordLayout.setError(getString(R.string.password_empty));
                    activitySignInBinding.signIn.setEnabled(true);
                    return;
                }

                if (password.length() < 6) {
                    activitySignInBinding.passwordLayout.setError(getString(R.string.password_invalid));
                    activitySignInBinding.signIn.setEnabled(true);
                    return;
                }

                activitySignInBinding.loading.setVisibility(View.VISIBLE);
                viewModel.createUserWithEmailAndPassword(SignInActivity.this, SignInActivity.this, username, password);
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
                activitySignInBinding.passwordLayout.setError(weakPassword.getReason());
                activitySignInBinding.loading.setVisibility(View.GONE);
                activitySignInBinding.signIn.setEnabled(true);
            }
            catch (FirebaseAuthUserCollisionException existEmail) {
                String username = Objects.requireNonNull(activitySignInBinding.usernameLayout.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(activitySignInBinding.passwordLayout.getEditText()).getText().toString().trim();
                activitySignInBinding.loading.setVisibility(View.VISIBLE);
                viewModel.signInWithEmailAndPassword(this,this, username, password);
            }
            catch (Exception e) {
                activitySignInBinding.loading.setVisibility(View.GONE);
                activitySignInBinding.signIn.setEnabled(true);
                simpleAlertDialog(e.getMessage()).show();
            }
        } else {
            activitySignInBinding.loading.setVisibility(View.GONE);
            activitySignInBinding.signIn.setEnabled(true);
            navigateToMainActivity();
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
