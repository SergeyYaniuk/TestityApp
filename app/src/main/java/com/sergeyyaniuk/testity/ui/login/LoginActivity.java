package com.sergeyyaniuk.testity.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.LoginActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity implements CreateAccountDialog.CreateDialogListener,
        ForgotPasswordDialog.ForgotDialogListener{

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

    // just for facebook login
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.get(this).getAppComponent().createLoginComponent(new LoginActivityModule(this)).inject(this);
        ButterKnife.bind(this);
        changeGoogleButtonBackground(mGoogleButton); //change google button image
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter.userExist()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EMAIL, email);
        outState.putString(PASSWORD, password);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mEmail.setText(email);
        mPassword.setText(password);
    }

    @OnClick(R.id.login_button)
    public void onLoginButton() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        mPresenter.loginWithEmail(email, password);
    }

    @OnClick(R.id.create_account_tv)
    public void onCreateButton(){
        showCreateDialog();
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

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
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
    public void addNewUser(String email, String password) {
        mPresenter.createAccount(email, password);
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
                        R.drawable.google_plus));
                return;
            }
        }
    }

    @OnClick(R.id.facebookView)
    public void onFacebookClick(View v){
        if (v.getId() == R.id.facebookView){
            mFacebookButton.performClick();
        }
    }

    @OnTextChanged(R.id.email_edit_text)
    public void emailChanged(){
        email = mEmail.getText().toString();
    }

    @OnTextChanged(R.id.password_edit_text)
    public void passwordChanged(){
        password = mPassword.getText().toString();
    }
}
