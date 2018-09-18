package com.sergeyyaniuk.testity.ui.login;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;


public class LoginPresenter extends BasePresenter{

    private LoginActivity mActivity;
    private Authentication mAuthentication;

    public LoginPresenter(LoginActivity activity, Authentication authentication){
        this.mActivity = activity;
        this.mAuthentication = authentication;
    }

    //Auth with Google
    protected Intent loginWithGoogle(){
        return mAuthentication.getUserWithGoogle(mActivity);
    }

    protected void getAuthWithGoogle(GoogleSignInResult result){
        mActivity.showProgressDialog();
        if (result.isSuccess()){
            final GoogleSignInAccount account = result.getSignInAccount();
            mAuthentication.getAuthWithGoogle(mActivity, account)
                    .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                mActivity.hidePregressDialog();
                                mActivity.authSuccessful();
                            }
                            else{
                                mActivity.authFailed();
                            }
                        }
                    });
        }

    }
}
