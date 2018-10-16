package com.sergeyyaniuk.testity.ui.main;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sergeyyaniuk.testity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExitDialogFragment extends DialogFragment {

    @BindView(R.id.ok_button)
    Button mOkButton;
    @BindView(R.id.cancel_button)
    Button mCancelButton;

    ExitDialogListener mListener;


    public interface ExitDialogListener{
        void exit();
    }

    public ExitDialogFragment(){
        //required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_exit_fragment, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.ok_button)
    void positiveButton(){
        mListener.exit();
        ExitDialogFragment.this.getDialog().dismiss();
    }

    @OnClick(R.id.cancel_button)
    void negativeButton(){
        ExitDialogFragment.this.getDialog().dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (ExitDialogListener) context;
    }
}
