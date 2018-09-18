package com.sergeyyaniuk.testity.ui.base;

import android.view.View;

public interface BaseViewContract {
    void showProgressDialog();
    void hidePregressDialog();
    void hideKeyboard(View view);
}
