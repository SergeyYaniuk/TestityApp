package com.sergeyyaniuk.testity;

import android.app.Application;
import android.content.Context;

import com.sergeyyaniuk.testity.di.component.AppComponent;
import com.sergeyyaniuk.testity.di.component.DaggerAppComponent;
import com.sergeyyaniuk.testity.di.module.ApplicationModule;
import com.sergeyyaniuk.testity.di.module.FirebaseModule;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .firebaseModule(new FirebaseModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }
}
