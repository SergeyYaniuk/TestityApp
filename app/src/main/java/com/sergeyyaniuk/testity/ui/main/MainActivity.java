package com.sergeyyaniuk.testity.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.MainActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.user_name)
    TextView mUserName;



    @Inject
    MainPresenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Dagger injection
        App.get(this).getAppComponent().createMainComponent(new MainActivityModule(this)).inject(this);
        //ButterKnife
        ButterKnife.bind(this);
        //set user name in toolbar
        setUserName();
    }

    protected void setUserName(){
        if (mPresenter.provideUserName() != null){
            mUserName.setText(mPresenter.provideUserName());
        } else {
            mUserName.setText(R.string.anonymous);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
