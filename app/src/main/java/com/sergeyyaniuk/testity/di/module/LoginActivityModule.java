package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;
import com.sergeyyaniuk.testity.ui.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

    private LoginActivity mActivity;

    public LoginActivityModule(LoginActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    LoginActivity provideLoginActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    LoginPresenter provideLoginPresenter(Authentication authentication){
        return new LoginPresenter(mActivity, authentication);
    }
}