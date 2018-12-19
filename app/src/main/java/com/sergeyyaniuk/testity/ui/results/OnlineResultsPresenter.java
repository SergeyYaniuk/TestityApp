package com.sergeyyaniuk.testity.ui.results;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

public class OnlineResultsPresenter extends BasePresenter {

    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;
    private OnlineResultsFragment mFragment;

    public OnlineResultsPresenter(OnlineResultsFragment fragment, DatabaseManager database,
                                  Firestore firestore, PrefHelper prefHelper) {
        this.mFragment = fragment;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }
}
