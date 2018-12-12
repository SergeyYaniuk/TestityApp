package com.sergeyyaniuk.testity.ui.find.findList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.FindListModule;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class FindListActivity extends AppCompatActivity {

    @Inject
    FindListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_list);
        App.get(this).getAppComponent().create(new FindListModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
