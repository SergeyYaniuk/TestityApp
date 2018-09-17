package com.sergeyyaniuk.testity;

import android.app.Application;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class App extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
