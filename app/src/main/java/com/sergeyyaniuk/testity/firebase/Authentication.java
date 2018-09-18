package com.sergeyyaniuk.testity.firebase;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;
import javax.inject.Singleton;

public class Authentication {

    private Application mApplication;
    private FirebaseAuth mAuth;

    public Authentication(Application application){
        this.mApplication = application;
        this.mAuth = FirebaseAuth.getInstance();
    }



}
