package com.sergeyyaniuk.testity.ui.tests.myTests;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        viewHolder.mTestName.setText(mTests.get(i).getTitle());
        viewHolder.mCategory.setText(mTests.get(i).getCategory());
        viewHolder.mLanguage.setText(mTests.get(i).getLanguage());
        boolean isOnline = mTests.get(i).isOnline();
        Drawable whiteCircle = viewHolder.itemView.getContext().getResources().getDrawable(R.drawable.white_circle);
        Drawable greenCircle = viewHolder.itemView.getContext().getResources().getDrawable(R.drawable.green_circle);
        if (isOnline){
            viewHolder.isOnline.setText(R.string.online);
            viewHolder.isOnlineImage.setImageDrawable(greenCircle);
        } else {
            viewHolder.isOnline.setText(R.string.offline);
            viewHolder.isOnlineImage.setImageDrawable(whiteCircle);
        }
    }

    @Override
    public int getItemCount() {
        return mTests.size();
    }

    public void removeTest(int position){
        mTests.remove(position);
        notifyItemRemoved(position);
    }

    public Test getItem(int position){
        return mTests.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTestName, mCategory, mLanguage, isOnline;
        ImageView isOnlineImage;

        TestClickListener mListener;

        public ViewHolder(@NonNull View itemView, TestClickListener listener) {
            super(itemView);
            this.mListener = listener;
            mTestName = itemView.findViewById(R.id.test_name_tv);
            mCategory = itemView.findViewById(R.id.category_tv);
            mLanguage = itemView.findViewById(R.id.language_tv);
            isOnline = itemView.findViewById(R.id.is_online_tv);
            isOnlineImage = itemView.findViewById(R.id.is_online_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onTestClick(position);
        }
    }
}
