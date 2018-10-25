package com.sergeyyaniuk.testity.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.ui.base.BaseDialogNoTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ForgotPasswordDialog extends BaseDialogNoTitle {

    private Unbinder unbinder;

    @BindView(R.id.email_edit_text)
    EditText mEmail;
    @BindView(R.id.ok_button)
    Button mOkButton;
    @BindView(R.id.cancel_button)
    Button mCancelButton;

    ForgotDialogListener mListener;

    public interface ForgotDialogListener{
        void sendPassword(String email);
    }

    //required empty constructor
    public ForgotPasswordDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_forgot_password, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.ok_button)
    void positiveButton(){
        if (!validateForm()) {
            return;
        }
        mListener.sendPassword(mEmail.getText().toString());
        ForgotPasswordDialog.this.getDialog().dismiss();
    }

    @OnClick(R.id.cancel_button)
    void negativeButton(){
        ForgotPasswordDialog.this.getDialog().dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (ForgotDialogListener) context;
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

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        return valid;
    }
}