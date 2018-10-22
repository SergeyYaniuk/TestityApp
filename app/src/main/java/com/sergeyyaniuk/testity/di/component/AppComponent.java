package com.sergeyyaniuk.testity.di.component;


import com.sergeyyaniuk.testity.di.module.ApplicationModule;
import com.sergeyyaniuk.testity.di.module.CreateTestActivityModule;
import com.sergeyyaniuk.testity.di.module.FirebaseModule;
import com.sergeyyaniuk.testity.di.module.LoginActivityModule;
import com.sergeyyaniuk.testity.di.module.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, FirebaseModule.class})
public interface AppComponent {

    LoginActivityComponent createLoginComponent(LoginActivityModule loginActivityModule);
    MainActivityComponent createMainComponent(MainActivityModule mainActivityModule);
    CreateTestActivityComponent createCreateTestComponent(CreateTestActivityModule createTestActivityModule);
}
