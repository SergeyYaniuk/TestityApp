package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.LoginModule;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {

    LoginActivity inject(LoginActivity loginActivity);
}
