package com.sergeyyaniuk.testity.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.ActivityScope;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ActivityScope
public class LoginActivity extends DaggerAppCompatActivity implements LoginContract.View {

    @Inject
    LoginContract.Presenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hidePregressDialog() {

    }

    @Override
    public void hideKeyboard(View view) {

    }
}
