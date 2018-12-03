package com.sergeyyaniuk.testity.ui.tests.editTest;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditTestFragPresenter extends BasePresenter {

    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;
    private EditTestFragment mFragment;

    public EditTestFragPresenter(EditTestFragment fragment, DatabaseManager database,
                                  Firestore firestore, PrefHelper prefHelper) {
        this.mFragment = fragment;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public void loadTest(String testId){
        getCompositeDisposable().add(mDatabase.getTest(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(test -> {
                    mFragment.enterDataToLayout(test);
                }, throwable -> {}));
    }
}
