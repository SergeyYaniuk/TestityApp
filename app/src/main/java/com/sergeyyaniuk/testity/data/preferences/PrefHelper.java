package com.sergeyyaniuk.testity.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {

    private static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    private static final String CURRENT_USER_NAME = "CURRENT_USER_NAME";
    private static final String CURRENT_USER_EMAIL = "CURRENT_USER_EMAIL";

    private static final String CURRENT_TEST_ID = "CURRENT_TEST_ID";

    private static final String NUM_OF_COR_ANS = "NUM_OF_COR_ANS";
    private final SharedPreferences mPrefs;

    public PrefHelper(Context context, String prefFileName){
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    public void setCurrentUserId(String userId){
        mPrefs.edit().putString(CURRENT_USER_ID, userId).apply();
    }

    public String getCurrentUserId(){
        return mPrefs.getString(CURRENT_USER_ID, null);
    }

    public void removeCurrentUserId(){
        mPrefs.edit().remove(CURRENT_USER_ID).apply();
    }

    public void setCurrentUserName(String userName){
        mPrefs.edit().putString(CURRENT_USER_NAME, userName).apply();
    }

    public String getCurrentUserName(){
        return mPrefs.getString(CURRENT_USER_NAME, null);
    }

    public void removeCurrentUserName(){
        mPrefs.edit().remove(CURRENT_USER_NAME).apply();
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

    public void setCurrentTestId(String testId){
        mPrefs.edit().putString(CURRENT_TEST_ID, testId).apply();
    }

    public String getCurrentTestId(){
        return mPrefs.getString(CURRENT_TEST_ID, null);
    }

    public void setNumOfCorAnsw(int number){
        mPrefs.edit().putInt(NUM_OF_COR_ANS, number).apply();
    }

    public int getNumOfCorAnsw(){
        return mPrefs.getInt(NUM_OF_COR_ANS, -1);
    }

    public void cleanNumOfCorAnsw(){
        mPrefs.edit().remove(NUM_OF_COR_ANS).apply();
    }
}
