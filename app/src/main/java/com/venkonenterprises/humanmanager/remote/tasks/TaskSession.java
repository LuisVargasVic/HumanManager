package com.venkonenterprises.humanmanager.remote.tasks;

import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TaskSession {

    private OnCompleteListener<AuthResult> mOnCompleteListener = null;
    private final FirebaseAuth mFirebaseAuth;

    public TaskSession(Context context, OnCompleteListener<AuthResult> onCompleteListener) {
        mOnCompleteListener = onCompleteListener;
        FirebaseApp.initializeApp(context);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public TaskSession() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void signInWithEmailAndPassword(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mOnCompleteListener);
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(mOnCompleteListener);
    }

    public void signOut() {
        mFirebaseAuth.signOut();
    }
}
