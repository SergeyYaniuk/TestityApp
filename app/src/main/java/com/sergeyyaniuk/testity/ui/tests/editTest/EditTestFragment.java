package com.sergeyyaniuk.testity.ui.tests.editTest;

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
import com.sergeyyaniuk.testity.di.module.EditTestFragModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditTestFragment extends Fragment {

    @Inject
    EditTestFragPresenter mPresenter;

    private Unbinder unbinder;
    EditTestFragListener mListener;

    public interface EditTestFragListener{

    }

    public EditTestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_test, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent().create(new EditTestFragModule(this))
                .inject(this);
        mPresenter.onCreate();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (EditTestFragListener) context;
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
