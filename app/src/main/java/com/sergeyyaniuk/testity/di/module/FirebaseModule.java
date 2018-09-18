package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.firebase.AuthenticationContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    @Singleton
    public AuthenticationContract provideAuthentication(Authentication authentication){
        return authentication;
    }
}
