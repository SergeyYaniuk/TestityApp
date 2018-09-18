package com.sergeyyaniuk.testity.ui.login;

import com.sergeyyaniuk.testity.ui.base.BasePresenter;

public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V>
        implements LoginContract.Presenter<V>  {

    private LoginActivity mActivity;
}
