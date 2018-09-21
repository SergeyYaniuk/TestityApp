package com.sergeyyaniuk.testity.ui.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.util.Arrays;


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

    //Auth with Facebook
    protected CallbackManager loginWithFacebook(){
        CallbackManager callbackManager = mAuthentication.getUserWithFacebook();
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                mActivity.authFailed();
            }

            @Override
            public void onError(FacebookException error) {
                mActivity.authFailed();
            }
        });
        return callbackManager;
    }

    protected void getAuthWithFacebook(final AccessToken accessToken) {
        mActivity.showProgressDialog();
        mAuthentication.getAuthWithFacebook(accessToken)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mActivity.hidePregressDialog();
                            mActivity.authSuccessful();
                        }
                        else{
                            mActivity.hidePregressDialog();
                            mActivity.authFailed();
                        }
                    }
                });
    }

    //Auth with Email
    protected void loginWithEmail(final String email, final String password){
        mActivity.showProgressDialog();
        mAuthentication.getUserWithEmail(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mActivity.hidePregressDialog();
                            mActivity.authSuccessful();
                        }
                        else{
                            mActivity.hidePregressDialog();
                            mActivity.authFailed();
                        }
                    }
                });
    }

    //create account
    protected void createAccount(String email, String password){
        mActivity.showProgressDialog();
        mAuthentication.createUserWithEmail(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mActivity.hidePregressDialog();
                            mActivity.authSuccessful();
                        }
                        else{
                            mActivity.hidePregressDialog();
                            mActivity.authFailed();
                        }
                    }
                });
    }
}
