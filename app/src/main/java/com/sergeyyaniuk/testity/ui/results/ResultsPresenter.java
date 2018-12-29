package com.sergeyyaniuk.testity.ui.results;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ResultsPresenter extends BasePresenter {

    private ResultsActivity mActivity;
    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;

    public ResultsPresenter(ResultsActivity activity, DatabaseManager database,
                              Firestore firestore, PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public void deleteLocalResult(String resultId){
        getCompositeDisposable().add(mDatabase.deleteResult(resultId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mActivity.showToast(mActivity, R.string.result_deleted);
                }, throwable -> {
                    mActivity.showToast(mActivity, R.string.unable_delete_result);
                }));
    }

    public void deleteOnlineResult(String resultId){
        mFirestore.deleteResult(resultId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mActivity.showToast(mActivity, R.string.result_deleted);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mActivity.showToast(mActivity, R.string.unable_delete_result);
            }
        });
    }
}
