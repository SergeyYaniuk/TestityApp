package com.sergeyyaniuk.testity.ui.create;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Question;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/** Need to add communication with Database */
public class QuestionsListFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.questionsRecView)
    RecyclerView mRecyclerView;

    @BindView(R.id.questions_list_layout)
    FrameLayout mFrameLayout;

    @BindView(R.id.add_question_button)
    ImageButton mAddQuestionButton;

    ArrayList<Question> mQuestions;

    QuestionsAdapter mQuestionsAdapter;
    QuestionsListListener mListener;

    public interface QuestionsListListener{
        void onQuestionSelected(Uri questionUri);
        void onAddQuestion();
    }

    public QuestionsListFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  //Fragment has menu
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mQuestions = new ArrayList<>();
        updateQuestions();
        enableSwipe();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (QuestionsListListener)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateQuestions(){
        if (mQuestionsAdapter == null){
            mQuestionsAdapter = new QuestionsAdapter(mQuestions);
            mRecyclerView.setAdapter(mQuestionsAdapter);
        } else {
            mQuestionsAdapter.setQuestions(mQuestions);
            mQuestionsAdapter.notifyDataSetChanged();
        }
    }

    private void enableSwipe(){
        QuestionsSwipeCallback questionsSwipeCallback = new QuestionsSwipeCallback(getContext()) {  //or getActivity
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final Question question = mQuestionsAdapter.getQuestions().get(position);
                mQuestionsAdapter.removeQuestion(position);

                Snackbar snackbar = Snackbar.make(mFrameLayout, R.string.item_removed, Snackbar.LENGTH_SHORT);
                snackbar.setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mQuestionsAdapter.restoreQuestion(question, position);
                        mRecyclerView.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(questionsSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
