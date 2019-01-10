package com.sergeyyaniuk.testity.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.internal.CallbackManagerImpl;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.LoginModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements CreateAccountDialog.CreateDialogListener,
        ForgotPasswordDialog.ForgotDialogListener{

    public static final int REQUEST_SIGN_GOOGLE = 9001;
    
    public static final String TAG = "MyLog";

    @BindView(R.id.email_edit_text)
    EditText mEmailEditText;
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.forgot_password)
    TextView mForgotTextView;
    @BindView(R.id.login_button)
    Button mLoginButton;
    @BindView(R.id.google_button)
    SignInButton mGoogleButton;
    @BindView(R.id.create_account_tv)
    TextView mCreateTextView;

    @Inject
    LoginPresenter mPresenter;

    // just for facebook login
    private CallbackManager mCallbackManager;

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    String mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.get(this).getAppComponent().create(new LoginModule(this)).inject(this);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        changeGoogleButtonBackground(mGoogleButton); //change google button image
        mCallbackManager = mPresenter.loginWithFacebook(); //initialization facebook
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter.isUserLogIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EMAIL, mEmail);
        outState.putString(PASSWORD, mPassword);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mEmailEditText.setText(mEmail);
        mPasswordEditText.setText(mPassword);
    }

    @OnClick(R.id.login_button)
    public void onLoginButton() {
        if (!validateForm()) {
            return;
        } else if (!mPresenter.isActiveNetwork(this)){
            showToast(this, R.string.no_connection);
        }
        mPresenter.loginWithEmail(mEmail, mPassword);
    }

    @OnClick(R.id.create_account_tv)
    public void onCreateButton(){
        showCreateDialog();
    }

    @OnClick(R.id.google_button)
    public void onGoogleButton(){
        if (mPresenter.isActiveNetwork(this)){
            Intent intent = mPresenter.loginWithGoogle();
            startActivityForResult(intent, REQUEST_SIGN_GOOGLE);
        } else {
            showToast(this, R.string.no_connection);
        }
    }

    @OnClick(R.id.facebookView)
    public void onFacebookButton(){
        if (mPresenter.isActiveNetwork(this)){
            com.facebook.login.widget.LoginButton button =
                    new com.facebook.login.widget.LoginButton(LoginActivity.this);
            button.performClick();
        } else {
            showToast(this, R.string.no_connection);
        }
    }

    @OnClick(R.id.forgot_password)
    public void onForgotButton(){
        showForgotDialog();
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

    //required not empty fields
    public boolean validateForm() {
        boolean valid = true;

        mEmail = mEmailEditText.getText().toString();
        if (TextUtils.isEmpty(mEmail)) {
            mEmailEditText.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            mEmailEditText.setError(null);
        }

        mPassword = mPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordEditText.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            mPasswordEditText.setError(null);
        }

        return valid;
    }

    public void showCreateDialog(){
        DialogFragment createAccountDialog = new CreateAccountDialog();
        //createAccountDialog.setCancelable(false);
        createAccountDialog.show(getSupportFragmentManager(), "createAccountDialog");
    }

    public void showForgotDialog(){
        DialogFragment forgotPasswordDialog = new ForgotPasswordDialog();
        forgotPasswordDialog.show(getSupportFragmentManager(), "forgotPasswordDialog");
    }

    //get email and password from dialog fragment
    @Override
    public void addNewUser(String name, String email, String password) {
        mPresenter.createAccount(name, email, password);
    }

    //get email from forgotPasswordDialog
    @Override
    public void sendPassword(String email) {
        mPresenter.sendEmailReset(email);
    }

    public void startIntent(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void changeGoogleButtonBackground(SignInButton signInButton){
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView){
                TextView tv = (TextView) v;
                tv.setText(" ");
                tv.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.google_login_icon));
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
