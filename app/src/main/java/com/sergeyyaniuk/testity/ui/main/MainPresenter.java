package com.sergeyyaniuk.testity.ui.main;

import com.sergeyyaniuk.testity.firebase.Authentication;

public class MainPresenter {

    private MainActivity mActivity;
    private Authentication mAuthentication;

    public MainPresenter(MainActivity activity, Authentication authentication){
        this.mActivity = activity;
        this.mAuthentication = authentication;
    }

    protected String provideUserName(){
        return mAuthentication.getUserName();
    }


}
