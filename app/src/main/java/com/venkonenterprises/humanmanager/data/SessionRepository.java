package com.venkonenterprises.humanmanager.data;

import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.venkonenterprises.humanmanager.remote.TaskSession;

public class SessionRepository {

    public void signInWithEmailAndPassword(Context context, OnCompleteListener<AuthResult> onCompleteListener, String email, String password) {
        new TaskSession(context, onCompleteListener).signInWithEmailAndPassword(email, password);
    }

    public void createUserWithEmailAndPassword(Context context, OnCompleteListener<AuthResult> onCompleteListener, String email, String password) {
        new TaskSession(context, onCompleteListener).createUserWithEmailAndPassword(email, password);
    }

}
