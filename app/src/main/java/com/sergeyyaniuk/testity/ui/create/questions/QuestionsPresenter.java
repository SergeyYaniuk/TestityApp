package com.sergeyyaniuk.testity.ui.create.questions;

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

public class QuestionsPresenter extends BasePresenter {

    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;
    private QuestionsActivity mActivity;

    public QuestionsPresenter(QuestionsActivity activity, DatabaseManager database,
                              Firestore firestore, PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }
    
    public String getTestId(){
        return mPrefHelper.getCurrentTestId();
    }

    public void saveQuestion(Question question, boolean isOnline){
        getCompositeDisposable().add(mDatabase.insertQuestion(question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mActivity.showQuestionsListFragment();
                }, throwable -> {
                    mActivity.showToast(mActivity, R.string.cannot_add_question);
                }));
        if (isOnline){
            mFirestore.addQuestion(question).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void updateQuestion(Question question, boolean isOnline){
        getCompositeDisposable().add(mDatabase.updateQuestion(question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mActivity.showQuestionsListFragment();
                }, throwable -> {
                    mActivity.showToast(mActivity, R.string.cannot_add_question);
                }));
        if (isOnline){
            mFirestore.updateQuestion(question).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void saveAnswerList(List<Answer> answers, boolean isOnline){
        getCompositeDisposable().add(mDatabase.insertAnswerList(answers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                }, throwable -> {
                    mActivity.showToast(mActivity, R.string.cannot_add_answers);
                }));
        if (isOnline){
            mFirestore.addAnswerList(answers).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
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

    public void getNumberOfQuestions(final boolean isTestOnline) {
        String testId = getTestId();
        getCompositeDisposable().add(mDatabase.getQuestionList(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(questions -> {
                    List<Question> questionList = new ArrayList<>(questions);
                    int number = questionList.size();
                    loadDataToTest(isTestOnline, number);
                    }
                , throwable -> {}));
    }

    public void loadDataToTest(boolean isTestOnline, int numberOfQuestions){
        String testId = getTestId();
        getCompositeDisposable().add(mDatabase.getTest(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(test -> {
                    Test mTest = test;
                    mTest.setNumberOfQuestions(numberOfQuestions);
                    updateTest(isTestOnline, mTest);
                        }, throwable -> {}));
    }

    public void updateTest(boolean isTestOnline, Test test){
        getCompositeDisposable().add(mDatabase.updateTest(test)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    mActivity.startTestsActivity();
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
