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

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.di.module.LocalResultsModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LocalResultsFragment extends Fragment {

    @Inject
    LocalResultsPresenter mPresenter;

    @BindView(R.id.local_results_recycler)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    LocalResultsAdapter mAdapter;
    LocalResultsListener mListener;

    public interface LocalResultsListener{
        void onLocalResultDeleted(String resultId, int position);
    }

    public LocalResultsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_local_results, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent()
                .create(new LocalResultsModule(this)).inject(this);
        mPresenter.onCreate();
        mPresenter.loadResults();
        return view;
    }

    public void setRecyclerView(List<Result> results){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        mAdapter = new LocalResultsAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
        enableSwipe();
    }

    private void enableSwipe(){
        ResultsSwipeCallback resultsSwipeCallback = new ResultsSwipeCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                String resultId = mAdapter.getItem(position).getId();
                mListener.onLocalResultDeleted(resultId, position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(resultsSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
        mListener = (LocalResultsListener) context;
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
