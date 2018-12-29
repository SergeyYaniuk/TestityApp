package com.sergeyyaniuk.testity.ui.create.questions;

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

public class DeleteQuestionDialog extends BaseDialogNoTitle{

    private Unbinder unbinder;
    DeleteQuestionListener mListener;

    String mQuestionId;
    int mPosition;

    public interface DeleteQuestionListener {
        void onConfirmDelete(String questionId, int position);
        void onCancelDelete();
    }

    public DeleteQuestionDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete_question, container);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        if (arguments != null){
            mQuestionId = arguments.getString(QuestionsActivity.QUESTION_ID);
            mPosition = arguments.getInt(QuestionsActivity.QUES_POSITION);
        }
        return view;
    }

    @OnClick(R.id.confirm_btn)
    public void onConfirmDelete(){
        mListener.onConfirmDelete(mQuestionId, mPosition);
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
        mListener = (DeleteQuestionListener) context;
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
