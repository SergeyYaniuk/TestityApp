package com.sergeyyaniuk.testity.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.component.LoginActivityComponent;
import com.sergeyyaniuk.testity.di.module.LoginActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class LoginActivity extends BaseActivity {

    @Inject
    LoginPresenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.get(this).getAppComponent().createLoginComponent(new LoginActivityModule(this)).inject(this);
    }
}
