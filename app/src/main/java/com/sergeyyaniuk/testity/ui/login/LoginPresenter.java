package com.sergeyyaniuk.testity.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.User;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter extends BasePresenter{
    
    private static final String TAG = "MyLog";

    private LoginActivity mActivity;
    private Authentication mAuthentication;
    private DatabaseManager mDatabaseManager;
    private PrefHelper mPrefHelper;

    public LoginPresenter(LoginActivity activity, Authentication authentication,
                          DatabaseManager databaseManager, PrefHelper prefHelper){
        this.mActivity = activity;
        this.mAuthentication = authentication;
        this.mDatabaseManager = databaseManager;
        this.mPrefHelper = prefHelper;
    }

    //____Methods for Firebase queries_____//

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
                                insertDataToPreferences();
                                mActivity.hideProgressDialog();
                                mActivity.showToast(mActivity, R.string.auth_successful);
                                mActivity.startIntent();
                            }
                            else {
                                mActivity.hideProgressDialog();
                                mActivity.showToast(mActivity, R.string.auth_failed);
                            }
                        }
                    });
        }

    }

    //Auth with Facebook
    protected CallbackManager loginWithFacebook(){
        CallbackManager callbackManager = mAuthentication.getCallbackManager();
        com.facebook.login.widget.LoginButton button =
                new com.facebook.login.widget.LoginButton(mActivity);
        button.setReadPermissions("email", "public_profile");
        button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return callbackManager;
    }

    protected void handleFacebookAccessToken(AccessToken token){
        mActivity.showProgressDialog();
        mAuthentication.getAuthWithFacebook(token)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    insertDataToPreferences();
                    mActivity.hideProgressDialog();
                    mActivity.showToast(mActivity, R.string.auth_successful);
                    mActivity.startIntent();
                } else {
                    mActivity.hideProgressDialog();
                    mActivity.showToast(mActivity, R.string.auth_failed);
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
                            insertDataToPreferences();
                            mActivity.hideProgressDialog();
                            mActivity.showToast(mActivity, R.string.auth_successful);
                            mActivity.startIntent();
                        }
                        else {
                            mActivity.hideProgressDialog();
                            mActivity.showToast(mActivity, R.string.auth_failed);
                        }
                    }
                });
    }

    //create account. Need to uncomment after version of database will change
    protected void createAccount(final String name, final String email, final String password){
        mActivity.showProgressDialog();
        mAuthentication.createUserWithEmail(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            insertDataToPreferences();
                            mActivity.hideProgressDialog();
                            mActivity.showToast(mActivity, R.string.auth_successful);
                            mActivity.startIntent();
                        }
                        else {
                            mActivity.hideProgressDialog();
                            mActivity.showToast(mActivity, R.string.auth_failed);
                        }
                    }
                });
    }

    //send new password
    protected void sendEmailReset(String email){
        mActivity.showProgressDialog();
        mAuthentication.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mActivity.hideProgressDialog();
                            mActivity.showToast(mActivity, R.string.pass_sent);
                        }
                        else {
                            mActivity.hideProgressDialog();
                            mActivity.showToast(mActivity, R.string.unable_send);
                        }
                    }
                });
    }

    private void insertDataToPreferences(){
        FirebaseUser user = mAuthentication.getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        Long id = 1L;   //need to be changed
        mPrefHelper.setCurrentUserName(name);
        mPrefHelper.setCurrentUserEmail(email);
        mPrefHelper.setCurrentUserId(id);
    }

    protected boolean isUserLogIn(){
        return mAuthentication.getCurrentUser() != null;
    }

    protected boolean isActiveNetwork(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    //____Methods for database queries_____//

    //need to be uncomment after database version will change
//    protected void insertNewUser(Long id, String name, String email, String password){
//        User user = new User(id, name, email, password);
//        getCompositeDisposable().add(mDatabaseManager.insertUser(user)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        aBoolean -> {
//                            //here should be code if user added to database successfully
//                        },
//                        throwable -> {
//                            //here should be exception
//                        }
//                        ));
//
//    }
//
//    protected boolean checkIfUserExist(String email){
//        boolean exist = false;
//        // here need to be method for find user by email and if user exist then true
//        return exist;
//    }
//
//    protected void loginWithDatabase(String email, String password){
//        //need to add implementation
//    }
}
