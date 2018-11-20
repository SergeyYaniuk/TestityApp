package com.sergeyyaniuk.testity.data.database;

import android.util.Log;

import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DatabaseManager {

    public static final String TAG = "MyLog";

    private final TestityDatabase testityDatabase;

    public DatabaseManager(TestityDatabase testityDatabase) {
        this.testityDatabase = testityDatabase;
    }

    public Observable<Boolean> insertUser(final User user){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.userDao().insert(user);
                return true;
            }
        });
    }

    public Observable<Boolean> insertTest(final Test test){
        Log.d(TAG, "insertTest: start");
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Log.d(TAG, "call: before interface");
                testityDatabase.testDao().insert(test);
                Log.d(TAG, "call: after interface");
                return true;
            }
        });
    }

    public Observable<Question> getQuestion(final String questionId){
        return Observable.fromCallable(new Callable<Question>() {
            @Override
            public Question call() throws Exception {
                return testityDatabase.questionDao().getQuestionById(questionId);
            }
        });
    }

    public Observable<List<Question>> getQuestionList(final String testId){
        return Observable.fromCallable(new Callable<List<Question>>() {
            @Override
            public List<Question> call() throws Exception {
                return testityDatabase.questionDao().getQuestionsByTestId(testId);
            }
        });
    }

    public Observable<Boolean> insertQuestion(final Question question){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().insert(question);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteQuestion(final String questionId){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().deleteQuestionById(questionId);
                return true;
            }
        });
    }

    public Observable<Boolean> insertAnswers(final List<Answer> answers){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.answerDao().insertMany(answers);
                return true;
            }
        });
    }

}
