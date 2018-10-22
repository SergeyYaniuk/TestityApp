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

    public QuestionsAdapter(ArrayList<Question> questions){
        this.mQuestions = questions;
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

    //need to be changed
    public void removeQuestion(int position){
        mQuestions.remove(position);
        notifyItemRemoved(position);
    }

    //need to be changed
    public void restoreQuestion(Question question, int position){
        mQuestions.add(position, question);
        notifyItemInserted(position);
    }

    //need to be changed
    public ArrayList<Question> getQuestions(){
        return mQuestions;
    }

    //need to be changed
    public void setQuestions(ArrayList<Question> questions){
        mQuestions = questions;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.question_title)
        TextView mTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
