package com.sergeyyaniuk.testity.ui.tests.editTest;

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

public class EditDeleteQuesDialog extends BaseDialogNoTitle{

    private Unbinder unbinder;
    EditDeleteQuesListener mListener;

    String mQuestionId;
    int mPosition;

    public interface EditDeleteQuesListener {
        void onConfirmDelete(String questionId, int position);
        void onCancelDelete();
    }

    public EditDeleteQuesDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete_question, container);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        if (arguments != null){
            mQuestionId = arguments.getString(EditTestActivity.QUESTION_ID);
            mPosition = arguments.getInt(EditTestActivity.QUES_POSITION);
        }
        return view;
    }

    @OnClick(R.id.ok_button)
    public void onOkDelete(){
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
        mListener = (EditDeleteQuesListener) context;
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
