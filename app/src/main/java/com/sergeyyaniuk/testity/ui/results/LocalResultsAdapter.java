package com.sergeyyaniuk.testity.ui.results;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Result;

import java.util.List;

public class LocalResultsAdapter extends RecyclerView.Adapter<LocalResultsAdapter.ViewHolder>{

    private List<Result> mResult;

    public LocalResultsAdapter(List<Result> results) {
        this.mResult = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_local_results, viewGroup, false);
        return new LocalResultsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mApplicantName.setText(mResult.get(i).getApplicantName());
        viewHolder.mTestName.setText(mResult.get(i).getTestId());
        viewHolder.mResult.setText(String.valueOf(mResult.get(i).getScore()));
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

    public void updateData(List<Result> results){
        mResult.clear();
        mResult.addAll(results);
        notifyDataSetChanged();
    }

    public Result getItem(int position) {
        return mResult.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mApplicantName, mTestName, mDate, mResult;
        ProgressBar mResultProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mApplicantName = itemView.findViewById(R.id.applicant_name_tv);
            mTestName = itemView.findViewById(R.id.test_name_tv);
            mDate = itemView.findViewById(R.id.date_tv);
            mResult = itemView.findViewById(R.id.result_tv);
            mResultProgress = itemView.findViewById(R.id.result_progress_bar);
        }
    }
}
