package com.sergeyyaniuk.testity.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.MainActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ExitDialogFragment.ExitDialogListener{


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_icon)
    ImageView mUserPhoto;

    @Inject
    MainPresenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Dagger injection
        App.get(this).getAppComponent().createMainComponent(new MainActivityModule(this)).inject(this);
        //ButterKnife
        ButterKnife.bind(this);
        //set toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);  //remove label from toolbar
        //set user name and photo in toolbar
        mPresenter.setUserName(mUserName);
        mPresenter.setUserPhoto(mUserPhoto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                showSignOutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSignOutDialog(){
        FragmentManager fm = getSupportFragmentManager();
        ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
        exitDialogFragment.show(fm, "ExitDialogFragment");
    }

    //reaction on positive button in ExitDialogFragment
    @Override
    public void exit() {
        mPresenter.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
