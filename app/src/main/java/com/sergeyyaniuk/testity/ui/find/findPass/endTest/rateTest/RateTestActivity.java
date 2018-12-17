package com.sergeyyaniuk.testity.ui.find.findPass.endTest.rateTest;

import android.os.Bundle;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.RateTestModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class RateTestActivity extends BaseActivity {

    @Inject
    RateTestPresenter mPresenter;

    private String mTestId;
    private double mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_test);
        App.get(this).getAppComponent().create(new RateTestModule(this))
                .inject(this);  //inject presenter
        mPresenter.onCreate();  //create CompositeDisposable
        ButterKnife.bind(this);
        mTestId = getIntent().getStringExtra("test_id");
        mScore = getIntent().getDoubleExtra("score", 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
