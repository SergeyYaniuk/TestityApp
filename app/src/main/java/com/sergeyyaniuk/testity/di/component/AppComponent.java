package com.sergeyyaniuk.testity.di.component;


import com.sergeyyaniuk.testity.di.module.ApplicationModule;
import com.sergeyyaniuk.testity.di.module.FirebaseModule;
import com.sergeyyaniuk.testity.di.module.LoginActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, FirebaseModule.class})
public interface AppComponent {

    LoginActivityComponent createLoginComponent(LoginActivityModule loginActivityModule);
}
