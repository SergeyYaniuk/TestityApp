package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.di.module.EditTestModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.tests.myTests.MyTestsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTestActivity extends BaseActivity implements EditTestFragment.EditTestFragListener,
        EditListFragment.EditListListener{

    public static final String TEST_ID = "test_id";
    public static final String QUESTION_ID = "question_id";

    @Inject
    EditPresenter mPresenter;

    @BindView(R.id.edit_test_toolbar)
    Toolbar mToolbar;

    private String mTestId;
    boolean isTestOnline, isTestCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);
        App.get(this).getAppComponent().create(new EditTestModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        mTestId = getIntent().getStringExtra("test_id");
        mPresenter.setTestId(mTestId);
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

    //addQuestion button in editListFragment
    @Override
    public void onAddNewQuestion() {
        EditQuestionFragment editQuestionFragment = new EditQuestionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, editQuestionFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    //onCompleted button in EditListFragment
    @Override
    public void onTestCompleted() {
        isTestCompleted = true;
        mPresenter.getNumberOfQuestions(isTestOnline, mTestId);
    }

    //onClick on RecView item in EditListFragment
    @Override
    public void onClickQuestion(String questionId) {
        EditQuestionFragment editQuestionFragment = new EditQuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putString(QUESTION_ID, questionId);
        editQuestionFragment.setArguments(arguments);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, editQuestionFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    //Swipe item of RecView in EditListFragment
    @Override
    public void onSwipedQuestion(String questionId) {
        mPresenter.deleteQuestion(questionId, isTestOnline);
        mPresenter.deleteAnswerList(questionId, isTestOnline);
    }

    public void startMyTestsActivity(){
        startActivity(new Intent(EditTestActivity.this, MyTestsActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
