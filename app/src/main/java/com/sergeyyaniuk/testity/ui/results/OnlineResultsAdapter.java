package com.sergeyyaniuk.testity.ui.results;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.firebase.FirestoreAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlineResultsAdapter extends RecyclerView.Adapter<OnlineResultsAdapter.ViewHolder> {

    private List<Result> mResult;

    public OnlineResultsAdapter(List<Result> results) {
        this.mResult = results;
    }

    @NonNull
    @Override
    public OnlineResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_online_results, viewGroup, false);
        return new OnlineResultsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineResultsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mApplicantName.setText(mResult.get(i).getApplicantName());
        viewHolder.mTestName.setText(mResult.get(i).getTestId());
        viewHolder.mResultTV.setText(String.valueOf(mResult.get(i).getScore()));
        viewHolder.mResultProgress.setProgress((int)mResult.get(i).getScore());
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    public void removeResult(int position){
        mResult.remove(position);
        notifyItemRemoved(position);
    }

    public Result getItem(int position) {
        return mResult.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.applicant_name_tv)
        TextView mApplicantName;

        @BindView(R.id.test_name_tv)
        TextView mTestName;

        @BindView(R.id.date_tv)
        TextView mDate;

        @BindView(R.id.result_progress_bar)
        ProgressBar mResultProgress;

        @BindView(R.id.result_tv)
        TextView mResultTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
