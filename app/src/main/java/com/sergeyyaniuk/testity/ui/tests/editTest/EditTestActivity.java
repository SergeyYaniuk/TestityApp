package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.di.module.EditTestModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTestActivity extends BaseActivity implements EditTestFragment.EditTestFragListener{

    public static final String TEST_ID = "test_id";

    @Inject
    EditPresenter mPresenter;

    @BindView(R.id.edit_test_toolbar)
    Toolbar mToolbar;

    private String mTestId;
    private boolean isTestOnline;

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
        showEditTestFragment();
    }

    public void showEditTestFragment(){
        EditTestFragment editTestFragment = new EditTestFragment();
        Bundle arguments = new Bundle();
        arguments.putString(TEST_ID, mTestId);
        editTestFragment.setArguments(arguments);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, editTestFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    public void showEditListFragment(){
        EditListFragment editListFragment = new EditListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(TEST_ID, mTestId);
        editListFragment.setArguments(arguments);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, editListFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    @Override
    public void onEditTestFragCompleted(Test test) {
        isTestOnline = test.isOnline();
        mPresenter.updateTest(test);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
