package com.sergeyyaniuk.testity.ui.create;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTestActivity extends BaseActivity implements NotCompletedTestDialog.NotCompletedTestListener,
        CreateTestFragment.CreateTestListener {

    @BindView(R.id.create_toolbar)
    Toolbar mToolbar;

    @Inject
    CreateTestPresenter mPresenter;

    public static final String TEST_STATUS = "test_status";
    public static final String TEST_ID = "test_id";
    private boolean isTestFinished = true;
    private long test_id;

    CreateTestFragment mCreateTestFragment;

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
        checkTestStatus();
    }

    private void checkTestStatus(){
        if (isTestFinished){
            mCreateTestFragment = new CreateTestFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, mCreateTestFragment);
            transaction.commit();

        } else{
            //Show NotCompletedTestDialog
            NotCompletedTestDialog notCompletedTestDialog = new NotCompletedTestDialog();
            notCompletedTestDialog.show(getSupportFragmentManager(), "dialog_not_completed_test");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TEST_STATUS, isTestFinished);
        outState.putLong(TEST_ID, test_id);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isTestFinished = savedInstanceState.getBoolean(TEST_STATUS);
        test_id = savedInstanceState.getLong(TEST_ID);
    }

    //onClick "continue" in NotCompletedTestDialog
    @Override
    public void onContinueEditTest() {
        mPresenter.loadTest(test_id); //need to add implementation, load to create test fragment
    }

    //onClick "create new" in NotCompletedTestDialog
    @Override
    public void onCreateNewTest() {
        isTestFinished = true;
        mCreateTestFragment = new CreateTestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, mCreateTestFragment);
        transaction.commit();
    }

    @Override
    public void onCreateTestCompleted(String title, String category, String language, boolean isOnline,
                                      String description) {
        //need to add implementation
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
