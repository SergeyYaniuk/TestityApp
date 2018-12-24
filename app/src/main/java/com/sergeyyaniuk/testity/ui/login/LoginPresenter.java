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
import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.User;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import org.json.JSONObject;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter extends BasePresenter{

    private LoginActivity mActivity;
    private Authentication mAuthentication;
    private DatabaseManager mDatabaseManager;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;

    public LoginPresenter(LoginActivity activity, Authentication authentication, DatabaseManager databaseManager,
                          Firestore firestore, PrefHelper prefHelper){
        this.mActivity = activity;
        this.mAuthentication = authentication;
        this.mDatabaseManager = databaseManager;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
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
                                saveUser("google", null);
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
                    saveUser("facebook", null);
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
                            saveUser("login", null);
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

    //create account
    protected void createAccount(final String name, final String email, final String password){
        mActivity.showProgressDialog();
        mAuthentication.createUserWithEmail(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            setUserName(name);
                        }
                        else {
                            mActivity.hideProgressDialog();
                            mActivity.showToast(mActivity, R.string.cannot_create_acc);
                        }
                    }
                });
    }

    protected void setUserName(String name) {
        mAuthentication.setUserName(name).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    saveUser("create account", name);
                    mActivity.hideProgressDialog();
                    mActivity.showToast(mActivity, R.string.auth_successful);
                    mActivity.startIntent();
                } else {
                    String anonymous = getResources(mActivity, R.string.anonymous);
                    saveUser("create account", anonymous);
                    mActivity.hideProgressDialog();
                    mActivity.showToast(mActivity, R.string.unable_set_name);
                    mActivity.startIntent();
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

    private void saveUser(String loginWith, String userName){
        FirebaseUser firebaseUser = mAuthentication.getCurrentUser();
        String id = firebaseUser.getUid();
        String name = userName != null ? userName : firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        insertUserDataToPreferences(id, name);  //add user to SharedPreferences
        User user = new User(id, name, email, loginWith);
        insertUserDataToDatabase(user);  //add user to RoomDatabase
        mFirestore.addUser(user);  //add user to Firestore
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

    private void insertUserDataToPreferences(String id, String name){
        mPrefHelper.setCurrentUserName(name);
        mPrefHelper.setCurrentUserId(id);
    }

    private void insertUserDataToDatabase(User user){
        getCompositeDisposable().add(mDatabaseManager.insertUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                        },
                        throwable -> {
                    mAuthentication.signOut();
                        }));
    }
}
