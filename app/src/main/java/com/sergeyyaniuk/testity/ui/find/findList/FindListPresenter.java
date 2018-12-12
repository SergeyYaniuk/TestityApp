package com.sergeyyaniuk.testity.ui.find.findList;

import com.google.firebase.firestore.Query;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

public class FindListPresenter extends BasePresenter {

    private FindListActivity mActivity;
    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;

    public FindListPresenter(FindListActivity activity, DatabaseManager database, Firestore firestore,
                             PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public Query getTestList(){
        return mFirestore.get50Tests();
    }
}
