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

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlineResultsAdapter extends RecyclerView.Adapter<OnlineResultsAdapter.ViewHolder> {

    private List<Result> mResults;

    public OnlineResultsAdapter(List<Result> results) {
        this.mResults = results;
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
        viewHolder.mApplicantName.setText(mResults.get(i).getApplicantName());
        viewHolder.mTestName.setText(mResults.get(i).getTestName());
        String result = String.valueOf(mResults.get(i).getScore()) + "%";
        viewHolder.mResultTV.setText(result);
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

    static class ViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.applicant_name_tv)
        TextView mApplicantName;

        @BindView(R.id.test_name_tv)
        TextView mTestName;

        @BindView(R.id.date_tv)
        TextView mDate;

        @BindView(R.id.result_tv)
        TextView mResultTV;

        @BindView(R.id.result_progress)
        ProgressBar mResultProgress;

        @BindView(R.id.time_spent_tv)
        TextView mTimeSpent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
