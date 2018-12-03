package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Question;

import java.util.List;

public class EditListAdapter extends RecyclerView.Adapter<EditListAdapter.ViewHolder> {

    private List<Question> mQuestions;
    private QuestionClickListener mQuestionClickListener;


    public interface QuestionClickListener{
        void onQuestionClick(String questionId);
    }

    public EditListAdapter(List<Question> questions, QuestionClickListener questionClickListener) {
        this.mQuestions = questions;
        this.mQuestionClickListener = questionClickListener;
    }

    @NonNull
    @Override
    public EditListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_edit_question, viewGroup, false);
        return new EditListAdapter.ViewHolder(itemView, mQuestionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EditListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mTitle.setText(mQuestions.get(i).getQuestionText());
        String questionNumber = Integer.toString(++i) + ".";
        viewHolder.mQuestionNumber.setText(questionNumber);
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
        mQuestions.clear();
        mQuestions.addAll(questions);
        notifyDataSetChanged();
    }

    public Question getItem(int position) {
        return mQuestions.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTitle;
        TextView mQuestionNumber;

        private EditListAdapter.QuestionClickListener mQuestionClickListener;

        public ViewHolder(@NonNull View itemView, EditListAdapter.QuestionClickListener listener) {
            super(itemView);
            mQuestionClickListener = listener;
            mTitle = itemView.findViewById(R.id.question_title);
            mQuestionNumber = itemView.findViewById(R.id.number_of_question);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String questionId = getItem(getAdapterPosition()).getId();
            mQuestionClickListener.onQuestionClick(questionId);
        }
    }
}
