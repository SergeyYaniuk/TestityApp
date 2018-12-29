package com.sergeyyaniuk.testity.ui.tests.passTest.passTest;

import android.annotation.SuppressLint;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PassTestPresenter extends BasePresenter {

    private PassTestActivity mActivity;
    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;

    public PassTestPresenter(PassTestActivity activity, DatabaseManager database,
                             Firestore firestore, PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public int getNumberOfCorrect(){
        return mPrefHelper.getNumOfCorAnsw();
    }

    public void cleanTotalCorr(){
        mPrefHelper.cleanNumOfCorAnsw();
    }

    public void loadQuestions(String testId){
        getCompositeDisposable().add(mDatabase.getQuestionList(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        questions -> {
                            mActivity.setQuestionList(questions);
                            getNumberOfCorrectAnswers(questions);
                        }, throwable -> { }));
    }

    public void loadAnswers(String questionId) {
        getCompositeDisposable().add(mDatabase.getAnswerList(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answers -> {
                    mActivity.updateAnswers(answers);
                }, throwable -> {

                }));
    }

    public void getNumberOfCorrectAnswers(List<Question> questions){
        for (Question question : questions){
            String questionId = question.getId();
            getCompositeDisposable().add(mDatabase.getAnswerList(questionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(answers -> {
                        for (Answer answer : answers){
                            if (answer.isCorrect()){
                                mPrefHelper.addCorrAnswer();
                            }
                        }
                    }, throwable -> {

                    }));
        }
    }

    public void saveResult(String testId, String applicantName,String testTitle, double score){
        String userId = mPrefHelper.getCurrentUserId();
        String date = getCurrentDate();
        String resultId = testId + userId + getCurrentTime();
        Result result = new Result(resultId, testId, applicantName, score, userId, testTitle, date);
        getCompositeDisposable().add(mDatabase.insertResult(result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                }, throwable -> {
                }));
    }

    @SuppressLint("SimpleDateFormat")
    private String getCurrentDate(){
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    private String getCurrentTime(){
        return new SimpleDateFormat("yyMMddHHmmss").format(new Date());
    }
}
