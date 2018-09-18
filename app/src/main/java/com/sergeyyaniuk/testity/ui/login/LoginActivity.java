package com.sergeyyaniuk.testity.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.component.LoginActivityComponent;
import com.sergeyyaniuk.testity.di.module.LoginActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;


public class LoginActivity extends BaseActivity {

    public static final int REQUEST_SIGN_GOOGLE = 9001;

    @Inject
    LoginPresenter mPresenter;

    @BindView(R.id.text_view)
    TextView textView;

    @BindView(R.id.google_button)
    SignInButton googleButton;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.get(this).getAppComponent().createLoginComponent(new LoginActivityModule(this)).inject(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.google_button)
    public void onGoogleButton(){
        Intent intent = mPresenter.loginWithGoogle();
        startActivityForResult(intent, REQUEST_SIGN_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //google
        if (requestCode == REQUEST_SIGN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mPresenter.getAuthWithGoogle(result);
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
        textView.setText(currentUser.getDisplayName());
    }
}
