package com.sergeyyaniuk.testity.ui.create;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Question;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Question> mQuestions;
    private QuestionClickListener mQuestionClickListener;

    public interface QuestionClickListener{
        void onQuestionClick(String questionId);
    }

    public QuestionsAdapter(List<Question> questions, QuestionClickListener questionClickListener){
        this.mQuestions = questions;
        this.mQuestionClickListener = questionClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_question, parent, false);
        return new ViewHolder(itemView, mQuestionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    public Question getItem(int position) {
        return mQuestions.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTitle;

        private QuestionClickListener mQuestionClickListener;

        public ViewHolder(@NonNull View itemView, QuestionClickListener listener) {
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
