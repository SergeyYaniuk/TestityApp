package com.sergeyyaniuk.testity.ui.create;

import android.util.Log;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;


public class CreateTestPresenter extends BasePresenter implements TestContract.UserActionListener{

    public static final String TAG = "MyLog";

    private CreateTestActivity mActivity;
    private DatabaseManager mDatabase;
    private PrefHelper mPrefHelper;

    public CreateTestPresenter(CreateTestActivity activity, DatabaseManager database, PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mPrefHelper = prefHelper;
    }

    public Long getCurrentUserId(){
        return mPrefHelper.getCurrentUserId();
    }

    public Long getCurrentTestId(){
        return mPrefHelper.getCurrentTestId();
    }

    public void setCurrentTestId(Long testId){
        mPrefHelper.setCurrentTestId(testId);
    }

    public void addTest(final Test test){
        getCompositeDisposable().add(mDatabase.insertTestWithId(test).subscribe(
                aLong -> {
                    Log.d(TAG, "addTest: success");
                    mPrefHelper.setCurrentTestId(aLong);
                },
                throwable -> {
                    Log.d(TAG, "addTest: throwable");
                }
                ));
    }

    @Override
    public Test loadTest(final long testId) {
        final Test[] loadedTest = {new Test()};
        getCompositeDisposable().add(mDatabase.getTestById(testId).subscribe(
                test -> {
                    Log.d(TAG, "loadTest: success");
                    loadedTest[0] = test;

                },
                throwable -> {
                    Log.d(TAG, "loadTest: exception");
                    //exception
                }
        ));
        return loadedTest[0];
    }

    public void loadQuestions(final long testId){
        getCompositeDisposable().add(mDatabase.getQuestionsSorted(testId).subscribe(
                questions -> {},
                throwable -> {}
                ));
    }
}
