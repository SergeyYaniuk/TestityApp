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

    ArrayList<Question> mQuestionList = new ArrayList<>();

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

    @Override
    public Test loadTest(String testId){
        return  new Test();
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
    public ArrayList<Question> loadQuestions(String testId){
        getCompositeDisposable().add(mDatabase.getQuestionList(testId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        questions -> {
                            mQuestionList.clear();
                            mQuestionList.addAll(questions);
                            Log.d(TAG, "loadQuestions: success");},
                        throwable -> {
                            Log.d(TAG, "loadQuestions: error");
                        }));
        return mQuestionList;
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
        getCompositeDisposable().add(mDatabase.insertAnswers(answers)
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
