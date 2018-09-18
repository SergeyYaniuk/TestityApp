package com.sergeyyaniuk.testity.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.sergeyyaniuk.testity.R;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgressDialog(){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hidePregressDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view){
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void onStop() {
        super.onStop();
        hidePregressDialog();
    }
}
