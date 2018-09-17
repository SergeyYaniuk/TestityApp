package com.sergeyyaniuk.testity.di.component;


import android.app.Application;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.di.module.ActivityModule;
import com.sergeyyaniuk.testity.di.module.ApplicationModule;
import com.sergeyyaniuk.testity.di.module.FirebaseModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ApplicationModule.class, ActivityModule.class, FirebaseModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();
    }
}
