package com.sergeyyaniuk.testity.ui.tests.myTests;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;

import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder> {

    private List<Test> mTests;
    private TestClickListener mListener;

    public interface TestClickListener{
        void onTestClick(int position);
    }

    public TestListAdapter(List<Test> tests, TestClickListener listener) {
        this.mTests = tests;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_test, viewGroup, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTitle.setText(mTests.get(i).getTitle());
        viewHolder.mCategory.setText(mTests.get(i).getCategory());
        viewHolder.mLanguage.setText(mTests.get(i).getLanguage());
        viewHolder.mNumberOfQuestions.setText(String.valueOf(mTests.get(i).getNumberOfQuestions()));
    }

    @Override
    public int getItemCount() {
        return mTests.size();
    }

    public void removeTest(int position){
        mTests.remove(position);
        notifyItemRemoved(position);
    }

    public void updateData(List<Test> tests){
        mTests.clear();
        mTests.addAll(tests);
        notifyDataSetChanged();
    }

    public Test getItem(int position){
        return mTests.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTitle, mCategory, mLanguage, mNumberOfQuestions;

        TestClickListener mListener;

        public ViewHolder(@NonNull View itemView, TestClickListener listener) {
            super(itemView);
            this.mListener = listener;
            mTitle = itemView.findViewById(R.id.test_title_tv);
            mCategory = itemView.findViewById(R.id.category_tv);
            mLanguage = itemView.findViewById(R.id.language_tv);
            mNumberOfQuestions = itemView.findViewById(R.id.num_of_ques_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onTestClick(position);
        }
    }
}
