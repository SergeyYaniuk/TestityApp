package com.sergeyyaniuk.testity.ui.createTest.questions;

import android.util.Log;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailQuestionPresenter extends BasePresenter {

    private static final String TAG = "MyLog";

    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;
    private DetailQuestionFragment mFragment;

    List<Answer> mAnswerList;
    Question mQuestion;

    public DetailQuestionPresenter(DetailQuestionFragment fragment, DatabaseManager database,
                                  Firestore firestore, PrefHelper prefHelper) {
        this.mFragment = fragment;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public String getTestId(){
        return mPrefHelper.getCurrentTestId();
    }

    public Question loadQuestion(String questionId) {
        getCompositeDisposable().add(mDatabase.getQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(question -> {
                    mQuestion = question;
                }, throwable -> {

                }));
        return mQuestion;
    }

    public List<Answer> loadAnswers(String questionId) {
        mAnswerList = new ArrayList<>();
        getCompositeDisposable().add(mDatabase.getAnswerList(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answers -> {
                    mAnswerList.addAll(answers);
                }, throwable -> {
                    Log.d(TAG, "loadAnswers: error");
                }));
        return mAnswerList;
    }
}
