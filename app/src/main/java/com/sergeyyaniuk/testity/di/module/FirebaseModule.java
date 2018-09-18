package com.sergeyyaniuk.testity.di.module;

import android.app.Application;

import com.sergeyyaniuk.testity.firebase.Authentication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    @Singleton
    public Authentication provideAuthentication(Application application){
        return new Authentication(application);
    }
}
