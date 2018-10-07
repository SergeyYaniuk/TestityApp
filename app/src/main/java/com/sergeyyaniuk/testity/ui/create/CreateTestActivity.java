package com.sergeyyaniuk.testity.ui.create;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sergeyyaniuk.testity.R;

import javax.inject.Inject;

public class CreateTestActivity extends AppCompatActivity {

    @Inject
    CreateTestPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
