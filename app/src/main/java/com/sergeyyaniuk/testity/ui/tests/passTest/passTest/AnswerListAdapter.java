package com.sergeyyaniuk.testity.ui.tests.passTest.passTest;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Answer;

import java.util.List;

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.ViewHolder>{

    private List<Answer> mAnswers;
    private AnswerClickListener mListener;

    public interface AnswerClickListener{
        void onAnswerClick(boolean isCorrect);
    }

    public AnswerListAdapter(List<Answer> answers, AnswerClickListener listener) {
        this.mAnswers = answers;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_answer, viewGroup, false);
        return new AnswerListAdapter.ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mAnswerText.setText(mAnswers.get(i).getAnswerText());
    }

    public Answer getItem(int position){
        return mAnswers.get(position);
    }

    @Override
    public int getItemCount() {
        return mAnswers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        AnswerClickListener mListener;

        TextView mAnswerText;
        LinearLayout mItemLayout;

        public ViewHolder(@NonNull View itemView, AnswerClickListener listener) {
            super(itemView);
            this.mListener = listener;
            mAnswerText = itemView.findViewById(R.id.answer_tv);
            mItemLayout = itemView.findViewById(R.id.item_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemLayout.setBackgroundColor(Color.YELLOW);
            mItemLayout.setEnabled(false);
            boolean isCorrect = getItem(getAdapterPosition()).isCorrect();
            mListener.onAnswerClick(isCorrect);
        }
    }
}
