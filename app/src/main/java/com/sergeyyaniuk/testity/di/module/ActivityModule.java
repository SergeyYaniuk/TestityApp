package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity loginActivity();

}
