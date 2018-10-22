package com.sergeyyaniuk.testity.ui.create;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.CreateTestActivityModule;
import com.sergeyyaniuk.testity.di.module.LoginActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTestActivity extends BaseActivity {

    @BindView(R.id.create_toolbar)
    Toolbar mToolbar;

    @Inject
    CreateTestPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        App.get(this).getAppComponent().createCreateTestComponent(new CreateTestActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.create_test);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
