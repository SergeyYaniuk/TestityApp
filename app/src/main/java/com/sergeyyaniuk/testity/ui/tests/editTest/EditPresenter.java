package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.util.Log;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

public class EditPresenter extends BasePresenter {

    private static final String TAG = "MyLog";

    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;
    private EditTestActivity mActivity;

    public EditPresenter(EditTestActivity activity, DatabaseManager database,
                              Firestore firestore, PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public void updateTest(Test test){
        Log.d(TAG, "updateTest: " + test.getId());
        Log.d(TAG, "updateTest: " + test.getTitle());
        Log.d(TAG, "updateTest: " + test.getCategory());
        Log.d(TAG, "updateTest: " + test.getLanguage());
        Log.d(TAG, "updateTest: " + test.getDescription());
        Log.d(TAG, "updateTest: " + test.getNumberOfQuestions());
    }
}
