package com.sergeyyaniuk.testity.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sergeyyaniuk.testity.App;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.di.module.MainActivityModule;
import com.sergeyyaniuk.testity.ui.base.BaseActivity;
import com.sergeyyaniuk.testity.ui.create.CreateTestActivity;
import com.sergeyyaniuk.testity.ui.find.FindTestActivity;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;
import com.sergeyyaniuk.testity.ui.myTests.MyTestsActivity;
import com.sergeyyaniuk.testity.ui.results.ResultsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ExitDialogFragment.ExitDialogListener,
        GoogleApiClient.OnConnectionFailedListener{

    private static final int REQUEST_INVITE = 0;
    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_icon)
    ImageView mUserPhoto;

    @BindView(R.id.create_test_button)
    LinearLayout mCreateTest;
    @BindView(R.id.my_tests_button)
    LinearLayout mMyTests;
    @BindView(R.id.results_button)
    LinearLayout mResults;
    @BindView(R.id.find_test_button)
    LinearLayout mFindTest;
    @BindView(R.id.rateus_button)
    LinearLayout mRateUs;
    @BindView(R.id.invite_friend_button)
    LinearLayout mInviteFriend;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Dagger injection
        App.get(this).getAppComponent().createMainComponent(new MainActivityModule(this)).inject(this);
        //ButterKnife
        ButterKnife.bind(this);
        //set toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);  //remove label from toolbar
        //set user name in toolbar
        mUserName.setText(mPresenter.getCurrentUserName());
        mPresenter.getInvitation();  //get invitation if exist
    }

    @OnClick(R.id.create_test_button)
    public void onCreateTest(){
        startActivity(new Intent(MainActivity.this, CreateTestActivity.class));
    }

    @OnClick(R.id.my_tests_button)
    public void onMyTests(){
        startActivity(new Intent(MainActivity.this, MyTestsActivity.class));
    }

    @OnClick(R.id.results_button)
    public void onResults(){
        startActivity(new Intent(MainActivity.this, ResultsActivity.class));
    }

    @OnClick(R.id.find_test_button)
    public void onFindTest(){
        startActivity(new Intent(MainActivity.this, FindTestActivity.class));
    }

    @OnClick(R.id.invite_friend_button)
    public void inviteFriend(){
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                showToast(MainActivity.this, R.string.send_invitation_failed);

            }
        }
    }

    @OnClick(R.id.rateus_button)
    public void rateUs(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + this.getPackageName())));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                showSignOutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSignOutDialog(){
        FragmentManager fm = getSupportFragmentManager();
        ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
        exitDialogFragment.show(fm, "ExitDialogFragment");
    }

    //reaction on positive button in ExitDialogFragment
    @Override
    public void exit() {
        mPresenter.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showToast(MainActivity.this, R.string.google_play_services_error);
    }
}
