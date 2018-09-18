package com.sergeyyaniuk.testity.di.module;

import android.support.v7.app.AlertDialog;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;
import com.sergeyyaniuk.testity.ui.login.LoginContract;
import com.sergeyyaniuk.testity.ui.login.LoginPresenter;

import dagger.Binds;
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
    public LoginContract.Presenter provideLoginPresenter(LoginPresenter presenter){
        return presenter;
    }
}
