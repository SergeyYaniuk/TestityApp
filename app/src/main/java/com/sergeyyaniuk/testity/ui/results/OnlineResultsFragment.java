package com.sergeyyaniuk.testity.ui.results;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.di.module.OnlineResultsModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OnlineResultsFragment extends Fragment {

    @BindView(R.id.online_results_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading_layout)
    LinearLayout mLoadingLayout;

    @Inject
    OnlineResultsPresenter mPresenter;

    OnlineResultsListener mListener;
    OnlineResultsAdapter mAdapter;
    private Unbinder unbinder;

    public interface OnlineResultsListener{
        void onOnlineResultDelete(String resultId, int position);
    }

    public OnlineResultsFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_online_results, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent()
                .create(new OnlineResultsModule(this)).inject(this);
        mPresenter.onCreate();
        mPresenter.loadResults();
        return view;
    }

    public void setRecyclerView(List<Result> results) throws NullPointerException {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mAdapter = new OnlineResultsAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
        enableSwipe();
    }

    private void enableSwipe(){
        ResultsSwipeCallback resultsSwipeCallback = new ResultsSwipeCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                String resultId = mAdapter.getItem(position).getId();
                mListener.onOnlineResultDelete(resultId, position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(resultsSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void hideLoadingLayout(){
        mLoadingLayout.setVisibility(View.GONE);
    }

    public void showLoadingLayout(){
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    public void removeResult(int position){
        mAdapter.removeResult(position);
    }

    public void notifyAdapterAboutChanges(){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnlineResultsListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.onDestroy();
    }
}
