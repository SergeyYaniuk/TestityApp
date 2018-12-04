package com.sergeyyaniuk.testity.ui.tests.editTest;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.sergeyyaniuk.testity.R;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    public void setTestId(String testId){
        mPrefHelper.setCurrentTestId(testId);
    }

    public void updateTest(Test test){
        getCompositeDisposable().add(mDatabase.updateTest(test)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mActivity.showEditListFragment();
                }, throwable -> {
                    mActivity.showToast(mActivity, R.string.cannot_update_test);
                }));
        if (test.isOnline()){
            mFirestore.updateTestEditFive(test).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }

    public void deleteQuestion(String questionId, boolean isOnline){
        getCompositeDisposable().add(mDatabase.deleteQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mActivity.showToast(mActivity, R.string.question_deleted);
                }, throwable -> {}));
        if (isOnline){
            mFirestore.deleteQuestion(questionId).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }

    public void deleteAnswerList(String questionId, boolean isOnline){
        getCompositeDisposable().add(mDatabase.deleteAnswerList(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {}, throwable -> {}));
        if (isOnline){
            mFirestore.deleteAnswerList(questionId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });
        }
    }

    public void getNumberOfQuestions(boolean isTestOnline, String testId) {
        getCompositeDisposable().add(mDatabase.getQuestionList(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(questions -> {
                            List<Question> questionList = new ArrayList<>(questions);
                            int number = questionList.size();
                            getNumberOfCorrectAnswers(testId, isTestOnline, number, questions); }
                        , throwable -> {}));
    }

    public void getNumberOfCorrectAnswers(String testId, boolean isTestOnline, int numberOfQuestion, List<Question> questions){
        for (Question question : questions){
            String questionId = question.getId();
            getCompositeDisposable().add(mDatabase.getAnswerList(questionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(answers -> {
                        int correctAnswer = 0;
                        for (Answer answer : answers){
                            if (answer.isCorrect()){
                                correctAnswer++;
                            }
                        }
                        loadDataToTest(testId, isTestOnline, numberOfQuestion, correctAnswer);

                    }, throwable -> {

                    }));
        }
    }

    public void loadDataToTest(String testId, boolean isTestOnline, int numberOfQuestions, int numberOfCorr){
        getCompositeDisposable().add(mDatabase.getTest(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(test -> {
                    Test mTest = test;
                    mTest.setNumberOfQuestions(numberOfQuestions);
                    mTest.setNumberOfCorrectAnswers(numberOfCorr);
                    updateTest(isTestOnline, mTest);
                }, throwable -> {}));
    }

    public void updateTest(boolean isTestOnline, Test test){
        getCompositeDisposable().add(mDatabase.updateTest(test)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                            mActivity.startMyTestsActivity();
                        }, throwable -> {}
                ));
        if (isTestOnline){
            mFirestore.updateTestAddTwo(test).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }
}
