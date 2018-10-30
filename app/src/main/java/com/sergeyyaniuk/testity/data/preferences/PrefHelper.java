package com.sergeyyaniuk.testity.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.sergeyyaniuk.testity.util.Constants;

public class PrefHelper {

    private static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    private static final String CURRENT_USER_NAME = "CURRENT_USER_NAME";
    private static final String CURRENT_USER_EMAIL = "CURRENT_USER_EMAIL";

    private final SharedPreferences mPrefs;

    public PrefHelper(Context context, String prefFileName){
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    public void setCurrentUserId(Long userId){
        long id = userId == null ? Constants.NULL_INDEX : userId;
        mPrefs.edit().putLong(CURRENT_USER_ID, id).apply();
    }

    public Long getCurrentUserId(){
        long userId = mPrefs.getLong(CURRENT_USER_ID, Constants.NULL_INDEX);
        return userId == Constants.NULL_INDEX ? null : userId;
    }

    public void removeCurrentUserId(){
        mPrefs.edit().remove(CURRENT_USER_ID).apply();
    }

    public void setCurrentUserName(String userName){
        mPrefs.edit().putString(CURRENT_USER_NAME, userName).apply();
    }

    public void removeCurrentUserName(){
        mPrefs.edit().remove(CURRENT_USER_NAME).apply();
    }

    public String getCurrentUserName(){
        return mPrefs.getString(CURRENT_USER_NAME, null);
    }

    public void setCurrentUserEmail(String email){
        mPrefs.edit().putString(CURRENT_USER_EMAIL, email).apply();
    }

    public String getCurrentUserEmail(){
        return mPrefs.getString(CURRENT_USER_EMAIL, null);
    }

    public void removeCurrentUserEmail(){
        mPrefs.edit().remove(CURRENT_USER_EMAIL).apply();
    }

    public void clearSharedPreferences(){
        mPrefs.edit().clear().apply();
    }
}
