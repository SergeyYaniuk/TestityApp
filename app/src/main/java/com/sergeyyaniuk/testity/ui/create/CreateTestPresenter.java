package com.sergeyyaniuk.testity.ui.create;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CreateTestPresenter extends BasePresenter implements CreatePresenterContract{

    public static final String TAG = "MyLog";

    private CreateTestActivity mActivity;
    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;

    List<Question> mQuestionList = new ArrayList<>();
    List<Answer> mAnswerList = new ArrayList<>();
    Test mTest;
    Question mQuestion;

    public CreateTestPresenter(CreateTestActivity activity, DatabaseManager database, Firestore firestore,
                               PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public String getCurrentUserId(){
        return mPrefHelper.getCurrentUserId();
    }

    public String getCurrentTestId(){
        return mPrefHelper.getCurrentTestId();
    }

    public void addTest(String title, String category, String language, boolean isOnline,
                        String description){
        String userId = getCurrentUserId();
        @SuppressLint("SimpleDateFormat")
        String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String testId = userId + currentDateAndTime;
        Test test = new Test(testId, title, category, language, description,
                isOnline, 0, userId, 0);
        mPrefHelper.setCurrentTestId(testId);
        addTestToDatabase(test);
        if (isOnline){
            mFirestore.addTest(test).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: add test");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: add test");
                }
            });
        }
    }

    public void addTestToDatabase(Test test){
        Log.d(TAG, "addTest: start");
        getCompositeDisposable().add(mDatabase.insertTest(test)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                            Log.d(TAG, "addTest: success");
                        },
                        throwable -> {
                            Log.d(TAG, "addTest: throwable");
                        }));
    }

    @Override
    public Test loadTest(String testId){
        getCompositeDisposable().add(mDatabase.getTest(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(test -> {
                            Log.d(TAG, "loadTest: Success");
                            mTest = test;

                        }
                , throwable -> {
                            Log.d(TAG, "loadTest: Error");
                        }));
        return mTest;
    }

    public void updateTest(Test test, boolean isTestOnline){
        getCompositeDisposable().add(mDatabase.updateTest(test)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    Log.d(TAG, "updateTest: Success! add info to test");
                }, throwable -> {
                    Log.d(TAG, "updateTest: Error! add info to test ");
                }));
        if (isTestOnline){
            mFirestore.updateTest(test).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: update test successfuly");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Error! Update test");
                }
            });
        }
    }

    //method is invoked after user save addEditFragment
    public void updateTestAfterEditing(String testId, Question question, boolean isUpdating,
                           List<Answer> answers, boolean isTestOnline){
        Test test = loadTest(testId);
        //if user updated exist question and answers
        if (isUpdating){
            List<Answer> answersOld = loadAnswers(question.getId());
            int numberOfCorrectWas = 0;
            for (Answer answer : answersOld){
                if (answer.isCorrect()){ numberOfCorrectWas++; }
            }
            int numberOfCorrectBecome = 0;
            for (Answer answer : answers){
                if (answer.isCorrect()){ numberOfCorrectBecome++; }
            }
            test.setNumberOfCorrectAnswers(
                    (test.getNumberOfCorrectAnswers() - numberOfCorrectWas) + numberOfCorrectBecome);
        } else{
            //if user created new question and answers
            test.setNumberOfQuestions(test.getNumberOfQuestions() + 1);
            for (Answer answer : answers){
                if (answer.isCorrect()){
                    test.setNumberOfCorrectAnswers(test.getNumberOfCorrectAnswers() + 1);
                }
            }
        }
        updateTest(test, isTestOnline);
    }

    //method is invoked after user swipe to delete in QuestionListFragment
    public void updateTestAfterSwipe(String questionId, String testId, boolean isTestOnline){
        Test test = loadTest(testId);
        List<Answer> answerList = loadAnswers(questionId);
        int numberOfCorrectAnswers = 0;
        for (Answer answer : answerList){
            if (answer.isCorrect()){
                numberOfCorrectAnswers++;
            }
        }
        test.setNumberOfQuestions(test.getNumberOfQuestions() - 1);
        test.setNumberOfCorrectAnswers(test.getNumberOfCorrectAnswers() - numberOfCorrectAnswers);
        updateTest(test, isTestOnline);
    }

    @Override
    public List<Question> loadQuestions(String testId){
        getCompositeDisposable().add(mDatabase.getQuestionList(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        questions -> {
                            mQuestionList = questions;
                            Log.d(TAG, "loadQuestions: success");},
                        throwable -> {
                            Log.d(TAG, "loadQuestions: error");
                        }));
        return mQuestionList;
    }

    @Override
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

    public void saveQuestion(Question question, boolean isOnline){
        getCompositeDisposable().add(mDatabase.insertQuestion(question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {}, throwable -> {}));
        if (isOnline){
            mFirestore.addQuestion(question).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: save question");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: save question");
                }
            });
        }
    }

    public void updateQuestion(Question question, boolean isOnline){
        getCompositeDisposable().add(mDatabase.updateQuestion(question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    Log.d(TAG, "updateQuestion: Success");
                }, throwable -> {
                    Log.d(TAG, "updateQuestion: Error");
                }));
        if (isOnline){
            mFirestore.updateQuestion(question).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: update Question in Firestore");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: update Question in Firestore");
                }
            });
        }
    }

    public void deleteQuestion(String questionId, boolean isOnline){
        getCompositeDisposable().add(mDatabase.deleteQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {}, throwable -> {}));
        if (isOnline){
            mFirestore.deleteQuestion(questionId).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: delete question");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: delete question");

                }
            });
        }
    }

    public void saveAnswerList(List<Answer> answers, boolean isOnline){
        getCompositeDisposable().add(mDatabase.insertAnswerList(answers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {}, throwable -> {}));
        if (isOnline){
            mFirestore.addAnswerList(answers).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "onComplete: success");
                }
            });
        }
    }

    public void updateAnswerList(List<Answer> answers, boolean isOnline){
        getCompositeDisposable().add(mDatabase.updateAnswerList(answers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    Log.d(TAG, "updateAnswerList: Success");
                }, throwable -> {
                    Log.d(TAG, "updateAnswerList: Error");
                }));
        if (isOnline){
            mFirestore.updateAnswerList(answers).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "onComplete: Success");
                }
            });
        }
    }

    @Override
    public List<Answer> loadAnswers(String questionId) {
        getCompositeDisposable().add(mDatabase.getAnswerList(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answers -> {
                    mAnswerList = answers;
                }, throwable -> {
                    Log.d(TAG, "loadAnswers: error");
                }));
        return mAnswerList;
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
                    Log.d(TAG, "onComplete: success");
                }
            });
        }
    }
}
