package com.sergeyyaniuk.testity.ui.createTest.questions;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Question;

import java.util.List;

public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.ViewHolder> {

    private static final String TAG = "MyLog";

    private List<Question> mQuestions;
    private QuestionsListAdapter.QuestionClickListener mQuestionClickListener;

    public interface QuestionClickListener{
        void onQuestionClick(String questionId);
    }

    public QuestionsListAdapter(List<Question> questions, QuestionsListAdapter.QuestionClickListener questionClickListener){
        this.mQuestions = questions;
        this.mQuestionClickListener = questionClickListener;
    }

    @NonNull
    @Override
    public QuestionsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_question, parent, false);
        return new QuestionsListAdapter.ViewHolder(itemView, mQuestionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsListAdapter.ViewHolder holder, int position) {
        holder.mTitle.setText(mQuestions.get(position).getQuestionText());
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public void removeQuestion(int position){
        mQuestions.remove(position);
        notifyItemRemoved(position);
    }

    public void updateData(List<Question> questions){
        Log.d(TAG, "updateData: questionsList adapter");
        mQuestions.clear();
        mQuestions.addAll(questions);
        notifyDataSetChanged();
    }

    public Question getItem(int position) {
        return mQuestions.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTitle;

        private QuestionsListAdapter.QuestionClickListener mQuestionClickListener;

        public ViewHolder(@NonNull View itemView, QuestionsListAdapter.QuestionClickListener listener) {
            super(itemView);
            mQuestionClickListener = listener;
            mTitle = itemView.findViewById(R.id.question_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String questionId = getItem(getAdapterPosition()).getId();
            mQuestionClickListener.onQuestionClick(questionId);
        }
    }
}
