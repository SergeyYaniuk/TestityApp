package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.EditTestModule;
import com.sergeyyaniuk.testity.di.module.QuestionsListModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTestActivity extends AppCompatActivity {

    @Inject
    EditPresenter mPresenter;

    @BindView(R.id.edit_test_toolbar)
    Toolbar mToolbar;

    private String mTestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);
        App.get(this).getAppComponent().create(new EditTestModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();

        mTestId = getIntent().getStringExtra("test_id");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
