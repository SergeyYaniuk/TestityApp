package com.sergeyyaniuk.testity.ui.create;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Question;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private ArrayList<Question> mQuestions;
    private QuestionClickListener mQuestionClickListener;

    public interface QuestionClickListener{
        void onQuestionClick(int position);
    }

    public QuestionsAdapter(ArrayList<Question> questions, QuestionClickListener questionClickListener){
        this.mQuestions = questions;
        this.mQuestionClickListener = questionClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_question, parent, false);
        return new ViewHolder(itemView);
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

    public ArrayList<Question> getQuestions(){
        return mQuestions;
    }

    public void setQuestions(ArrayList<Question> questions){
        mQuestions = questions;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.question_title)
        TextView mTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mQuestionClickListener.onQuestionClick(position);
        }
    }
}
