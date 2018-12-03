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
import com.sergeyyaniuk.testity.di.module.EditQuestionModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditQuestionFragment extends Fragment {

    @Inject
    EditQuestionPresenter mPresenter;

    private Unbinder unbinder;
    EditQuestionListener mListener;



    public interface EditQuestionListener{

    }

    public EditQuestionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getAppComponent().create(new EditQuestionModule(this))
                .inject(this);
        mPresenter.onCreate();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (EditQuestionListener) context;
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
