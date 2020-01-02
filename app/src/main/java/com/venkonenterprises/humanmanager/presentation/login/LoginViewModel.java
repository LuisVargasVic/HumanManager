package com.venkonenterprises.humanmanager.presentation.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.venkonenterprises.humanmanager.data.SessionRepository;

public class LoginViewModel extends AndroidViewModel {

    private SessionRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new SessionRepository();
    }

    void signInWithEmailAndPassword(Context context, OnCompleteListener<AuthResult> onCompleteListener, String email, String password) {
        repository.signInWithEmailAndPassword(context, onCompleteListener, email, password);
    }

    void createUserWithEmailAndPassword(Context context, OnCompleteListener<AuthResult> onCompleteListener, String email, String password) {
        repository.createUserWithEmailAndPassword(context, onCompleteListener, email, password);
    }
}
