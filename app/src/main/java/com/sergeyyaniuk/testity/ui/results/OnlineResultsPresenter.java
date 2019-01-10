package com.sergeyyaniuk.testity.ui.results;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

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

    public void loadResults(){
        mFragment.showLoadingLayout();
        String userId = mPrefHelper.getCurrentUserId();
        mFirestore.getUserResults(userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Result> results = new ArrayList<>();
                for (DocumentSnapshot document : queryDocumentSnapshots){
                    Result result = new Result();
                    result.setId(document.getString("id"));
                    result.setUserId(document.getString("userId"));
                    result.setApplicantName(document.getString("applicantName"));
                    result.setScore(document.getDouble("score"));
                    result.setTestId(document.getString("testId"));
                    result.setUserName(document.getString("userName"));
                    result.setDate(document.getString("date"));
                    result.setTestName(document.getString("testName"));
                    int timeSpent = document.getLong("timeSpent").intValue();
                    result.setTimeSpent(timeSpent);
                    results.add(result);
                }
                mFragment.setRecyclerView(results);
                mFragment.hideLoadingLayout();
            }
        });
    }
}
