package com.sergeyyaniuk.testity.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {

    private static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    private static final String CURRENT_USER_NAME = "CURRENT_USER_NAME";
    private static final String CURRENT_TEST_ID = "CURRENT_TEST_ID";
    private static final String NUM_OF_COR_ANS = "NUM_OF_COR_ANS";
    public static final String TEST_START_TIME = "TEST_START_TIME";

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

    public void addCorrAnswer(){
        int currNumber = mPrefs.getInt(NUM_OF_COR_ANS, 0);
        mPrefs.edit().putInt(NUM_OF_COR_ANS, currNumber + 1).apply();
    }

    public void addCorrAnswer(int number){
        int currNumber = mPrefs.getInt(NUM_OF_COR_ANS, 0);
        mPrefs.edit().putInt(NUM_OF_COR_ANS, currNumber + number).apply();
    }

    public int getNumOfCorAnsw(){
        return mPrefs.getInt(NUM_OF_COR_ANS, 0);
    }

    public void cleanNumOfCorAnsw(){
        mPrefs.edit().remove(NUM_OF_COR_ANS).apply();
    }

    public void setStartTestTime(long time){
        mPrefs.edit().putLong(TEST_START_TIME, time).apply();
    }

    public long getStartTestTime(){
        return mPrefs.getLong(TEST_START_TIME, 0L);
    }
}
