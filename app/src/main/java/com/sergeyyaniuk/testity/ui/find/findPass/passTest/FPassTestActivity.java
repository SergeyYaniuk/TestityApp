package com.sergeyyaniuk.testity.ui.find.findPass.passTest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.di.module.FPassTestModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.find.findPass.endTest.FEndTestActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FPassTestActivity extends BaseActivity implements RateTestDialog.RateTestListener {

    private static final String KEY_INDEX = "index";
    private static final String KEY_NUMBER_OF_CORRECT = "number_of_correct";
    private static final String KEY_SCORE = "score";

    @Inject
    FPassTestPresenter mPresenter;

    FAnswerListAdapter mAdapter;

    @BindView(R.id.pass_test_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.test_progress)
    ProgressBar mTestProgress;

    @BindView(R.id.number_of_ques_tv)
    TextView mNumberOfQuesTV;

    @BindView(R.id.answers_rec_view)
    RecyclerView mAnswersRecView;

    @BindView(R.id.question_tv)
    TextView mQuestionTV;

    @BindView(R.id.next_question_btn)
    Button mNextQuesButton;

    @BindView(R.id.loading_layout)
    LinearLayout mLoadingLayout;

    String mTestId, mApplicantName, mTestTitle;
    private int mCurrentIndex;
    private int mCorrectAnswers;
    private int mNumberOfTries;
    private int mNumberOfChance;
    List<Question> mQuestionList;
    List<Answer> mAnswerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpass_test);
        App.get(this).getAppComponent().create(new FPassTestModule(this))
                .inject(this);  //inject presenter
        mPresenter.onCreate();  //create CompositeDisposable
        ButterKnife.bind(this);
        //get data from intent
        mTestId = getIntent().getStringExtra("test_id");
        mApplicantName = getIntent().getStringExtra("name");
        mTestTitle = getIntent().getStringExtra("test_title");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mPresenter.cleanTotalCorr();  //clean number of total correct answers
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCorrectAnswers = savedInstanceState.getInt(KEY_NUMBER_OF_CORRECT, 0);
        }
        mPresenter.loadQuestions(mTestId); //load data from database
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_NUMBER_OF_CORRECT, mCorrectAnswers);
    }

    //invoke from presenter
    public void setQuestionList(List<Question> questions){
        mQuestionList = new ArrayList<>(questions);
        updateData();
    }

    public void updateData(){
        updateQuestion();  //update question TextView
        loadAnswers();  //load answerList from database by questionId
        setTestProgress(); //set current test progress
        mCurrentIndex++;
    }

    private void updateQuestion(){
        String questionText = mQuestionList.get(mCurrentIndex).getQuestionText();
        mQuestionTV.setText(questionText);
    }

    private void loadAnswers(){
        showProgressBar();
        String questionId = mQuestionList.get(mCurrentIndex).getId();
        mPresenter.loadAnswers(questionId);
    }

    private void setTestProgress(){
        int progress = (mCurrentIndex * 100) / mQuestionList.size();
        mTestProgress.setProgress(progress);
        int secProgress = ((mCurrentIndex + 1) * 100) / mQuestionList.size();
        mTestProgress.setSecondaryProgress(secProgress);
        String numOfQues = String.valueOf(mCurrentIndex + 1) + " " + getString(R.string.of) + " " +
                String.valueOf(mQuestionList.size());
        mNumberOfQuesTV.setText(numOfQues);
    }

    //invoke from presenter after answers loaded
    public void updateAnswers(List<Answer> answers){
        mAnswerList = new ArrayList<>(answers);
        mAnswersRecView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FAnswerListAdapter(mAnswerList, answerClickListener);
        mAnswersRecView.setAdapter(mAdapter);
        mNumberOfTries = 0;
        mNumberOfChance = 0;
        updateNumOfChance(answers);
    }

    private void updateNumOfChance(List<Answer> answers){
        for(Answer answer : answers){
            if (answer.isCorrect()){
                mNumberOfChance = mNumberOfChance + 1;
            }
        }
    }

    FAnswerListAdapter.AnswerClickListener answerClickListener = new FAnswerListAdapter.AnswerClickListener() {
        @Override
        public void onAnswerClick(boolean isCorrect) {
            mNumberOfTries = mNumberOfTries + 1;
            if (mNumberOfTries <= mNumberOfChance && isCorrect){
                mCorrectAnswers = mCorrectAnswers + 1;
            }
        }
    };

    @OnClick(R.id.next_question_btn)
    public void onNextQuestion(){
        if (mCurrentIndex < mQuestionList.size()){
            updateData();
        } else {
            showRateTestDialog();
        }
    }

    private void showRateTestDialog(){
        FragmentManager fm = getSupportFragmentManager();
        RateTestDialog rateTestDialog = new RateTestDialog();
        rateTestDialog.show(fm, "rateTestDialog");
    }

    public void hideProgressBar(){
        mLoadingLayout.setVisibility(View.GONE);
    }

    private void showProgressBar(){
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    private void saveResults(){
        int totalCorrectAnswers = mPresenter.getNumberOfCorrect();
        double score = (mCorrectAnswers * 100) / totalCorrectAnswers;
        mPresenter.saveResult(mTestId, mApplicantName, mTestTitle, score);
        showResults(score);
    }

    private void showResults(double score){
        Intent intent = new Intent(FPassTestActivity.this, FEndTestActivity.class);
        intent.putExtra(KEY_SCORE, score);
        startActivity(intent);
    }

    @Override
    public void onRateTest(double rating) {
        mPresenter.addRating(mTestId, rating);
        saveResults();
    }

    @Override
    public void onCancelRating() {
        saveResults();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
