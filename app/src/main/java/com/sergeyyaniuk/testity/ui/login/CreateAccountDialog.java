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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAccountDialog extends DialogFragment{

    @BindView(R.id.email_edit_text)
    EditText mEmailEditText;
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.ok_button)
    Button mOkButton;
    @BindView(R.id.cancel_button)
    Button mCancelButton;


    EditDialogListener mListener;

    public interface EditDialogListener{
        void onEdit(String email, String password);
    }

    public CreateAccountDialog(){
        //required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_account, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.ok_button)
    void positiveButton(){
        if (!validateForm()) {
            return;
        }
        mListener.onEdit(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        CreateAccountDialog.this.getDialog().dismiss();
    }

    @OnClick(R.id.cancel_button)
    void negativeButton(){
        CreateAccountDialog.this.getDialog().dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (EditDialogListener)context;
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError("Required.");
            valid = false;
        } else {
            mEmailEditText.setError(null);
        }

        String password = mPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError("Required.");
            valid = false;
        } else {
            mPasswordEditText.setError(null);
        }

        return valid;
    }
}