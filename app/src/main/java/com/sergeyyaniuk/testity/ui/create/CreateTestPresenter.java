package com.sergeyyaniuk.testity.ui.create;

import android.util.Log;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;


public class CreateTestPresenter extends BasePresenter implements TestContract.UserActionListener{

    public static final String TAG = "MyLog";

    private CreateTestActivity mActivity;
    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;

    public CreateTestPresenter(CreateTestActivity activity, DatabaseManager database, Firestore firestore,
                               PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mFirestore = firestore;
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

//    public void addTest(Test test){
//        Log.d(TAG, "addTest: start");
//        getCompositeDisposable().add(mDatabase.insertTest(test)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(aBoolean -> {
//                            Log.d(TAG, "addTest: success");
//                        },
//                throwable -> {
//                    Log.d(TAG, "addTest: throwable");
//                }
//                ));
//    }

    public void addTestWithId(Test test){
        getCompositeDisposable().add(mDatabase.insertTestWithId(test)
                .subscribe(aLong -> {

                }, throwable -> {

                }));
    }

    @Override
    public Test loadTest(final long testId) {
        return new Test();
    }

//    public void loadQuestions(final long testId){
//        getCompositeDisposable().add(mDatabase.getQuestionsSorted(testId).subscribe(
//                questions -> {},
//                throwable -> {}
//                ));
//    }
}
