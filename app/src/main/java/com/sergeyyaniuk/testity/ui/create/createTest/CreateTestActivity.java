package com.sergeyyaniuk.testity.ui.create.createTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.CreateTestModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.create.questions.QuestionsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTestActivity extends BaseActivity implements CreateTestFragment.CreateTestListener{

    private static final String IS_ONLINE = "is_online";
    private static final String TEST_TITLE = "test_title";

    @Inject
    CreateTestPresenter mPresenter;

    @BindView(R.id.create_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        App.get(this).getAppComponent().create(new CreateTestModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText(R.string.create_test);
    }

    @Override
    public void onCreateTest(String title, String category, String language, boolean isOnline, String description) {
        mPresenter.addTest(title, category, language, isOnline, description);
    }

    public void startQuestionsActivity(boolean isOnline, String title){
        Intent intent = new Intent(CreateTestActivity.this, QuestionsActivity.class);
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
