package com.sergeyyaniuk.testity.ui.results;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.OnlineResultsModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OnlineResultsFragment extends Fragment {

    private Unbinder unbinder;

    @Inject
    OnlineResultsPresenter mPresenter;

    OnlineResultsListener mListener;

    public interface OnlineResultsListener{
        void onOnlineResultDelete(String resultId);
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
        return view;
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
