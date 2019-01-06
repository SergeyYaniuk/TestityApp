package com.sergeyyaniuk.testity.ui.create.questions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.di.module.QuestionsListModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.main.MainActivity;
import com.sergeyyaniuk.testity.ui.tests.myTests.MyTestsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsActivity extends BaseActivity implements QuestionsListFragment.QuestionsListListener,
        DetailQuestionFragment.DetailQuestionListener, DeleteQuestionDialog.DeleteQuestionListener,
        StopAddQuestionsDialog.StopAddQuestionsListener {

    public static final String QUESTION_ID = "question_id";
    public static final String QUES_POSITION = "ques_position";

    @BindView(R.id.questions_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitleTV;

    @Inject
    QuestionsPresenter mPresenter;

    String mTestTitle;
    boolean isTestOnline, isTestCompleted, isDetailFragment;

    DetailQuestionFragment mDetailQuestionFragment;
    QuestionsListFragment mQuestionsListFragment;

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
        mTitleTV.setText(R.string.add_questions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        showQuestionsListFragment();
    }

    public void showQuestionsListFragment(){
        isDetailFragment = false;
        mQuestionsListFragment = new QuestionsListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, mQuestionsListFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
        if (mDetailQuestionFragment != null)
            getSupportFragmentManager().beginTransaction().remove(mDetailQuestionFragment).commit();
    }

    public void showDetailQuestionFragment(){
        isDetailFragment = true;
        mDetailQuestionFragment = new DetailQuestionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mDetailQuestionFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
        if (mQuestionsListFragment != null)
            getSupportFragmentManager().beginTransaction().remove(mQuestionsListFragment).commit();
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
        isDetailFragment = true;
        mDetailQuestionFragment = new DetailQuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putString(QUESTION_ID, questionId);
        mDetailQuestionFragment.setArguments(arguments);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, mDetailQuestionFragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    @Override
    public void onSwipedQuestion(String questionId, int position) {
        DeleteQuestionDialog dialog = new DeleteQuestionDialog();
        Bundle arguments = new Bundle();
        arguments.putString(QUESTION_ID, questionId);
        arguments.putInt(QUES_POSITION, position);
        dialog.setArguments(arguments);
        dialog.show(getSupportFragmentManager(), "dialog_delete_question");
    }

    @Override
    public void onConfirmDelete(String questionId, int position) {
        mPresenter.deleteQuestion(questionId, isTestOnline);
        mPresenter.deleteAnswerList(questionId, isTestOnline);
        mQuestionsListFragment.removeQuestion(position);
    }

    @Override
    public void onCancelDelete() {
        mQuestionsListFragment.notifyAdapterAboutChanges();
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
    public void onBackPressed() {
        if (isDetailFragment){
            showQuestionsListFragment();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            StopAddQuestionsDialog stopAddQuestionsDialog = new StopAddQuestionsDialog();
            stopAddQuestionsDialog.show(fm, "StopAddQuestionsDialog");
        }
    }

    @Override
    public void onStopAddQuestions() {
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
