package com.sergeyyaniuk.testity.ui.base;

import android.view.View;

public interface BaseView<T> {

    void showProgressDialog();
    void hidePregressDialog();
    void hideKeyboard(View view);
}
