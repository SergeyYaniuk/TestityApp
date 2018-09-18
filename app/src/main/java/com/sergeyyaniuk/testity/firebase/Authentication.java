package com.sergeyyaniuk.testity.firebase;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Authentication implements AuthenticationContract {

    private Application mApplication;
    private FirebaseAuth mAuth;

    @Inject
    public Authentication(Application application){
        this.mApplication = application;
        mAuth = FirebaseAuth.getInstance();
    }



}
