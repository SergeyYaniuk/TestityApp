package com.sergeyyaniuk.testity.ui.find.findList.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Test;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class TestAdapter extends FirestoreAdapter<TestAdapter.ViewHolder> {

    public interface OnTestSelectedListener {
        void onTestSelected(DocumentSnapshot test);
    }

    private OnTestSelectedListener mListener;

    public TestAdapter(Query query, OnTestSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(inflater.inflate(R.layout.list_item_find_test, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(getSnapshot(i), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.find_list_item)
        ViewGroup mLayout;

        @BindView(R.id.test_name_tv)
        TextView mTestName;

        @BindView(R.id.author_tv)
        TextView mAuthor;

        @BindView(R.id.test_rating)
        MaterialRatingBar mRatingBar;

        @BindView(R.id.num_ratings_tv)
        TextView mRatingNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnTestSelectedListener listener) {

            Test test = snapshot.toObject(Test.class);

            mTestName.setText(test.getTitle());
            mRatingNumber.setText(String.valueOf(test.getNumRatings()));
            mAuthor.setText(test.getUserName());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onTestSelected(snapshot);
                    }
                }
            });
        }
    }
}
