package com.sergeyyaniuk.testity.ui.login;

import android.content.Intent;
import android.support.annotation.NonNull;

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
import com.google.firebase.auth.FirebaseUser;
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
        mAuthentication.signOut(); //test
        if (result.isSuccess()){
            final GoogleSignInAccount account = result.getSignInAccount();
            mAuthentication.getAuthWithGoogle(mActivity, account)
                    .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser currentUser = mAuthentication.getCurrentUser();
                                mActivity.updateUI(currentUser);
                            }
                            else {
                                mActivity.updateUI(null);
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
        mAuthentication.signOut(); //test
        mAuthentication.getAuthWithFacebook(accessToken)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser currentUser = mAuthentication.getCurrentUser();
                            mActivity.updateUI(currentUser);
                        }
                        else {
                            mActivity.updateUI(null);
                        }
                    }
                });
    }

    //Auth with Email
    protected void loginWithEmail(final String email, final String password){
        if (!mActivity.validateForm()) {
            return;
        }
        mActivity.showProgressDialog();
        mAuthentication.signOut(); //test
        mAuthentication.getUserWithEmail(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser currentUser = mAuthentication.getCurrentUser();
                            mActivity.updateUI(currentUser);
                        }
                        else {
                            mActivity.updateUI(null);
                        }
                    }
                });
    }

    //create account
    protected void createAccount(String email, String password){
        mActivity.showProgressDialog();
        mAuthentication.signOut(); //test
        mAuthentication.createUserWithEmail(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser currentUser = mAuthentication.getCurrentUser();
                            mActivity.updateUI(currentUser);
                        }
                        else {
                            mActivity.updateUI(null);
                        }
                    }
                });
    }

    //send new password
    protected void sendEmailReset(String email){
        mAuthentication.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mActivity.emailSent();
                        } else {
                            mActivity.cannotSendEmail();
                        }
                    }
                });
    }

    public boolean userExist(){
        FirebaseUser existUser = mAuthentication.getCurrentUser();
        return existUser != null;
    }
}
