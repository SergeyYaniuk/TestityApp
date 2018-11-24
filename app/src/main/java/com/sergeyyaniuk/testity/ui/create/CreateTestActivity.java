package com.sergeyyaniuk.testity.ui.create;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
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
    public static final String TEST_ONLINE = "test_online";
    public static final String TEST_ID = "test_id";
    public static final String QUESTION_ID = "question_id";
    public static final String IS_UPDATING = "is_updating";
    private boolean isContinueEditing;
    private boolean isTestOnline;
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TEST_STATUS, isContinueEditing);
        outState.putString(TEST_ID, mTestId);
        outState.putBoolean(TEST_ONLINE, isTestOnline);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isContinueEditing = savedInstanceState.getBoolean(TEST_STATUS);
        mTestId = savedInstanceState.getString(TEST_ID);
        isTestOnline = savedInstanceState.getBoolean(TEST_ONLINE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTestStatus();
    }

    private void checkTestStatus(){
        if (isContinueEditing){
            NotCompletedTestDialog notCompletedTestDialog = new NotCompletedTestDialog();
            notCompletedTestDialog.show(getSupportFragmentManager(), "dialog_not_completed_test");
        } else{
            showCreateTestFragment();
        }
    }

    //onClick "create new" in NotCompletedTestDialog
    @Override
    public void onStartNewTest() {
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
        mTestId = mPresenter.getCurrentTestId();
        isContinueEditing = true;
        isTestOnline = isOnline;
        showQuestionsListFragment();
    }

    @Override
    public void onAddNewQuestion() {
        @SuppressLint("SimpleDateFormat")
        String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String questionId = mTestId + currentDateAndTime;
        showAddEditFragment(questionId, false);
    }

    @Override
    public void onClickQuestion(String questionId) {
        showAddEditFragment(questionId, true);
    }

    @Override
    public void onSwipedQuestion(String questionId) {
        mPresenter.updateTestAfterSwipe(questionId, mTestId, isTestOnline);
        mPresenter.deleteQuestion(questionId, isTestOnline);
        mPresenter.deleteAnswerList(questionId, isTestOnline);
    }

    //AddEditQuestionFragment. when press on done button
    @Override
    public void onAddEditQuestionCompleted(Question question, List<Answer> answers, boolean isUpdating) {
        mPresenter.updateTestAfterEditing(mTestId, question, isUpdating, answers, isTestOnline);
        if (isUpdating){
            mPresenter.updateQuestion(question, isTestOnline);
            mPresenter.updateAnswerList(answers, isTestOnline);
        } else {
            mPresenter.saveQuestion(question, isTestOnline);
            mPresenter.saveAnswerList(answers, isTestOnline);
        }
        showQuestionsListFragment();
    }

    @Override
    public void onTestCompleted() {
        //QuestionList. when press on done button
        isContinueEditing = false;
        startActivity(new Intent(CreateTestActivity.this, MainActivity.class));
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showCreateTestFragment(){
        mCreateTestFragment = new CreateTestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, mCreateTestFragment);
        transaction.commit();
    }

    private void showQuestionsListFragment(){
        QuestionsListFragment questionsList = new QuestionsListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(TEST_ID, mTestId);
        questionsList.setArguments(arguments);
        replaceFragment(questionsList);
    }

    private void showAddEditFragment(String questionId, boolean isUpdating){
        AddEditQuestionFragment addEditQuestionFragment = new AddEditQuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putString(QUESTION_ID, questionId);
        arguments.putString(TEST_ID, mTestId);
        arguments.putBoolean(IS_UPDATING, isUpdating);
        addEditQuestionFragment.setArguments(arguments);
        replaceFragment(addEditQuestionFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
