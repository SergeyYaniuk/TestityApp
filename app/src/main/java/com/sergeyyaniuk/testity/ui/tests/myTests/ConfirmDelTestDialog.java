package com.sergeyyaniuk.testity.ui.tests.myTests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseDialogNoTitle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfirmDelTestDialog extends BaseDialogNoTitle {

    private Unbinder unbinder;
    DeleteTestListener mListener;

    String mTestId;
    int mPosition;
    boolean isTestOnline;

    public interface DeleteTestListener{
        void onDeleteTest(String testId, int position, boolean isTestOnline);
        void onCancelDelete();
    }

    public ConfirmDelTestDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_del_test, container);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        if (arguments != null){
            mTestId = arguments.getString(MyTestsActivity.TEST_ID);
            mPosition = arguments.getInt(MyTestsActivity.POSITION);
            isTestOnline = arguments.getBoolean(MyTestsActivity.IS_TEST_ONLINE);
        }
        return view;
    }

    @OnClick(R.id.confirm_btn)
    public void onConfirmDelete(){
        mListener.onDeleteTest(mTestId, mPosition, isTestOnline);
        dismiss();
    }

    @OnClick(R.id.cancel_btn)
    public void onCancelDelete(){
        mListener.onCancelDelete();
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DeleteTestListener) context;
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
