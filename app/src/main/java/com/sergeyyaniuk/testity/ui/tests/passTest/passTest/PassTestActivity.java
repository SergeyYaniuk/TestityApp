package com.sergeyyaniuk.testity.ui.tests.passTest.passTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.di.module.MyTestsModule;
import com.sergeyyaniuk.testity.di.module.PassTestModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassTestActivity extends AppCompatActivity {

    @Inject
    PassTestPresenter mPresenter;

    AnswerListAdapter mAdapter;

    @BindView(R.id.answers_rec_view)
    RecyclerView mAnswersRecView;

    @BindView(R.id.question_tv)
    TextView mQuestionTV;

    @BindView(R.id.next_question_btn)
    Button mNextQuesButton;

    String mTestId, mApplicantName;

    List<Answer> mAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_test);
        App.get(this).getAppComponent().create(new PassTestModule(this)).inject(this);
        mPresenter.onCreate();
        ButterKnife.bind(this);
        //get data from intent
        mTestId = getIntent().getStringExtra("test_id");
        mApplicantName = getIntent().getStringExtra("name");
        //setup Answers Recycler View
        mAnswersRecView.setLayoutManager(new LinearLayoutManager(this));
        mAnswers = new ArrayList<>();
        mAdapter = new AnswerListAdapter(mAnswers, answerClickListener);
        mAnswersRecView.setAdapter(mAdapter);
    }

    AnswerListAdapter.AnswerClickListener answerClickListener = new AnswerListAdapter.AnswerClickListener() {
        @Override
        public void onAnswerClick(int position) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
