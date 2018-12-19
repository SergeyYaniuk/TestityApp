package com.sergeyyaniuk.testity.ui.results;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocalResultsPresenter extends BasePresenter {

    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;
    private LocalResultsFragment mFragment;

    List<Result> mResults = new ArrayList<>();

    public LocalResultsPresenter(LocalResultsFragment fragment, DatabaseManager database,
                                  Firestore firestore, PrefHelper prefHelper) {
        this.mFragment = fragment;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public void loadResults(){
        String userId = mPrefHelper.getCurrentUserId();
        getCompositeDisposable().add(mDatabase.getTestList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tests -> {
                    for (Test test : tests){
                        getCompositeDisposable().add(mDatabase.getResultList(test.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(results -> {
                                    mResults.addAll(results);
                                    mFragment.setRecyclerView(mResults);
                                }, throwable -> {}));
                    }
                }, throwable -> {}));
    }
}
