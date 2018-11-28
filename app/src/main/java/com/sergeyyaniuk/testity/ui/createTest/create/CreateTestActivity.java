package com.sergeyyaniuk.testity.ui.createTest.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.CreateTestModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.createTest.questions.QuestionsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTestActivity extends BaseActivity implements CreateTestFragment.CreateTestListener{

    private static final String TEST_ID = "test_id";
    private static final String IS_ONLINE = "is_online";
    private static final String TEST_TITLE = "test_title";

    @Inject
    CreateTestPresenter mPresenter;

    @BindView(R.id.create_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test2);
        App.get(this).getAppComponent().create(new CreateTestModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.create_test);
    }

    @Override
    public void onCreateTest(String title, String category, String language, boolean isOnline, String description) {
        String testId = mPresenter.addTest(title, category, language, isOnline, description);
        Intent intent = new Intent(CreateTestActivity.this, QuestionsActivity.class);
        intent.putExtra(TEST_ID, testId);
        intent.putExtra(IS_ONLINE, isOnline);
        intent.putExtra(TEST_TITLE, title);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
