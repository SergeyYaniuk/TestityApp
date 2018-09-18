package com.sergeyyaniuk.testity.ui.login;

import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;


public class LoginPresenter extends BasePresenter{

    private LoginActivity mActivity;
    private Authentication mAuthentication;

    public LoginPresenter(LoginActivity activity, Authentication authentication){
        this.mActivity = activity;
        this.mAuthentication = authentication;
    }
}
