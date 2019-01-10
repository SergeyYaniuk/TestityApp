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

    private List<Result> mResults;

    public LocalResultsAdapter(List<Result> results) {
        this.mResults = results;
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
        viewHolder.mApplicantName.setText(mResults.get(i).getApplicantName());
        viewHolder.mTestName.setText(mResults.get(i).getTestName());
        String result = String.valueOf(mResults.get(i).getScore()) + "%";
        viewHolder.mResult.setText(result);
        viewHolder.mDate.setText(mResults.get(i).getDate());
        viewHolder.mResultProgress.setProgress((int)mResults.get(i).getScore());
        String timeSpent = Integer.toString(mResults.get(i).getTimeSpent()) + " "
                + viewHolder.itemView.getContext().getResources().getString(R.string.minutes);
        viewHolder.mTimeSpent.setText(timeSpent);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void removeResult(int position){
        mResults.remove(position);
        notifyItemRemoved(position);
    }

    public Result getItem(int position) {
        return mResults.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mApplicantName, mTestName, mDate, mResult, mTimeSpent;
        ProgressBar mResultProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mApplicantName = itemView.findViewById(R.id.applicant_name_tv);
            mTestName = itemView.findViewById(R.id.test_name_tv);
            mDate = itemView.findViewById(R.id.date_tv);
            mResult = itemView.findViewById(R.id.result_tv);
            mResultProgress = itemView.findViewById(R.id.result_progress);
            mTimeSpent = itemView.findViewById(R.id.time_spent_tv);
        }
    }
}
