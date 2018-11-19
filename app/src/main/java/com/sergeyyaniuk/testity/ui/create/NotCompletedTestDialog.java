package com.sergeyyaniuk.testity.ui.create;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseDialogNoTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NotCompletedTestDialog extends BaseDialogNoTitle {

    private Unbinder unbinder;
    NotCompletedTestListener mListener;

    @BindView(R.id.continue_button)
    Button mContinueButton;
    @BindView(R.id.create_new_button)
    Button mCreateNewButton;

    public interface NotCompletedTestListener{
        void onContinueEditTest();
        void onStartNewTest();
    }

    public NotCompletedTestDialog(){
        //required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_not_completed_test, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.continue_button)
    public void onContinueButton(){
        mListener.onContinueEditTest();
        dismiss();
    }

    @OnClick(R.id.create_new_button)
    public void onCreateNewButton(){
        mListener.onStartNewTest();
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (NotCompletedTestListener) context;
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
    }
}
