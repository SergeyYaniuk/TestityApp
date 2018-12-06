package com.sergeyyaniuk.testity.ui.create.questions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.di.module.QuestionsListFragModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class QuestionsListFragment extends Fragment {

    private Unbinder unbinder;

    @Inject
    QuestionsListPresenter mPresenter;

    @BindView(R.id.questionsRecView)
    RecyclerView mRecyclerView;

    @BindView(R.id.add_question_button)
    ImageButton mAddQuestionButton;

    @BindView(R.id.saveActionButton)
    FloatingActionButton saveTestButton;

    List<Question> mQuestions;

    QuestionsListAdapter mQuestionsAdapter;
    QuestionsListListener mListener;

    public interface QuestionsListListener{
        void onAddNewQuestion();
        void onTestCompleted();
        void onClickQuestion(String questionId);
        void onSwipedQuestion(String questionId);
    }

    public QuestionsListFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_questions_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent().create(new QuestionsListFragModule(this)).inject(this);
        mPresenter.onCreate();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mQuestions = new ArrayList<>();
        mQuestionsAdapter = new QuestionsListAdapter(mQuestions, questionClickListener);
        mRecyclerView.setAdapter(mQuestionsAdapter);
        enableSwipe();
        mPresenter.loadQuestions();
        return view;
    }

    public void updateList(List<Question> questions){
        mQuestionsAdapter.updateData(questions);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (QuestionsListFragment.QuestionsListListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.saveActionButton)
    public void saveTest(){
        mListener.onTestCompleted();
    }

    @OnClick(R.id.add_question_button)
    public void addQuestion(){
        mListener.onAddNewQuestion();
    }

    QuestionsListAdapter.QuestionClickListener questionClickListener = new QuestionsListAdapter.QuestionClickListener() {
        @Override
        public void onQuestionClick(String questionId) {
            mListener.onClickQuestion(questionId);
        }
    };

    private void enableSwipe(){
        QuestionsSwipeCallback questionsSwipeCallback = new QuestionsSwipeCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                String questionId = mQuestionsAdapter.getItem(position).getId();
                mListener.onSwipedQuestion(questionId);
                mQuestionsAdapter.removeQuestion(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(questionsSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.onDestroy();
    }
}
