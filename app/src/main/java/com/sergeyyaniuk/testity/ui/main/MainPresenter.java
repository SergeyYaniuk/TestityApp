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
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;

public class MainPresenter {

    private MainActivity mActivity;
    private Authentication mAuthentication;

    public MainPresenter(MainActivity activity, Authentication authentication){
        this.mActivity = activity;
        this.mAuthentication = authentication;
    }

    protected void setUserName(TextView textView){
        String name = mAuthentication.getUserName();
        if (name != null){
            textView.setText(name);
        } else {
            textView.setText(R.string.anonymous);
        }
    }

    protected void setUserPhoto(ImageView imageView){
        Uri photo = mAuthentication.getUserPhoto();
        if (photo != null){
            imageView.setImageURI(null);
            imageView.setImageURI(photo);
        } else {
            imageView.setImageResource(R.drawable.user_icon);
        }
    }

    protected void signOut(){
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
