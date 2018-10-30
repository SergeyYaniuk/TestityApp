package com.sergeyyaniuk.testity.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;

public class MainPresenter extends BasePresenter {

    private MainActivity mActivity;
    private Authentication mAuthentication;
    private PrefHelper mPrefHelper;

    public MainPresenter(MainActivity activity, Authentication authentication, PrefHelper prefHelper){
        this.mActivity = activity;
        this.mAuthentication = authentication;
        this.mPrefHelper = prefHelper;
    }

    protected String getCurrentUserName(){
        return mPrefHelper.getCurrentUserName();
    }

    protected void signOut(){
        mPrefHelper.removeCurrentUserName();
        mAuthentication.signOut();
    }

    //get deep link
    public void getInvitation(){
        FirebaseDynamicLinks.getInstance().getDynamicLink(mActivity.getIntent())
                .addOnSuccessListener(mActivity, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData data) {
                        if (data == null) {
                            return;
                        }

                        // Get the deep link
                        Uri deepLink = data.getLink();

                        // Extract invite
                        FirebaseAppInvite invite = FirebaseAppInvite.getInvitation(data);
                        if (invite != null) {
                            String invitationId = invite.getInvitationId();
                        }

                        // Handle the deep link
                        if (deepLink != null) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setPackage(mActivity.getPackageName());
                            intent.setData(deepLink);

                            mActivity.startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(mActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}
