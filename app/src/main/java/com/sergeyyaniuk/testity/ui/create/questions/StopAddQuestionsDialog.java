package com.sergeyyaniuk.testity.ui.create.questions;

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

public class StopAddQuestionsDialog extends BaseDialogNoTitle {

    private Unbinder unbinder;

    @BindView(R.id.ok_button)
    Button mOkButton;
    @BindView(R.id.cancel_button)
    Button mCancelButton;

    StopAddQuestionsListener mListener;


    public interface StopAddQuestionsListener {
        void onStopAddQuestions();
    }

    public StopAddQuestionsDialog(){
        //required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_stop_add_questions, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.ok_button)
    void onOkButton(){
        mListener.onStopAddQuestions();
        dismiss();

    }

    @OnClick(R.id.cancel_button)
    void onCancelButton(){
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (StopAddQuestionsListener) context;
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
