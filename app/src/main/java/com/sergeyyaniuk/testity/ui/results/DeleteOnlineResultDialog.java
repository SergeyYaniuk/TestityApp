package com.sergeyyaniuk.testity.ui.results;

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

public class DeleteOnlineResultDialog extends BaseDialogNoTitle {

    private Unbinder unbinder;
    DeleteOnlineResultListener mListener;

    String mResultId;
    int mPosition;

    public interface DeleteOnlineResultListener{
        void onConfDelOnlineResult(String resultId, int position);
        void onCancelDelOnlineResult();
    }

    public DeleteOnlineResultDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete_result, container);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        if (arguments != null){
            mResultId = arguments.getString(ResultsActivity.RESULT_ID);
            mPosition = arguments.getInt(ResultsActivity.POSITION);
        }
        return view;
    }

    @OnClick(R.id.ok_button)
    public void onOkDelete(){
        mListener.onConfDelOnlineResult(mResultId, mPosition);
        dismiss();
    }

    @OnClick(R.id.cancel_btn)
    public void onCancelDelete(){
        mListener.onCancelDelOnlineResult();
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DeleteOnlineResultListener) context;
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
