package com.sergeyyaniuk.testity.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.LoginActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    public static final int REQUEST_SIGN_GOOGLE = 9001;

    @BindView(R.id.email_edit_text)
    EditText mEmail;
    @BindView(R.id.password_edit_text)
    EditText mPassword;
    @BindView(R.id.forgot_password)
    TextView mForgotTextView;
    @BindView(R.id.login_button)
    Button mLoginButton;
    @BindView(R.id.google_button)
    SignInButton mGoogleButton;
    @BindView(R.id.facebook_button)
    LoginButton mFacebookButton;
    @BindView(R.id.create_account_tv)
    TextView mCreateTextView;

    @Inject
    LoginPresenter mPresenter;
    FirebaseAuth mAuth;
    // just for facebook login
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.get(this).getAppComponent().createLoginComponent(new LoginActivityModule(this)).inject(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.google_button)
    public void onLoginButton() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        mPresenter.loginWithEmail(email, password);
    }


    @OnClick(R.id.google_button)
    public void onGoogleButton(){
        Intent intent = mPresenter.loginWithGoogle();
        startActivityForResult(intent, REQUEST_SIGN_GOOGLE);
    }

    @OnClick(R.id.facebook_button)
    public void onFacebookButton(){
        mCallbackManager = mPresenter.loginWithFacebook();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //google
        if (requestCode == REQUEST_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mPresenter.getAuthWithGoogle(result);
        }

        //facebook
        else if(requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void authFailed(){
        Toast.makeText(LoginActivity.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }

    public void authSuccessful(){
        Toast.makeText(LoginActivity.this, "Authentication successful.",
                Toast.LENGTH_SHORT).show();
    }

    public void changeText(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
