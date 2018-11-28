package com.sergeyyaniuk.testity.ui.createTest.questions;

import android.util.Log;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QuestionsListPresenter extends BasePresenter {

    private static final String TAG = "MyLog";

    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;
    private QuestionsListFragment mFragment;

    public QuestionsListPresenter(QuestionsListFragment fragment, DatabaseManager database,
                                  Firestore firestore, PrefHelper prefHelper) {
        this.mFragment = fragment;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public String getTestId(){
        return mPrefHelper.getCurrentTestId();
    }

    public void loadQuestions(){
        Log.d(TAG, "loadQuestions: QuestionsListPresenter");
        String testId = mPrefHelper.getCurrentTestId();
        getCompositeDisposable().add(mDatabase.getQuestionList(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        questions -> {
                            Log.d(TAG, "loadQuestions: success after mQuestionList");
                            mFragment.updateList(questions);
                            Log.d(TAG, "loadQuestions: success after updateList");
                        },
                        throwable -> {
                            Log.d(TAG, "loadQuestions: error");
                        }));
    }
}
