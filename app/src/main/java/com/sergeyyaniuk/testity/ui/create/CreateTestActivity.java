package com.sergeyyaniuk.testity.ui.create;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.di.module.CreateTestActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTestActivity extends BaseActivity implements NotCompletedTestDialog.NotCompletedTestListener,
        CreateTestFragment.CreateTestListener, QuestionsListFragment.QuestionsListListener,
        AddEditQuestionFragment.AddEditQuestionListener {

    public static final String TAG = "MyLog";

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
        mPresenter.onCreate();
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

    private void showCreateTestFragment(){
        mCreateTestFragment = new CreateTestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, mCreateTestFragment);
        transaction.commit();
    }

    //onClick "continue" in NotCompletedTestDialog
    @Override
    public void onContinueEditTest() {
        Bundle arguments = new Bundle();
        arguments.putLong(TEST_ID, test_id);
        mCreateTestFragment.setArguments(arguments);
        showCreateTestFragment();
    }

    //onClick "create new" in NotCompletedTestDialog
    @Override
    public void onCreateNewTest() {
        isTestFinished = true;
        showCreateTestFragment();
    }

    @Override
    public void onCreateTestCompleted(String title, String category, String language, boolean isOnline,
                                      String description) {
        mPresenter.addTest(title, category, language, isOnline, description); //add test to database and Firebase
        isTestFinished = false;
        displayQuestionsList();
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void displayQuestionsList(){
        QuestionsListFragment questionsList = new QuestionsListFragment();
        Bundle arguments = new Bundle();
        String testId = mPresenter.getCurrentTestId();
        arguments.putLong(TEST_ID, test_id);
        questionsList.setArguments(arguments);
        replaceFragment(questionsList);
    }

    @Override
    public void onAddQuestion() {
        //QuestionList. when press on RecyclerView item
        AddEditQuestionFragment addEditQuestionFragment = new AddEditQuestionFragment();
        replaceFragment(addEditQuestionFragment);
    }

    @Override
    public void onTestCompleted() {
        //QuestionList. when press on done button
        startActivity(new Intent(CreateTestActivity.this, MainActivity.class));
    }

    @Override
    public void onClickQuestion(int position) {

    }

    @Override
    public void onSwipedQuestion(int position) {

    }

    @Override
    public void onAddEditQuestionCompleted() {
        //AddEditQuestionFragment. when press on done button
        QuestionsListFragment questionsList = new QuestionsListFragment();
        replaceFragment(questionsList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
