package com.sergeyyaniuk.testity.ui.main;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.firebase.Authentication;

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
            imageView.setImageURI(photo);
        } else {
            imageView.setImageResource(R.drawable.user_icon);
        }
    }

    protected void signOut(){
        mAuthentication.signOut();
    }


}
