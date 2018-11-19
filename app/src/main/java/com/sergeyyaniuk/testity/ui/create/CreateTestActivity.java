package com.sergeyyaniuk.testity.ui.create;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.di.module.CreateTestActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public static final String QUESTION_ID = "question_id";
    private boolean isTestFinished = true;
    private String mTestId;

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
            showCreateTestFragment();
        } else{
            //Show NotCompletedTestDialog
            NotCompletedTestDialog notCompletedTestDialog = new NotCompletedTestDialog();
            notCompletedTestDialog.show(getSupportFragmentManager(), "dialog_not_completed_test");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TEST_STATUS, isTestFinished);
        outState.putString(TEST_ID, mTestId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isTestFinished = savedInstanceState.getBoolean(TEST_STATUS);
        mTestId = savedInstanceState.getString(TEST_ID);
    }

    //onClick "create new" in NotCompletedTestDialog
    @Override
    public void onStartNewTest() {
        isTestFinished = true;
        showCreateTestFragment();
    }

    //onClick "continue" in NotCompletedTestDialog
    @Override
    public void onContinueEditTest() {
        Bundle arguments = new Bundle();
        arguments.putString(TEST_ID, mTestId);
        mCreateTestFragment.setArguments(arguments);
        showCreateTestFragment();
    }

    //method create test without questions
    @Override
    public void onCreateTest(String title, String category, String language, boolean isOnline,
                                      String description) {
        mPresenter.addTest(title, category, language, isOnline, description); //add test to database and Firebase
        isTestFinished = false;
        displayQuestionsList();
    }

    @Override
    public void onAddNewQuestion() {
        @SuppressLint("SimpleDateFormat")
        String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String questionId = mTestId + currentDateAndTime;
        showAddEditFragment(questionId);
    }

    @Override
    public void onClickQuestion(String questionId) {
        showAddEditFragment(questionId);
    }

    @Override
    public void onTestCompleted() {
        //QuestionList. when press on done button
        startActivity(new Intent(CreateTestActivity.this, MainActivity.class));
    }

    @Override
    public void onSwipedQuestion(int position) {

    }

    //AddEditQuestionFragment. when press on done button
    @Override
    public void onAddEditQuestionCompleted(Question question, List<Answer> answers) {
        mPresenter.saveQuestion(question);
        mPresenter.saveAnswerList(answers);
        QuestionsListFragment questionsList = new QuestionsListFragment();
        replaceFragment(questionsList);
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
        arguments.putString(TEST_ID, mTestId);
        questionsList.setArguments(arguments);
        replaceFragment(questionsList);
    }

    private void showCreateTestFragment(){
        mCreateTestFragment = new CreateTestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, mCreateTestFragment);
        transaction.commit();
    }

    private void showAddEditFragment(String questionId){
        AddEditQuestionFragment addEditQuestionFragment = new AddEditQuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putString(QUESTION_ID, questionId);
        addEditQuestionFragment.setArguments(arguments);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, addEditQuestionFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
