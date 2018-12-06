package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.di.module.EditListModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditListFragment extends Fragment {

    @Inject
    EditListPresenter mPresenter;

    @BindView(R.id.questionsRecView)
    RecyclerView mRecyclerView;

    List<Question> mQuestions;

    private Unbinder unbinder;
    EditListListener mListener;
    EditListAdapter mQuestionsAdapter;

    String mTestId;

    public interface EditListListener{
        void onAddNewQuestion();
        void onTestCompleted();
        void onClickQuestion(String questionId);
        void onSwipedQuestion(String questionId);
    }

    public EditListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_list, container, false);
        getDataFromArgs();
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent().create(new EditListModule(this))
                .inject(this);
        mPresenter.onCreate();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mQuestions = new ArrayList<>();
        mQuestionsAdapter = new EditListAdapter(mQuestions, questionClickListener);
        mRecyclerView.setAdapter(mQuestionsAdapter);
        enableSwipe();
        mPresenter.loadQuestions(mTestId);
        return view;
    }

    private void getDataFromArgs(){
        Bundle arguments = getArguments();
        if (arguments != null){
            mTestId = arguments.getString(EditTestActivity.TEST_ID);
        }
    }

    public void updateList(List<Question> questions){
        mQuestionsAdapter.updateData(questions);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (EditListListener) context;
    }

    EditListAdapter.QuestionClickListener questionClickListener = new EditListAdapter.QuestionClickListener() {
        @Override
        public void onQuestionClick(String questionId) {
            mListener.onClickQuestion(questionId);
        }
    };

    private void enableSwipe(){
        EditQuesSwipeCallback quesSwipeCallback = new EditQuesSwipeCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                String questionId = mQuestionsAdapter.getItem(position).getId();
                mListener.onSwipedQuestion(questionId);
                mQuestionsAdapter.removeQuestion(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(quesSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @OnClick(R.id.saveActionButton)
    public void saveTest(){
        mListener.onTestCompleted();
    }

    @OnClick(R.id.add_question_button)
    public void addQuestion(){
        mListener.onAddNewQuestion();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.onDestroy();
    }
}
