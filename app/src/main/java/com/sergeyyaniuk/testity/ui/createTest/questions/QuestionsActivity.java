package com.sergeyyaniuk.testity.ui.createTest.questions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.di.module.QuestionsListModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsActivity extends BaseActivity implements QuestionsListFragment.QuestionsListListener,
        DetailQuestionFragment.DetailQuestionListener {

    private static final String TAG = "MyLog";
    public static final String QUESTION_ID = "question_id";

    @BindView(R.id.create_toolbar)
    Toolbar mToolbar;

    @Inject
    QuestionsPresenter mPresenter;

    String mTestId, mTestTitle;
    boolean isTestOnline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Questions Activity");
        setContentView(R.layout.activity_questions);
        App.get(this).getAppComponent().create(new QuestionsListModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        //get data from intent
        mTestId = getIntent().getStringExtra("test_id");
        mTestTitle = getIntent().getStringExtra("test_title");
        isTestOnline = getIntent().getBooleanExtra("is_online", false);
        //setup actionbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTestTitle);
        showQuestionsListFragment();
    }

    public void showQuestionsListFragment(){
        Log.d(TAG, "showQuestionsListFragment: Questions Activity");
        QuestionsListFragment questionsListFragment = new QuestionsListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, questionsListFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    private void showDetailQuestionFragment(){
        Log.d(TAG, "showDetailQuestionFragment: Questions activity");
        DetailQuestionFragment detailQuestionFragment = new DetailQuestionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, detailQuestionFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    @Override
    public void onAddNewQuestion() {
        showDetailQuestionFragment();
    }

    @Override
    public void onTestCompleted() {

    }

    @Override
    public void onClickQuestion(String questionId) {
        DetailQuestionFragment detailQuestionFragment = new DetailQuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putString(QUESTION_ID, questionId);
        detailQuestionFragment.setArguments(arguments);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, detailQuestionFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    @Override
    public void onSwipedQuestion(String questionId) {

    }

    @Override
    public void onAddEditQuestionCompleted(Question question, List<Answer> answers, boolean isUpdating) {
        Log.d(TAG, "onAddEditQuestionCompleted: start");
        if (isUpdating){
            Log.d(TAG, "onAddEditQuestionCompleted: updating - start = Questions activity");
            mPresenter.updateQuestion(question, isTestOnline);
            mPresenter.updateAnswerList(answers, isTestOnline);
        } else {
            Log.d(TAG, "onAddEditQuestionCompleted: create - start = Questions activity");
            mPresenter.saveQuestion(question, isTestOnline);
            mPresenter.saveAnswerList(answers, isTestOnline);
        }
        showQuestionsListFragment();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
