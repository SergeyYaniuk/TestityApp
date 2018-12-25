package com.sergeyyaniuk.testity.ui.create.questions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.di.module.QuestionsListModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.tests.myTests.MyTestsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsActivity extends BaseActivity implements QuestionsListFragment.QuestionsListListener,
        DetailQuestionFragment.DetailQuestionListener {

    public static final String QUESTION_ID = "question_id";

    @BindView(R.id.questions_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitleTV;

    @Inject
    QuestionsPresenter mPresenter;

    String mTestTitle;
    boolean isTestOnline, isTestCompleted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        App.get(this).getAppComponent().create(new QuestionsListModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        //get data from intent
        mTestTitle = getIntent().getStringExtra("test_title");
        isTestOnline = getIntent().getBooleanExtra("is_online", false);
        //setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitleTV.setText(mTestTitle);
        showQuestionsListFragment();
    }

    public void showQuestionsListFragment(){
        QuestionsListFragment questionsListFragment = new QuestionsListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, questionsListFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    public void showDetailQuestionFragment(){
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
        isTestCompleted = true;
        mPresenter.getNumberOfQuestions(isTestOnline);
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
        mPresenter.deleteQuestion(questionId, isTestOnline);
        mPresenter.deleteAnswerList(questionId, isTestOnline);
    }

    @Override
    public void onAddEditQuestionCompleted(Question question, List<Answer> answers, boolean isUpdating) {
        if (isUpdating){
            mPresenter.updateQuestion(question, isTestOnline);
        } else {
            mPresenter.saveQuestion(question, isTestOnline);
        }
        mPresenter.saveAnswerList(answers, isTestOnline);
    }

    public void startTestsActivity(){
        startActivity(new Intent(QuestionsActivity.this, MyTestsActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isTestCompleted){
            mPresenter.getNumberOfQuestions(isTestOnline);  //get number of questions, number of correct answers and update test
        }
        mPresenter.onDestroy();
    }
}
